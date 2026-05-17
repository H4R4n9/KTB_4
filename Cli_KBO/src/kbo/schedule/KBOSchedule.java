package kbo.schedule;
import java.time.LocalDate;

public abstract class KBOSchedule {
    protected LocalDate matchDate;
    protected String stadium;

    public KBOSchedule(LocalDate matchDate, String stadium) {
        this.matchDate = matchDate;
        this.stadium   = stadium;
    }

    public LocalDate getMatchDate() { return matchDate; }
    public String getStadium() { return stadium; }
    public abstract void printInfo();
}
 	