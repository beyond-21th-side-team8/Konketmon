package view;

import controller.KonketmonController;

import java.util.Scanner;

public class KonketmonView {


    KonketmonController konketController;
    public KonketmonView(KonketmonController konketController) {
        this.konketController = konketController;
    }

    public void mainmenu() {
        Scanner scanner = new Scanner(System.in);

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
            displayMainMenu(scanner);
        }
    }

    public void displayMainMenu(Scanner scanner) {
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
                    registerUser(scanner); // 회원 가입 함수 호출
                    break;
                case "2":
                    loginUser(scanner); // 로그인 함수 호출
                    break;
                case "3":
                    System.out.println("\n    게임을 종료합니다. 안녕히 가십시오...");
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

    public void registerUser(Scanner scanner) {
        clearConsole();
        System.out.println("=================================================");
        System.out.println("                [ 새로 시작하기 ]");
        System.out.println("=================================================");
        System.out.println("\n    콘켓몬 세계에 오신 것을 환영합니다!");
        System.out.print("    사용할 아이디를 입력해주세요: ");
        String username = scanner.nextLine();
        System.out.print("    사용할 비밀번호를 입력해주세요: ");
        String password = scanner.nextLine();

        // --- (여기에 DB에 사용자 정보를 저장하는 로직 구현) ---

        System.out.println("\n    " + username + " 님, 반갑습니다!");
        System.out.println("    이제 모험을 시작합니다...");
        sleep(2000);
        // loggedInMenu(scanner, username); // 로그인 후 메뉴로 이동
    }

    public void loginUser(Scanner scanner) {
        clearConsole();
        System.out.println("=================================================");
        System.out.println("                 [ 이어 하기 ]");
        System.out.println("=================================================");
        System.out.print("\n    아이디: ");
        String username = scanner.nextLine();
        System.out.print("    비밀번호: ");
        String password = scanner.nextLine();

        // --- (여기에 DB에서 아이디와 비밀번호를 검증하는 로직 구현) ---

        // if (로그인 성공) {
        //     System.out.println("\n    " + username + " 님, 다시 오셨군요!");
        //     System.out.println("    모험을 계속합니다...");
        //     sleep(2000);
        //     loggedInMenu(scanner, username); // 로그인 후 메뉴로 이동
        // } else {
        //     System.out.println("\n    아이디 또는 비밀번호가 일치하지 않습니다.");
        //     sleep(1500);
        // }
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

