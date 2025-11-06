package org.example.lab4um.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private int price;
}
