package com.example.user_service.DTO;

import com.example.user_service.Entities.UserEntity;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String photo;
    private String role;
    private String skills;
    private boolean placed;
    private int nbexperience ;
    private String firstname;
    private String lastname;
    private boolean confirm ;
    private String cv ;

    //getter&setter

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public int getNbexperience() {
        return nbexperience;
    }

    public void setNbexperience(int nbexperience) {
        this.nbexperience = nbexperience;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    //constructor

    public UserDTO(Long id, String username, String password, String email, String address, String phone, String photo, String role, String skills, boolean placed, int nbexperience, String firstname, String lastname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.photo = photo;
        this.role = role;
        this.skills = skills;
        this.placed = placed;
        this.nbexperience = nbexperience;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UserDTO() {
    }

 // DTO ==> Entity
    //visibility typeFonctiopn nameFonction (type parm1 parm1, typeparm2 parm2.......)
    public UserEntity toEntity(UserDTO userDTO) {
UserEntity userEntity = new UserEntity();
userEntity.setId(userDTO.getId());
userEntity.setUsername(userDTO.getUsername());
userEntity.setPassword(userDTO.getPassword());
userEntity.setEmail(userDTO.getEmail());
userEntity.setAddress(userDTO.getAddress());
userEntity.setPhone(userDTO.getPhone());
userEntity.setRole(userDTO.getRole());
userEntity.setSkills(userDTO.getSkills());
userEntity.setPlaced(userDTO.isPlaced());
userEntity.setNbexperience(userDTO.getNbexperience());
userEntity.setFirstname(userDTO.getFirstname());
userEntity.setLastname(userDTO.getLastname());
userEntity.setCv(userDTO.getCv());
userEntity.setPhoto(userDTO.getPhoto());
return userEntity;
    }


    //fonction de conversion de type Entity to DTO
    public UserDTO fromEntity(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setPassword(userEntity.getPassword());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setAddress(userEntity.getAddress());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setRole(userEntity.getRole());
        userDTO.setSkills(userEntity.getSkills());
        userDTO.setPlaced(userEntity.isPlaced());
        userDTO.setPhoto(userEntity.getPhoto());
        userDTO.setNbexperience(userEntity.getNbexperience());
        userDTO.setFirstname(userEntity.getFirstname());
        userDTO.setLastname(userEntity.getLastname());
        userDTO.setCv(userEntity.getCv());
        return userDTO;
    }
}
