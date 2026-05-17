package kbo.regular;
import java.time.LocalDate;

public class CanceledMatch extends RegularGame {

    public enum CancelReason { RAIN, HOT, ETC }
    private CancelReason cancelReason;

    public CanceledMatch(LocalDate matchDate, String stadium, String homeTeam, String awayTeam, int season, CancelReason cancelReason) {
        super(matchDate, stadium, homeTeam, awayTeam, season);
        this.cancelReason = cancelReason;}

    public CancelReason getCancelReason() { return cancelReason; }

    @Override
    public void printInfo() {
        System.out.println("=== 취소 경기 ===");
        System.out.printf("  날짜     : %s%n", matchDate);
        System.out.printf("  구장     : %s%n", stadium);
        System.out.printf("  시즌     : %d%n", season);
        System.out.printf("  홈팀     : %s%n", homeTeam);
        System.out.printf("  어웨이   : %s%n", awayTeam);
        System.out.printf("  취소 사유: %s%n", reasonLabel());
    }

    private String reasonLabel() {
        return switch (cancelReason) {
            case RAIN -> "우천 취소";
            case HOT -> "폭염 취소";
            case ETC -> "기타 취소";
        };
    }
}
