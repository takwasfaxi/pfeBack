package com.example.project_service.Entities;

import com.example.project_service.DTO.UserDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long iduser;
    @Transient // Indique que cet attribut n'est PAS stocké en base de données
    private UserDTO userdto ;

    //Getter&Setter


    public UserDTO getUserdto() {
        return userdto;
    }

    public void setUserdto(UserDTO userdto) {
        this.userdto = userdto;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
    }
    //constructor

    public ProjectEntity(Long id, String name, String description, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public ProjectEntity() {
    }
}
