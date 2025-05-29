package com.example.task_service.OpenFeign;

import com.example.task_service.DTO.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "UserService")
public interface UserClient {
    @GetMapping("user/detail/{id}")
    public UserDTO getById(@PathVariable Long id);
}
