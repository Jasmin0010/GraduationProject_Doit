package com.example.demo.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.ResponseListDTO;
import com.example.demo.dto.ResponseObjDTO;
import com.example.demo.dto.WorkoutVideoDTO;
import com.example.demo.model.WorkoutVideoEntity;
import com.example.demo.service.WorkoutVideoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("workoutvideo")
public class WorkoutVideoController {

    @Autowired
    private WorkoutVideoService service;

    @GetMapping()
    public ResponseEntity<?> findAll(
                    @AuthenticationPrincipal String userId
    ) {
        try {
            List<WorkoutVideoEntity> list = service.retrieve(userId);

            List<WorkoutVideoDTO> dtos = list.stream().map(WorkoutVideoDTO::new).collect(Collectors.toList());

            ResponseListDTO<WorkoutVideoDTO> response = ResponseListDTO.<WorkoutVideoDTO>builder().data(dtos).build();

            return new ResponseEntity<ResponseListDTO<WorkoutVideoDTO>>(response, HttpStatus.OK);
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
            WorkoutVideoEntity entity = service.retrieveOne(userId, id);

            WorkoutVideoDTO dto = new WorkoutVideoDTO(entity);

            ResponseObjDTO<WorkoutVideoDTO> response = ResponseObjDTO.<WorkoutVideoDTO>builder().data(dto).build();

            return new ResponseEntity<ResponseObjDTO<WorkoutVideoDTO>>(response, HttpStatus.OK);
            
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<?> create(
        @AuthenticationPrincipal String userId,
        @RequestBody WorkoutVideoDTO dto) {
        try {
            WorkoutVideoEntity entity = WorkoutVideoDTO.toEntity(dto);

            entity.setId(null);

            entity.setUserId(userId);

            WorkoutVideoEntity savedEntity = service.create(entity);

            WorkoutVideoDTO savedDto = new WorkoutVideoDTO(savedEntity);

            ResponseObjDTO<WorkoutVideoDTO> response = ResponseObjDTO.<WorkoutVideoDTO>builder().data(savedDto).build();

            return new ResponseEntity<ResponseObjDTO<WorkoutVideoDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<?> update(
        @AuthenticationPrincipal String userId,
        @RequestBody WorkoutVideoDTO dto) {
        try {
            WorkoutVideoEntity entity = WorkoutVideoDTO.toEntity(dto);

            if (!entity.getUserId().equals(userId)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            WorkoutVideoEntity savedEntity = service.update(entity);

            WorkoutVideoDTO savedDto = new WorkoutVideoDTO(savedEntity);

            ResponseObjDTO<WorkoutVideoDTO> response = ResponseObjDTO.<WorkoutVideoDTO>builder().data(savedDto).build();

            return new ResponseEntity<ResponseObjDTO<WorkoutVideoDTO>>(response, HttpStatus.OK);
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
            WorkoutVideoEntity entity = service.retrieveOne(userId, id);

            if (!entity.getUserId().equals(userId)) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            service.delete(entity);

            // make empty json response
            ResponseObjDTO<WorkoutVideoDTO> response = ResponseObjDTO.<WorkoutVideoDTO>builder().build();
            return new ResponseEntity<ResponseObjDTO<WorkoutVideoDTO>>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
