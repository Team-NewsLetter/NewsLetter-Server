package com.swacademy.newsletter.service.certification;

import com.swacademy.newsletter.web.dto.response.certification.CertificationResponseDto;

public interface CertificationService {
    CertificationResponseDto.CertificationListDto getCertification(Long userId);
}
