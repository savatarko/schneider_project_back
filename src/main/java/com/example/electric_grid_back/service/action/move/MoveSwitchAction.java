package com.example.electric_grid_back.service.action.move;

import com.example.electric_grid_back.domain.Switch;
import com.example.electric_grid_back.repository.SwitchRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class MoveSwitchAction extends AbstractAction {
    private SwitchRepository switchRepository;
    private Switch s;
    private double xo;
    private double yo;
    private double xn;
    private double yn;

    public MoveSwitchAction(SwitchRepository switchRepository, Switch s, double xo, double yo, double xn, double yn) {
        this.switchRepository = switchRepository;
        this.s = s;
        this.xo = xo;
        this.yo = yo;
        this.xn = xn;
        this.yn = yn;
    }

    @Override
    public void doAction() {
        s.setX(xn);
        s.setY(yn);
        switchRepository.save(s);
    }

    @Override
    public void undoAction() {
        s.setX(xo);
        s.setY(yo);
        switchRepository.save(s);
    }
}
