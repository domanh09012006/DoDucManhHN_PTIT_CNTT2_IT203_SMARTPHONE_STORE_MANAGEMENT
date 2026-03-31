package presentation;

import model.OrderDetail;
import model.Product;
import service.AuthService;
import service.OrderService;
import service.ProductService;
import util.InputUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
    private ProductService productService;
    private OrderService orderService;
    private Scanner scanner;
    private List<OrderDetail> cart;

    public CustomerMenu() {
        this.productService = new ProductService();
        this.orderService = new OrderService();
        this.scanner = new Scanner(System.in);
        this.cart = new ArrayList<>();
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                  CUSTOMER MENU                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃       1. Xem danh sách sản phẩm        ┃        2. Thêm vào giỏ hàng        ┃         3. Quản lý giỏ hàng            ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃       4. Lịch sử mua hàng              ┃        0. Đăng xuất                ┃                                        ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            String choice = InputUtil.getString(scanner, "Lựa chọn của bạn (0-4): ", "Lỗi: Không được để trống!");

            switch (choice) {
                case "1":
                    productService.displayAll();
                    break;
                case "2":
                    addToCart();
                    break;
                case "3":
                    // Gọi sang file CartMenu và truyền List cart cùng orderService sang đó
                    CartMenu cartMenu = new CartMenu(cart, orderService);
                    cartMenu.displayMenu();
                    break;
                case "4":
                    viewOrderHistory();
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

    private void addToCart() {
        System.out.println("\n--- THÊM VÀO GIỎ HÀNG ---");
        productService.displayAll();

        int productId = InputUtil.getInt(scanner, "Nhập ID sản phẩm muốn mua (nhập 0 để hủy): ", "Lỗi: ID phải là số nguyên!");
        if (productId == 0) return;

        Product product = productService.getProductById(productId);
        if (product == null) {
            System.out.println("Lỗi: Không tìm thấy sản phẩm có ID = " + productId);
            return;
        }

        if (product.getStock() <= 0) {
            System.out.println("Rất tiếc, sản phẩm này hiện đã hết hàng!");
            return;
        }
        int quantity;
        while (true) {
            quantity = InputUtil.getInt(scanner, "Nhập số lượng muốn mua: ", "Lỗi: Số lượng phải là số nguyên!");
            if (quantity > 0 && quantity <= product.getStock()) {
                break;
            }
            System.out.println("Lỗi: Số lượng phải lớn hơn 0 và không được vượt quá số lượng tồn kho (" + product.getStock() + " chiếc)!");
        }
        boolean found = false;
        for (OrderDetail item : cart) {
            if (item.getProductId() == productId) {
                if (item.getQuantity() + quantity > product.getStock()) {
                    System.out.println("Lỗi: Tổng số lượng trong giỏ và số lượng mua thêm đã vượt quá tồn kho!");
                    return;
                }
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            OrderDetail newItem = new OrderDetail(0, productId, quantity, product.getPrice());
            newItem.setProductName(product.getName());
            cart.add(newItem);
        }

        System.out.println("Thành công: Đã thêm " + quantity + " chiếc [" + product.getName() + "] vào giỏ hàng!");
    }

    private void viewOrderHistory() {
        System.out.println("\n--- LỊCH SỬ MUA HÀNG ---");
        orderService.displayOrderHistory(AuthService.loggedInUser.getId());
    }
}