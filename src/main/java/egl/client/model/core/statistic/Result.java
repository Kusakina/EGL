package egl.client.model.core.statistic;

import egl.client.model.core.DatabaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Result extends DatabaseEntity implements Comparable<Result> {

    public static final Result NONE = new Result(-1, 1);

    private int correctAnswers;
    private int totalAnswers;

    public Result() {
        this(0, 0);
    }

    private Result(int correctAnswers, int totalAnswers) {
        this.correctAnswers = correctAnswers;
        this.totalAnswers = totalAnswers;
    }

    public void registerAnswer(boolean isCorrect) {
        ++totalAnswers;
        if (isCorrect) ++correctAnswers;
    }

    public void accumulate(Result other) {
        this.totalAnswers += other.totalAnswers;
        this.correctAnswers += other.correctAnswers;
    }

    /**
     * This > other == this is better than other
     * this.correct / this.total > other.correct / other.total
     * this.correct * other.total > other.correct * this.total
     */
    @Override
    public int compareTo(Result other) {
        if (0 == this.totalAnswers || 0 == other.totalAnswers) return 0;
        return Integer.compare(this.correctAnswers * other.totalAnswers, other.correctAnswers * this.totalAnswers);
    }
}
