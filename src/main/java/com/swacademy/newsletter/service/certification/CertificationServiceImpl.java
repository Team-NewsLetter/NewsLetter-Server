package com.swacademy.newsletter.service.certification;

import com.swacademy.newsletter.apiPayload.code.status.ErrorStatus;
import com.swacademy.newsletter.apiPayload.exception.handler.CertificationHandler;
import com.swacademy.newsletter.apiPayload.exception.handler.UserHandler;
import com.swacademy.newsletter.domain.certification.Certification;
import com.swacademy.newsletter.domain.user.Users;
import com.swacademy.newsletter.repository.certification.CertificationRepository;
import com.swacademy.newsletter.repository.user.UserRepository;
import com.swacademy.newsletter.web.dto.response.certification.CertificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationServiceImpl implements CertificationService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;

    @Override
    @Transactional(readOnly = true)
    public CertificationResponseDto.CertificationListDto getCertification(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ErrorStatus.USER_NOT_FOUND));

        int count = Math.min(user.getPracticeCount() / 2, 9);

        List<Certification> certifications = certificationRepository.findTop9ByOrderBySequenceAsc();

        List<CertificationResponseDto.CertificationDto> dtoList = certifications.stream()
                .limit(count)
                .map(cert -> CertificationResponseDto.CertificationDto.builder()
                        .sequence(cert.getSequence())
                        .imageUrl(cert.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return CertificationResponseDto.CertificationListDto.builder()
                .totalCount(dtoList.size())
                .certifications(dtoList)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CertificationResponseDto.CertificationImageDto getCertificationImage(int sequence) {
        Certification certification = certificationRepository.findBySequence(sequence)
                .orElseThrow(() -> new CertificationHandler(ErrorStatus.CERTIFICATION_NOT_FOUND));

        return new CertificationResponseDto.CertificationImageDto(certification.getImageUrl());
    }
}
