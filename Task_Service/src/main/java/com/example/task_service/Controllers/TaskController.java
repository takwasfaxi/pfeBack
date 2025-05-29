package com.example.task_service.Controllers;


import com.example.task_service.DTO.ProjectDTO;
import com.example.task_service.DTO.Task_DTO;
import com.example.task_service.DTO.UserDTO;
import com.example.task_service.Entities.Task_Entity;
import com.example.task_service.OpenFeign.ProjectClient;
import com.example.task_service.OpenFeign.UserClient;
import com.example.task_service.Services.Task_Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("task")
@CrossOrigin("*")
public class TaskController {
    //injetion de dependene
    @Autowired
    Task_Service taskService;

    @Autowired
    ProjectClient projectClient;
    @Autowired
    private UserClient userClient;


    @PostMapping("/create/{idproject}")
    public Task_DTO creation(Task_Entity taskEntity, @PathVariable Long idproject) {

        ProjectDTO projectDetails = projectClient.ShowDetails(idproject);
        taskEntity.setProject(projectDetails);
        taskEntity.setId_project(idproject);
        Task_Entity mytask = taskService.create(taskEntity);
        Task_DTO task_dto = new Task_DTO().fromEntity(mytask);
        task_dto.setId_project(idproject);
        task_dto.setProject(projectDetails);
        return task_dto;
    }


    @GetMapping("/all")
    public List<Task_DTO> getall() {
        //recuperation d'un objet projet à partir idprojet
        List<Task_Entity> listen= taskService.getAllTasks(); //reuperer toutes les taches stockés dans BD
        List<Task_DTO> listDTO = new ArrayList<>();   //creer liste vide DTO
        for (Task_Entity task : listen) {

            Task_DTO tt=new Task_DTO().fromEntity(task);
            tt.setId_project(task.getId_project());
            ProjectDTO myp = projectClient.ShowDetails(task.getId_project());
            tt.setProject(myp);

            if (task.getIduser()!= null){
                tt.setIduser(task.getIduser());
                UserDTO myu = userClient.getById(task.getIduser());
                tt.setUserdto(myu);
            }


            listDTO.add(tt); //prendre les objets entities de listen et les convertir vers DTO puis les mettre dans la liste vide de DTO
        }
        return  listDTO;
    }


    @GetMapping("/allByBrojectID/{idproject}")
    public List<Task_DTO> getallbyidproject(@PathVariable Long idproject) {
        //recuperation d'un objet projet à partir idprojet
//
        List<Task_Entity> listen= taskService.alltasksbyIdproject(idproject); //reuperer toutes les taches stockés dans BD
        List<Task_DTO> listDTO = new ArrayList<>();   //creer liste vide DTO
        for (Task_Entity task : listen) {

            Task_DTO tt=new Task_DTO().fromEntity(task);
            tt.setId_project(task.getId_project());
            ProjectDTO myp = projectClient.ShowDetails(task.getId_project());
            tt.setProject(myp);

            listDTO.add(tt); //prendre les objets entities de listen et les convertir vers DTO puis les mettre dans la liste vide de DTO
        }
        return  listDTO;
    }





    @GetMapping("/detail/{id}")
    public Task_DTO detailByID(@PathVariable Long id) {
        Task_Entity myTask = taskService.getTaskById(id);
        Task_DTO Tdto = new Task_DTO().fromEntity(myTask);
        Tdto.setId_project(myTask.getId_project());
        ProjectDTO myp = projectClient.ShowDetails(myTask.getId_project());
        Tdto.setProject(myp);
        if (myTask.getIduser()!= null){
            Tdto.setIduser(myTask.getIduser());
            UserDTO myu = userClient.getById(myTask.getIduser());
            Tdto.setUserdto(myu);
        }
        return Tdto;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @DeleteMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return "Task deleted.";
    }

    @PutMapping("/update/{id}/{idproject}")
    public Task_DTO updateTask(@PathVariable Long id, Task_Entity updatedTask ,@PathVariable Long idproject) {

        ProjectDTO projectDetails = projectClient.ShowDetails(idproject);

        updatedTask.setProject(projectDetails);
        updatedTask.setId_project(idproject);

        Task_Entity task = taskService.getTaskById(id);
        updatedTask.setId(id);
        if(updatedTask.getName() == null){ updatedTask.setName(task.getName()); }
        if(updatedTask.getDescription() == null){ updatedTask.setDescription(task.getDescription()); }
        if(updatedTask.getPeriod() == 0){ updatedTask.setPeriod(task.getPeriod()); }
        if(updatedTask.getPriority() == 0){ updatedTask.setPriority(task.getPriority()); }
        if(updatedTask.getIduser()==null){ updatedTask.setIduser(task.getIduser()); }

        Task_Entity a=  taskService.updateTask(updatedTask);
        Task_DTO Tdto =new Task_DTO().fromEntity(a);
        if(a.getIduser()!=null)
        {
            Tdto.setIduser(a.getIduser());
            Tdto.setUserdto(userClient.getById(a.getIduser()));
        }
        Tdto.setProject(projectDetails);
        Tdto.setId_project(idproject);

        return Tdto;

    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/AttachUserToTask/{idtask}/{idUser}")
    public Task_DTO attachUserToTask(@PathVariable Long idtask, @PathVariable Long idUser){
        //recuperation de projet
        Task_Entity myt=taskService.getTaskById(idtask);
        //recuperation user
        UserDTO user =userClient.getById(idUser);
        //attachement user au projet
        myt.setIduser(idUser);
        myt.setUserdto(user);
        taskService.updateTask(myt);
        Task_DTO finaltask= new Task_DTO().fromEntity(myt);
        finaltask.setIduser(idUser);
        finaltask.setUserdto(user);
        finaltask.setId_project(myt.getId_project());
        ProjectDTO myp = projectClient.ShowDetails(myt.getId_project());
        finaltask.setProject(myp);
        return finaltask;
    }

    @GetMapping("/allByUser/{iduser}")
    public List<Task_DTO> allByUser(@PathVariable Long iduser) {
        List<Task_Entity> list = taskService.alltasksByUser(iduser);
        List<Task_DTO> projectDTOList=new ArrayList<>();
        for (Task_Entity x : list) {
            Task_DTO mytdto=new Task_DTO().fromEntity(x);
            mytdto.setIduser(x.getIduser());
            ProjectDTO myp = projectClient.ShowDetails(x.getId_project());
            mytdto.setProject(myp);
            if (x.getIduser()!= null){
                mytdto.setIduser(x.getIduser());
                UserDTO myu = userClient.getById(x.getIduser());
                mytdto.setUserdto(myu);
            }
            projectDTOList.add(mytdto);
        }
        return projectDTOList;
    }
}
