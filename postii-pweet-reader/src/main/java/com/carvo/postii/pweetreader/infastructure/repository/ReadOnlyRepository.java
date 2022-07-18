package com.carvo.postii.pweetreader.infastructure.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID> extends Neo4jRepository<T, ID> {
    Optional<T> findById(ID id);
    List<T> findAll();
}