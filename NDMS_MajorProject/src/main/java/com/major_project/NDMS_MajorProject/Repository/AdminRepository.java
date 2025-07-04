package com.major_project.NDMS_MajorProject.Repository;

import com.major_project.NDMS_MajorProject.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsernameAndPassword(String username, String password);
}
