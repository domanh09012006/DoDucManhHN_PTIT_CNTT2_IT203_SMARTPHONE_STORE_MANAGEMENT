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

    // Giỏ hàng lưu tạm trên RAM
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
            System.out.println("┃       1. Xem danh sách sản phẩm        ┃        2. Thêm vào giỏ hàng        ┃             3. Xem giỏ hàng            ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃       4. Thanh toán (Chốt đơn)         ┃        5. Lịch sử mua hàng         ┃             0. Đăng xuất               ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            String choice = InputUtil.getString(scanner, "Lựa chọn của bạn (0-5): ", "Lỗi: Không được để trống!");

            switch (choice) {
                case "1":
                    productService.displayAll();
                    break;
                case "2":
                    addToCart();
                    break;
                case "3":
                    viewCart();
                    break;
                case "4":
                    checkout();
                    break;
                case "5":
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

        int productId = InputUtil.getInt(scanner, "Nhập ID sản phẩm muốn mua (hoặc nhập 0 để hủy): ", "Lỗi: ID phải là số nguyên!");
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
    private void viewCart() {
        System.out.println("\n--- GIỎ HÀNG CỦA BẠN ---");
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng hiện đang trống.");
            return;
        }
        double total = 0;
        System.out.printf("%-5s | %-20s | %-10s | %-15s | %-15s\n", "ID", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền");
        System.out.println("--------------------------------------------------------------------------------");
        for (OrderDetail item : cart) {
            double subTotal = item.getPrice() * item.getQuantity();
            total += subTotal;
            System.out.printf("%-5d | %-20s | %-10d | %,15.0f | %,15.0f\n",
                    item.getProductId(), item.getProductName(), item.getQuantity(), item.getPrice(), subTotal);
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("TỔNG CỘNG: %,.0f VNĐ\n", total);
    }

    private void checkout() {
        if (cart.isEmpty()) {
            System.out.println("Lỗi: Giỏ hàng trống, không có gì để thanh toán!");
            return;
        }
        viewCart();
        String confirm;
        while (true) {
            confirm = InputUtil.getString(scanner, "Bạn có chắc chắn muốn chốt đơn không? (Y/N): ", "Lỗi: Không được để trống!");
            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
                break;
            }
            System.out.println("Lỗi: Vui lòng chỉ nhập Y  hoặc N!");
        }

        if (confirm.equalsIgnoreCase("Y")) {
            boolean success = orderService.checkout(AuthService.loggedInUser, cart);

            if (success) {
                cart.clear();
            }
        } else {
            System.out.println("Đã hủy quá trình thanh toán. Giỏ hàng của bạn vẫn được giữ nguyên.");
        }
    }
    private void viewOrderHistory() {
        System.out.println("\n--- LỊCH SỬ MUA HÀNG ---");
        orderService.displayOrderHistory(AuthService.loggedInUser.getId());
    }
}