package com.carvo.postii.postiiuser.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.LocalDateTime;

@Node
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id private Long id;
    private String username;
    private LocalDateTime createdAt;

}
