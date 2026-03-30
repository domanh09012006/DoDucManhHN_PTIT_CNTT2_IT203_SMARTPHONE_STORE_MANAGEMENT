package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

public class AuthService {
    private UserDAO userDAO;
    public static User loggedInUser = null;
    public AuthService() {
        this.userDAO = new UserDAO();
    }
    public boolean isEmailExist(String email) {
        return userDAO.findByEmail(email.trim()) != null;
    }
    public boolean register(String name, String email, String password, String phone, String address) {
        String hashedPassword = PasswordUtil.hashPassword(password);
        User newUser = new User(name.trim(), email.trim(), hashedPassword, "CUSTOMER", phone.trim(), address.trim());
        if (userDAO.add(newUser)) {
            System.out.println("Đăng ký thành công! Bạn có thể đăng nhập ngay.");
            return true;
        } else {
            System.out.println("Đăng ký thất bại do lỗi hệ thống.");
            return false;
        }
    }
    public User login(String email, String password) {
        User user = userDAO.findByEmail(email.trim());
        if (user == null || !PasswordUtil.checkPassword(password, user.getPassword())) {
            System.out.println("Lỗi: Sai mật khẩu!");
            return null;
        }
        loggedInUser = user;
        System.out.println("Đăng nhập thành công! Chào mừng " + user.getName());
        return user;
    }
    public void logout() {
        loggedInUser = null;
        System.out.println("Đã đăng xuất an toàn.");
    }
}