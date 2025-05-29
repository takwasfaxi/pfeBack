package com.example.user_service.Controllers;


import com.example.user_service.DTO.UserDTO;
import com.example.user_service.Entities.Mail;
import com.example.user_service.Entities.RefreshToken;
import com.example.user_service.Entities.UserEntity;
import com.example.user_service.Payload.Request.LoginRequest;
import com.example.user_service.Payload.Response.JwtResponse;
import com.example.user_service.Payload.Response.MessageResponse;
import com.example.user_service.Repositories.UserRepo;
import com.example.user_service.Services.RefreshTokenService;
import com.example.user_service.Services.StorageService;
import com.example.user_service.Services.UserDetailsImpl;
import com.example.user_service.Services.UserService;
import com.example.user_service.jwt.JwtUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.ws.rs.core.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import javax.validation.Valid;
import java.beans.Encoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private StorageService storageService;
    @Autowired
    //javaMailSender c'est une bibliotheque dans pom.xml
    private JavaMailSender JavaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private JavaMailSender javaMailSender;


    @PostMapping("/sendMail")
    public ResponseEntity sendMail(Mail mail) {
        try{
            //récupérer les données li hatinehom f postman
            String to  = mail.getTo();
            String subject = mail.getSubject();
            String content = mail.getContent();
            String from = mail.getFrom();
            //creation de message
            MimeMessage message = JavaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message); //MimeMessageHelper est une classe utilitaire de Spring qui simplifie la configuration d'un MimeMessage
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText("<html><body>"+content+"</br></body></html>", true);
            JavaMailSender.send(message);
            return ResponseEntity.ok("Please check your inbox");

        }catch (MessagingException e){
            //log the error and return a response with details
            logger.error("  Error sending email : " + e.getMessage() , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email :" + e.getMessage());
        } catch (Exception e) {
            //catch any other unexpected exceptions
            logger.error("Unexpected error  : " + e.getMessage() , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred :" + e.getMessage());
        }
    }
//l create khaliha entity aady mouch dto khater li bch ychoufou l creation houma project w task w li houma makhdoumin deja getall b dto
@PostMapping("/create")
public ResponseEntity<?> create(UserEntity user, @RequestParam("file") MultipartFile file, @RequestParam("file1") MultipartFile file1) throws MessagingException {
    Boolean exists = userRepo.existsByUsername(user.getUsername()) && userRepo.existsByEmail(user.getEmail());
    if (exists) {
        return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
    } else {

        String cryptedpassword = encoder.encode(user.getPassword());
        user.setPassword(cryptedpassword);

        String namePhoto = storageService.store(file);
        user.setPhoto(namePhoto);

        String nameCv = storageService.store(file1);
        user.setCv(nameCv);

        String from = "admin@gmail.com";
        String to = user.getEmail();
        String subject = "Success to create account";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(from);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText("<html><body><br><a href = http://localhost:8770/user/confirm?email=" + user.getEmail() + "> verify</br></body></html>", true);
        javaMailSender.send(message);

        UserEntity myuser = userService.create(user);
        myuser.setCv(nameCv);
        UserDTO userdto = new UserDTO().fromEntity(myuser);

        return ResponseEntity.ok("User created successfully");
    }
}
    //fonction hethy bch tkhali l front yaqralna tsawer
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = storageService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/all")
    public List<UserDTO> getall() {
        List<UserEntity> listen = userService.getAll();
        List<UserDTO> listDTO = new ArrayList<>();
        for (UserEntity user : listen) {
            listDTO.add(new UserDTO().fromEntity(user));
        }
        return listDTO;
    }

    @GetMapping("/detail/{id}")
    public UserDTO getById(@PathVariable Long id) {
        //recuperer l'objet avec la methode de service
        UserEntity myUser = userService.getOne(id);
        UserDTO userDTO = new UserDTO().fromEntity(myUser);
        userDTO.setPhoto(myUser.getPhoto());
        return userDTO;

    }


    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @GetMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestParam String email) {
        UserEntity x = userRepo.findFirstByEmail(email);
        x.setConfirm(true);
        userRepo.save(x);
        return ResponseEntity.ok(new MessageResponse("confirmation sent"));
    }

    @PutMapping("/update/{id}" )
    public UserDTO updateProject(@PathVariable Long id, UserEntity updatedUser , @RequestParam("file") MultipartFile file , @RequestParam("file1") MultipartFile file1) {
        UserEntity User = userService.getOne(id);
        updatedUser.setId(id);
        updatedUser.setConfirm(User.isConfirm());
        String namephoto = storageService.store(file);
        String cv = storageService.store(file1);
        updatedUser.setPhoto(namephoto);
        updatedUser.setCv(cv);
        if(updatedUser.getLastname() == null){ updatedUser.setLastname(User.getLastname()); }
        if(updatedUser.getFirstname() == null){ updatedUser.setFirstname(User.getFirstname()); }
        if(updatedUser.getEmail() == null){ updatedUser.setEmail(User.getEmail()); }
        if(updatedUser.getAddress() == null){ updatedUser.setAddress(User.getAddress()); }
        if(updatedUser.getNbexperience() ==0){ updatedUser.setNbexperience(User.getNbexperience()); }

        if(updatedUser.getSkills() == null){ updatedUser.setSkills(User.getSkills()); }
        if(updatedUser.getPhone()== null){ updatedUser.setPhone(User.getPhone()); }
        if(updatedUser.getRole()== null){ updatedUser.setRole(User.getRole()); }
        if(updatedUser.isPlaced()== false){ updatedUser.setPlaced(User.isPlaced()); }

        if(updatedUser.getPassword() == null){ updatedUser.setPassword(User.getPassword()); }
        if(updatedUser.getPassword() !=null){
            String cryptedpassword = encoder.encode(updatedUser.getPassword());
            updatedUser.setPassword(cryptedpassword);

        }




        userService.update(updatedUser);
        return new UserDTO().fromEntity(updatedUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid LoginRequest  loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        Optional<UserEntity> u=userRepo.findByUsername(loginRequest.getUsername());
        if (u.get().isConfirm()==true){
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);
            List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
            return ResponseEntity.ok(new JwtResponse(
                    jwt,
                    "Bearer",
                    refreshToken.getToken(),
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    roles.get(0)));

        }else {
            throw new RuntimeException("user not confirmed");

        }
    }

    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log Out Successfully"));
    }



    @GetMapping("/GetByRole")
    public ResponseEntity<List<UserEntity>> GetByRole(@RequestParam String role){
        List<UserEntity> AllUsers = userService.getAll();
        List<UserEntity> SpecificUsers = new ArrayList<>();
        for(UserEntity user : AllUsers){
            if(user.getRole().equals(role)){
                SpecificUsers.add(user);
            }
        }
        //retourne la liste des utilisateurs filtrés dans une réponse HTTP 200 OK
        return ResponseEntity.ok(SpecificUsers);
    }


}
