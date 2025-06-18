package com.swacademy.newsletter.web.dto.response.certification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class CertificationResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertificationDto {
        private int sequence;
        private String imageUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CertificationListDto {
        private int totalCount;
        private List<CertificationDto> certifications;
    }
}
