package com.example.electric_grid_back.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {
    private Long id;
    private Long sourceId;
    private Long targetId;
}
