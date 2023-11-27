package com.example.electric_grid_back.service.action.delete;

import com.example.electric_grid_back.domain.Switch;
import com.example.electric_grid_back.domain.SwitchLink;
import com.example.electric_grid_back.repository.SwitchLinkRepository;
import com.example.electric_grid_back.repository.SwitchRepository;
import com.example.electric_grid_back.service.action.AbstractAction;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public class DeleteSwitchAction extends AbstractAction {
    private SwitchRepository switchRepository;
    private SwitchLinkRepository switchLinkRepository;
    private List<SwitchLink> switchLinks;
    private Switch s;

    public DeleteSwitchAction(SwitchRepository switchRepository, SwitchLinkRepository switchLinkRepository, List<SwitchLink> switchLinks, Switch s) {
        this.switchRepository = switchRepository;
        this.switchLinkRepository = switchLinkRepository;
        this.switchLinks = switchLinks;
        this.s = s;
    }

    @Override
    public void doAction() {
        switchRepository.delete(s);
    }

    @Override
    public void undoAction() {
        switchRepository.save(s);
    }
}
