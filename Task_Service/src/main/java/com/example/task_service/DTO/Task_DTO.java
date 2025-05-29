package com.example.task_service.DTO;

import com.example.task_service.Entities.Task_Entity;


public class Task_DTO {

        private Long id;
        private String name;
        private String description;
        private int period;
        private int priority;
        private Long id_project;
        private ProjectDTO project;
        private String skills;
        private Long iduser;
        private UserDTO userdto;

        //getter&setter


    public UserDTO getUserdto() {
        return userdto;
    }

    public void setUserdto(UserDTO userdto) {
        this.userdto = userdto;
    }

    public Long getIduser() {
        return iduser;
    }

    public void setIduser(Long iduser) {
        this.iduser = iduser;
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
    //constructor

        public Task_DTO(Long id, String name, String description, int period, int priority) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.period = period;
            this.priority = priority;
        }

        public Task_DTO() {
        }

        //DTO=>Entity
        public Task_Entity toEntity(Task_DTO task_DTO) {
            Task_Entity task_Entity = new Task_Entity();
            task_Entity.setName(task_DTO.getName());
            task_Entity.setDescription(task_DTO.getDescription());
            task_Entity.setPeriod(task_DTO.getPeriod());
            task_Entity.setPriority(task_DTO.getPriority());
            task_Entity.setSkills(task_DTO.getSkills());
            return task_Entity;
        }


    //Entity => DTO
    public Task_DTO fromEntity(Task_Entity taskEntity){
        Task_DTO task_DTO = new Task_DTO();
        task_DTO.setId(taskEntity.getId());
        task_DTO.setName(taskEntity.getName());
        task_DTO.setDescription(taskEntity.getDescription());
        task_DTO.setPeriod(taskEntity.getPeriod());
        task_DTO.setPriority(taskEntity.getPriority());
        task_DTO.setSkills(taskEntity.getSkills());
        return task_DTO;
    }

}







