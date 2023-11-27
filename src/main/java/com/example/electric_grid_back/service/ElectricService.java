package com.example.electric_grid_back.service;

import com.example.electric_grid_back.dtos.AbstractNodeDto;
import com.example.electric_grid_back.dtos.NewNodeDto;
import com.example.electric_grid_back.dtos.ResponseDto;

import java.util.List;

public interface ElectricService {
    ResponseDto getState();

    void removeNode(Long id);

    Long createNode(NewNodeDto nodeDto);

    void moveNode(AbstractNodeDto nodeDto);
    void makeConnection(Long nodeId, Long connectionId);

    void changeState(Long id);

    void removeLine(Long id);
    void undo();
    void redo();
}
