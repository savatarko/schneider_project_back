package com.example.electric_grid_back.service.action.connection;

import com.example.electric_grid_back.domain.SwitchLink;
import com.example.electric_grid_back.repository.SwitchLinkRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class SwitchLinkConnectionAction extends AbstractAction {
    private SwitchLinkRepository switchLinkRepository;
    private SwitchLink s;

    public SwitchLinkConnectionAction(SwitchLinkRepository switchLinkRepository, SwitchLink s) {
        this.switchLinkRepository = switchLinkRepository;
        this.s = s;
    }

    @Override
    public void doAction() {
        switchLinkRepository.save(s);
    }

    @Override
    public void undoAction() {
        switchLinkRepository.delete(s);
    }
}
