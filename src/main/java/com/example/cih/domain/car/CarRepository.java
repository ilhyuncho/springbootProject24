package com.example.cih.domain.car;

import com.example.cih.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CarRepository extends JpaRepository<Car, Long> {

    Page<Car> findAll(Pageable pageable);

    List<Projection.CarSummary> findByUser(User user);
    List<Projection.CarSummary2> findAllByUser(User user);

    @Query("select c from Car c inner join fetch c.carTemps where c.carId = :carId")
    Car findCarWithCarTemps(@Param("carId") Long carid);
    @Query(value = "SELECT carTemp FROM carTemps WHERE carId = ?1",
    nativeQuery = true)
    Set<String> findCarTempsNative(Long carId);

    @EntityGraph(attributePaths = {"imageSet"})     // 같이 로딩해야 하는 속성을 명시
    @Query("select c from Car c where c.carId =:carId")
    Optional<Car> findyByWithImages(Long carId);
}
