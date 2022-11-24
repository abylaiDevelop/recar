package kz.recar.repository;

import kz.recar.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ClubRepository extends JpaRepository<Club, Long> {

}
