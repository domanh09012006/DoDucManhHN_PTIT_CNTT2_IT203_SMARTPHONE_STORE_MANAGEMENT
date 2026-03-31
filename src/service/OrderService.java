package service;

import dao.OrderDAO;
import model.Order;
import model.OrderDetail;
import model.User;

import java.util.List;

public class OrderService {
    private OrderDAO orderDAO;

    public OrderService() {
        this.orderDAO = new OrderDAO();
    }

    public boolean checkout(User customer, List<OrderDetail> cart) {
        if (cart == null || cart.isEmpty()) {
            System.out.println("Lỗi: Giỏ hàng của bạn đang trống. Vui lòng chọn sản phẩm trước khi thanh toán!");
            return false;
        }

        double total = 0;
        for (OrderDetail item : cart) {
            total += item.getPrice() * item.getQuantity();
        }

        Order newOrder = new Order(customer.getId(), total, "PENDING");

        if (orderDAO.placeOrder(newOrder, cart)) {
            System.out.println("--------------------------------------------------");
            System.out.println("THANH TOÁN THÀNH CÔNG!");
            System.out.println("Cảm ơn " + customer.getName() + " đã tin tưởng mua sắm.");
            System.out.printf("Tổng hóa đơn của bạn là: %,.0f VNĐ%n", total);
            System.out.println("--------------------------------------------------");
            return true;
        } else {
            System.out.println("Lỗi: Thanh toán thất bại do sự cố hệ thống. Vui lòng thử lại sau.");
            return false;
        }
    }
    public void displayOrderHistory(int userId) {
        List<Order> orders = orderDAO.getOrdersByUserId(userId);

        if (orders.isEmpty()) {
            System.out.println("Bạn chưa có đơn hàng nào trong hệ thống.");
            return;
        }
        System.out.println("------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-24s | %-16s | %-22s |%n",
                "ID", "Ngày đặt hàng", "Tổng tiền", "Trạng thái");
        System.out.println("------------------------------------------------------------------------------");

        for (Order order : orders) {
            System.out.printf("| %-4d | %-24s | %,14.0f đ | %-22s |%n",
                    order.getId(),
                    order.getCreatedAt().toString(),
                    order.getTotal(),
                    order.getStatus());
        }
        System.out.println("------------------------------------------------------------------------------");
    }
    public void displayAllOrders() {
        List<Order> orders = orderDAO.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Hệ thống hiện chưa có bất kỳ đơn hàng nào.");
            return;
        }
        System.out.println("\n" + "--------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-4s | %-25s | %-23s | %-16s | %-20s |%n",
                "ID", "Email Khách hàng", "Ngày đặt hàng", "Tổng tiền", "Trạng thái");
        System.out.println("--------------------------------------------------------------------------------------------------------");
        for (Order order : orders) {
            System.out.printf("| %-4d | %-25s | %-23s | %,14.0f đ | %-20s |%n",
                    order.getId(),
                    order.getCustomerEmail(),
                    order.getCreatedAt().toString(),
                    order.getTotal(),
                    order.getStatus());
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");
    }
    public void updateOrderStatus(int orderId, String status) {
        if (orderDAO.updateOrderStatus(orderId, status)) {
            System.out.println("Cập nhật trạng thái thành công! Đơn hàng #" + orderId + " -> " + status);
        } else {
            System.out.println("Lỗi: Không thể cập nhật trạng thái. Có thể ID đơn hàng không tồn tại.");
        }
    }
}