package com.example.demo.persistence;

import java.util.List;//생성
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.WorkSheetEntity;

@Repository
public interface WorkSheetRepository extends JpaRepository<WorkSheetEntity, Long> {

    List<WorkSheetEntity> findByUserId(String userId);

    WorkSheetEntity findByUserIdAndDate(String userId, String date);
    List<WorkSheetEntity> findManyByUserIdAndDate(String userId, String date);

}
