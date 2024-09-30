package Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankSQL {
    // SQL클래스에서 CRUD를 사용하기 위한 3가지 객체
    // DB에 접속하기 위한 연결(Connection) 객체 : con
    Connection con;
    // SQL 쿼리문 전달을 위한 (PreparedStatement) 객체 : pstmt
    PreparedStatement pstmt;
    // DB에서 조회(select)한 결과(ResultSet)를 담을 객체 : rs
    ResultSet rs;

    // [1] DB접속
    public void connect() {
        con = DBC.DBConnect();
    }

    // [2] 접속해제
    public void conClose() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 고객생성 메소드
    public int clientNumber() {
        int cNum = 0;

        // BCLIENT 테이블에서 MAX(CNUM)을 조회 : 1 ~ 5단계

        try {
            //(1)
            String sql = "SELECT MAX(CNUM) FROM BCLIENT";
            //(2)
            pstmt = con.prepareStatement(sql);

            // (3) 데이터 입력인데 sql문에 '?'가 없기 때문에 생략

            //(4)
            rs = pstmt.executeQuery();

            //(5)
            if (rs.next()) {
                cNum = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cNum + 1;
    }

    public String accountNumber() {
        String cAccount = null;

        // 카카오뱅크 계좌 : 3333-xx(2자리)-xxxxxxx(7자리)
        // Math.random() 메소드 사용해서 무작위 숫자 생성
        // Math.random() : 0.00001 ~ 0.9999999 까지
        // (Math.random() * 9 ) : 0.0000009 ~ 9.99999998
        // (int)(Math.random() * 9 ) : 0 ~ 9 => 0부터 9 사이의 숫자가 무작위로 생성

        // 3333-
        cAccount = "3333-";

        // 3333-xx
        // 반복문을 2번 돌려서 0부터 9사이의 숫자를 추가(누적)
        for (int i = 1; i <= 2; i++) {
            cAccount += (int) (Math.random() * 10);
        }

        // 3333-xx-
        cAccount += "-";

        // 3333-xx-xxxxxxx
        for (int i = 1; i <= 7; i++) {
            cAccount += (int) (Math.random() * 10);
        }
        // 우선 중복 걱정 x

        return cAccount;
    }

    public void createAccount(Client client) {
        // (1) sql문
        String sql = "INSERT INTO BCLIENT VALUES(?, ?, ?, ?)";

        // (2) 준비
        try {
            pstmt = con.prepareStatement(sql);

            // (3) 데이터입력
            pstmt.setInt(1, client.getcNum());
            pstmt.setString(2, client.getcName());
            pstmt.setString(3, client.getcAccount());
            pstmt.setInt(4, client.getBalance());

            // (4) 실행
            int result = pstmt.executeUpdate();

            // (5) 결과
            if (result > 0) {
                System.out.println("계좌생성 성공");
                System.out.println("고객번호 : " + client.getcNum());
                System.out.println("계좌번호 : " + client.getcAccount());
                System.out.println();
            } else {
                System.out.println("계좌생성 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean checkAccount(String cAccount) {
        boolean checked = false;

        String sql = "SELECT * FROM BCLIENT WHERE CACCOUNT = ?";
        try {
            // 2
            pstmt = con.prepareStatement(sql);

            // 3
            pstmt.setString(1, cAccount);

            // 4 select할때는 rs사용
            rs = pstmt.executeQuery();

            // 5
            checked = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return checked;
    }


    public void deposit(String cAccount, int amount) {
        // 1
        String sql = "UPDATE BCLIENT SET BALANCE = BALANCE + ? WHERE CACCOUNT = ?";

        // 2
        try {
            pstmt = con.prepareStatement(sql);

            // 3
            pstmt.setInt(1, amount);
            pstmt.setString(2, cAccount);

            // 4
            int result = pstmt.executeUpdate();

            // 5
//            if (result > 0){
//                System.out.println("입금 성공");
//            } else {
//                System.out.println("입금 실패");
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public int checkBalance(String cAccount) {
        int balance = 0;
        String sql = "SELECT BALANCE FROM BCLIENT WHERE CACCOUNT = ?";

        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, cAccount);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                balance = rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return balance;
    }


    public void withdraw(String cAccount, int amount) {
        // 1
        String sql = "UPDATE BCLIENT SET BALANCE = BALANCE - ? WHERE CACCOUNT = ?";

        // 2
        try {
            pstmt = con.prepareStatement(sql);

            // 3
            pstmt.setInt(1, amount);
            pstmt.setString(2, cAccount);

            // 4
            int result = pstmt.executeUpdate();

            // 5
//            if (result > 0){
//                System.out.println("출금 성공");
//            } else {
//                System.out.println("출금 실패");
//            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}