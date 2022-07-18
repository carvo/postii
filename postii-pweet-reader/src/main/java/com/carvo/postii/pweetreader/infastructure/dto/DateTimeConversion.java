package com.carvo.postii.pweetreader.infastructure.dto;

import org.neo4j.driver.Value;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class DateTimeConversion implements Neo4jPersistentPropertyConverter<OffsetDateTime> {

    @Override
    public Value write(OffsetDateTime source) {
        // must not be used here
        return null;
    }

    @Override
    public OffsetDateTime read(Value source) {
        return source.asOffsetDateTime();
    }
}
