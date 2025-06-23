package com.rifqy.potofolio.job_tracker.applicationuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rifqy.potofolio.job_tracker.applicationuser.model.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByUsername(String username);

}
