package com.example.task_service.Services;

import com.example.task_service.Entities.Task_Entity;
import com.example.task_service.Repositories.Task_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Task_Service {
    @Autowired
    Task_Repo taskRepo;

    //visibilité typeRetour Nomfonction (type param1 ....) {******}

    public Task_Entity create (Task_Entity task_Entity){
        return taskRepo.save(task_Entity);
    }

    public Task_Entity getTaskById(Long id){
        return taskRepo.findById(id).orElse(null);
    }

    public List<Task_Entity> getAllTasks(){
        return taskRepo.findAll();
    }

    public Task_Entity updateTask(Task_Entity task_Entity){
        return taskRepo.save(task_Entity);
    }

    public void deleteTaskById(Long id){
        taskRepo.deleteById(id);
    }

    public List<Task_Entity> alltasksbyIdproject(Long id){
        return taskRepo.findAllByProject_Id(id);
    }
    public List<Task_Entity> alltasksByUser(Long id) {

        return taskRepo.findByUserid(id);
    }



}
