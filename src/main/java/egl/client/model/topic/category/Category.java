package egl.client.model.topic.category;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import egl.client.model.topic.LocalTopic;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Category extends LocalTopic {

    @ManyToMany
    private List<Translation> translations;
}
