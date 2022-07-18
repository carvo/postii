package com.carvo.postii.pweetsubscriber.infastructure.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PweetDto {
    @NotNull(message = "UserId (the author) is required")
    private Long userId;

    @Size(min = 1, max = 777, message = "Text must be between 1 and 777 characters")
    private String text;

    private Long quotedPweetId;
}
