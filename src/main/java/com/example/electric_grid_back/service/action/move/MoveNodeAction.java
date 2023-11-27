package com.example.electric_grid_back.service.action.move;

import com.example.electric_grid_back.domain.Node;
import com.example.electric_grid_back.repository.NodeRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class MoveNodeAction extends AbstractAction {
    private NodeRepository nodeRepository;
    private Node node;
    private double xo;
    private double yo;
    private double xn;
    private double yn;

    public MoveNodeAction(NodeRepository nodeRepository, Node node, double xo, double yo, double xn, double yn) {
        this.nodeRepository = nodeRepository;
        this.node = node;
        this.xo = xo;
        this.yo = yo;
        this.xn = xn;
        this.yn = yn;
    }

    @Override
    public void doAction() {
        node.setX(xn);
        node.setY(yn);
        nodeRepository.save(node);
    }

    @Override
    public void undoAction() {
        node.setX(xo);
        node.setY(yo);
        nodeRepository.save(node);
    }
}
