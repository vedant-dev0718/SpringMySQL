package spring.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.restapi.model.Developer;


public interface DeveloperRepository extends JpaRepository<Developer, Long> {
}
