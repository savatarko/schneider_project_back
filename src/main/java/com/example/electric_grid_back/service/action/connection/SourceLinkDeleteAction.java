package com.example.electric_grid_back.service.action.connection;

import com.example.electric_grid_back.domain.SourceLink;
import com.example.electric_grid_back.repository.SourceLinkRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class SourceLinkDeleteAction extends AbstractAction {
    private SourceLinkRepository sourceLinkRepository;
    private SourceLink s;

    public SourceLinkDeleteAction(SourceLinkRepository sourceLinkRepository, SourceLink s) {
        this.sourceLinkRepository = sourceLinkRepository;
        this.s = s;
    }

    @Override
    public void doAction() {
        sourceLinkRepository.delete(s);

    }

    @Override
    public void undoAction() {
        sourceLinkRepository.save(s);

    }
}
