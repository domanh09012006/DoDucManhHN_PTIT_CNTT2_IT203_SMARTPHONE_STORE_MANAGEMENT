package presentation;

import service.OrderService;
import util.InputUtil;
import java.util.Scanner;

public class OrderMenu {
    private OrderService orderService;
    private Scanner scanner;

    public OrderMenu() {
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
    }
    public void displayMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                  ORDERS MANAGEMENT                                                   ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃       1. Hiển thị tất cả đơn hàng      ┃      2. Cập nhật trạng thái đơn    ┃              0. Quay lại               ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            String choice = InputUtil.getString(scanner, "Lựa chọn của bạn (0-2): ", "Lỗi: Không được để trống!");
            switch (choice) {
                case "1":
                    orderService.displayAllOrders();
                    break;
                case "2":
                    updateStatus();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ!");
            }
        }
    }
    private void updateStatus() {
        System.out.println("\n--- CẬP NHẬT TRẠNG THÁI ĐƠN HÀNG ---");
        orderService.displayAllOrders();
        int orderId = InputUtil.getInt(scanner, "Nhập ID đơn hàng cần cập nhật (nhập 0 để hủy): ", "Lỗi: ID phải là số nguyên!");
        if (orderId == 0) return;

        System.out.println("Chọn trạng thái mới:");
        System.out.println("1. COMPLETED");
        System.out.println("2. CANCELED");

        String statusChoice = InputUtil.getString(scanner, "Lựa chọn (1-2): ", "Lỗi: Không được để trống!");

        if (statusChoice.equals("1")) {
            orderService.updateOrderStatus(orderId, "COMPLETED");
        } else if (statusChoice.equals("2")) {
            orderService.updateOrderStatus(orderId, "CANCELED");
        } else {
            System.out.println("Lỗi: Lựa chọn trạng thái không hợp lệ!");
        }
    }
}