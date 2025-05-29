package com.example.project_service.OpenFeign;

import com.example.project_service.DTO.Task_DTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "TaskService")
public interface TaskClient {

    @GetMapping("/task/detail/{id}")
    public Task_DTO detailByID(@PathVariable Long id);
}
