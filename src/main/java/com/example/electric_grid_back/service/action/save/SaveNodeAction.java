package com.example.electric_grid_back.service.action.save;

import com.example.electric_grid_back.domain.Node;
import com.example.electric_grid_back.repository.NodeRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class SaveNodeAction extends AbstractAction {

    private NodeRepository nodeRepository;
    private Node node;

    public SaveNodeAction(NodeRepository nodeRepository, Node node) {
        this.nodeRepository = nodeRepository;
        this.node = node;
    }

    @Override
    public void doAction() {
        nodeRepository.save(node);
    }

    @Override
    public void undoAction() {
        nodeRepository.delete(node);
    }
}
