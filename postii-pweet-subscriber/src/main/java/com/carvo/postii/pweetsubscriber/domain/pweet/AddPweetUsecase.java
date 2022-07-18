package com.carvo.postii.pweetsubscriber.domain.pweet;

import com.carvo.postii.pweetsubscriber.infastructure.dto.PweetDto;
import com.carvo.postii.pweetsubscriber.infastructure.PweetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddPweetUsecase {

    private final Validator validator;
    private final PweetRepository repo;

    public void execute(final PweetDto dto) {
        final Set<ConstraintViolation<PweetDto>> violations = validator.validate(dto);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<PweetDto> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }
            //TODO put in dead-letter queue
            log.error("Invalid message body: " + dto + ". Cause: " + sb);
        }
        else {
            if (dto.getQuotedPweetId() == null) {
                repo.createPweet(dto.getUserId(), dto.getText());
            }
            else {
                repo.createQuotingPweet(dto.getUserId(), dto.getText(), dto.getQuotedPweetId());
            }
        }
    }

}
