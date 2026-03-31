package presentation;

import service.AuthService;
import util.InputUtil;
import java.util.Scanner;

public class AdminMenu {
    private Scanner scanner;

    public AdminMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                  ADMINISTRATOR MENU                                                  ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃          1. Quản lý Danh mục           ┃       2. Quản lý Sản phẩm          ┃         3. Quản lý Đơn hàng            ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                                                                                      ┃");
            System.out.println("┃                                                      0. Đăng xuất                                                    ┃");
            System.out.println("┃                                                                                                                      ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            String choice = InputUtil.getString(scanner, "Lựa chọn của bạn (0-3): ", "Lỗi: Không được để trống!");
            switch (choice) {
                case "1":
                    CategoryMenu categoryMenu = new CategoryMenu();
                    categoryMenu.displayMenu();
                    break;
                case "2":
                    ProductMenu productMenu = new ProductMenu();
                    productMenu.displayMenu();
                    break;
                case "3":
                    OrderMenu orderMenu = new OrderMenu();
                    orderMenu.displayMenu();
                    break;
                case "0":
                    AuthService authService = new AuthService();
                    authService.logout();
                    return;
                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ!");
            }
        }
    }
}