package egl.client.service.model.topic;

import egl.client.model.core.DatabaseEntity;
import egl.client.model.local.topic.LocalTopicInfo;

public interface SpecificLocalTopicInfoService<T extends DatabaseEntity> {

    T findBy(LocalTopicInfo localTopicInfo);
}
