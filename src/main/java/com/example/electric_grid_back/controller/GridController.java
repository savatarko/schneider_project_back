package com.example.electric_grid_back.controller;

import com.example.electric_grid_back.dtos.AbstractNodeDto;
import com.example.electric_grid_back.dtos.NewNodeDto;
import com.example.electric_grid_back.dtos.ResponseDto;
import com.example.electric_grid_back.service.ElectricService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/power")
public class GridController {
    private ElectricService electricService;

    public GridController(ElectricService electricService) {
        this.electricService = electricService;
    }

    @GetMapping("/state")
    public ResponseEntity<ResponseDto> getState() {
        return new ResponseEntity<>(electricService.getState(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Long> createNode(@RequestBody NewNodeDto nodeDto) {
        return new ResponseEntity<>(electricService.createNode(nodeDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{id}")
    public void removeNode(@PathVariable Long id) {
        electricService.removeNode(id);
    }

    @PutMapping("/move")
    public void moveNode(@RequestBody AbstractNodeDto nodeDto) {
        electricService.moveNode(nodeDto);
    }

    @PutMapping("/connect/{nodeId}/{connectionId}")
    public void makeConnection(@PathVariable Long nodeId, @PathVariable Long connectionId) {
        electricService.makeConnection(nodeId, connectionId);
    }

    @PutMapping("/change/{id}")
    public void changeState(@PathVariable Long id) {
        electricService.changeState(id);
    }

    @DeleteMapping("/removecon/{id}")
    public void removeLine(@PathVariable Long id) {
        electricService.removeLine(id);
    }

    @PutMapping("/undo")
    public void undo() {
        electricService.undo();
    }
    @PutMapping("/redo")
    public void redo() {
        electricService.redo();
    }
}
