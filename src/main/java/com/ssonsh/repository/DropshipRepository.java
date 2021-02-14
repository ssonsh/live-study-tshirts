package com.ssonsh.repository;

import com.ssonsh.domain.Dropship;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DropshipRepository extends JpaRepository<Dropship, Integer> {
    Dropship findByGithub(String github);
}
