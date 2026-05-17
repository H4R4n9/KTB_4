package kbo.regular;
import java.time.LocalDate;

public class CompleteMatch extends RegularGame {

    public enum Result { HOME_WIN, AWAY_WIN, DRAW }
    private int homeScore;
    private int awayScore;
    private Result result;

    public CompleteMatch(LocalDate matchDate, String stadium, String homeTeam, String awayTeam, int season, int homeScore, int awayScore) {
        super(matchDate, stadium, homeTeam, awayTeam, season);
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.result = calcResult();
    }

    private Result calcResult() {
        if      
        	(homeScore > awayScore) return Result.HOME_WIN;
        else if
        	(awayScore > homeScore) return Result.AWAY_WIN;
        else
        	return Result.DRAW;
    }

    public int getHomeScore() {return homeScore;}
    public int getAwayScore() {return awayScore;}
    public Result getResult() {return result;}

    @Override
    public void printInfo() {
        System.out.println("=== 완료 경기 ===");
        System.out.printf("  날짜   : %s%n", matchDate);
        System.out.printf("  구장   : %s%n", stadium);
        System.out.printf("  시즌   : %d%n", season);
        System.out.printf("  홈팀   : %s%n", homeTeam);
        System.out.printf("  어웨이 : %s%n", awayTeam);
        System.out.printf("  스코어 : %d - %d%n", homeScore, awayScore);
        System.out.printf("  결과   : %s%n", resultLabel());
    }

    private String resultLabel() {
        return switch (result) {
            case HOME_WIN -> homeTeam + " 승";
            case AWAY_WIN -> awayTeam + " 승";
            case DRAW -> "무승부";
        };
    }
}
