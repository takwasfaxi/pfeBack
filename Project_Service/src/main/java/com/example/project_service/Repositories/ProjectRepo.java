package com.example.project_service.Repositories;

import com.example.project_service.Entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo extends JpaRepository<ProjectEntity , Long> {


    Boolean existsByName(String name);

    //@Query("SELECT P FROM ProjectEntity P where P.iduser = :id")
    //public List<ProjectEntity> findByUserid(Long id);
}
