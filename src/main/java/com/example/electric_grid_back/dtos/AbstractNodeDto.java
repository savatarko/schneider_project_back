package com.example.electric_grid_back.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractNodeDto {
    private Long id;
    private NodeType type;
    private double x;
    private double y;
    private int state;
}
