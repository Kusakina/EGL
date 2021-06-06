package egl.client.repository;

import egl.client.model.core.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository<T extends DatabaseEntity> extends JpaRepository<T, Long> {
}
