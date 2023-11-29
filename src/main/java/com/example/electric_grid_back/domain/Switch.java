package com.example.electric_grid_back.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
public class Switch extends AbstractNode {
    private boolean state;

    public Switch(Long id, double x, double y, boolean state) {
        super(id, x, y);
        this.state = state;
    }
}
