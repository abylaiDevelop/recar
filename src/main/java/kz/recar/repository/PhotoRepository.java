package kz.recar.repository;

import kz.recar.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
  public Photo findByPath(String path);

  Optional<Photo> findTopByOrderByIdDesc();
}
