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
@Table(name = "worksheetentity")
public class WorkSheetEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="worksheetentity_sequence")
    @SequenceGenerator(name="worksheetentity_sequence", sequenceName="worksheetentity_sequence", allocationSize=100)
    private Long id;
    
    @Column(nullable = false)
    private String userId;

    private String title;
    
    private String content;

    @Column(nullable = false)
    private String date;

    private String timeSpent;

    private String status;

    private String created;

    private String updated;

}