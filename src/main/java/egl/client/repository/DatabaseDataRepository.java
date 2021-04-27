package egl.client.repository;

import egl.core.model.DatabaseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface DatabaseDataRepository<T extends DatabaseData> extends JpaRepository<T, Long> { }
