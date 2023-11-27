package com.example.electric_grid_back.service.action.save;

import com.example.electric_grid_back.domain.Switch;
import com.example.electric_grid_back.repository.SwitchRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class SaveSwitchAction extends AbstractAction {

    private SwitchRepository switchRepository;
    private Switch s;

    public SaveSwitchAction(SwitchRepository switchRepository, Switch s) {
        this.switchRepository = switchRepository;
        this.s = s;
    }

    @Override
    public void doAction() {
        switchRepository.save(s);
    }

    @Override
    public void undoAction() {
        switchRepository.delete(s);
    }
}
