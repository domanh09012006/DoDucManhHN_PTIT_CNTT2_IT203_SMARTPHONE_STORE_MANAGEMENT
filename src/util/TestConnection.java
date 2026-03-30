package util;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Connection con = DBContext.getConnection();

            if (con != null) {
                System.out.println("KẾT NỐI THÀNH CÔNG!");
            } else {
                System.out.println("KẾT NỐI THẤT BẠI!");
            }

        } catch (Exception e) {
            System.out.println("LỖI KẾT NỐI:");
            e.printStackTrace();
        }
    }
}