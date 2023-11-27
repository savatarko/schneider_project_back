package com.example.electric_grid_back.service.action.move;

import com.example.electric_grid_back.domain.Source;
import com.example.electric_grid_back.repository.SourceRepository;
import com.example.electric_grid_back.service.action.AbstractAction;

public class MoveSourceAction extends AbstractAction {
    private SourceRepository sourceRepository;
    private Source source;
    private double xo;
    private double yo;
    private double xn;
    private double yn;

    public MoveSourceAction(SourceRepository sourceRepository, Source source, double xo, double yo, double xn, double yn) {
        this.sourceRepository = sourceRepository;
        this.source = source;
        this.xo = xo;
        this.yo = yo;
        this.xn = xn;
        this.yn = yn;
    }

    @Override
    public void doAction() {
        source.setX(xn);
        source.setY(yn);
        sourceRepository.save(source);
    }

    @Override
    public void undoAction() {
        source.setX(xo);
        source.setY(yo);
        sourceRepository.save(source);
    }
}
