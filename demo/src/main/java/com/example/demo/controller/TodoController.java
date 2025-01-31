package com.example.demo.controller;

import com.example.demo.dto.ResponseListDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j

@RestController
@RequestMapping("todo")
public class TodoController {
	
	@Autowired
	private TodoService service;

	@GetMapping("/test")
	public ResponseEntity<?> testTodo() {
		log.info("Test Todo");
		
		String str = service.testService(); // 테스트 서비스 사용
		List<String> list = new ArrayList<>();
		list.add(str);
		ResponseListDTO<String> response = ResponseListDTO.<String>builder().data(list).build();
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<?> createTodo(
					@AuthenticationPrincipal String userId,
					@RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);

			entity.setId(null);

			entity.setUserId(userId);

			List<TodoEntity> entities = service.create(entity);


			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().data(dtos).build();

			return ResponseEntity.ok(response);
		} catch (Exception e) {

			String error = e.getMessage();
			ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping
	public ResponseEntity<?> retrieveTodoList(
					@AuthenticationPrincipal String userId) {
		System.out.println("UserID : " + userId);
		List<TodoEntity> entities = service.retrieve(userId);

		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().data(dtos).build();

		return ResponseEntity.ok(response);
	}


	@PutMapping
	public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId,
	                                    @RequestBody TodoDTO dto) {
		TodoEntity entity = TodoDTO.toEntity(dto);

		entity.setUserId(userId);

		List<TodoEntity> entities = service.update(entity);

		List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

		ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().data(dtos).build();

	return ResponseEntity.ok(response);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTodo(
					@AuthenticationPrincipal String userId,
					@RequestBody TodoDTO dto) {
		try {
			TodoEntity entity = TodoDTO.toEntity(dto);


			entity.setUserId(userId);

			List<TodoEntity> entities = service.delete(entity);

			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().data(dtos).build();

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}

    @GetMapping("/by-date")
	public ResponseEntity<?> getByDate(
			@AuthenticationPrincipal String userId,
            @RequestParam String date) {
		try {

			List<TodoEntity> entities = service.retrieveByDate(userId, date);

			List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

			ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().data(dtos).build();

			return ResponseEntity.ok(response);
		} catch (Exception e) {
			String error = e.getMessage();
			ResponseListDTO<TodoDTO> response = ResponseListDTO.<TodoDTO>builder().error(error).build();
			return ResponseEntity.badRequest().body(response);
		}
	}
}