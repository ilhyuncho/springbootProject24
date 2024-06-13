package com.example.cih.domain.reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RefCarSampleRepository extends JpaRepository<RefCarSample, Long> {

    Optional<RefCarSample> findByCarNumber(String carNumber);

}
