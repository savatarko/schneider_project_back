package com.example.electric_grid_back.service.action.state;

import com.example.electric_grid_back.domain.Switch;
import com.example.electric_grid_back.repository.SwitchRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class ChangeSwitchStateAction extends AbstractAction {
    private SwitchRepository switchRepository;
    private Switch s;
    private boolean state;

    public ChangeSwitchStateAction(SwitchRepository switchRepository, Switch s, boolean state) {
        this.switchRepository = switchRepository;
        this.s = s;
        this.state = state;
    }

    @Override
    public void doAction() {
        s.setState(state);
        switchRepository.save(s);

    }

    @Override
    public void undoAction() {
        s.setState(!state);
        switchRepository.save(s);
    }
}
