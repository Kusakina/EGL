package egl.client.model.core.statistic;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RatingInfo implements Comparable<RatingInfo> {

    private final String name;
    private final Result result;

    @Override
    public int compareTo(RatingInfo other) {
        return -this.result.compareTo(other.result);
    }
}
