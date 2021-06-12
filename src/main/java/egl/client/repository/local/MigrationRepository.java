package egl.client.repository.local;

import egl.client.model.local.topic.MigrationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigrationRepository extends JpaRepository<MigrationInfo, Long> {
}
