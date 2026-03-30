package presentation;

import service.CategoryService;
import util.InputUtil;
import java.util.Scanner;

public class CategoryMenu {
    private CategoryService categoryService;
    private Scanner scanner;

    public CategoryMenu() {
        this.categoryService = new CategoryService();
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("┃                                                CATEGORIES MANAGEMENT                                                 ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┃       1. Hiển thị danh sách danh mục   ┃         2. Thêm danh mục           ┃         3. Sửa danh mục                ┃");
            System.out.println("┃                                        ┃                                    ┃                                        ┃");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┃                                                           ┃                                                          ┃");
            System.out.println("┃                   4. Xóa danh mục                         ┃                         0. Quay lại                      ┃");
            System.out.println("┃                                                           ┃                                                          ┃");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn (0-4): ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    categoryService.displayAll();
                    break;
                case "2":
                    String name = InputUtil.getString(scanner, "Nhập tên danh mục mới: ", "Lỗi: Tên danh mục không được để trống!");
                    categoryService.addCategory(name);
                    break;
                case "3":
                    int updateId = InputUtil.getInt(scanner, "Nhập ID danh mục cần sửa: ", "Lỗi: ID phải là một con số!");
                    if (categoryService.getCategoryById(updateId) == null) {
                        System.out.println("Lỗi: Không tìm thấy danh mục có ID = " + updateId);
                        break;
                    }
                    String newName = InputUtil.getString(scanner, "Nhập tên danh mục mới: ", "Lỗi: Tên danh mục không được để trống!");
                    categoryService.updateCategory(updateId, newName);
                    break;
                case "4":
                    int deleteId = InputUtil.getInt(scanner, "Nhập ID danh mục cần xóa: ", "Lỗi: ID phải là một con số!");
                    categoryService.deleteCategory(deleteId);
                    break;
                case "0":
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 0-4!");
            }
        }
    }
}