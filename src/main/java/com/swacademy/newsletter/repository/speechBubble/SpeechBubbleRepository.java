package com.swacademy.newsletter.repository.speechBubble;

import com.swacademy.newsletter.domain.speechBubble.SpeechBubble;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SpeechBubbleRepository extends JpaRepository<SpeechBubble, Long> {
    List<SpeechBubble> findByLevel(int level);
}
