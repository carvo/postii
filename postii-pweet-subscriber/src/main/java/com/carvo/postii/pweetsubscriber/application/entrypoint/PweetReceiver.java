package com.carvo.postii.pweetsubscriber.application.entrypoint;

import com.carvo.postii.pweetsubscriber.domain.pweet.AddPweetUsecase;
import com.carvo.postii.pweetsubscriber.infastructure.dto.PweetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
public class PweetReceiver {

	private final AddPweetUsecase addPweetUsecase;

	@RabbitListener(queues = "${spring.rabbitmq.newPweetQueue}")
	public void receiveMessage(@Validated final PweetDto pweetDto) {
		addPweetUsecase.execute(pweetDto);
	}

}
