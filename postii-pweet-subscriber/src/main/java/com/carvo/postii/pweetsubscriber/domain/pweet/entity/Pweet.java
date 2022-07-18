package com.carvo.postii.pweetsubscriber.domain.pweet.entity;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * Just for repository mapping
 */
@Node
public class Pweet {
    @Id private Long id;
    private String text;
}
