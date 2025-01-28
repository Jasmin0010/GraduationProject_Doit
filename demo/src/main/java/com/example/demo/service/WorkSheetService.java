package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.WorkSheetEntity;
import com.example.demo.persistence.WorkSheetRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Slf4j
@Service
public class WorkSheetService {

    @Autowired
    private WorkSheetRepository repository;

    public String testService() {
        // WorkSheetEntity 생성
        WorkSheetEntity entity = WorkSheetEntity.builder()
                .title("routine1")
                .userId("admin")
                .date("2024-03-17")
                .build();
        // WorkSheetEntity 저장
        repository.save(entity);
        // WorkSheetEntity 검색
        WorkSheetEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public WorkSheetEntity create(final WorkSheetEntity entity) {
        validate(entity);

        String currentDateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        entity.setCreated(currentDateTime);

        repository.save(entity);

        log.info("Entity Id : {} is saved.", entity.getId());

        return repository.findById(entity.getId()).get();
    }

    public List<WorkSheetEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public WorkSheetEntity retrieveOne(final String userId, final Long id) {
        final Optional<WorkSheetEntity> original = repository.findById(id);
        return original.orElse(null);
    }

    public WorkSheetEntity retrieveByDateOrCreate(final String userId, final String date) {
        // log.info("retrieveByDateOrCreate: userId={}, date={}", userId, date);
        try {
            WorkSheetEntity _entity = repository.findByUserIdAndDate(userId, date);

            if (_entity == null) {
                _entity = WorkSheetEntity.builder()
                .title("Do it!")
                .userId(userId)
                .date(date)
                .created(date + " 00:00:00")
                .build();

                log.info("added {} {}", date, _entity);
                _entity = create(_entity);
            } else {
                log.info("found {} {}", date, _entity);
            }

            return _entity;
        } catch (Exception e) {
            log.warn(e.getMessage());
            if (e.getMessage().contains("Query did not return a unique result")) {
                // remove existing records
                List<WorkSheetEntity> list = repository.findManyByUserIdAndDate(userId, date);
                for (WorkSheetEntity entity : list) {
                    delete(entity);
                }
                WorkSheetEntity entity = WorkSheetEntity.builder()
                .title("Do it!")
                .userId(userId)
                .date(date)
                .build();

                log.info("replaced {} {}", date, entity);
                return create(entity);
            }

            throw e;
        }
    }

    public WorkSheetEntity update(final WorkSheetEntity entity) {
        validate(entity);

        final Optional<WorkSheetEntity> original = repository.findById(entity.getId());

        String currentDateTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        original.ifPresent(worksheet -> {
            worksheet.setTitle(entity.getTitle());
            worksheet.setDate(entity.getDate());
            worksheet.setContent(entity.getContent());
            worksheet.setTimeSpent(entity.getTimeSpent());
            worksheet.setStatus(entity.getStatus());
            worksheet.setUpdated(currentDateTime);
            repository.save(worksheet);
        });

        return repository.findById(entity.getId()).get();
    }

    public void delete(final WorkSheetEntity entity) {
        validate(entity);

        repository.delete(entity);
    }

    private void validate(final WorkSheetEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null");
        }
        if (entity.getUserId() == null) {
            log.warn("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

}
