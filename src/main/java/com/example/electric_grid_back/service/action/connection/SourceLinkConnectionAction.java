package com.example.electric_grid_back.service.action.connection;

import com.example.electric_grid_back.domain.SourceLink;
import com.example.electric_grid_back.repository.SourceLinkRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class SourceLinkConnectionAction extends AbstractAction{
    private SourceLinkRepository sourceLinkRepository;
    private SourceLink s;

    public SourceLinkConnectionAction(SourceLinkRepository sourceLinkRepository, SourceLink s) {
        this.sourceLinkRepository = sourceLinkRepository;
        this.s = s;
    }

    @Override

    public void doAction() {
        sourceLinkRepository.save(s);

    }

    @Override
    public void undoAction() {
        sourceLinkRepository.delete(s);

    }
}
