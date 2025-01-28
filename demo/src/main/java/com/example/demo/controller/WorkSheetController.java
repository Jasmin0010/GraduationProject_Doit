package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.demo.dto.ResponseListDTO;
import com.example.demo.dto.ResponseObjDTO;
import com.example.demo.dto.WorkSheetDTO;
import com.example.demo.model.WorkSheetEntity;
import com.example.demo.service.WorkSheetService;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("worksheet")
public class WorkSheetController {

    @Autowired
    private WorkSheetService service;

    @GetMapping()
    public ResponseEntity<?> findAll(
            @AuthenticationPrincipal String userId) {

        try {
            List<WorkSheetEntity> list = service.retrieve(userId);

            List<WorkSheetDTO> dtos = list.stream().map(WorkSheetDTO::new).collect(Collectors.toList());

            ResponseListDTO<WorkSheetDTO> response = ResponseListDTO.<WorkSheetDTO>builder().data(dtos).build();

            return new ResponseEntity<ResponseListDTO<WorkSheetDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id) {
        try {
            WorkSheetEntity entity = service.retrieveOne(userId, id);

            WorkSheetDTO dto = new WorkSheetDTO(entity);

            ResponseObjDTO<WorkSheetDTO> response = ResponseObjDTO.<WorkSheetDTO>builder().data(dto).build();

            return new ResponseEntity<ResponseObjDTO<WorkSheetDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/by-date")
    public ResponseEntity<?> getByDate(
            @AuthenticationPrincipal String userId,
            @RequestParam String date) {
                log.info("date: {}", date);
        try {
            WorkSheetEntity entity = service.retrieveByDateOrCreate(userId, date);

            log.info("entity: {}", entity);

            WorkSheetDTO dto = new WorkSheetDTO(entity);

            ResponseObjDTO<WorkSheetDTO> response = ResponseObjDTO.<WorkSheetDTO>builder().data(dto).build();

            return new ResponseEntity<ResponseObjDTO<WorkSheetDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(
            @AuthenticationPrincipal String userId,
            @RequestBody WorkSheetDTO dto) {
        try {
            WorkSheetEntity entity = WorkSheetDTO.toEntity(dto);

            entity.setId(null);

            entity.setUserId(userId);

            WorkSheetEntity entities = service.create(entity);

            WorkSheetDTO dtos = new WorkSheetDTO(entities);

            ResponseObjDTO<WorkSheetDTO> response = ResponseObjDTO.<WorkSheetDTO>builder().data(dtos).build();

            return new ResponseEntity<ResponseObjDTO<WorkSheetDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> update(
            @AuthenticationPrincipal String userId,
            @RequestBody WorkSheetDTO dto) {
        try {
            WorkSheetEntity entity = WorkSheetDTO.toEntity(dto);

            if (!entity.getUserId().equals(userId)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            WorkSheetEntity entities = service.update(entity);

            WorkSheetDTO dtos = new WorkSheetDTO(entities);

            ResponseObjDTO<WorkSheetDTO> response = ResponseObjDTO.<WorkSheetDTO>builder().data(dtos).build();

            return new ResponseEntity<ResponseObjDTO<WorkSheetDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @AuthenticationPrincipal String userId,
            @PathVariable Long id) {
        try {
            WorkSheetEntity entity = service.retrieveOne(userId, id);
            log.info("entity: {}", entity);

            if (!entity.getUserId().equals(userId)) {
                log.warn("Unauthorized attempt to delete worksheet. entity user: {}, User: {}", entity.getUserId(), userId);
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            service.delete(entity);

            ResponseObjDTO<WorkSheetDTO> response = ResponseObjDTO.<WorkSheetDTO>builder().build();
            return new ResponseEntity<ResponseObjDTO<WorkSheetDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getStatistics(
            @AuthenticationPrincipal String userId,
            @RequestParam String start, @RequestParam String end) {
        try {
            List<WorkSheetEntity> entities = service.retrieve(userId);

            // filter by date [start, end]
            entities = entities.stream().filter(entity -> {
                String date = entity.getDate();
                LocalDateTime startDateTime = LocalDateTime.parse(start + "T00:00:00");
                LocalDateTime endDateTime = LocalDateTime.parse(end + "T23:59:59");
                LocalDateTime entityDateTime = LocalDateTime.parse(date + "T00:00:00");

                return entityDateTime.compareTo(startDateTime) >= 0 && entityDateTime.compareTo(endDateTime) <= 0;
            }).collect(Collectors.toList());

            List<WorkSheetDTO> dtos = entities.stream().map(WorkSheetDTO::new).collect(Collectors.toList());

            ResponseListDTO<WorkSheetDTO> response = ResponseListDTO.<WorkSheetDTO>builder().data(dtos).build();

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
