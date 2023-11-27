package com.example.electric_grid_back.service.impl;

import com.example.electric_grid_back.domain.*;
import com.example.electric_grid_back.dtos.*;
import com.example.electric_grid_back.exceptions.*;
import com.example.electric_grid_back.repository.*;
import com.example.electric_grid_back.service.ElectricService;
import com.example.electric_grid_back.service.action.AbstractAction;
import com.example.electric_grid_back.service.action.connection.SourceLinkConnectionAction;
import com.example.electric_grid_back.service.action.connection.SourceLinkDeleteAction;
import com.example.electric_grid_back.service.action.connection.SwitchLinkConnectionAction;
import com.example.electric_grid_back.service.action.connection.SwitchLinkDeleteAction;
import com.example.electric_grid_back.service.action.delete.DeleteNodeAction;
import com.example.electric_grid_back.service.action.delete.DeleteSwitchAction;
import com.example.electric_grid_back.service.action.move.MoveNodeAction;
import com.example.electric_grid_back.service.action.move.MoveSourceAction;
import com.example.electric_grid_back.service.action.move.MoveSwitchAction;
import com.example.electric_grid_back.service.action.save.SaveNodeAction;
import com.example.electric_grid_back.service.action.save.SaveSwitchAction;
import com.example.electric_grid_back.service.action.state.ChangeSourceStateAction;
import com.example.electric_grid_back.service.action.state.ChangeSwitchStateAction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ElectricServiceImpl implements ElectricService {
    private NodeRepository nodeRepository;
    private SourceRepository sourceRepository;
    private SwitchRepository switchRepository;
    private SwitchLinkRepository switchLinkRepository;
    private SourceLinkRepository sourceLinkRepository;
    private Map<Node, Boolean> visitedMap;

    private List<AbstractAction> actions = new ArrayList<>();
    private int currentAction = -1;

    public ElectricServiceImpl(NodeRepository nodeRepository, SourceRepository sourceRepository, SwitchRepository switchRepository, SwitchLinkRepository switchLinkRepository, SourceLinkRepository sourceLinkRepository) {
        this.nodeRepository = nodeRepository;
        this.sourceRepository = sourceRepository;
        this.switchRepository = switchRepository;
        this.switchLinkRepository = switchLinkRepository;
        this.sourceLinkRepository = sourceLinkRepository;
    }

    private List<Node> nodes;
    private Source source;
    private List<Switch> switches;
    private List<SwitchLink> cons;
    private SourceLink start;
    private Map<Node, List<Switch>> nodeMap;

    private Map<Switch, List<Node>> switchMap;
    @Override
    public ResponseDto getState() {
        nodes = nodeRepository.findAll();
        source = sourceRepository.findAll().get(0);
        switches = switchRepository.findAll();
        cons = switchLinkRepository.findAll();
        List<SourceLink> tmp = sourceLinkRepository.findAll();
        if(!tmp.isEmpty()) {
            start = sourceLinkRepository.findAll().get(0);
        }
        else{
            List<AbstractNodeDto> responsenodes = new ArrayList<>();
            for(var s:switches){
                AbstractNodeDto abstractNodeDto = new AbstractNodeDto(s.getId(), NodeType.SWITCH, s.getX(), s.getY(), s.isState()?1:0);
                responsenodes.add(abstractNodeDto);
            }
            for(var s:nodes){
                AbstractNodeDto abstractNodeDto = new AbstractNodeDto(s.getId(), NodeType.NODE, s.getX(), s.getY(), s.getState());
                responsenodes.add(abstractNodeDto);
            }
            AbstractNodeDto abstractNodeDto = new AbstractNodeDto(source.getId(), NodeType.SOURCE, source.getX(), source.getY(), source.isState()?1:0);
            responsenodes.add(abstractNodeDto);
            //todo moras da vratis i konekcije izmedju
            List<ConnectionDto> connectionDtos = new ArrayList<>();
            //connectionDtos.add(new ConnectionDto(start.getId(), start.getNode().getId(), start.getSource().getId()));
            for(var con:cons){
                connectionDtos.add(new ConnectionDto(con.getId(), con.getLink().getId(), con.getNode().getId()));
            }
            return new ResponseDto(responsenodes, connectionDtos);
        }
        nodeMap = new HashMap<>();
        switchMap = new HashMap<>();
        visitedMap = new HashMap<>();

        for(Node n :nodes){
            nodeMap.put(n, new ArrayList<>());
            visitedMap.put(n, false);
            n.setState(3);
        }

        for(Switch s: switches){
            switchMap.put(s, new ArrayList<>());
        }

        for(SwitchLink switchLink: cons){
            nodeMap.get(switchLink.getNode()).add(switchLink.getLink());
            switchMap.get(switchLink.getLink()).add(switchLink.getNode());
        }

        List<Node> stack = new ArrayList<>();

        setUp(start.getNode(), stack, start.getSource().isState(), null);

        List<AbstractNodeDto> responsenodes = new ArrayList<>();
        responsenodes.add(new AbstractNodeDto(source.getId(), NodeType.SOURCE, source.getX(), source.getY(), source.isState()?1:0));

        for(var s:switches){
            AbstractNodeDto abstractNodeDto = new AbstractNodeDto(s.getId(), NodeType.SWITCH, s.getX(), s.getY(), s.isState()?1:0);
            responsenodes.add(abstractNodeDto);
        }
        for(var s:nodes){
            AbstractNodeDto abstractNodeDto = new AbstractNodeDto(s.getId(), NodeType.NODE, s.getX(), s.getY(), s.getState());
            responsenodes.add(abstractNodeDto);
        }
        //todo moras da vratis i konekcije izmedju
        List<ConnectionDto> connectionDtos = new ArrayList<>();
        connectionDtos.add(new ConnectionDto(start.getId(), start.getSource().getId(), start.getNode().getId()));
        for(var con:cons){
            connectionDtos.add(new ConnectionDto(con.getId(), con.getLink().getId(), con.getNode().getId()));
        }
        return new ResponseDto(responsenodes, connectionDtos);
    }

    private void setUp(Node current, List<Node> stack, boolean on, Switch lastSwitch){
        if(!stack.isEmpty() && stack.get(stack.size()-1).equals(current)){
            return;
        }
        if(stack.contains(current) && on){
            int i = stack.size() -1;
            boolean loopflag = true;
            while(i>0 && !stack.get(i).equals(current)){
                if(stack.get(i).getState() == 0 || stack.get(i).getState() == 3){
                    loopflag = false;
                }
                for(Switch s:nodeMap.get(stack.get(i))){
                    for(Node n:switchMap.get(s)){
                        if(stack.get(i - 1).equals(n) && s.isState() == false){
                            loopflag = false;
                        }
                    }
                }
                i--;
            }
            if (loopflag) {
                i = stack.size() -1;
                while (i >= 0 && !stack.get(i).equals(current)) {
                    stack.get(i).setState(2);
                    i--;
                }
                current.setState(2);
            }
        }
        else {
            int val = (on) ? 1 : 0;
            if(current.getState()!=1 && current.getState()!=2 && val ==1)
                current.setState(val);
            if(visitedMap.get(current)){
                return;
            }
            current.setState(val);
            stack.add(current);
            visitedMap.put(current, true);
            for(Switch s:nodeMap.get(current)){
                for(Node n:switchMap.get(s)){
                    if(!current.equals(n)){
                        if(lastSwitch!=null && lastSwitch.equals(s)){
                            continue;
                        }
                        setUp(n, stack, on && s.isState(), s);
                    }
                }
            }
            stack.remove(current);
        }
    }

    @Override
    public void removeNode(Long id) {
        Node node = nodeRepository.findById(id).orElse(null);
        if(node!=null){
            List<SwitchLink> switchLinks = switchLinkRepository.findAll();
            List<SwitchLink> contains = new ArrayList<>();
            for(SwitchLink link:switchLinks){
                if(link.getNode() == node){
                    contains.add(link);
                    switchLinkRepository.delete(link);
                }
            }
            SourceLink sourceLink = sourceLinkRepository.findAll().get(0);
            if(sourceLink.getNode() == node){
                sourceLinkRepository.delete(sourceLink);
            }
            else{
                sourceLink = null;
            }
            nodeRepository.delete(node);
            DeleteNodeAction deleteNodeAction = new DeleteNodeAction(nodeRepository, switchLinkRepository, sourceLinkRepository, contains, sourceLink, node);
            addAction(deleteNodeAction);
            return;
        }
        Switch s = switchRepository.findById(id).orElse(null);
        if(s!=null){
            List<SwitchLink> switchLinks = switchLinkRepository.findAll();
            List<SwitchLink> contains = new ArrayList<>();
            for(SwitchLink link:switchLinks){
                if(link.getLink() == s){
                    contains.add(link);
                    switchLinkRepository.delete(link);
                }
            }
            switchRepository.delete(s);
            DeleteSwitchAction deleteSwitchAction = new DeleteSwitchAction(switchRepository, switchLinkRepository, contains, s);
            addAction(deleteSwitchAction);
            return;
        }
        Source source = sourceRepository.findById(id).orElse(null);
        if(source!=null){
            throw new NodeCantBeDeletedException();
        }
        throw new CantFindNodeException();
    }

    @Override
    public Long createNode(NewNodeDto nodeDto) {
        if(nodeDto.getType().equals(NodeType.NODE)){
            Node node = new Node();
            node.setX(nodeDto.getX());
            node.setY(nodeDto.getY());
            node.setState(3);
            nodeRepository.save(node);
            SaveNodeAction saveNodeAction = new SaveNodeAction(nodeRepository, node);
            addAction(saveNodeAction);
            return node.getId();
        }
        else if(nodeDto.getType().equals(NodeType.SWITCH)){
            Switch s = new Switch();
            s.setState(true);
            s.setX(nodeDto.getX());
            s.setY(nodeDto.getY());
            switchRepository.save(s);
            SaveSwitchAction saveSwitchAction = new SaveSwitchAction(switchRepository, s);
            addAction(saveSwitchAction);
            return s.getId();
        }
        else if(nodeDto.getType().equals(NodeType.SOURCE)){
            throw new OnlyOneSourceException();
        }
        throw new CustomException("Bad request", ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    @Override
    public void moveNode(AbstractNodeDto nodeDto) {
        Node node = nodeRepository.findById(nodeDto.getId()).orElse(null);
        if(node!=null) {
            MoveNodeAction moveNodeAction = new MoveNodeAction(nodeRepository, node, node.getX(), node.getY(), nodeDto.getX(), nodeDto.getY());
            addAction(moveNodeAction);
            node.setX(nodeDto.getX());
            node.setY(nodeDto.getY());
            nodeRepository.save(node);
            return;
        }
        Switch s = switchRepository.findById(nodeDto.getId()).orElse(null);
        if(s!=null){
            MoveSwitchAction moveSwitchAction = new MoveSwitchAction(switchRepository, s, s.getX(), s.getY(), nodeDto.getX(), nodeDto.getY());
            addAction(moveSwitchAction);
            s.setX(nodeDto.getX());
            s.setY(nodeDto.getY());
            switchRepository.save(s);
            return;
        }

        Source source = sourceRepository.findById(nodeDto.getId()).orElseThrow(CantFindNodeException::new);
        MoveSourceAction moveSourceAction = new MoveSourceAction(sourceRepository, source, source.getX(), source.getY(), nodeDto.getX(), nodeDto.getY());
        addAction(moveSourceAction);
        source.setX(nodeDto.getX());
        source.setY(nodeDto.getY());
        sourceRepository.save(source);

    }

    @Override
    public void makeConnection(Long nodeId, Long connectionId) {
        Node node = nodeRepository.findById(nodeId).orElseThrow(CantFindNodeException::new);
        Switch s = switchRepository.findById(connectionId).orElse(null);
        if(s!=null){
            if(switchLinkRepository.findByNodeAndLink(node, s).isPresent()){
                return;
            }
            SwitchLink switchLink = new SwitchLink();
            switchLink.setNode(node);
            switchLink.setLink(s);
            SwitchLinkConnectionAction switchLinkConnectionAction = new SwitchLinkConnectionAction(switchLinkRepository, switchLink);
            addAction(switchLinkConnectionAction);
            switchLinkRepository.save(switchLink);
            return;
        }
        Source source = sourceRepository.findById(connectionId).orElseThrow(CantFindNodeException::new);
        if(!sourceLinkRepository.findAll().isEmpty()){
            return;
        }
        SourceLink sourceLink = new SourceLink();
        sourceLink.setNode(node);
        sourceLink.setSource(source);
        SourceLinkConnectionAction sourceLinkConnectionAction = new SourceLinkConnectionAction(sourceLinkRepository, sourceLink);
        addAction(sourceLinkConnectionAction);
        sourceLinkRepository.save(sourceLink);
        return;
    }

    @Override
    public void changeState(Long id) {
        Switch s = switchRepository.findById(id).orElse(null);
        if(s!=null){
            s.setState(!s.isState());
            switchRepository.save(s);
            ChangeSwitchStateAction changeSwitchStateAction = new ChangeSwitchStateAction(switchRepository, s, s.isState());
            addAction(changeSwitchStateAction);
            return;
        }
        Source source = sourceRepository.findById(id).orElseThrow(CantFindNodeException::new);
        source.setState(!source.isState());
        ChangeSourceStateAction changeSourceStateAction = new ChangeSourceStateAction(sourceRepository, source, source.isState());
        addAction(changeSourceStateAction);
        sourceRepository.save(source);
    }

    @Override
    public void removeLine(Long id) {
        SwitchLink switchLink = switchLinkRepository.findById(id).orElse(null);
        if(switchLink!=null){
            switchLinkRepository.delete(switchLink);
            SwitchLinkDeleteAction switchLinkDeleteAction = new SwitchLinkDeleteAction(switchLinkRepository, switchLink);
            addAction(switchLinkDeleteAction);
            return;
        }
        SourceLink sourceLink = sourceLinkRepository.findById(id).orElseThrow(CantFindNodeException::new);
        sourceLinkRepository.delete(sourceLink);
        SourceLinkDeleteAction sourceLinkDeleteAction = new SourceLinkDeleteAction(sourceLinkRepository, sourceLink);
        addAction(sourceLinkDeleteAction);
    }

    @Override
    public void undo() {
        if(currentAction == -1){
            return;
        }
        actions.get(currentAction).undoAction();
        currentAction--;
    }

    @Override
    public void redo() {
        if(currentAction == actions.size()-1){
            return;
        }
        currentAction++;
        actions.get(currentAction).doAction();

    }
    private void addAction(AbstractAction action){
        if(currentAction != actions.size()-1){
            actions = actions.subList(0, currentAction+1);
        }
        actions.add(action);
        currentAction++;
    }
}
