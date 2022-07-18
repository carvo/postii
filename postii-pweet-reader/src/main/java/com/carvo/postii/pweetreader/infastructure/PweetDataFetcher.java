package com.carvo.postii.pweetreader.infastructure;

import com.carvo.postii.pweetreader.infastructure.repository.PweetRepository;
import graphql.schema.DataFetcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PweetDataFetcher {

    final PweetRepository repository;

    public DataFetcher allPaged() {
        return env -> {
            final Integer page = env.getArgument("page");
            final Integer size = env.getArgument("size");
            return repository.retrieveUserPweets(env.getArgument("userId"), page * size, size);
        };
    }
}
