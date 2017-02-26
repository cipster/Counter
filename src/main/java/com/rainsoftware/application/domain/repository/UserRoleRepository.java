package com.rainsoftware.application.domain.repository;

import com.rainsoftware.application.domain.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    @Query("SELECT a.role FROM UserRole a, User b WHERE b.username=?1 AND a.username=b.username")
    List<String> findRoleByUsername(String username);
}
