package com.example.electric_grid_back.repository;

import com.example.electric_grid_back.domain.Node;
import com.example.electric_grid_back.domain.SourceLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceLinkRepository extends JpaRepository<SourceLink, Long> {
}
