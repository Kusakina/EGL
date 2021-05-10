package egl.client.repository;

import egl.core.model.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseDataRepository<T extends DatabaseEntity> extends JpaRepository<T, Long> { }
