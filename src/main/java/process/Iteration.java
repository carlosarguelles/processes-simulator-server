package process;

import lombok.Data;

@Data
public class Iteration {
    private Integer start;
    private Integer end;
    private Integer actualBurstTime;

    public Iteration(Integer start, Integer end) {
        this.start = start;
        this.end = end;
        this.actualBurstTime = 0;
    }
}
