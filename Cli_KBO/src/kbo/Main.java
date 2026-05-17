package kbo;

import kbo.regular.CanceledMatch;
import kbo.regular.CanceledMatch.CancelReason;
import kbo.regular.CompleteMatch;
import kbo.regular.RegularGame;
import kbo.schedule.KBOSchedule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    private final Scanner sc = new Scanner(System.in);
    private final List<KBOSchedule> schedules = new ArrayList<>(loadSampleData());

    public static void main(String[] args) {
        new Main().run();
    }

    private void run() {
        boolean isRunning = true;
        while (isRunning) {
            printMainMenu();
            switch (sc.nextLine().trim()) {
                case "1" -> menuSchedule();
                case "2" -> menuResult();
                case "3" -> menuRegister();
                case "0" -> {
                    System.out.println("종료합니다.");
                    isRunning = false;
                }
                default -> System.out.println("올바른 번호를 입력하세요.");
            }
        }
        sc.close();
    }


    private void printMainMenu() {
        System.out.println();
        System.out.println("──────────────────────────");
        System.out.println("    KBO 경기 관리 시스템   ");
        System.out.println("──────────────────────────");
        System.out.println("  1. 일정 조회             ");
        System.out.println("  2. 결과 조회             ");
        System.out.println("  3. 경기 등록             ");
        System.out.println("  0. 종료                 ");
        System.out.println("─────────────────────────");
        System.out.print("선택 > ");

    }


    private void menuSchedule() {
        boolean back = false;
        while (!back) {
            System.out.println("\n[ 일정 조회 ]");
            System.out.println("  1. 전체 일정");
            System.out.println("  2. 구단별 일정");
            System.out.println("  0. 뒤로");
            System.out.print("선택 > ");
            switch (sc.nextLine().trim()) {
                case "1" -> listAll();
                case "2" -> listByTeam();
                case "0" -> back = true;
                default  -> System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }


    private void menuResult() {
        boolean back = false;
        while (!back) {
            System.out.println("\n[ 결과 조회 ]");
            System.out.println("  1. 완료 경기");
            System.out.println("  2. 취소 경기");
            System.out.println("  3. 구단별 결과");
            System.out.println("  0. 뒤로");
            System.out.print("선택 > ");
            switch (sc.nextLine().trim()) {
                case "1" -> listCompleted();
                case "2" -> listCanceled();
                case "3" -> listResultByTeam();
                case "0" -> back = true;
                default  -> System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }


    private void menuRegister() {
        boolean back = false;
        while (!back) {
            System.out.println("\n[ 경기 등록 ]");
            System.out.println("  1. 완료 경기 등록");
            System.out.println("  2. 취소 경기 등록");
            System.out.println("  0. 뒤로");
            System.out.print("선택 > ");
            switch (sc.nextLine().trim()) {
                case "1" -> addCompleteMatch();
                case "2" -> addCanceledMatch();
                case "0" -> back = true;
                default  -> System.out.println("올바른 번호를 입력하세요.");
            }
        }
    }


    private void listAll() {
        System.out.println("\n[전체 일정] " + schedules.size() + "건");
        if (schedules.isEmpty()) {
            System.out.println("  등록된 일정이 없습니다.");
            return;
        }
        for (KBOSchedule s : schedules) {
            s.printInfo();
            System.out.println();
        }
    }

    private void listByTeam() {
        System.out.print("구단명 입력 > ");
        String team = sc.nextLine().trim();
        System.out.println("\n[" + team + " 전체 일정]");
        for (KBOSchedule s : schedules) {
            if (s instanceof RegularGame) {
                RegularGame rg = (RegularGame) s;
                if (rg.involvesTeam(team)) {
                    rg.printInfo();
                    System.out.println();
                }
            }
        }
    }

    private void listCompleted() {
        System.out.println("\n[완료 경기]");
        schedules.stream()
                 .filter(s -> s instanceof CompleteMatch)
                 .forEach(s -> {s.printInfo();});
    }

    private void listCanceled() {
        System.out.println("\n[취소 경기]");
        schedules.stream()
                 .filter(s -> s instanceof CanceledMatch)
                 .forEach(s -> {s.printInfo();});
    }

    private void listResultByTeam() {
        System.out.print("구단명 입력 > ");
        String team = sc.nextLine().trim();
        System.out.println("\n[" + team + " 경기 결과]");
        schedules.stream()
                 .filter(s -> s instanceof CompleteMatch)
                 .map(s -> (CompleteMatch) s)
                 .filter(cm -> cm.involvesTeam(team))
                 .forEach(cm -> {cm.printInfo();});
    }


    private void addCompleteMatch() {
        System.out.println("\n[완료 경기 등록]");
        try {
            System.out.print("경기 날짜 (yyyy-MM-dd) > ");
            LocalDate date   = LocalDate.parse(sc.nextLine().trim());
            System.out.print("구장 > ");        String stadium = sc.nextLine().trim();
            System.out.print("홈팀 > ");        String home    = sc.nextLine().trim();
            System.out.print("어웨이팀 > ");     String away    = sc.nextLine().trim();
            System.out.print("시즌 연도 > ");    int season     = Integer.parseInt(sc.nextLine().trim());
            System.out.print("홈팀 점수 > ");    int hs         = Integer.parseInt(sc.nextLine().trim());
            System.out.print("어웨이 점수 > ");  int as         = Integer.parseInt(sc.nextLine().trim());
            schedules.add(new CompleteMatch(date, stadium, home, away, season, hs, as));
            System.out.println("완료 경기가 등록되었습니다.");
        } catch (Exception e) {
            System.out.println("입력 오류: " + e.getMessage());
        }
    }

    private void addCanceledMatch() {
        System.out.println("\n[취소 경기 등록]");
        try {
            System.out.print("경기 날짜 (yyyy-MM-dd) > ");
            LocalDate date   = LocalDate.parse(sc.nextLine().trim());
            System.out.print("구장 > ");       String stadium = sc.nextLine().trim();
            System.out.print("홈팀 > ");       String home    = sc.nextLine().trim();
            System.out.print("어웨이팀 > ");    String away    = sc.nextLine().trim();
            System.out.print("시즌 연도 > ");   int season     = Integer.parseInt(sc.nextLine().trim());
            System.out.println("취소 사유: 1.우천 2.폭염 3.기타");
            System.out.print("> ");
            CancelReason reason = switch (sc.nextLine().trim()) {
                case "1" -> CancelReason.RAIN;
                case "2" -> CancelReason.HOT;
                default  -> CancelReason.ETC;
            };
            System.out.println("취소 경기가 등록되었습니다.");
        } catch (Exception e) {
            System.out.println("입력 오류: " + e.getMessage());
        }
    }

// 샘플 데이터

    private List<KBOSchedule> loadSampleData() {
        List<KBOSchedule> list = new ArrayList<>();
        list.add(new CompleteMatch(
            LocalDate.of(2026, 5, 13), "잠실야구장",
            "삼성 라이온즈", "LG 트윈스", 2026, 3, 5));
        list.add(new CompleteMatch(
            LocalDate.of(2026, 5, 14), "고척스카이돔",
            "한화 이글스", "키움 히어로즈", 2026, 10, 1));
        list.add(new CompleteMatch(
            LocalDate.of(2026, 5, 14), "사직야구장",
            "NC 다이노스", "롯데 자이언츠", 2026, 5, 4));
        list.add(new CanceledMatch(
            LocalDate.of(2026, 5, 3), "사직야구장",
            "롯데 자이언츠", "삼성 라이온즈", 2026,
            CancelReason.RAIN));
        return list;
    }
}