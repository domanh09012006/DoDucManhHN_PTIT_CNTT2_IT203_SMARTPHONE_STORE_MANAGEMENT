package presentation;

import model.OrderDetail;
import model.Product;
import service.AuthService;
import service.OrderService;
import service.ProductService;
import util.InputUtil;

import java.util.List;
import java.util.Scanner;

public class CartMenu {
    private List<OrderDetail> cart;
    private OrderService orderService;
    private ProductService productService;
    private Scanner scanner;

    public CartMenu(List<OrderDetail> cart, OrderService orderService) {
        this.cart = cart;
        this.orderService = orderService;
        this.productService = new ProductService();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                      CART MENU                                                       ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃       1. Xem giỏ hàng                  ┃        2. Cập nhật số lượng        ┃             3. Xóa sản phẩm            ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃       4. Thanh toán (Chốt đơn)         ┃        0. Quay lại                 ┃                                        ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            String choice = InputUtil.getString(scanner, "Lựa chọn của bạn (0-4): ", "Lỗi: Không được để trống!");

            switch (choice) {
                case "1":
                    viewCart();
                    break;
                case "2":
                    updateQuantity();
                    break;
                case "3":
                    removeItem();
                    break;
                case "4":
                    if (checkout()) return;
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lỗi: Lựa chọn không hợp lệ!");
            }
        }
    }

    private void viewCart() {
        System.out.println("\n--- GIỎ HÀNG CỦA BẠN ---");
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng hiện đang trống.");
            return;
        }
        double total = 0;
        System.out.printf("%-5s | %-25s | %-10s | %-15s | %-15s\n", "ID", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền");
        System.out.println("--------------------------------------------------------------------------------------");
        for (OrderDetail item : cart) {
            double subTotal = item.getPrice() * item.getQuantity();
            total += subTotal;
            System.out.printf("%-5d | %-25s | %-10d | %,15.0f | %,15.0f\n",
                    item.getProductId(), item.getProductName(), item.getQuantity(), item.getPrice(), subTotal);
        }
        System.out.println("--------------------------------------------------------------------------------------");
        System.out.printf("TỔNG CỘNG: %,.0f VNĐ\n", total);
    }

    private void updateQuantity() {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng đang trống, không có gì để cập nhật!");
            return;
        }
        viewCart();
        int productId = InputUtil.getInt(scanner, "Nhập ID sản phẩm cần cập nhật số lượng (0 để hủy): ", "Lỗi: ID phải là số nguyên!");
        if (productId == 0) return;

        OrderDetail targetItem = null;
        for (OrderDetail item : cart) {
            if (item.getProductId() == productId) {
                targetItem = item;
                break;
            }
        }

        if (targetItem == null) {
            System.out.println("Lỗi: Sản phẩm này không có trong giỏ hàng của bạn!");
            return;
        }

        Product product = productService.getProductById(productId);
        int newQuantity;
        while (true) {
            newQuantity = InputUtil.getInt(scanner, "Nhập số lượng mới (Nhập 0 nếu muốn xóa sản phẩm này): ", "Lỗi: Số lượng phải là số nguyên!");

            if (newQuantity == 0) {
                cart.remove(targetItem);
                System.out.println("Đã xóa sản phẩm khỏi giỏ hàng!");
                return;
            } else if (newQuantity > 0 && newQuantity <= product.getStock()) {
                targetItem.setQuantity(newQuantity);
                System.out.println("Thành công: Đã cập nhật số lượng thành " + newQuantity + " chiếc.");
                return;
            } else {
                System.out.println("Lỗi: Số lượng phải > 0 và không vượt quá tồn kho hiện tại!");
            }
        }
    }

    private void removeItem() {
        if (cart.isEmpty()) {
            System.out.println("Giỏ hàng đang trống, không có gì để xóa!");
            return;
        }
        viewCart();
        int productId = InputUtil.getInt(scanner, "Nhập ID sản phẩm cần xóa khỏi giỏ (0 để hủy): ", "Lỗi: ID phải là số nguyên!");
        if (productId == 0) return;

        boolean removed = cart.removeIf(item -> item.getProductId() == productId);
        if (removed) {
            System.out.println("Đã xóa thành công sản phẩm khỏi giỏ hàng!");
        } else {
            System.out.println("Lỗi: Không tìm thấy sản phẩm có ID này trong giỏ hàng!");
        }
    }

    private boolean checkout() {
        if (cart.isEmpty()) {
            System.out.println("Lỗi: Giỏ hàng trống, không có gì để thanh toán!");
            return false;
        }
        viewCart();
        String confirm;
        while (true) {
            confirm = InputUtil.getString(scanner, "Bạn có chắc chắn muốn chốt đơn không? (Y/N): ", "Lỗi: Không được để trống!");
            if (confirm.equalsIgnoreCase("Y") || confirm.equalsIgnoreCase("N")) {
                break;
            }
            System.out.println("Lỗi: Vui lòng chỉ nhập Y hoặc N!");
        }

        if (confirm.equalsIgnoreCase("Y")) {
            boolean success = orderService.checkout(AuthService.loggedInUser, cart);
            if (success) {
                cart.clear();
                return true;
            }
        } else {
            System.out.println("Đã hủy quá trình thanh toán.");
        }
        return false;
    }
}