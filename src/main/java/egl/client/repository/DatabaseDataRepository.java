package egl.client.repository;

import egl.core.model.DatabaseData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseDataRepository<T extends DatabaseData> extends JpaRepository<T, Long> { }
