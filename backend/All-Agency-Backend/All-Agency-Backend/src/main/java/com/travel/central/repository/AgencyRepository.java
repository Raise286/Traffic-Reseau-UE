
package com.travel.central.repository;
import com.travel.central.domain.model.Agency;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgencyRepository extends ReactiveCrudRepository<Agency, Long> {
}