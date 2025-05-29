package com.example.task_service.Entities;

import com.example.task_service.DTO.ProjectDTO;
import com.example.task_service.DTO.UserDTO;
import jakarta.persistence.*;

@Entity
public class Task_Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int period;
    private int priority;
    private Long id_project;
    @Transient
    private ProjectDTO project;
    private String skills;
    private Long iduser;
    @Transient
    private UserDTO userdto ;




    //getter&Setter


    public UserDTO getUserdto() {
        return userdto;
    }

    public void setUserdto(UserDTO userdto) {
        this.userdto = userdto;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getId_project() {
        return id_project;
    }

    public void setId_project(Long id_project) {
        this.id_project = id_project;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }
    //constructor

    public Task_Entity(Long id, String name, String description, int period, int priority) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.period = period;
        this.priority = priority;
    }

    public Task_Entity() {
    }
}

