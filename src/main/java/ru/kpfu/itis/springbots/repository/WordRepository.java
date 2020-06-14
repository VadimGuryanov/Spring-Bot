package ru.kpfu.itis.springbots.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.springbots.models.Word;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
}
