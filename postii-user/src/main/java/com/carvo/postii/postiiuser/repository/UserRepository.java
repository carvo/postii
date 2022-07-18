package com.carvo.postii.postiiuser.repository;


import com.carvo.postii.postiiuser.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (u:User) WHERE ID(u) = $id RETURN u { id:ID(u), .* }")
    @Override
    Optional<User> findById(Long id);

}
