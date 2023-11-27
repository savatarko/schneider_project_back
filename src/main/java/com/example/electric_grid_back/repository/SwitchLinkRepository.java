package com.example.electric_grid_back.repository;

import com.example.electric_grid_back.domain.Node;
import com.example.electric_grid_back.domain.Switch;
import com.example.electric_grid_back.domain.SwitchLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SwitchLinkRepository extends JpaRepository<SwitchLink, Long> {
    Optional<SwitchLink> findByNodeAndLink(Node node, Switch link);

    Optional<List<SwitchLink>> findAllByLink(Switch link);
}
