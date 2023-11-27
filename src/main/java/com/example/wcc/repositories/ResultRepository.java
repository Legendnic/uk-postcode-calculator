package com.example.wcc.repositories;

import com.example.wcc.models.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ResultRepository extends JpaRepository<Result, Integer>,
  JpaSpecificationExecutor<Result> {
}
