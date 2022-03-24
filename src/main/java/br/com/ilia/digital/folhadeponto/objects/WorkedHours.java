package br.com.ilia.digital.folhadeponto.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Horas Trabalhadas
 *
 * @since 2022-03-23 17:36
 */

@Getter
@Setter
public class WorkedHours {
    private int totalHours, totalMinutes, totalSeconds;

    public WorkedHours(int totalHours, int totalMinutes, int totalSeconds) {
        this.totalHours = totalHours;
        this.totalMinutes = totalMinutes;
        this.totalSeconds = totalSeconds;
    }

    @Override
    public String toString() {
        return "PT" + totalHours + "H" + totalMinutes + "M" + totalSeconds + "S";
    }

    public int getOverallSeconds() {
        return this.totalHours * 60 * 60 + this.totalMinutes * 60 + this.totalSeconds;
    }
}
