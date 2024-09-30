package JDBCTest;

import java.util.Scanner;

public class CRUDMain {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean run = true;
        int menu = 0;

        // CRUD 객체 생성
        CRUD crud = new CRUD();
        // CRUD클래스의 생성자를 호출해서 CRUD 객체를 생성


        while (run) {
            System.out.println("===========================================");
            System.out.println("[1]connect\t\t[2]insert\t\t[3]select");
            System.out.println("[4]update\t\t[5]delete\t\t[6]close");
            System.out.println("[7]EMP");
            System.out.println("===========================================");
            System.out.print("선택 >> ");
            menu = sc.nextInt();

            switch (menu) {
                case 1:  // DB 접속
                    crud.connect();
                    break;

                case 2:  // C : insert()
                    crud.insert();
                    break;

                case 3:
                    crud.select();
                    break;

                case 4:
                    crud.update();
                    break;

                case 5:
                    crud.delete();
                    break;

                case 6:
                    run = false;
                    crud.conClose();
                    sc.close();
                    break;

                case 7:
                    crud.selectEMP();
                    break;
                default:
                    System.out.println("다시 입력하세요.");
                    break;

            }

        }

    }
}

