package Naver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NaveSQL {

    Connection con;
    PreparedStatement pstmt;
    ResultSet rs;


    // DB접속 메소드
    public void connect() {
        con = DBC.DBConnect();
    }

    // DB해제 메소드
    public void conClose() {
        try {
            con.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    //회원가입
    public void memJoin(NaverMember mem) {
        try {
            String sql = "INSERT INTO NAVER VALUES(?, ? ,? ,? ,? ,? ,?)";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mem.getnId());
            pstmt.setString(2, mem.getnPw());
            pstmt.setString(3, mem.getnName());
            pstmt.setString(4, mem.getnBirth());
            pstmt.setString(5, mem.getnGender());
            pstmt.setString(6, mem.getnEmail());
            pstmt.setString(7, mem.getnPhone());

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("회원가입 성공!");
            } else {
                System.out.println("회원가입 실패!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // 로그인
    public boolean memLogin(String nId, String nPw) {
        boolean result = false;

        try {
            String sql = "SELECT * FROM NAVER WHERE NID=? AND NPW=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nId);
            pstmt.setString(2, nPw);

            rs = pstmt.executeQuery();
            result = rs.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;

    }

    // 회원 목록
    public void memList() {
        NaverMember mem = new NaverMember();
        ArrayList<NaverMember> memList = new ArrayList<>();

        try {
            String sql = "SELECT NID, NPW, NNAME, TO_CHAR(NBIRTH, 'YYYY-MM-DD'),NGENDER, NEMAIL, NPHONE FROM NAVER";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                mem.setnId(rs.getString(1));
                mem.setnPw(rs.getString(2));
                mem.setnName(rs.getString(3));
                mem.setnBirth(rs.getString(4));
                mem.setnGender(rs.getString(5));
                mem.setnEmail(rs.getString(6));
                mem.setnPhone(rs.getString(7));
                memList.add(mem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < memList.size(); i++) {
            System.out.println(memList.get(i));
        }
        

    }


    // 내정보
    public void myInfo(String nId) {

        try {
            String sql = "SELECT * FROM NAVER WHERE NID=?";
            // String sql = "SELECT NID, NPW, NNAME, TO_CHAR(NBIRTH, 'YYYY-MM-DD'),NGENDER,NEMAIL,NPHONE FROM NAVER WHERE NID=?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("ID\t:\t" + rs.getString(1));
                System.out.println("PW\t:\t" + rs.getString(2));
                System.out.println("NAME\t:\t" + rs.getString(3));
                System.out.println("BIRTH\t:\t" + rs.getString(4));
                System.out.println("GENDER\t:\t" + rs.getString(5));
                System.out.println("EMAIL\t:\t" + rs.getString(6));
                System.out.println("PHONE\t:\t" + rs.getString(7));
                System.out.println();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    // 내 정보 수정
    public void memModify(NaverMember mem) {
        try {
            String sql = "UPDATE NAVER SET NPW=?, NNAME=?, NBIRTH=?, NGENDER=?, NEMAIL=?,  NPHONE=? WHERE NID=?";
            pstmt = con.prepareStatement(sql);

            pstmt.setString(1, mem.getnPw());
            pstmt.setString(2, mem.getnName());
            pstmt.setString(3, mem.getnBirth());
            pstmt.setString(4, mem.getnGender());
            pstmt.setString(5, mem.getnEmail());
            pstmt.setString(6, mem.getnPhone());
            pstmt.setString(7, mem.getnId());

            int result = pstmt.executeUpdate();

            if(result > 0){
                System.out.println("내 정보 수정 성공!");
            } else {
                System.out.println("내 정보 수정 실패!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    // 탈퇴
    public void memDelete(String nId) {

        try {
            String sql = "DELETE FROM NAVER WHERE NID=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, nId);
            int result = pstmt.executeUpdate();

            if (result > 0) {
                System.out.println("회원 탈퇴 성공");
            } else {
                System.out.println("회원 탈퇴 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
