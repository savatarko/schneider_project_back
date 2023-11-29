package com.example.electric_grid_back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SequenceGenerator(name = "node_generator", sequenceName = "SEQ_NODE", allocationSize = 1)
public class Node extends AbstractNode{
    private int state; //0-> off 1->on 2->loop 3->out of network

    public Node(Long id, double x, double y, int state) {
        super(id, x, y);
        this.state = state;
    }
}
