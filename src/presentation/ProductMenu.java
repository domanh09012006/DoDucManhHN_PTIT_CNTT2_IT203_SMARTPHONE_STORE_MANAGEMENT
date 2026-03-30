package presentation;

import model.Product;
import service.ProductService;
import util.InputUtil;
import java.util.Scanner;

public class ProductMenu {
    private ProductService productService;
    private Scanner scanner;

    public ProductMenu() {
        this.productService = new ProductService();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                 PRODUCTS MANAGEMENT                                                  ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃        1. Hiển thị danh sách sản phẩm  ┃           2. Thêm sản phẩm         ┃           3. Cập nhật sản phẩm         ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃           4. Xóa sản phẩm              ┃         5. Tìm kiếm sản phẩm       ┃               0. Quay lại              ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn (0-5): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    productService.displayAll();
                    break;
                case "2":
                    Product newProduct = inputProductData();
                    if (newProduct != null) productService.addProduct(newProduct);
                    break;
                case "3":
                    int updateId = InputUtil.getInt(scanner, "Nhập ID sản phẩm cần sửa: ", "Lỗi: ID phải là một số nguyên!");
                    if (productService.getProductById(updateId) == null) {
                        System.out.println("Lỗi: Không tìm thấy sản phẩm có ID = " + updateId);
                        break;
                    }
                    Product updateData = inputProductData();
                    if (updateData != null) productService.updateProduct(updateId, updateData);
                    break;
                case "4":
                    int deleteId = InputUtil.getInt(scanner, "Nhập ID sản phẩm cần xóa: ", "Lỗi: ID phải là một số nguyên!");
                    productService.deleteProduct(deleteId);
                    break;
                case "5":
                    String keyword = InputUtil.getString(scanner, "Nhập tên sản phẩm cần tìm: ", "Lỗi: Từ khóa không được để trống!");
                    productService.searchProduct(keyword);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 0-5!");
            }
        }
    }

    private Product inputProductData() {
        productService.displayCategoriesForSelection();

        int categoryId = InputUtil.getInt(scanner, "Nhập ID Danh mục (Category ID): ", "Lỗi: ID Danh mục phải là một số nguyên hợp lệ!");
        if (!productService.isCategoryExist(categoryId)) {
            System.out.println("Lỗi: Danh mục có ID = " + categoryId + " không tồn tại!");
            return null;
        }

        String name = InputUtil.getString(scanner, "Nhập tên sản phẩm: ", "Lỗi: Tên sản phẩm không được để trống!");
        String brand = InputUtil.getString(scanner, "Nhập hãng (Brand): ", "Lỗi: Hãng không được để trống!");
        String storage = InputUtil.getString(scanner, "Nhập dung lượng (VD: 128GB): ", "Lỗi: Dung lượng không được để trống!");
        String color = InputUtil.getString(scanner, "Nhập màu sắc: ", "Lỗi: Màu sắc không được để trống!");

        double price;
        while (true) {
            price = InputUtil.getDouble(scanner, "Nhập giá bán (VNĐ): ", "Lỗi: Giá bán phải là một con số!");
            if (price >= 0) break;
            System.out.println("Lỗi: Giá bán không được là số âm!");
        }

        int stock;
        while (true) {
            stock = InputUtil.getInt(scanner, "Nhập số lượng tồn kho: ", "Lỗi: Số lượng phải là một con số!");
            if (stock >= 0) break;
            System.out.println("Lỗi: Số lượng tồn kho không được là số âm!");
        }

        System.out.print("Nhập mô tả ngắn gọn (có thể ấn Enter để bỏ qua): ");
        String desc = scanner.nextLine();

        return new Product(name, brand, storage, color, price, stock, desc, categoryId);
    }
}