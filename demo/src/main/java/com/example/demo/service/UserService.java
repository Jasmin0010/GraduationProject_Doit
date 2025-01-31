package com.example.demo.service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public UserEntity create(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null ) {
			throw new RuntimeException("Invalid arguments");
		}
		final String email = userEntity.getEmail();
		if(userRepository.existsByEmail(email)) {
			log.warn("Email already exists {}", email);
			throw new RuntimeException("Email already exists");
		}

		return userRepository.save(userEntity);
	}

	public UserEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
		final UserEntity originalUser = userRepository.findByEmail(email);

		// matches 메서드를 이용해 패스워드가 같은지 확인
		if(originalUser != null && encoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}
		return null;
	}

	public UserEntity getByEmail(final String email) {
		return userRepository.findByEmail(email);
	}

	public UserEntity getById(final String id) {
		return userRepository.findById(id).orElse(null);
	}

	public UserEntity update(final UserEntity userEntity) {
		if(userEntity == null || userEntity.getEmail() == null ) {
			throw new RuntimeException("Invalid arguments");
		}
		final String email = userEntity.getEmail();
		if(!userRepository.existsByEmail(email)) {
			log.warn("Email does not exist {}", email);
			throw new RuntimeException("Email does not exist");
		}

		final Optional<UserEntity> originalUser = userRepository.findById(userEntity.getId());

		originalUser.ifPresent(entity -> {
			entity.setUsername(userEntity.getUsername());
			entity.setHeight(userEntity.getHeight());
			entity.setWeight(userEntity.getWeight());
			userRepository.save(entity);
		});

		return userRepository.findById(userEntity.getId()).orElse(null);
	}
}