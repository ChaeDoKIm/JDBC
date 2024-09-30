package Naver;

import java.sql.Date;
import java.util.Scanner;

public class NaverMain {
    public static void main(String[] args) {

        // 메인메뉴
        // [1] 회원가입 [2] 로그인

        // [1] 회원가입
        // 회원 등록하는 작업 실행

        // [2] 로그인
        // 아이디와 비밀번호를 입력받아서 일치여부 확인

        // 로그인 성공시
        // [1] 회원목록 [2] 내 정보 보기 //내 정보 수정 [4] 탈퇴

        // 로그인 실패시
        // 메인 메뉴로 돌아가기

        NaverMember mem = new NaverMember();
        NaveSQL sql = new NaveSQL();
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        int menu = 0;

        boolean run2 = true;
        int menu2 = 0;

        // 접속
        sql.connect();

        while (run) {
            System.out.println("===========================");
            System.out.println("[1] 회원가입\t\t[2] 로그인");
            System.out.println("[3] 종료(접속해제)");
            System.out.println("===========================");
            System.out.print("선택 >> ");
            menu = sc.nextInt();


            switch (menu) {

                case 1: // 회원 가입
                    System.out.println("회원 정보 입력 ");

                    System.out.print("아이디 >> ");
                    String nId = sc.next();

                    System.out.print("비밀번호 >> ");
                    String nPw = sc.next();

                    System.out.print("이름 >> ");
                    String nName = sc.next();

                    System.out.print("생년월일 >> ");
                    String nBirth = sc.next();

                    System.out.print("성별 >> ");
                    String nGender = sc.next();

                    System.out.print("이메일 >> ");
                    String nEmail = sc.next();

                    System.out.print("연락처 >> ");
                    String nPhone = sc.next();

                    mem.setnId(nId);
                    mem.setnPw(nPw);
                    mem.setnName(nName);
                    mem.setnBirth(nBirth);
                    mem.setnGender(nGender);
                    mem.setnEmail(nEmail);
                    mem.setnPhone(nPhone);
                    sql.memJoin(mem);
                    break;

                case 2:  // 로그인
                    System.out.print("ID >> ");
                    nId = sc.next();
                    System.out.print("PW >> ");
                    nPw = sc.next();
                    boolean login = sql.memLogin(nId, nPw);
                    if (login) {
                        System.out.println("로그인 성공");
                        run2 = true;
                        while (run2) {
                            System.out.println("===========================");
                            System.out.println("[1]회원목록\t\t[2]내정보 보기");
                            System.out.println("[3]내정보 수정\t[4]탈퇴");
                            System.out.println("[5]로그아웃");
                            System.out.println("===========================");
                            System.out.print("선택 >> ");
                            menu2 = sc.nextInt();

                            switch (menu2) {
                                case 1: // 회원 목록
                                    sql.memList();
                                    break;

                                case 2: // 내 정보보기
                                    sql.myInfo(nId);
                                    break;

                                case 3: // 내 정보 수정
                                    System.out.println("수정할 정보를 입력해주세요!");

                                    System.out.print("비밀번호 >> ");
                                    nPw = sc.next();

                                    System.out.print("이름 >> ");
                                    nName = sc.next();

                                    System.out.print("생년월일 >> ");
                                    nBirth = sc.next();

                                    System.out.print("성별 >> ");
                                    nGender = sc.next();

                                    System.out.print("이메일 >> ");
                                    nEmail = sc.next();

                                    System.out.print("연락처 >> ");
                                    nPhone = sc.next();

                                    mem.setnId(nId);
                                    mem.setnPw(nPw);
                                    mem.setnName(nName);
                                    mem.setnBirth(nBirth);
                                    mem.setnGender(nGender);
                                    mem.setnEmail(nEmail);
                                    mem.setnPhone(nPhone);
                                    sql.memModify(mem);
                                    break;

                                case 4: // 회원 탈퇴
                                    System.out.println("회원 탈퇴하시겠습니까? (y/n)");
                                    String check = sc.next();

                                    switch (check) {
                                        case "y":
                                            sql.memDelete(nId);
                                            run2 = false;
                                            break;
                                        case "n":
                                            System.out.println("탈퇴를 취소합니다.");
                                            break;
                                        default:
                                            System.out.println("y와 n중에 입력해주세요.");
                                            break;
                                    }
                                    break;


                                case 5:
                                    System.out.println("로그아웃 성공!");
                                    run2 = false;
                                    break;
                                default:
                                    System.out.println("다시 입력해주세요");
                                    break;
                            }
                        }

                    } else {
                        System.out.println("로그인 실패! 아이디와 비밀번호를 다시 확인해주세요");
                    }

                    break;


                case 3:
                    run = false;
                    sql.conClose();     // DB접속 해제
                    System.out.println("프로그램을 종료합니다!");
                    sc.close();
                    break;

                default:
                    System.out.println("잘못입력했습니다!다시 입력하세요!");
                    break;

            }

        }

    }


}
