package com.exam.springboot.authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserUpdateRepository extends JpaRepository<UserUpdate, Long> {
  Optional<UserUpdate> findByUsername(String username);
  Boolean existsByUsername(String username);
}