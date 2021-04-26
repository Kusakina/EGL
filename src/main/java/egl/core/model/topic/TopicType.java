package egl.core.model.topic;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import egl.core.model.DescribedData;
import egl.core.model.task.Task;
import egl.core.model.task.Test;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class TopicType extends DescribedData {

    @ManyToOne
    Task theoryTask;

    @ManyToMany
    List<Task> tasks;

    @ManyToOne
    Test test;
}
