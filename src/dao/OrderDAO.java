package dao;

import model.Order;
import model.OrderDetail;
import util.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean placeOrder(Order order, List<OrderDetail> details) {
        Connection conn = null;
        PreparedStatement insertOrderStmt = null;
        PreparedStatement insertDetailStmt = null;
        PreparedStatement updateStockStmt = null;
        ResultSet rs = null;

        String insertOrderSql = "INSERT INTO orders (user_id, total, status) VALUES (?, ?, ?)";
        String insertDetailSql = "INSERT INTO order_details (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        String updateStockSql = "UPDATE products SET stock = stock - ? WHERE id = ?";

        try {
            conn = DBContext.getConnection();

            conn.setAutoCommit(false);

            insertOrderStmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS);
            insertOrderStmt.setInt(1, order.getUserId());
            insertOrderStmt.setDouble(2, order.getTotal());
            insertOrderStmt.setString(3, "PENDING");
            insertOrderStmt.executeUpdate();

            rs = insertOrderStmt.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            } else {
                throw new SQLException("Lỗi: Không thể lấy ID của hóa đơn mới.");
            }

            insertDetailStmt = conn.prepareStatement(insertDetailSql);
            updateStockStmt = conn.prepareStatement(updateStockSql);

            for (OrderDetail item : details) {
                insertDetailStmt.setInt(1, orderId);
                insertDetailStmt.setInt(2, item.getProductId());
                insertDetailStmt.setInt(3, item.getQuantity());
                insertDetailStmt.setDouble(4, item.getPrice());
                insertDetailStmt.addBatch();

                updateStockStmt.setInt(1, item.getQuantity());
                updateStockStmt.setInt(2, item.getProductId());
                updateStockStmt.addBatch();
            }

            insertDetailStmt.executeBatch();
            updateStockStmt.executeBatch();

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.out.println("Lỗi Transaction: Hệ thống sẽ tự động hủy giao dịch này.");
            System.out.println("Chi tiết lỗi: " + e.getMessage());

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Lỗi khi rollback: " + ex.getMessage());
                }
            }
            return false;

        } finally {
            try {
                if (rs != null) rs.close();
                if (insertOrderStmt != null) insertOrderStmt.close();
                if (insertDetailStmt != null) insertDetailStmt.close();
                if (updateStockStmt != null) updateStockStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Lỗi đóng kết nối: " + e.getMessage());
            }
        }

    }
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotal(rs.getDouble("total"));
                    order.setStatus(rs.getString("status"));
                    order.setCreatedAt(rs.getTimestamp("created_at"));
                    list.add(order);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy lịch sử đơn hàng: " + e.getMessage());
        }
        return list;
    }
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT o.*, u.email FROM orders o JOIN users u ON o.user_id = u.id ORDER BY o.created_at DESC";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setTotal(rs.getDouble("total"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setCustomerEmail(rs.getString("email"));

                list.add(order);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách tất cả đơn hàng: " + e.getMessage());
        }
        return list;
    }
    public boolean updateOrderStatus(int orderId, String newStatus) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, orderId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật trạng thái đơn hàng: " + e.getMessage());
            return false;
        }
    }
}