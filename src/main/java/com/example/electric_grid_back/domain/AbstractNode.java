package com.example.electric_grid_back.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
public class AbstractNode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "node_generator")
    private Long id;
    private double x;
    private double y;
}
