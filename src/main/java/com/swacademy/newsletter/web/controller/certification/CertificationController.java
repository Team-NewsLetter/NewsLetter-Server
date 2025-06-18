package com.swacademy.newsletter.web.controller.certification;

import com.swacademy.newsletter.apiPayload.ApiResponse;
import com.swacademy.newsletter.security.JwtTokenProvider;
import com.swacademy.newsletter.service.certification.CertificationService;
import com.swacademy.newsletter.web.dto.response.certification.CertificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/certifications")
public class CertificationController {

    private final CertificationService certificationService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    public ApiResponse<CertificationResponseDto.CertificationListDto> getCertifications(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtTokenProvider.getUserIdFromToken(token);

        return ApiResponse.onSuccess(certificationService.getCertification(userId));
    }
}
