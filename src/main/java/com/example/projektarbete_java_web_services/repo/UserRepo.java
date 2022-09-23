package com.example.projektarbete_java_web_services.repo;

import com.example.projektarbete_java_web_services.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Integer> {

   List<AppUser> findUsersByUsernameContaining(String contains);

   AppUser findByUsername(String username);

   Optional<AppUser> findUsersByUsernameIgnoreCase(String username);

   List<AppUser> findByUserId(int id);

   List<AppUser> deleteByUserId(int id);


}
