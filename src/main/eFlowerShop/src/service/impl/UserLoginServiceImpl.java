package main.eFlowerShop.src.service.impl;

import main.eFlowerShop.src.service.UserLoginService;
import main.eFlowerShop.src.dao.FlowerOwnerDao;
import main.eFlowerShop.src.dao.FlowerStoreDao;
import main.eFlowerShop.src.dao.impl.FlowerOwnerDaoImpl;
import main.eFlowerShop.src.dao.impl.FlowerStoreDaoImpl;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;

/**
 * 用户登录服务实现类
 */
public class UserLoginServiceImpl implements UserLoginService {
    
    private FlowerOwnerDao ownerDao;
    private FlowerStoreDao storeDao;
    
    public UserLoginServiceImpl() {
        this.ownerDao = new FlowerOwnerDaoImpl();
        this.storeDao = new FlowerStoreDaoImpl();
    }
    
    @Override
    public LoginResult login(String email, String password, UserType userType) {
        try {
            switch (userType) {
                case CUSTOMER:
                    FlowerOwner customer = loginAsCustomer(email, password);
                    if (customer != null) {
                        return new LoginResult(true, UserType.CUSTOMER, customer, "顾客登录成功");
                    } else {
                        return new LoginResult(false, UserType.CUSTOMER, null, "邮箱或密码错误");
                    }
                    
                case STORE:
                    FlowerStore store = loginAsStore(email, password);
                    if (store != null) {
                        return new LoginResult(true, UserType.STORE, store, "商店登录成功");
                    } else {
                        return new LoginResult(false, UserType.STORE, null, "邮箱或密码错误");
                    }
                    
                default:
                    return new LoginResult(false, null, null, "未知用户类型");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new LoginResult(false, userType, null, "登录过程中发生错误: " + e.getMessage());
        }
    }
    
    @Override
    public FlowerOwner loginAsCustomer(String email, String password) {
        try {
            return ownerDao.validateOwner(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public FlowerStore loginAsStore(String email, String password) {
        try {
            return storeDao.validateStore(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean registerCustomer(FlowerOwner customer) {
        try {
            // 检查邮箱是否已存在
            if (isEmailExists(customer.getOwnerEmail(), UserType.CUSTOMER)) {
                System.out.println("邮箱已存在，无法注册");
                return false;
            }
            
            // 设置默认值
            if (customer.getMoney() == 0) {
                customer.setMoney(1000.0); // 新用户送1000元
            }
            
            int result = ownerDao.addFlowerOwner(customer);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean registerStore(FlowerStore store) {
        try {
            // 检查邮箱是否已存在
            if (isEmailExists(store.getStoreEmail(), UserType.STORE)) {
                System.out.println("邮箱已存在，无法注册");
                return false;
            }
            
            // 设置默认值
            if (store.getBalance() == 0) {
                store.setBalance(10000.0); // 新商店送10000元启动资金
            }
            
            int result = storeDao.addFlowerStore(store);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean isEmailExists(String email, UserType userType) {
        try {
            switch (userType) {
                case CUSTOMER:
                    FlowerOwner owner = ownerDao.getFlowerOwnerByEmail(email);
                    return owner != null;
                    
                case STORE:
                    FlowerStore store = storeDao.getFlowerStoreByEmail(email);
                    return store != null;
                    
                default:
                    return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
