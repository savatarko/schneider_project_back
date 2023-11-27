package com.example.electric_grid_back.service.action.connection;

import com.example.electric_grid_back.domain.SwitchLink;
import com.example.electric_grid_back.repository.SwitchLinkRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class SwitchLinkDeleteAction extends AbstractAction {

    private SwitchLinkRepository switchLinkRepository;
    private SwitchLink s;

    public SwitchLinkDeleteAction(SwitchLinkRepository switchLinkRepository, SwitchLink s) {
        this.switchLinkRepository = switchLinkRepository;
        this.s = s;
    }

    @Override
    public void doAction() {
        switchLinkRepository.delete(s);
    }

    @Override
    public void undoAction() {
        switchLinkRepository.save(s);
    }
}
