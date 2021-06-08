package egl.client.model.local.topic;

import javax.persistence.Entity;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class MigrationInfo extends DatabaseEntity {

}
