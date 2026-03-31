package presentation;

import model.User;
import service.AuthService;
import util.InputUtil;
import java.util.Scanner;

public class AuthMenu {
    private AuthService authService;
    private Scanner scanner;

    public AuthMenu() {
        this.authService = new AuthService();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                       HỆ THỐNG ĐĂNG NHẬP                            ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃         1. Đăng nhập           ┃           2. Đăng ký               ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                            0. Thoát                                 ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn (0-2): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    handleLogin();
                    break;
                case "2":
                    handleRegister();
                    break;
                case "0":
                    System.out.println("Cảm ơn bạn đã sử dụng Cửa hàng Điện thoại!");
                    System.exit(0);
                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ!");
            }
        }
    }

    private void handleLogin() {
        System.out.println("\n--- ĐĂNG NHẬP ---");
        String email;
        while (true) {
            email = InputUtil.getEmail(scanner, "Nhập Email: ", "Lỗi: Email không đúng định dạng!");
            if (authService.isEmailExist(email)) {
                break;
            }
            System.out.println("Lỗi: Tài khoản không tồn tại. Vui lòng nhập lại!");
        }
        User user = null;
        while (true) {
            String password = InputUtil.getString(scanner, "Nhập Mật khẩu: ", "Lỗi: Mật khẩu không được để trống!");
            user = authService.login(email, password);
            if (user != null) {
                break;
            }
        }
        if (user.getRole().equalsIgnoreCase("ADMIN")) {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.displayMenu();
        } else {
            CustomerMenu customerMenu = new CustomerMenu();
            customerMenu.displayMenu();
        }
    }

    private void handleRegister() {
        System.out.println("\n--- ĐĂNG KÝ TÀI KHOẢN ---");
        String name = InputUtil.getString(scanner, "Nhập Tên hiển thị: ", "Lỗi: Tên không được để trống!");
        String email;
        while (true) {
            email = InputUtil.getEmail(scanner, "Nhập Email: ", "Lỗi: Email không đúng định dạng!");
            if (!authService.isEmailExist(email)) {
                break;
            }
            System.out.println("Lỗi: Email này đã được đăng ký. Vui lòng dùng Email khác!");
        }

        String password = InputUtil.getPassword(scanner, "Nhập Mật khẩu (ít nhất 6 ký tự): ", "Lỗi: Mật khẩu phải có ít nhất 6 ký tự!");
        String phone = InputUtil.getPhone(scanner, "Nhập Số điện thoại (10 số, bắt đầu bằng 0): ", "Lỗi: Số điện thoại không hợp lệ!");
        String address = InputUtil.getString(scanner, "Nhập Địa chỉ nhận hàng: ", "Lỗi: Địa chỉ không được để trống!");

        authService.register(name, email, password, phone, address);
    }
}