package com.hometalk.onepass.auth.repository;

import com.hometalk.onepass.auth.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
