package egl.core.model.task;

import lombok.Data;

@Data
public class Result {

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
