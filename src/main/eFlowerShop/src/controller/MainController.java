package main.eFlowerShop.src.controller;

import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;

import java.util.Scanner;

/**
 * 主控制器 - 负责系统流程控制和用户界面导航
 */
public class MainController {
    
    private AuthController authController;
    private CustomerController customerController;
    private StoreController storeController;
    private Scanner scanner;
    
    // 当前登录的用户信息
    private FlowerOwner currentCustomer;
    private FlowerStore currentStore;
    
    public MainController() {
        this.scanner = new Scanner(System.in);
        this.authController = new AuthController(scanner);
        this.customerController = new CustomerController(scanner);
        this.storeController = new StoreController(scanner);
    }
    
    /**
     * 系统主入口
     */
    public void start() {
        System.out.println("==========================================");
        System.out.println("     欢迎使用 eFlowerShop 鲜花商店系统");
        System.out.println("==========================================");
        
        while (true) {
            if (currentCustomer == null && currentStore == null) {
                showMainMenu();
            } else if (currentCustomer != null) {
                customerController.setCurrentCustomer(currentCustomer);
                customerController.showCustomerMenu();
                int choice = getIntInput();
                if (!customerController.handleCustomerChoice(choice)) {
                    logout(); // 用户选择注销
                } else {
                    // 只有在没有注销的情况下才更新当前用户信息
                    currentCustomer = customerController.getCurrentCustomer();
                }
            } else if (currentStore != null) {
                storeController.setCurrentStore(currentStore);
                storeController.showStoreMenu();
                int choice = getIntInput();
                if (!storeController.handleStoreChoice(choice)) {
                    logout(); // 用户选择注销
                } else {
                    // 只有在没有注销的情况下才更新当前商店信息
                    currentStore = storeController.getCurrentStore();
                }
            }
        }
    }
    
    /**
     * 显示主菜单
     */
    private void showMainMenu() {
        System.out.println("\n=== 主菜单 ===");
        System.out.println("1. 顾客登录");
        System.out.println("2. 商店登录");
        System.out.println("3. 顾客注册");
        System.out.println("4. 商店注册");
        System.out.println("0. 退出系统");
        System.out.print("请选择操作: ");
        
        int choice = getIntInput();
        switch (choice) {
            case 1:
                currentCustomer = authController.customerLogin();
                break;
            case 2:
                currentStore = authController.storeLogin();
                break;
            case 3:
                authController.customerRegister();
                break;
            case 4:
                authController.storeRegister();
                break;
            case 0:
                System.out.println("感谢使用，再见！");
                System.exit(0);
                break;
            default:
                System.out.println("无效选择，请重新输入");
        }
    }
    
    /**
     * 注销登录
     */
    private void logout() {
        if (currentCustomer != null) {
            System.out.println("顾客 " + currentCustomer.getOwnerName() + " 已注销登录");
            currentCustomer = null;
            customerController.setCurrentCustomer(null); // 清理CustomerController状态
        } else if (currentStore != null) {
            System.out.println("商店 " + currentStore.getStoreName() + " 已注销登录");
            currentStore = null;
            storeController.setCurrentStore(null); // 清理StoreController状态
        }
        System.out.println("即将返回主菜单...\n");
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
