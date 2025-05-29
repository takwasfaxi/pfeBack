package com.example.user_service.Repositories;


import com.example.user_service.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    UserEntity findFirstByEmail(String email);


    Optional<UserEntity> findByUsername(String username);
    //Optional<UserModel> findByIdAndAdress(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    UserEntity findByPasswordResetToken(String passwordResetToken);
}
