package main.eFlowerShop.src.controller;

import main.eFlowerShop.src.service.UserLoginService;
import main.eFlowerShop.src.service.impl.UserLoginServiceImpl;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.factory.FlowerFactory;

import java.util.Scanner;

/**
 * 登录注册控制器
 */
public class AuthController {
    
    private UserLoginService loginService;
    private FlowerFactory flowerFactory;
    private Scanner scanner;
    
    public AuthController(Scanner scanner) {
        this.loginService = new UserLoginServiceImpl();
        this.flowerFactory = FlowerFactory.getInstance();
        this.scanner = scanner;
    }
    
    /**
     * 顾客登录
     */
    public FlowerOwner customerLogin() {
        System.out.println("\n=== 顾客登录 ===");
        System.out.print("邮箱: ");
        String email = scanner.nextLine();
        System.out.print("密码: ");
        String password = scanner.nextLine();
        
        UserLoginService.LoginResult result = loginService.login(email, password, UserLoginService.UserType.CUSTOMER);
        
        if (result.isSuccess()) {
            System.out.println("登录成功！欢迎, " + result.getCustomer().getOwnerName());
            return result.getCustomer();
        } else {
            System.out.println("登录失败: " + result.getMessage());
            return null;
        }
    }
    
    /**
     * 商店登录
     */
    public FlowerStore storeLogin() {
        System.out.println("\n=== 商店登录 ===");
        System.out.print("邮箱: ");
        String email = scanner.nextLine();
        System.out.print("密码: ");
        String password = scanner.nextLine();
        
        UserLoginService.LoginResult result = loginService.login(email, password, UserLoginService.UserType.STORE);
        
        if (result.isSuccess()) {
            System.out.println("登录成功！欢迎, " + result.getStore().getStoreName());
            return result.getStore();
        } else {
            System.out.println("登录失败: " + result.getMessage());
            return null;
        }
    }
    
    /**
     * 顾客注册
     */
    public boolean customerRegister() {
        System.out.println("\n=== 顾客注册 ===");
        System.out.print("姓名: ");
        String name = scanner.nextLine();
        System.out.print("联系方式: ");
        String contact = scanner.nextLine();
        System.out.print("地址: ");
        String address = scanner.nextLine();
        System.out.print("邮箱: ");
        String email = scanner.nextLine();
        System.out.print("密码: ");
        String password = scanner.nextLine();
        
        FlowerOwner customer = new FlowerOwner();
        customer.setOwnerName(name);
        customer.setOwnerContact(contact);
        customer.setOwnerAddress(address);
        customer.setOwnerEmail(email);
        customer.setOwnerPassword(password);
        customer.setMoney(1000.0); // 新用户送1000元
        
        if (loginService.registerCustomer(customer)) {
            System.out.println("注册成功！您获得了1000元新用户奖励！");
            return true;
        } else {
            System.out.println("注册失败，请检查邮箱是否已存在");
            return false;
        }
    }
    
    /**
     * 商店注册
     */
    public boolean storeRegister() {
        System.out.println("\n=== 商店注册 ===");
        System.out.print("商店名称: ");
        String name = scanner.nextLine();
        System.out.print("位置: ");
        String location = scanner.nextLine();
        System.out.print("联系方式: ");
        String contact = scanner.nextLine();
        System.out.print("邮箱: ");
        String email = scanner.nextLine();
        System.out.print("密码: ");
        String password = scanner.nextLine();
        
        FlowerStore store = flowerFactory.createFlowerStore(name, location, contact, email, password);
        
        if (loginService.registerStore(store)) {
            System.out.println("注册成功！您获得了10000元启动资金！");
            return true;
        } else {
            System.out.println("注册失败，请检查邮箱是否已存在");
            return false;
        }
    }
}
