package com.carvo.postii.postiiuser.repository;


import com.carvo.postii.postiiuser.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRelationshipRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (orig:User) WHERE ID(orig) = $originId\n" +
            "WITH orig\n" +
            "MATCH (target:User) WHERE ID(target) = $targetId\n" +
            "WITH orig, target\n" +
            "WHERE NOT (orig)-[:FOLLOWS]->(target) \n" +
            "CREATE (orig)-[:FOLLOWS]->(target)\n" +
            "RETURN orig {id: ID(orig), .*}")
    Optional<User> addFollowing(@Param("originId") Long originId, @Param("targetId") Long targetId);

    @Query("MATCH (orig:User) WHERE ID(orig) = $originId\n" +
            "WITH orig\n" +
            "MATCH (orig)-[r:FOLLOWS]->(target:User) WHERE ID(target) = $targetId\n" +
            "WITH orig, target, r\n" +
            "DELETE r\n" +
            "RETURN orig {id: ID(orig), .*}")
    Optional<User> removeFollowing(@Param("originId") Long originId, @Param("targetId") Long targetId);

    @Query("MATCH (u:User)-[:FOLLOWS]->(f:User)\n" +
            "WHERE ID(u) = $id\n" +
            "RETURN f {id:ID(f), .*} ")
    List<User> findFollowingsById(@Param("id") Long id);

    @Query("MATCH (u:User)<-[:FOLLOWS]-(f:User)\n" +
            "WHERE ID(u) = $id\n" +
            "RETURN f {id:ID(f), .*} ")
    List<User> findFollowersById(@Param("id") Long id);

}
