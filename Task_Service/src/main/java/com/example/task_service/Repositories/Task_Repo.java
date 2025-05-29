package com.example.task_service.Repositories;

import com.example.task_service.Entities.Task_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Task_Repo extends JpaRepository<Task_Entity, Long> {
    @Query("SELECT t FROM Task_Entity t WHERE t.id_project = :id")
    public List<Task_Entity> findAllByProject_Id(Long id);
    @Query("SELECT P FROM Task_Entity P where P.iduser = :id")
    public List<Task_Entity> findByUserid(Long id);

}
