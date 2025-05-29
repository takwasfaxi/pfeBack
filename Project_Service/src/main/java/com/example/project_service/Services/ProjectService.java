package com.example.project_service.Services;

import com.example.project_service.Entities.ProjectEntity;
import com.example.project_service.Repositories.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    //injection de dependance = invitation l repository ==> acces direct base de donnée
    @Autowired
    ProjectRepo projectRepo;

    public ProjectEntity create(ProjectEntity projectEntity) {


        return projectRepo.save(projectEntity);

    }

    public ProjectEntity update(ProjectEntity projectEntity) {
        return projectRepo.save(projectEntity);
    }

    public List<ProjectEntity> getAll() {
        return projectRepo.findAll();
    }

    public ProjectEntity getById(Long id) {
        return projectRepo.findById(id).orElse(null);
    }

    public void delete(Long id) {
        projectRepo.deleteById(id);
    }
   // public List<ProjectEntity> allProjectByUser(Long id) {

       // return projectRepo.findByUserid(id);
    //}
}
