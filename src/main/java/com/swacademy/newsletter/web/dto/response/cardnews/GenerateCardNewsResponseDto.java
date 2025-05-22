package com.swacademy.newsletter.web.dto.response.cardnews;

import lombok.*;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateCardNewsResponseDto {
    private String title;
    private List<String> summary;
    private List<String> imageUrl; //0번째가 thumbnail
}
