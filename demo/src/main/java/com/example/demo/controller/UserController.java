package com.example.demo.controller;

import com.example.demo.dto.ResponseListDTO;
import com.example.demo.dto.ResponseObjDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.security.TokenProvider;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenProvider tokenProvider;

	// Bean으로 작성해도 됨.
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
		try {
			// 리퀘스트를 이용해 저장할 유저 만들기
			UserEntity user = UserEntity.builder()
					.email(userDTO.getEmail())
					.username(userDTO.getUsername())
					.password(passwordEncoder.encode(userDTO.getPassword()))
					.build();
			// 서비스를 이용해 리파지토리에 유저 저장
			UserEntity registeredUser = userService.create(user);
			UserDTO responseUserDTO = UserDTO.builder()
					.email(registeredUser.getEmail())
					.id(registeredUser.getId())
					.username(registeredUser.getUsername())
					.build();
			// 유저 정보는 항상 하나이므로 그냥 리스트로 만들어야하는 ResponseDTO를 사용하지 않고 그냥 UserDTO 리턴.
			return ResponseEntity.ok(responseUserDTO);
		} catch (Exception e) {
			// 예외가 나는 경우 bad 리스폰스 리턴.
			ResponseListDTO responseDTO = ResponseListDTO.builder().error(e.getMessage()).build();
			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
		UserEntity user = userService.getByCredentials(
				userDTO.getEmail(),
				userDTO.getPassword(),
				passwordEncoder);

		if (user != null) {
			// 토큰 생성
			final String token = tokenProvider.create(user);
			final UserDTO responseUserDTO = UserDTO.builder()
					.email(user.getUsername())
					.id(user.getId())
					.token(token)
					.build();
			return ResponseEntity.ok().body(responseUserDTO);
		} else {
			ResponseListDTO responseDTO = ResponseListDTO.builder()
					.error("Login failed.")
					.build();
			return ResponseEntity
					.badRequest()
					.body(responseDTO);
		}
	}

	@PutMapping("/update")
	public ResponseEntity<?> updateUserInfo(
			@AuthenticationPrincipal String userId,
			@RequestBody UserDTO entity) {
		UserEntity user = userService.getById(userId);

		if (user != null) {
			user.setUsername(entity.getUsername());
			user.setHeight(entity.getHeight());
			user.setWeight(entity.getWeight());
			userService.update(user);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		UserDTO dto = UserDTO.builder()
				.email(user.getEmail())
				.id(user.getId())
				.username(user.getUsername())
				.height(user.getHeight())
				.weight(user.getWeight())
				.build();
		ResponseObjDTO<UserDTO> response = ResponseObjDTO.<UserDTO>builder().data(dto).build();

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("me")
	public ResponseEntity<?> getUserInfo(
			@AuthenticationPrincipal String userId) {
		UserEntity user = userService.getById(userId);

		if (user != null) {
			UserDTO dto = UserDTO.builder()
					.email(user.getEmail())
					.id(user.getId())
					.username(user.getUsername())
					.height(user.getHeight())
					.weight(user.getWeight())
					.build();
			ResponseObjDTO<UserDTO> response = ResponseObjDTO.<UserDTO>builder().data(dto).build();
			return ResponseEntity.ok().body(response);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

}