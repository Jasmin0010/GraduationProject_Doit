package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.WorkoutVideoEntity;
import com.example.demo.persistence.WorkoutVideoRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Slf4j
@Service
public class WorkoutVideoService {

    @Autowired
    private WorkoutVideoRepository repository;

    public WorkoutVideoEntity create(final WorkoutVideoEntity entity) {
        validate(entity);

        String currentDateTime = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        entity.setCreated(currentDateTime);

        repository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findById(entity.getId()).get();
    }

    private void validate(final WorkoutVideoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    public List<WorkoutVideoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public WorkoutVideoEntity retrieveOne(final String userId, final Long id) {
        final Optional<WorkoutVideoEntity> original = repository.findById(id);
        return original.orElse(null);
    }

    public WorkoutVideoEntity update(final WorkoutVideoEntity entity) {
        validate(entity);

        final Optional<WorkoutVideoEntity> original = repository.findById(entity.getId());

        original.ifPresent(workoutVideo -> {
            workoutVideo.setVideoId(entity.getVideoId());
            workoutVideo.setUserId(entity.getUserId());
            workoutVideo.setPlatform(entity.getPlatform());
            repository.save(workoutVideo);
        });

        return repository.findById(entity.getId()).get();
    }

    public void delete(final WorkoutVideoEntity entity) {
        validate(entity);

        repository.delete(entity);
    }

}
