package egl.client.model.core.statistic;

import lombok.Data;
import lombok.NonNull;

@Data
public class Result implements Comparable<Result> {

    public static final Result NONE = new Result(-1, 1);
    public static final String NO_DATA = "Нет данных";

    private int correctAnswers;

    private int totalAnswers;

    public Result() {
        this(0, 0);
    }

    public Result(int correctAnswers, int totalAnswers) {
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
    public int compareTo(@NonNull Result other) {
        if (0 == this.totalAnswers || 0 == other.totalAnswers) return 0;
        return Integer.compare(this.correctAnswers * other.totalAnswers, other.correctAnswers * this.totalAnswers);
    }

    @Override
    public String toString() {
        if (NONE.equals(this)) {
            return NO_DATA;
        }

        return correctAnswers + " из " + totalAnswers;
    }
}
