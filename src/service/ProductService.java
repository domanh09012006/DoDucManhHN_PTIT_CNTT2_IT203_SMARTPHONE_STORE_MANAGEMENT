package service;

import dao.CategoryDAO;
import dao.ProductDAO;
import model.Category;
import model.Product;

import java.util.List;

public class ProductService {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    public ProductService() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
    }
    public void displayCategoriesForSelection() {
        System.out.println("\n--- DANH SÁCH DANH MỤC ---");
        for (Category cat : categoryDAO.getAll()) {
            System.out.printf("ID: %-5d | Tên: %s%n", cat.getId(), cat.getName());
        }
        System.out.println("--------------------------");
    }
    public boolean isCategoryExist(int categoryId) {
        return categoryDAO.findById(categoryId) != null;
    }
    public Product getProductById(int id) {
        return productDAO.findById(id);
    }

    // 1. Hiển thị danh sách sản phẩm
    public void displayAll() {
        List<Product> list = productDAO.getAll();
        printProductTable(list);
    }

    // 2. Tìm kiếm sản phẩm
    public void searchProduct(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Lỗi: Vui lòng nhập từ khóa để tìm kiếm!");
            return;
        }
        List<Product> list = productDAO.searchByName(keyword.trim());
        System.out.println("\nKẾT QUẢ TÌM KIẾM CHO: '" + keyword + "'");
        printProductTable(list);
    }

    // 3. Thêm sản phẩm mới
    public void addProduct(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            System.out.println("Lỗi: Tên sản phẩm không được để trống!");
            return;
        }
        if (!isCategoryExist(product.getCategoryId())) {
            System.out.println("Lỗi: Danh mục với ID = " + product.getCategoryId() + " không tồn tại!");
            return;
        }
        if (product.getPrice() < 0) {
            System.out.println("Lỗi: Giá sản phẩm không được là số âm!");
            return;
        }
        if (product.getStock() < 0) {
            System.out.println("Lỗi: Số lượng tồn kho không được là số âm!");
            return;
        }

        if (productDAO.add(product)) {
            System.out.println("Thêm sản phẩm thành công!");
        } else {
            System.out.println("Thêm sản phẩm thất bại do lỗi hệ thống!");
        }
    }

    // 4. Cập nhật sản phẩm
    public void updateProduct(int id, Product newProductData) {
        Product existProduct = productDAO.findById(id);
        if (existProduct == null) {
            System.out.println("Lỗi: Không tìm thấy sản phẩm có ID = " + id);
            return;
        }

        if (!isCategoryExist(newProductData.getCategoryId())) {
            System.out.println("Lỗi: Danh mục với ID = " + newProductData.getCategoryId() + " không tồn tại!");
            return;
        }

        if (newProductData.getPrice() < 0 || newProductData.getStock() < 0) {
            System.out.println("Lỗi: Giá và Tồn kho không được là số âm!");
            return;
        }

        newProductData.setId(id);

        if (productDAO.update(newProductData)) {
            System.out.println("Cập nhật sản phẩm thành công!");
        } else {
            System.out.println("Cập nhật thất bại do lỗi hệ thống!");
        }
    }

    // 5. Xóa sản phẩm
    public void deleteProduct(int id) {
        Product existProduct = productDAO.findById(id);
        if (existProduct == null) {
            System.out.println("Lỗi: Không tìm thấy sản phẩm có ID = " + id);
            return;
        }

        if (productDAO.delete(id)) {
            System.out.println("Xóa sản phẩm thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }

    // 6. In bảng sản phẩm
    private void printProductTable(List<Product> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống.");
            return;
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-30s | %-10s | %-10s | %-20s | %-15s | %-10s | %-15s |%n",
                "ID", "TÊN SẢN PHẨM", "HÃNG", "DUNG LƯỢNG", "MÀU SẮC", "GIÁ (VNĐ)", "TỒN KHO", "DANH MỤC");
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");

        for (Product p : list) {
            String formattedPrice = String.format("%,.0f", p.getPrice());
            System.out.printf("| %-5d | %-30s | %-10s | %-10s | %-20s | %-15s | %-10d | %-15s |%n",
                    p.getId(), p.getName(), p.getBrand(), p.getStorage(), p.getColor(), formattedPrice, p.getStock(), p.getCategoryName());
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
    }
}