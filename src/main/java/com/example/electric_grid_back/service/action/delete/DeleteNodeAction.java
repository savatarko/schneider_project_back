package com.example.electric_grid_back.service.action.delete;

import com.example.electric_grid_back.domain.Node;
import com.example.electric_grid_back.domain.SourceLink;
import com.example.electric_grid_back.domain.SwitchLink;
import com.example.electric_grid_back.repository.NodeRepository;
import com.example.electric_grid_back.repository.SourceLinkRepository;
import com.example.electric_grid_back.repository.SwitchLinkRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

import java.util.List;

public class DeleteNodeAction extends AbstractAction {

    private NodeRepository nodeRepository;
    private SwitchLinkRepository switchLinkRepository;
    private SourceLinkRepository sourceLinkRepository;
    private List<SwitchLink> switchLinks;
    private SourceLink sourceLink;
    private Node node;

    public DeleteNodeAction(NodeRepository nodeRepository, SwitchLinkRepository switchLinkRepository, SourceLinkRepository sourceLinkRepository, List<SwitchLink> switchLinks, SourceLink sourceLinks, Node node) {
        this.nodeRepository = nodeRepository;
        this.switchLinkRepository = switchLinkRepository;
        this.sourceLinkRepository = sourceLinkRepository;
        this.switchLinks = switchLinks;
        this.sourceLink = sourceLinks;
        this.node = node;
    }

    @Override
    public void doAction() {
        switchLinkRepository.deleteAll(switchLinks);
        if(sourceLink!= null)
            sourceLinkRepository.delete(sourceLink);
        nodeRepository.delete(node);
    }

    @Override
    public void undoAction() {
        nodeRepository.save(node);
        switchLinkRepository.saveAll(switchLinks);
        if(sourceLink!= null)
            sourceLinkRepository.save(sourceLink);
    }
}
