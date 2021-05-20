package egl.core.model.statistic;

import egl.core.model.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Result extends DatabaseEntity {

    private int totalAnswers;
    private int correctAnswers;

    public Result() {
        this.totalAnswers = 0;
        this.correctAnswers = 0;
    }

    public void registerAnswer(boolean isCorrect) {
        ++totalAnswers;
        if (isCorrect) ++correctAnswers;
    }

    public void accumulate(Result other) {
        this.totalAnswers += other.totalAnswers;
        this.correctAnswers += other.correctAnswers;
    }
}
