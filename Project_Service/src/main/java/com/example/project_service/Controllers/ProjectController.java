package com.example.project_service.Controllers;

import com.example.project_service.DTO.ProjectDTO;
import com.example.project_service.DTO.UserDTO;
import com.example.project_service.Entities.ProjectEntity;
import com.example.project_service.OpenFeign.UserClient;
import com.example.project_service.Repositories.ProjectRepo;
import com.example.project_service.Services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/project")

@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    //injection de dependence = invitation
    @Autowired
    ProjectService projectService;
    @Autowired
    UserClient userClient;

    @Autowired
    ProjectRepo projectRepo;

    @PostMapping("/create")
    public ResponseEntity<?> create(ProjectEntity projectEntity) {
        boolean exists = projectRepo.existsByName(projectEntity.getName());
        if (exists) {
            return new ResponseEntity<>("Project already exists", HttpStatus.CONFLICT);
        }
        if (projectEntity.getStartDate().isBefore(projectEntity.getEndDate()) && !exists) {
            ProjectEntity newProjectEntity = projectService.create(projectEntity);
            ProjectDTO projectDTO = new ProjectDTO().fromEntity(newProjectEntity);
            return new ResponseEntity<>(projectDTO, HttpStatus.CREATED); // 201 Created
        } else {
            return new ResponseEntity<>("Start date must be before end date", HttpStatus.BAD_REQUEST); // 400 Bad Request
        }
    }


    @PostMapping("/AttachUserToProject/{idproject}/{idUser}")
    public ProjectDTO attachUserToProject(@PathVariable Long idproject, @PathVariable Long idUser) {
        //recuperation de projet
        ProjectEntity myp = projectService.getById(idproject);
        //recuperation user
        UserDTO user = userClient.getById(idUser);
        //attachement user au projet : Met à jour l'ID utilisateur dans le projet (stocké en base de données).
        myp.setIduser(idUser);
        //Attache un objet UserDTO contenant plus d'infos utilisateur, mais non stocké en base.
        myp.setUserdto(user);
        projectService.update(myp);
        ProjectDTO finalproject = new ProjectDTO().fromEntity(myp);
        finalproject.setIduser(idUser);
        finalproject.setUserdto(user);
        return finalproject;

    }


    @GetMapping("/listall")
    public List<ProjectDTO> Listal() {


        List<ProjectEntity> listen = projectService.getAll();
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        for (ProjectEntity x : listen) {
            ProjectDTO mypdto = new ProjectDTO().fromEntity(x);
            //si ce projet est associé à un utilisateur
            //if(x.getIduser()!=null){
            //UserDTO myp = userClient.getById(x.getIduser());//on recupere l'user correspondant
            //mypdto.setIduser(x.getIduser());
            //mypdto.setUserdto(myp);
            //}
            //else {
            //mypdto.setIduser(null);
            //mypdto.setUserdto(null);
            // }

            projectDTOList.add(mypdto);
        }
        return projectDTOList;

    }

    @GetMapping("/detail/{id}")
    public ProjectDTO detail(@PathVariable Long id) {
        ProjectEntity a = projectService.getById(id);
        ProjectDTO mypdto = new ProjectDTO().fromEntity(a);
        //if(a.getIduser()!=null){
        //  mypdto.setIduser(a.getIduser());
        //mypdto.setUserdto(userClient.getById(a.getIduser())); // récupérer les détails de l'utilisateur associé à ce projet .
//        }
//
//        else{
//            mypdto.setIduser(null);
//            mypdto.setUserdto(null);
//        }

        return mypdto;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        projectService.delete(id);
    }


    @PutMapping("/update/{id}")
    public ProjectDTO updateProject(@PathVariable Long id, ProjectEntity updatedProject) {


        ProjectEntity project = projectService.getById(id);
        updatedProject.setId(id);
        if (updatedProject.getName() == null) {
            updatedProject.setName(project.getName());
        }
        if (updatedProject.getDescription() == null) {
            updatedProject.setDescription(project.getDescription());
        }
        if (updatedProject.getStartDate() == null) {
            updatedProject.setStartDate(project.getStartDate());
        }
        if (updatedProject.getEndDate() == null) {
            updatedProject.setEndDate(project.getEndDate());
        }
        //if(updatedProject.getIduser()==null){ updatedProject.setIduser(project.getIduser()); }


        ProjectEntity a = projectService.update(updatedProject);
        ProjectDTO mypdto = new ProjectDTO().fromEntity(a);
        //mypdto.setIduser(a.getIduser());
        //mypdto.setUserdto(userClient.getById(a.getIduser()));

        return mypdto;
    }
//    @GetMapping("/allByUser/{iduser}")
//    //récupérer tous les projets liés à un utilisateur donné
//    public List<ProjectDTO> allByUser(@PathVariable Long iduser) {
//        List<ProjectEntity> list = projectService.allProjectByUser(iduser); //liste de projets des users avec iduser
//        List<ProjectDTO> projectDTOList=new ArrayList<>(); //liste dto vide
//        for (ProjectEntity x : list) {
//            ProjectDTO mypdto=new ProjectDTO().fromEntity(x); //convertir chaque projet (x) de la liste vers mypdto
//            mypdto.setIduser(x.getIduser()); //nzidouh iduser
//            UserDTO myp = userClient.getById(x.getIduser()); //njibou les details mtaa user atheka => myp howa user
//            mypdto.setUserdto(myp);
//            projectDTOList.add(mypdto);
//        }
//        return projectDTOList;
//    }
//}

}