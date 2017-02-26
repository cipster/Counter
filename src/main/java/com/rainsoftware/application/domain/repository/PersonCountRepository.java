package com.rainsoftware.application.domain.repository;

import com.rainsoftware.application.domain.model.PersonCount;
import com.rainsoftware.application.model.Direction;
import com.rainsoftware.application.model.Poarta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface PersonCountRepository extends CrudRepository<PersonCount, Long> {
    long countByRecordDateBetweenAndPoartaAndDirection(Timestamp lowerLimit, Timestamp upperLimit, Poarta poarta, Direction direction);
}
