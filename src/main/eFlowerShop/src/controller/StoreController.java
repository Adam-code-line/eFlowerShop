package main.eFlowerShop.src.controller;

import main.eFlowerShop.src.service.FlowerStoreService;
import main.eFlowerShop.src.service.impl.FlowerStoreServiceImpl;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.entity.Flower;
import main.eFlowerShop.src.entity.Account;
import main.eFlowerShop.src.factory.FlowerFactory;

import java.util.List;
import java.util.Scanner;

/**
 * 商店功能控制器
 */
public class StoreController {
    
    private FlowerStoreService flowerStoreService;
    private FlowerFactory flowerFactory;
    private Scanner scanner;
    private FlowerStore currentStore;
    
    public StoreController(Scanner scanner) {
        this.flowerStoreService = new FlowerStoreServiceImpl();
        this.flowerFactory = FlowerFactory.getInstance();
        this.scanner = scanner;
    }
    
    public void setCurrentStore(FlowerStore store) {
        this.currentStore = store;
    }
    
    public FlowerStore getCurrentStore() {
        return currentStore;
    }
    
    /**
     * 显示商店菜单
     */
    public void showStoreMenu() {
        System.out.println("\n=== 商店菜单 ===");
        System.out.println("欢迎，" + currentStore.getStoreName() + "！(商店身份)");
        System.out.println("商店余额: ¥" + String.format("%.2f", currentStore.getBalance()));
        System.out.println("1. 查看库存鲜花");
        System.out.println("2. 查看销售记录");
        System.out.println("3. 培育新鲜花 (商店版 - 批量)");
        System.out.println("4. 补充库存");
        System.out.println("6. 修改商店信息");
        System.out.println("9. 注销登录");
        System.out.print("请选择操作: ");
    }
    
    /**
     * 处理商店选择
     * @param choice 用户选择
     * @return true继续显示菜单，false表示注销登录
     */
    public boolean handleStoreChoice(int choice) {
        switch (choice) {
            case 1:
                viewInventory();
                break;
            case 2:
                viewSalesRecords();
                break;
            case 3:
                cultivateFlowersInBatch();
                break;
            case 4:
                restockFlowers();
                break;
            case 6:
                modifyStoreInfo();
                break;
            case 9:
                return false; // 返回false表示注销登录
            default:
                System.out.println("无效选择，请重新输入");
        }
        return true;
    }
    
    /**
     * 查看商店库存
     */
    private void viewInventory() {
        System.out.println("\n=== 商店库存 ===");
        List<Flower> flowers = flowerStoreService.getFlowersInStock(currentStore.getStoreId());
        
        if (flowers.isEmpty()) {
            System.out.println("库存为空");
            return;
        }
        
        for (Flower flower : flowers) {
            System.out.printf("ID: %d | %s (%s) - %s - %.2f元\n", 
                flower.getFlid(), flower.getFlname(), flower.getFltype(), 
                flower.getFlcolor(), flower.getFlprice());
        }
    }
    
    /**
     * 培育鲜花 - 商店版本
     */
    private void cultivateFlowersInBatch() {
        System.out.println("\n=== 批量培育鲜花 (商店版) ===");
        System.out.println("注意: 商店可以批量培育多朵鲜花");
        System.out.print("请输入鲜花类型: ");
        String flowerType = scanner.nextLine();
        System.out.print("请输入培育数量: ");
        int quantity = getIntInput();
        
        if (quantity <= 0) {
            System.out.println("培育数量必须大于0");
            return;
        }
        
        System.out.print("确认培育 " + quantity + " 朵 " + flowerType + "？(y/n): ");
        String confirm = scanner.nextLine();
        
        if (!"y".equalsIgnoreCase(confirm) && !"yes".equalsIgnoreCase(confirm)) {
            System.out.println("已取消培育操作。");
            return;
        }
        
        System.out.println("正在批量培育 " + quantity + " 朵 " + flowerType + "...");
        
        List<Flower> newFlowers = flowerFactory.cultivateFlowersBatch(flowerType, quantity, currentStore.getStoreId());
        
        int successCount = 0;
        for (Flower flower : newFlowers) {
            if (flowerStoreService.addFlowerToStore(flower, currentStore.getStoreId())) {
                successCount++;
            }
        }
        
        System.out.println("✅ 成功培育了 " + successCount + " 朵鲜花！");
    }
    
    /**
     * 查看销售记录
     */
    private void viewSalesRecords() {
        System.out.println("\n=== 销售记录 ===");
        List<Account> bills = flowerStoreService.getFlowerStoreBill(currentStore.getStoreId());
        
        if (bills.isEmpty()) {
            System.out.println("暂无销售记录");
            return;
        }
        
        for (Account bill : bills) {
            System.out.printf("订单ID: %d | 金额: %.2f | 日期: %s | 状态: %s\n",
                bill.getAccountId(), bill.getAmount(), bill.getDate(), bill.getStatus());
        }
    }
    
    /**
     * 补充库存
     */
    private void restockFlowers() {
        System.out.println("\n=== 补充库存 ===");
        String[] defaultTypes = {"玫瑰", "百合", "康乃馨", "郁金香", "向日葵"};
        
        FlowerStoreServiceImpl serviceImpl = (FlowerStoreServiceImpl) flowerStoreService;
        if (serviceImpl.restockStore(currentStore.getStoreId(), defaultTypes)) {
            System.out.println("库存补充成功！");
        } else {
            System.out.println("库存补充失败");
        }
    }
    
    /**
     * 修改商店信息
     */
    private void modifyStoreInfo() {
        System.out.println("\n=== 修改商店信息 ===");
        
        System.out.println("当前信息:");
        System.out.println("商店名称: " + currentStore.getStoreName());
        System.out.println("商店位置: " + currentStore.getStoreLocation());
        System.out.println("联系方式: " + currentStore.getStoreContact());
        System.out.println("邮箱: " + currentStore.getStoreEmail());
        System.out.println("余额: ¥" + String.format("%.2f", currentStore.getBalance()));
        
        System.out.print("\n是否要修改商店信息？(y/n): ");
        String confirm = scanner.nextLine();
        
        if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
            System.out.print("请输入新的商店名称 (直接回车保持不变): ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                currentStore.setStoreName(newName);
            }
            
            System.out.print("请输入新的商店位置 (直接回车保持不变): ");
            String newLocation = scanner.nextLine();
            if (!newLocation.trim().isEmpty()) {
                currentStore.setStoreLocation(newLocation);
            }
            
            System.out.print("请输入新的联系方式 (直接回车保持不变): ");
            String newContact = scanner.nextLine();
            if (!newContact.trim().isEmpty()) {
                currentStore.setStoreContact(newContact);
            }
            
            try {
                if (flowerStoreService.modifyFlowerStore(currentStore)) {
                    System.out.println("商店信息修改成功！");
                } else {
                    System.out.println("商店信息修改失败！");
                }
            } catch (Exception e) {
                System.out.println("修改失败: " + e.getMessage());
            }
        }
    }
    
    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
