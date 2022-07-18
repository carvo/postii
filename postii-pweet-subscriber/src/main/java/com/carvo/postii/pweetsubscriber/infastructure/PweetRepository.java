package com.carvo.postii.pweetsubscriber.infastructure;

import com.carvo.postii.pweetsubscriber.domain.pweet.entity.Pweet;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface PweetRepository extends Neo4jRepository<Pweet, Long> {

    @Query("MATCH (u:User)\n" +
            "WHERE ID(u) = $userId\n" +
            "CREATE (u)<-[r:CREATED_BY]-(pweet:Pweet {\n" +
            "  text: $text\n," +
            "  createdAt: dateTime({timezone:'UTC'})\n" +
            "})")
    void createPweet(@Param("userId") Long userId,
                     @Param("text") String text);

    @Query("MATCH (u:User), (quoted:Pweet)\n" +
            "WHERE ID(u) = $userId AND ID(quoted) = $quotedId\n" +
            "CREATE (u)<-[:CREATED_BY]-(pweet:Pweet {\n" +
            "  text: $text,\n" +
            "  createdAt: dateTime({timezone:'UTC'})\n" +
            "})<-[:QUOTED_BY]-(quoted)")
    void createQuotingPweet(@Param("userId") Long userId,
                            @Param("text") String text,
                            @Param("quotedId") Long quotedId);
}
