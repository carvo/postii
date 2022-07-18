package com.carvo.postii.pweetreader.infastructure.repository;

import com.carvo.postii.pweetreader.domain.pweet.Pweet;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PweetRepository extends Repository<Pweet, Long> {

    @Query("CALL { " +
            "   MATCH (u:User)<-[:CREATED_BY]-(p:Pweet)\n" +
            "   WHERE ID(u) = $userId AND NOT (p)<-[:QUOTED_BY]-(:Pweet) \n" +
            "   RETURN p { id: ID(p), userId: ID(u), .*, quotedUsername: null, quotedText: null }\n" +
            "   UNION\n" +
            "   MATCH (u:User)<-[:CREATED_BY]-(p:Pweet)<-[:QUOTED_BY]-(qP:Pweet)-[:CREATED_BY]->(qU:User)\n" +
            "   WHERE ID(u) = $userId \n" +
            "   RETURN p { id: ID(p), userId: ID(u), .*, quotedUsername:qU.username, quotedText: qP.text }\n" +
            "} \n" +
            "RETURN p\n" +
            "ORDER BY p.createdAt DESC\n" +
            "SKIP $skip LIMIT $limit"
    )
    List<Pweet> retrieveUserPweets(@Param("userId") Long userId,
                                       @Param("skip") Integer skip,
                                       @Param("limit") Integer limit);

}
