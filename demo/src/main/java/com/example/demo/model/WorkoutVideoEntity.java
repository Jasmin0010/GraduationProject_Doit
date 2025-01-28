package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workoutvideo")
public class WorkoutVideoEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="workoutvideo_sequence")
    @SequenceGenerator(name="workoutvideo_sequence", sequenceName="workoutvideo_sequence", allocationSize=100)
    private Long id;
    
    @Column(nullable = false)
    private String userId;

    private String videoId;

    private String platform;

    private String created;

}