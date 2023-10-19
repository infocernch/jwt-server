package com.example.jwtserver.repository;

import com.example.jwtserver.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
    public Users findByUsername(String username);
}
