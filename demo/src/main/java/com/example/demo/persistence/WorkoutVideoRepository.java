package com.example.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.WorkoutVideoEntity;

@Repository
public interface WorkoutVideoRepository extends JpaRepository<WorkoutVideoEntity, Long> {

    List<WorkoutVideoEntity> findByUserId(String userId);

}
