package com.example.wcc.repositories;

import com.example.wcc.models.PostcodeRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostcodeRefRepository extends JpaRepository<PostcodeRef, Integer>,
  JpaSpecificationExecutor<PostcodeRef> {
  Optional<PostcodeRef> findBySourceId(Integer sourceId);

  Optional<PostcodeRef> findByPostcode(String postcode);




}
