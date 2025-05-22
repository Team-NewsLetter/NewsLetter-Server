package com.swacademy.newsletter.web.dto.request.generation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCardNewsRequestDto {

    private String title;
    private String text;

}
