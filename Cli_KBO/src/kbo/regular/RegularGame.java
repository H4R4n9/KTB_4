package kbo.regular;
import java.time.LocalDate;
import kbo.schedule.KBOSchedule;

public abstract class RegularGame extends KBOSchedule {
    protected String homeTeam;
    protected String awayTeam;
    protected int season;

    public RegularGame(LocalDate matchDate, String stadium, String homeTeam, String awayTeam, int season) {
        super(matchDate, stadium);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.season = season;
    }

    public String getHomeTeam() { return homeTeam; }
    public String getAwayTeam() { return awayTeam; }
    public int getSeason() { return season; }

    public boolean involvesTeam(String teamName) {
	return homeTeam.contains(teamName) || awayTeam.contains(teamName);
    }
}