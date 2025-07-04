package main.eFlowerShop.src.controller;

import main.eFlowerShop.src.service.FlowerOwnerService;
import main.eFlowerShop.src.service.FlowerStoreService;
import main.eFlowerShop.src.service.impl.FlowerOwnerServiceImpl;
import main.eFlowerShop.src.service.impl.FlowerStoreServiceImpl;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.entity.Flower;

import java.util.List;
import java.util.Scanner;

/**
 * 顾客功能控制器
 */
public class CustomerController {
    
    private FlowerOwnerService flowerOwnerService;
    private FlowerStoreService flowerStoreService;
    private Scanner scanner;
    private FlowerOwner currentCustomer;
    
    public CustomerController(Scanner scanner) {
        this.flowerOwnerService = new FlowerOwnerServiceImpl();
        this.flowerStoreService = new FlowerStoreServiceImpl();
        this.scanner = scanner;
    }
    
    public void setCurrentCustomer(FlowerOwner customer) {
        this.currentCustomer = customer;
    }
    
    public FlowerOwner getCurrentCustomer() {
        return currentCustomer;
    }
    
    /**
     * 显示顾客菜单
     */
    public void showCustomerMenu() {
        System.out.println("\n=== 顾客菜单 ===");
        System.out.println("欢迎，" + currentCustomer.getOwnerName() + "！(顾客身份)");
        System.out.println("当前余额: ¥" + String.format("%.2f", currentCustomer.getMoney()));
        System.out.println("1. 浏览所有鲜花");
        System.out.println("2. 查看购买记录");
        System.out.println("3. 培育新鲜花 (个人版 - 单朵)");
        System.out.println("4. 查看我的鲜花");
        System.out.println("5. 购买鲜花");
        System.out.println("6. 出售鲜花");
        System.out.println("7. 充值余额");
        System.out.println("8. 修改个人信息");
        System.out.println("9. 注销登录");
        System.out.print("请选择操作: ");
    }
    
    /**
     * 处理顾客菜单选择
     */
    public boolean handleCustomerChoice(int choice) {
        switch (choice) {
            case 1:
                browseAllFlowers();
                break;
            case 2:
                viewPurchaseHistory();
                break;
            case 3:
                cultivateFlowerAsCustomer();
                break;
            case 4:
                viewMyFlowers();
                break;
            case 5:
                buyFlowers();
                break;
            case 6:
                sellFlowers();
                break;
            case 7:
                addMoney();
                break;
            case 8:
                modifyCustomerInfo();
                break;
            case 9:
                return false; // 返回false表示注销登录
            default:
                System.out.println("无效选择，请重新输入");
        }
        return true;
    }
    
    /**
     * 浏览所有鲜花
     */
    private void browseAllFlowers() {
        System.out.println("\n=== 所有鲜花列表 ===");
        
        try {
            List<Flower> flowers = flowerOwnerService.getAllFlowers();
            
            if (flowers.isEmpty()) {
                System.out.println("暂无鲜花");
                return;
            }
            
            System.out.println("共找到 " + flowers.size() + " 朵鲜花：");
            System.out.println("--------------------------------------------------------");
            
            for (int i = 0; i < flowers.size(); i++) {
                Flower flower = flowers.get(i);
                
                // 获取商店信息
                String storeInfo = "个人拥有";
                if (flower.getStoreId() != -1 && flower.getStoreId() > 0) {
                    try {
                        FlowerStore store = flowerStoreService.getFlowerStoreById(flower.getStoreId());
                        if (store != null) {
                            storeInfo = store.getStoreName() + " (ID: " + store.getStoreId() + ")";
                        } else {
                            storeInfo = "商店ID: " + flower.getStoreId() + " (未知商店)";
                        }
                    } catch (Exception e) {
                        storeInfo = "商店ID: " + flower.getStoreId() + " (查询失败)";
                    }
                }
                
                // 获取拥有者信息
                String ownerInfo = "未知拥有者";
                if (flower.getOwnerId() > 0) {
                    try {
                        FlowerOwner owner = flowerOwnerService.getFlowerOwnerById(flower.getOwnerId());
                        if (owner != null) {
                            ownerInfo = owner.getOwnerName() + " (ID: " + owner.getOwnerId() + ")";
                        } else {
                            ownerInfo = "拥有者ID: " + flower.getOwnerId() + " (未知用户)";
                        }
                    } catch (Exception e) {
                        ownerInfo = "拥有者ID: " + flower.getOwnerId() + " (查询失败)";
                    }
                }
                
                System.out.printf("%d. [ID:%d] %s (%s) - %s - ¥%.2f\n", 
                    i + 1, flower.getFlid(), flower.getFlname(), flower.getFltype(), 
                    flower.getFlcolor(), flower.getFlprice());
                System.out.printf("    拥有者: %s | 所在商店: %s\n", ownerInfo, storeInfo);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("获取鲜花列表失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 培育鲜花
     */
    private void cultivateFlowerAsCustomer() {
        System.out.println("\n=== 培育新鲜花 (个人版) ===");
        System.out.println("注意: 个人培育只能培育一朵鲜花");
        System.out.print("请输入鲜花类型 (如: 玫瑰, 百合, 康乃馨等): ");
        String flowerType = scanner.nextLine();
        
        System.out.print("确认培育一朵 " + flowerType + "？(y/n): ");
        String confirm = scanner.nextLine();
        
        if (!"y".equalsIgnoreCase(confirm) && !"yes".equalsIgnoreCase(confirm)) {
            System.out.println("已取消培育操作。");
            return;
        }
        
        System.out.println("正在培育 " + flowerType + "...");
        
        Flower newFlower = flowerOwnerService.cultivateFlower(currentCustomer.getOwnerId(), flowerType);
        
        if (newFlower != null) {
            System.out.println("✅ 成功培育了一朵 " + newFlower.getFlname() + "！");
            System.out.println("   颜色: " + newFlower.getFlcolor());
            System.out.println("   价值: ¥" + String.format("%.2f", newFlower.getFlprice()));
            System.out.println("   鲜花已添加到您的个人收藏中。");
        } else {
            System.out.println("❌ 培育失败，请稍后再试。");
        }
    }
    
    /**
     * 查看我的鲜花
     */
    private void viewMyFlowers() {
        System.out.println("\n=== 我的鲜花 ===");
        try {
            List<Flower> myFlowers = flowerOwnerService.getMyFlowers(currentCustomer.getOwnerId());
            
            if (myFlowers.isEmpty()) {
                System.out.println("您还没有任何鲜花");
                return;
            }
            
            double totalValue = 0;
            for (int i = 0; i < myFlowers.size(); i++) {
                Flower flower = myFlowers.get(i);
                System.out.printf("%d. %s (%s) - %s - %.2f元\n", 
                    i + 1, flower.getFlname(), flower.getFltype(), 
                    flower.getFlcolor(), flower.getFlprice());
                totalValue += flower.getFlprice();
            }
            System.out.printf("\n您共有 %d 朵鲜花，总价值: %.2f元\n", myFlowers.size(), totalValue);
            
        } catch (Exception e) {
            System.out.println("查询失败: " + e.getMessage());
        }
    }
    
    /**
     * 购买鲜花
     */
    private void buyFlowers() {
        System.out.println("\n=== 购买鲜花 ===");
        try {
            List<FlowerStore> stores = flowerOwnerService.getAllStores();
            
            if (stores.isEmpty()) {
                System.out.println("暂无商店");
                return;
            }
            
            System.out.println("可选择的商店:");
            for (int i = 0; i < stores.size(); i++) {
                FlowerStore store = stores.get(i);
                System.out.printf("%d. %s (位置: %s)\n", 
                    i + 1, store.getStoreName(), store.getStoreLocation());
            }
            
            System.out.print("请选择商店编号 (0取消): ");
            int storeChoice = getIntInput();
            
            if (storeChoice <= 0 || storeChoice > stores.size()) {
                System.out.println("已取消购买");
                return;
            }
            
            FlowerStore selectedStore = stores.get(storeChoice - 1);
            
            List<Flower> availableFlowers = flowerStoreService.getFlowersByStoreId(selectedStore.getStoreId());
            
            if (availableFlowers.isEmpty()) {
                System.out.println("该商店暂无鲜花");
                return;
            }
            
            System.out.println("\n" + selectedStore.getStoreName() + " 的鲜花:");
            for (int i = 0; i < availableFlowers.size(); i++) {
                Flower flower = availableFlowers.get(i);
                System.out.printf("%d. %s (%s) - %s - ¥%.2f\n", 
                    i + 1, flower.getFlname(), flower.getFltype(), 
                    flower.getFlcolor(), flower.getFlprice());
            }
            
            System.out.print("\n请选择要购买的鲜花编号 (0取消): ");
            int flowerChoice = getIntInput();
            
            if (flowerChoice <= 0 || flowerChoice > availableFlowers.size()) {
                System.out.println("已取消购买");
                return;
            }
            
            Flower selectedFlower = availableFlowers.get(flowerChoice - 1);
            
            if (currentCustomer.getMoney() < selectedFlower.getFlprice()) {
                System.out.println("余额不足！当前余额: ¥" + String.format("%.2f", currentCustomer.getMoney()) + 
                                 ", 需要: ¥" + String.format("%.2f", selectedFlower.getFlprice()));
                return;
            }
            
            System.out.printf("确认购买 %s (¥%.2f)？(y/n): ", selectedFlower.getFlname(), selectedFlower.getFlprice());
            String confirm = scanner.nextLine();
            
            if (!"y".equalsIgnoreCase(confirm) && !"yes".equalsIgnoreCase(confirm)) {
                System.out.println("已取消购买");
                return;
            }
            
            if (flowerOwnerService.purchaseFlower(currentCustomer.getOwnerId(), selectedStore.getStoreId(), selectedFlower.getFlid(), "现金")) {
                System.out.println("✅ 购买成功！");
                System.out.printf("您购买了: %s (%s) - %s - ¥%.2f\n", 
                    selectedFlower.getFlname(), selectedFlower.getFltype(),
                    selectedFlower.getFlcolor(), selectedFlower.getFlprice());
                
                FlowerOwner updatedCustomer = flowerOwnerService.getFlowerOwnerById(currentCustomer.getOwnerId());
                if (updatedCustomer != null) {
                    currentCustomer = updatedCustomer;
                    System.out.println("更新后余额: ¥" + String.format("%.2f", currentCustomer.getMoney()));
                }
            } else {
                System.out.println("❌ 购买失败，请稍后再试");
            }
            
        } catch (Exception e) {
            System.out.println("购买失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 出售鲜花
     */
    private void sellFlowers() {
        System.out.println("\n=== 出售鲜花 ===");
        try {
            List<Flower> myFlowers = flowerOwnerService.getMyFlowers(currentCustomer.getOwnerId());
            
            if (myFlowers.isEmpty()) {
                System.out.println("您没有可出售的鲜花");
                return;
            }

            System.out.println("您的鲜花:");
            for (int i = 0; i < myFlowers.size(); i++) {
                Flower flower = myFlowers.get(i);
                System.out.printf("%d. %s (%s) - %s - ¥%.2f\n", 
                    i + 1, flower.getFlname(), flower.getFltype(), 
                    flower.getFlcolor(), flower.getFlprice());
            }

            System.out.print("请选择要出售的鲜花编号 (0取消): ");
            int flowerChoice = getIntInput();
            
            if (flowerChoice <= 0 || flowerChoice > myFlowers.size()) {
                System.out.println("已取消出售");
                return;
            }
            
            Flower selectedFlower = myFlowers.get(flowerChoice - 1);
            
            // 显示所有商店供用户选择
            List<FlowerStore> allStores = flowerOwnerService.getAllStores();
            if (allStores.isEmpty()) {
                System.out.println("暂无商店可供出售");
                return;
            }
            
            System.out.println("\n可选择的商店:");
            for (int i = 0; i < allStores.size(); i++) {
                FlowerStore store = allStores.get(i);
                System.out.printf("%d. %s (地区: %s) - 余额: ¥%.2f\n", 
                    i + 1, store.getStoreName(), store.getStoreLocation(), store.getBalance());
            }
            
            System.out.print("请选择要出售到的商店编号 (0取消): ");
            int storeChoice = getIntInput();
            
            if (storeChoice <= 0 || storeChoice > allStores.size()) {
                System.out.println("已取消出售");
                return;
            }
            
            FlowerStore selectedStore = allStores.get(storeChoice - 1);
            double sellPrice = selectedFlower.getFlprice() * 0.8; // 商店收购价为市场价的80%
            
            System.out.printf("\n确认出售信息:\n");
            System.out.printf("鲜花: %s (%s) - %s\n", selectedFlower.getFlname(), selectedFlower.getFltype(), selectedFlower.getFlcolor());
            System.out.printf("原价: ¥%.2f\n", selectedFlower.getFlprice());
            System.out.printf("收购价: ¥%.2f (原价的80%%)\n", sellPrice);
            System.out.printf("出售商店: %s\n", selectedStore.getStoreName());
            System.out.print("确认出售？(y/n): ");
            
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("y") && !confirm.equals("yes")) {
                System.out.println("已取消出售");
                return;
            }
            
            if (flowerOwnerService.sellFlowerToStore(currentCustomer.getOwnerId(), selectedStore.getStoreId(), selectedFlower.getFlid())) {
                System.out.printf("出售成功！获得 ¥%.2f\n", sellPrice);
                // 更新当前用户信息
                FlowerOwner updatedCustomer = flowerOwnerService.getFlowerOwnerById(currentCustomer.getOwnerId());
                if (updatedCustomer != null) {
                    currentCustomer = updatedCustomer;
                    System.out.printf("当前余额: ¥%.2f\n", currentCustomer.getMoney());
                }
            } else {
                System.out.println("出售失败，可能是商店余额不足或其他原因");
            }
            
        } catch (Exception e) {
            System.out.println("出售失败: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 充值余额
     */
    private void addMoney() {
        System.out.println("\n=== 充值余额 ===");
        System.out.print("请输入充值金额: ");
        
        try {
            double amount = Double.parseDouble(scanner.nextLine());
            
            if (amount <= 0) {
                System.out.println("充值金额必须大于0");
                return;
            }
            
            if (flowerOwnerService.recharge(currentCustomer.getOwnerId(), amount)) {
                currentCustomer.setMoney(currentCustomer.getMoney() + amount);
                System.out.printf("充值成功！当前余额: %.2f元\n", currentCustomer.getMoney());
            } else {
                System.out.println("充值失败");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("请输入有效的金额");
        } catch (Exception e) {
            System.out.println("充值失败: " + e.getMessage());
        }
    }
    
    /**
     * 修改个人信息
     */
    private void modifyCustomerInfo() {
        System.out.println("\n=== 修改个人信息 ===");
        
        System.out.println("当前信息:");
        System.out.println("姓名: " + currentCustomer.getOwnerName());
        System.out.println("联系方式: " + currentCustomer.getOwnerContact());
        System.out.println("地址: " + currentCustomer.getOwnerAddress());
        System.out.println("邮箱: " + currentCustomer.getOwnerEmail());
        
        System.out.print("\n是否要修改个人信息？(y/n): ");
        String confirm = scanner.nextLine();
        
        if ("y".equalsIgnoreCase(confirm) || "yes".equalsIgnoreCase(confirm)) {
            System.out.print("请输入新的姓名 (直接回车保持不变): ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                currentCustomer.setOwnerName(newName);
            }
            
            System.out.print("请输入新的联系方式 (直接回车保持不变): ");
            String newContact = scanner.nextLine();
            if (!newContact.trim().isEmpty()) {
                currentCustomer.setOwnerContact(newContact);
            }
            
            System.out.print("请输入新的地址 (直接回车保持不变): ");
            String newAddress = scanner.nextLine();
            if (!newAddress.trim().isEmpty()) {
                currentCustomer.setOwnerAddress(newAddress);
            }
            
            try {
                if (flowerStoreService.modifyFlowerOwner(currentCustomer)) {
                    System.out.println("个人信息修改成功！");
                } else {
                    System.out.println("个人信息修改失败！");
                }
            } catch (Exception e) {
                System.out.println("修改失败: " + e.getMessage());
            }
        }
    }
    
    /**
     * 查看购买记录
     */
    private void viewPurchaseHistory() {
        System.out.println("\n=== 购买记录 ===");
        try {
            System.out.println("顾客ID: " + currentCustomer.getOwnerId());
            System.out.println("顾客姓名: " + currentCustomer.getOwnerName());
            System.out.println("当前余额: ¥" + String.format("%.2f", currentCustomer.getMoney()));
            System.out.println("\n购买记录功能正在完善中，暂时显示顾客基本信息。");
        } catch (Exception e) {
            System.out.println("查询购买记录失败: " + e.getMessage());
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
