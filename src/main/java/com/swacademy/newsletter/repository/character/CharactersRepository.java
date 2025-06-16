package com.swacademy.newsletter.repository.character;

import com.swacademy.newsletter.domain.character.Characters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharactersRepository extends JpaRepository<Characters, Long> {
}
