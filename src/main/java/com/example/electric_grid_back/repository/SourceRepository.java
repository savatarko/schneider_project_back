package com.example.electric_grid_back.repository;

import com.example.electric_grid_back.domain.Source;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
}
