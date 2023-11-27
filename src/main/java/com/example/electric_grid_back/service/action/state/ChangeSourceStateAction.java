package com.example.electric_grid_back.service.action.state;

import com.example.electric_grid_back.domain.Source;
import com.example.electric_grid_back.repository.SourceRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class ChangeSourceStateAction extends AbstractAction {
    private SourceRepository sourceRepository;
    private Source source;
    private boolean state;

    public ChangeSourceStateAction(SourceRepository sourceRepository, Source source, boolean state) {
        this.sourceRepository = sourceRepository;
        this.source = source;
        this.state = state;
    }

    @Override
    public void doAction() {
        source.setState(state);
        sourceRepository.save(source);
    }

    @Override
    public void undoAction() {
        source.setState(!state);
        sourceRepository.save(source);
    }
}
