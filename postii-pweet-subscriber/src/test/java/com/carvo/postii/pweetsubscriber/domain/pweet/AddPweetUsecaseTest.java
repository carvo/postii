package com.carvo.postii.pweetsubscriber.domain.pweet;

import com.carvo.postii.pweetsubscriber.infastructure.PweetRepository;
import com.carvo.postii.pweetsubscriber.infastructure.dto.PweetDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.Collections;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AddPweetUsecaseTest {

    @Mock
    private PweetRepository repository;

    @Mock
    private Validator validator;

    @Mock
    private Logger log;

    @InjectMocks
    private AddPweetUsecase addPweetUsecase;

    @Test
    void shouldAddPweet() {
        final PweetDto p = new PweetDto();
        p.setText("ABC");
        p.setUserId(1L);

        Mockito.when(validator.validate(p)).thenReturn(Collections.emptySet());

        addPweetUsecase.execute(p);

        Mockito.verify(repository, times(1)).createPweet(1L, "ABC");
        Mockito.verify(repository, never()).createQuotingPweet(anyLong(), anyString(), anyLong());
    }

    @Test
    void shouldAddQuotedPweet() {
        final PweetDto p = new PweetDto();
        p.setText("ABC");
        p.setUserId(1L);
        p.setQuotedPweetId(2L);

        Mockito.when(validator.validate(p)).thenReturn(Collections.emptySet());

        addPweetUsecase.execute(p);

        Mockito.verify(repository, times(1)).createQuotingPweet(1L, "ABC", 2L);
        Mockito.verify(repository, never()).createPweet(anyLong(), anyString());
    }

    @Test
    void shouldAddDeadLetterQueueForInvalidPweet() {
        final PweetDto p = new PweetDto();

        addPweetUsecase.execute(p);

        Mockito.verify(repository, never()).createPweet(anyLong(), anyString());
        Mockito.verify(repository, never()).createQuotingPweet(anyLong(), anyString(), anyLong());
    }

}
