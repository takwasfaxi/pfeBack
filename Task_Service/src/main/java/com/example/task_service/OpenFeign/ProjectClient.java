package com.example.task_service.OpenFeign;


import com.example.task_service.DTO.ProjectDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ProjectService")
public interface ProjectClient {
    @GetMapping("/project/detail/{id}")
    public ProjectDTO ShowDetails(@PathVariable Long id) ;
}
