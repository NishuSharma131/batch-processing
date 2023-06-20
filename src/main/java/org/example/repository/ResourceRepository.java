package org.example.repository;

import org.example.entity.Project;
import org.example.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {
}
