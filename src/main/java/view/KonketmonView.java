package view;

import controller.KonketmonController;
import model.Monster;
import model.User;

import java.sql.SQLException;
import java.util.Scanner;

public class KonketmonView {
    Scanner scanner = new Scanner(System.in);
    Monster monster = null;
    // [MODIFIED] 컨트롤러는 생성자에서 받아옵니다.
    KonketmonController konketController;
    public KonketmonView(KonketmonController konketController) {
        this.konketController = konketController;
    }

    public void mainmenu() throws SQLException {

        while (true) {
            // 타이틀 로고 (간단하게)
            System.out.println("=================================================");
            System.out.println("            コンケットモンスター"); // 일본어 원문 감성
            System.out.println("                KONKETMON");      // 우리 게임 제목
            System.out.println("             - Lite Version -");
            System.out.println("=================================================");
            System.out.println();
            System.out.println("        (c) 2025 Konketmon Project Team.");
            System.out.println();
            System.out.println("          PRESS ENTER TO START");
            System.out.println("=================================================");

            // 사용자가 엔터를 칠 때까지 대기
            scanner.nextLine();

            // 메인 메뉴 표시
            displayMainMenu();
        }
    }

    public void displayMainMenu() throws SQLException {
        while (true) {

            clearConsole(); // 화면을 깨끗하게 지우는 함수 (구현 필요)

            System.out.println("=================================================");
            System.out.println("                  [ 메인 메뉴 ]");
            System.out.println("=================================================");
            System.out.println();
            System.out.println("    무엇을 하시겠습니까?");
            System.out.println();
            System.out.println("    ▶  1. 새로 시작하기 (회원 가입)");
            System.out.println("    ▶  2. 이어 하기 (로그인)");
            System.out.println("    ▶  3. 게임 종료하기");
            System.out.println();
            System.out.println("=================================================");
            System.out.print("    입력: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // [MODIFIED] 더 이상 Scanner를 넘겨줄 필요가 없습니다.
                    registerUser(); // 회원 가입 함수 호출
                    break;
                case "2":
                    // [MODIFIED] 더 이상 Scanner를 넘겨줄 필요가 없습니다.
                    loginUser(); // 로그인 함수 호출
                    break;
                case "3":
                    System.out.println("\n    게임을 종료합니다. 안녕히 가십시오...");
                    // [MODIFIED] 컨트롤러를 통해 DB 연결을 닫습니다.
                    konketController.closeConnection();
                    System.exit(0); // 프로그램 종료
                    break;
                default:
                    System.out.println("\n    잘못된 입력입니다. 1, 2, 3 중 하나를 선택해주세요.");
                    sleep(1500); // 1.5초 대기
                    break;
            }
        }
    }

    // --- (이 아래에 registerUser, loginUser 등 다른 함수들을 구현합니다) ---

    // [MODIFIED] registerUser 메서드 수정
    public void registerUser() { // Scanner 파라미터 제거
        clearConsole();
        System.out.println("=================================================");
        System.out.println("                [ 새로 시작하기 ]");
        System.out.println("=================================================");
        System.out.println("\n    콘켓몬 세계에 오신 것을 환영합니다!");
        System.out.print("    사용할 아이디를 입력해주세요: ");
        String username = scanner.nextLine();
        System.out.print("    사용할 비밀번호를 입력해주세요: ");
        String password = scanner.nextLine();

        // --- [MODIFIED] 컨트롤러에게 회원가입 요청 ---
        try {
            boolean isSuccess = konketController.registerUser(username, password);

            if (isSuccess) {
                System.out.println("\n    " + username + " 님, 반갑습니다!");
                System.out.println("    이제 모험을 시작합니다...");
                sleep(2000);
                loggedInMenu(username); // [NEW] 로그인 후 메뉴로 이동
            } else {
                // 컨트롤러가 false를 반환한 경우 (e.g., 아이디 중복)
                System.out.println("\n    회원가입에 실패했습니다. (아이디가 이미 존재합니다)");
                sleep(1500);
            }
        } catch (SQLException e) {
            System.out.println("\n    [DB 오류] 회원가입 중 문제가 발생했습니다.");
            sleep(1500);
        }
        // 회원가입 실패 시 자동으로 displayMainMenu로 돌아갑니다.
    }

    // [MODIFIED] loginUser 메서드 수정
    public void loginUser() { // Scanner 파라미터 제거
        clearConsole();
        System.out.println("=================================================");
        System.out.println("                 [ 이어 하기 ]");
        System.out.println("=================================================");
        System.out.print("\n    아이디: ");
        String username = scanner.nextLine();
        System.out.print("    비밀번호: ");
        String password = scanner.nextLine();

        // --- [MODIFIED] 컨트롤러에게 로그인 요청 ---
        try {
            boolean isLoggedIn = konketController.loginUser(username, password);

            if (isLoggedIn) {
                System.out.println("\n    " + username + " 님, 다시 오셨군요!");
                System.out.println("    모험을 계속합니다...");
                sleep(2000);
                loggedInMenu(username); // [NEW] 로그인 후 메뉴로 이동
            } else {
                System.out.println("\n    아이디 또는 비밀번호가 일치하지 않습니다.");
                sleep(1500);
            }
        } catch (SQLException e) {
            System.out.println("\n    [DB 오류] 로그인 중 문제가 발생했습니다.");
            sleep(1500);
        }
        // 로그인 실패 시 자동으로 displayMainMenu로 돌아갑니다.
    }

    // [NEW] -----------------------------------------------------------------
    // 요청하신 '로그인 후 메인 메뉴' (배틀, 도감, 종료)
    // -----------------------------------------------------------------------
    public void loggedInMenu(String username) throws SQLException {
        boolean isRunning = true;
        while (isRunning) {
            clearConsole(); // 화면을 깨끗하게 지우기
            User user = konketController.getUser();
            // 간단한 필드 아스키 아트
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("  ^^^^      /\\     ^^^         ^^");
            System.out.println("      ^^   /  \\   ^^^^     ^^      /\\");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("    [ " + username + " 님의 모험 수첩 ]");
            System.out.println("-------------------------------------------------");
            System.out.println();
            System.out.println("    무엇을 하시겠습니까?");
            System.out.println();
            System.out.println("    ▶  1. 배틀하기 (풀숲에 들어간다)");
            System.out.println("    ▶  2. 콘켓몬 도감 (지금까지 모은 콘켓몬)");
            System.out.println("    ▶  3. 모험 종료 (로그아웃)");
            System.out.println();
            System.out.println("=================================================");
            System.out.print("    입력: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    startBattleView(user); // 배틀 시작 함수 호출
                    break;
                case "2":
                    showPokedexView(username); // 내 콘켓몬 관리 함수 호출
                    break;
                case "3":
                    System.out.println("\n    모험을 종료하고 메인 메뉴로 돌아갑니다...");
                    if(!user.isSaved()) {
                        System.out.println("    (데이터가 자동으로 저장됩니다)");
                        konketController.saveData(user);
                    }
                    else{
                        clearConsole();
                        System.out.println("도망친 곳에 낙원은 없습니다.");
                        sleep(1500);
                        clearConsole();
                        System.out.println("현재 사용자 계정을 삭제합니다.");
                        konketController.removeUser(user);
                    }
                    sleep(2000); // 2초 대기
                    isRunning = false; // while 루프 종료 -> displayMainMenu로 복귀
                    break;
                default:
                    System.out.println("\n    잘못된 입력입니다. 1, 2, 3 중 하나를 선택해주세요.");
                    sleep(1500); // 1.5초 대기
                    break;
            }
        }
    }

    // [NEW] 배틀 메뉴 (임시)
    public void startBattleView(User user) throws SQLException {
        clearConsole();
        System.out.println("=================================================");
        System.out.println("                [ 야생 풀숲 ]");
        System.out.println("=================================================");
        System.out.println("\n    " + user.getUsername() + "은(는) 풀숲에 발을 들여놓았다...");
        sleep(1000);
        System.out.println("    부스럭...");
        sleep(500);

        try {
            // 컨트롤러가 Map<String, String> 형태로 몬스터 정보를 반환한다고 가정
            Monster monster = konketController.findWildMonster();

            if (monster == null) {
                System.out.println("\n    ...아무것도 나오지 않았다.");
                sleep(500);
                return; // loggedInMenu로 복귀
            }

            String monsterName = monster.getName();

            System.out.println("=================================================");
            System.out.println("    앗! 야생의 " + monsterName + "(이)가 나타났다!");
            System.out.println("=================================================");
            sleep(1000);
            // 실제 배틀 메뉴 호출
            displayBattleMenu(user, monster);


        } catch (SQLException e) {
            System.out.println("\n    [DB 오류] 야생 몬스터를 만나는 중 오류가 발생했습니다.");
            e.printStackTrace();
            sleep(750);
        }
    }

    /**
     * 실제 배틀 메뉴 (아스키 아트 + 1.공격 2.포획 3.도망)
     */
    public void displayBattleMenu(User user, Monster monster) throws SQLException {
        boolean isBattling = true;
        clearConsole();
        while (isBattling) {
            // 1. 아스키 아트 표시
            System.out.println(monster.getAsciiArt()); // 컨트롤러에서 받아온 아스키 아트 출력
            System.out.println("======== "+ monster.getName() +"의 현재 체력 : " + monster.getHP()+" ===========");
            System.out.println("========    나의 현재 체력 : " + user.getHP()+" ===========");
            System.out.println("=================================================");
            System.out.println("\n    무엇을 하시겠습니까?\n");
            System.out.println("-------------------------------------------------");

            // 2. 메뉴 표시
            System.out.println("    ▶  1. 공격");
            System.out.println("    ▶  2. 포획");
            System.out.println("    ▶  3. 도망가기\n");
            System.out.println("=================================================");
            System.out.print("    입력: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // TODO: 컨트롤러의 '공격' 로직 호출
                    boolean battleContinues = konketController.attackMonster(user, monster);
                    if (!battleContinues) {
                        isBattling = false;
                        System.out.println("======== "+ monster.getName() +"에게 승리했다!=========");
                    }
                    else{
                        battleContinues = konketController.attackUser(user,monster);
                        if (!battleContinues) {
                            isBattling = false;
                            System.out.println("======== "+ user.getUsername() +"은 눈 앞이 아득해졌다.. =========");
                        }
                    }

                    sleep(1500);
                    break;
                case "2":
                    // TODO: 컨트롤러의 '포획' 로직 호출
                    boolean isSuccess = konketController.catchMonster(monster);
                    if (isSuccess) {
                        System.out.println("\n    " + monster.getName() + "을(를) 잡았다!");
                        isBattling = false;
                    } else {
                        System.out.println("\n    ...포획에 실패했다!");
                    }

                    sleep(1500);
                    break;
                case "3":
                    System.out.println("\n    " + user.getUsername() + "은(는) 재빨리 도망쳤다!");
                    sleep(1500);
                    isBattling = false; // while 루프 종료 -> loggedInMenu로 복귀
                    break;
                default:
                    System.out.println("\n    잘못된 입력입니다. 1, 2, 3 중 하나를 선택해주세요.");
                    sleep(1500);
                    break;
            }
        }
    }

    // [NEW] 도감 메뉴 (임시)
    public void showPokedexView(String username) {
        clearConsole();
        System.out.println("=================================================");
        System.out.println("                 [ 콘켓몬 도감 ]");
        System.out.println("=================================================");

        // --- 컨트롤러가 DB에서 도감 정보를 가져옴 ---
        // String pokedexData = konketController.getPokedexForUser(username);
        // System.out.println(pokedexData);

        System.out.println("\n    " + username + " 님이 발견/포획한 콘켓몬 목록입니다.");
        System.out.println("\n    (도감 기능 구현 중...)");
        System.out.println("\n    아무 키나 눌러 메뉴로 돌아갑니다.");
        scanner.nextLine();
    }

    // --- 유틸리티 함수 ---

    // 콘솔을 지우는 함수 (운영체제마다 방식이 다름)
    public void clearConsole() {
        // (간단한 구현) 빈 줄을 많이 출력해서 지운 것처럼 보이게 하기
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    // 잠시 대기하는 함수
    public void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            // InterruptedException 처리
        }
    }
}