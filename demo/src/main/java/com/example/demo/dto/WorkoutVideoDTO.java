package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import com.example.demo.model.WorkoutVideoEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class WorkoutVideoDTO {
    private Long id;
    private String userId;
    private String videoId;
    private String platform;
    private String created;

    public WorkoutVideoDTO(final WorkoutVideoEntity entity) {
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.videoId = entity.getVideoId();
        this.platform = entity.getPlatform();
        this.created = entity.getCreated();
    }

    public static WorkoutVideoEntity toEntity (final WorkoutVideoDTO dto) {
        return WorkoutVideoEntity.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .videoId(dto.getVideoId())
                .platform(dto.getPlatform())
                .created(dto.getCreated())
                .build();
    }
    
}
