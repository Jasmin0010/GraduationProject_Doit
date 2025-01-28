package com.example.demo.dto;

import com.example.demo.model.WorkSheetEntity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class WorkSheetDTO {
        private Long id;
        private String userId;
    private String title;
    private String content;
    private String date;
    private String timeSpent;
    private String status;
    private String created;
    private String updated;

    public WorkSheetDTO(final WorkSheetEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.date = entity.getDate();
        this.timeSpent = entity.getTimeSpent();
        this.status = entity.getStatus();
        this.created = entity.getCreated();
        this.updated = entity.getUpdated();
    }

    public static WorkSheetEntity toEntity (final WorkSheetDTO dto) {
        return WorkSheetEntity.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .date(dto.getDate())
                .timeSpent(dto.getTimeSpent())
                .status(dto.getStatus())
                .created(dto.getCreated())
                .updated(dto.getUpdated())
                .build();
    }
}
