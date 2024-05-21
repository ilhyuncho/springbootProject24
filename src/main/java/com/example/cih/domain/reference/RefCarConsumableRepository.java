package com.example.cih.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefCarConsumableRepository extends JpaRepository<RefCarConsumable, Long> {

    Optional<RefCarConsumable> findById(Long refConsumableId);
}
