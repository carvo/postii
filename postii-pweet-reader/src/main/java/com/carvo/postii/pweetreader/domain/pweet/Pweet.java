package com.carvo.postii.pweetreader.domain.pweet;

import com.carvo.postii.pweetreader.infastructure.dto.DateTimeConversion;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import java.time.OffsetDateTime;

/**
 * !! Be Careful !! Directly exposed
 */
@Node
@Getter
@Setter
@NoArgsConstructor
public class Pweet {
    @Id
    private Long id;
    private Long userId;
    private String text;

    @ConvertWith(converter = DateTimeConversion.class)
    private OffsetDateTime createdAt;

    private String quotedUsername;
    private String quotedText;
}
