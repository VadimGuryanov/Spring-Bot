package ru.kpfu.itis.springbots.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.springbots.models.Backward;

@Repository
public interface BackwardRepository extends JpaRepository<Backward, Long> {
}
