package com.example.electric_grid_back.repository;

import com.example.electric_grid_back.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

}
