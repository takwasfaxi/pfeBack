package com.example.user_service.Services;

import com.example.user_service.Entities.UserEntity;
import com.example.user_service.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    //visib typeRetour nomFonc (type nom param ..)
    public UserEntity create (UserEntity user) {
        return userRepo.save(user);
    }

    public UserEntity update (UserEntity user) {
        return userRepo.save(user);
    }
    public UserEntity getOne(Long id) {
        return userRepo.findById(id).orElse(null);
    }
    public List<UserEntity> getAll() {
        return userRepo.findAll();
    }

    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }
}
