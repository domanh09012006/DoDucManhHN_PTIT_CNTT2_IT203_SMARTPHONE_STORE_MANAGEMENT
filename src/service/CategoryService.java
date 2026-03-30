package service;

import dao.CategoryDAO;
import model.Category;

import java.util.List;

public class CategoryService {
    private CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    // 1. Hiển thị danh sách
    public void displayAll() {
        List<Category> list = categoryDAO.getAll();
        if (list.isEmpty()) {
            System.out.println("Danh sách danh mục đang trống.");
            return;
        }
        System.out.println("--------------------------------");
        System.out.printf("%-10s | %-30s%n", "ID", "TÊN DANH MỤC");
        System.out.println("--------------------------------");
        for (Category cat : list) {
            System.out.printf("%-10d | %-30s%n", cat.getId(), cat.getName());
        }
        System.out.println("--------------------------------");
    }

    // 2. Thêm mới
    public void addCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.err.println("Lỗi: Tên danh mục không được để trống!");
            return;
        }
        Category newCat = new Category(name.trim());
        if (categoryDAO.findByName(name.trim()) != null) {
            System.out.println("Lỗi: Danh mục " + name.trim() + " đã tồn tại!");
            return;
        }
        if (categoryDAO.add(newCat)) {
            System.out.println("Thêm danh mục thành công!");
        } else {
            System.err.println("Thêm thất bại!");
        }
    }

    // 3. Cập nhật
    public void updateCategory(int id, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            System.err.println("Lỗi: Tên danh mục mới không được để trống!");
            return;
        }
        Category existCat = categoryDAO.findById(id);
        if (existCat == null) {
            System.out.println("Lỗi: Không tìm thấy danh mục có ID = " + id);
            return;
        }
        Category duplicateCat = categoryDAO.findByName(newName.trim());
        if (duplicateCat != null && duplicateCat.getId() != id) {
            System.out.println("Lỗi: Danh mục " + newName.trim() + " đã tồn tại!");
            return;
        }
        if (categoryDAO.update(existCat)) {
            System.out.println("Cập nhật danh mục thành công!");
        } else {
            System.err.println("Cập nhật thất bại!");
        }
    }

    // 4. Xóa
    public void deleteCategory(int id) {
        Category existCat = categoryDAO.findById(id);
        if (existCat == null) {
            System.err.println("Lỗi: Không tìm thấy danh mục có ID = " + id);
            return;
        }
        if (categoryDAO.delete(id)) {
            System.out.println("Xóa danh mục thành công!");
        } else {
            System.err.println("Xóa thất bại! Vui lòng kiểm tra xem danh mục có đang chứa sản phẩm không.");
        }
    }
    public Category getCategoryById(int id) {
        return categoryDAO.findById(id);
    }
}