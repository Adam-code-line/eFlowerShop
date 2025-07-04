package main.eFlowerShop.src.service;

import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;

/**
 * 用户登录管理器 - 处理顾客和商店的登录验证
 */
public interface UserLoginService {
    
    /**
     * 用户类型枚举
     */
    enum UserType {
        CUSTOMER,    // 顾客
        STORE       // 商店
    }
    
    /**
     * 登录结果封装类
     */
    class LoginResult {
        private boolean success;
        private UserType userType;
        private Object user;  // FlowerOwner 或 FlowerStore
        private String message;
        
        public LoginResult(boolean success, UserType userType, Object user, String message) {
            this.success = success;
            this.userType = userType;
            this.user = user;
            this.message = message;
        }
        
        // Getters
        public boolean isSuccess() { return success; }
        public UserType getUserType() { return userType; }
        public Object getUser() { return user; }
        public String getMessage() { return message; }
        
        public FlowerOwner getCustomer() {
            return userType == UserType.CUSTOMER ? (FlowerOwner) user : null;
        }
        
        public FlowerStore getStore() {
            return userType == UserType.STORE ? (FlowerStore) user : null;
        }
    }
    
    /**
     * 统一登录方法
     * @param email 邮箱
     * @param password 密码
     * @param userType 用户类型
     * @return 登录结果
     */
    LoginResult login(String email, String password, UserType userType);
    
    /**
     * 顾客登录
     */
    FlowerOwner loginAsCustomer(String email, String password);
    
    /**
     * 商店登录
     */
    FlowerStore loginAsStore(String email, String password);
    
    /**
     * 注册新顾客
     */
    boolean registerCustomer(FlowerOwner customer);
    
    /**
     * 注册新商店
     */
    boolean registerStore(FlowerStore store);
    
    /**
     * 验证邮箱是否已存在
     */
    boolean isEmailExists(String email, UserType userType);
}
