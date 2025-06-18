package com.swacademy.newsletter.repository.certification;

import com.swacademy.newsletter.domain.certification.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    List<Certification> findTop9ByOrderBySequenceAsc();
    Optional<Certification> findBySequence(int sequence);
}
