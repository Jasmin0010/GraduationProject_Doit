package com.example.demo.persistence;

import java.util.List;//생성
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.TodoEntity;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String>{
	
	List<TodoEntity> findByUserId(String userId);//생성

	List<TodoEntity> findByUserIdAndDate(String userId, String date);//생성

}
