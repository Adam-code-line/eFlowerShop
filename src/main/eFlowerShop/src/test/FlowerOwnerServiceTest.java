package main.eFlowerShop.src.test;

import main.eFlowerShop.src.service.FlowerOwnerService;
import main.eFlowerShop.src.service.impl.FlowerOwnerServiceImpl;
import main.eFlowerShop.src.entity.Flower;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.entity.Account;

import java.util.List;

public class FlowerOwnerServiceTest {
    public static void main(String[] args) {
        FlowerOwnerService ownerService = new FlowerOwnerServiceImpl();
        
        System.out.println("=== 测试 FlowerOwnerService 功能 ===\n");
        
        // 测试1: 用户登录
        testUserLogin(ownerService);
        
        // 测试2: 查看个人鲜花
        testGetMyFlowers(ownerService);
        
        // 测试3: 查看市场上的鲜花
        testGetFlowersInStock(ownerService);
        
        // 测试4: 培育新鲜花
        testCultivateFlower(ownerService);
        
        // 测试5: 批量培育鲜花
        testCultivateFlowersBatch(ownerService);
        
        // 测试6: 查看所有商店
        testGetAllStores(ownerService);
        
        // 测试7: 根据地区查找商店
        testGetStoresByLocation(ownerService);
        
        // 测试8: 查看特定商店的鲜花
        testGetFlowersByStore(ownerService);
        
        // 测试9: 根据类型搜索鲜花
        testSearchFlowersByType(ownerService);
        
        // 测试10: 根据价格区间搜索鲜花
        testSearchFlowersByPriceRange(ownerService);
        
        // 测试11: 根据颜色搜索鲜花
        testSearchFlowersByColor(ownerService);
        
        // 测试12: 查看购买历史
        testGetPurchaseHistory(ownerService);
        
        // 测试13: 查看余额
        testGetBalance(ownerService);
        
        // 测试14: 购买鲜花
        testPurchaseFlower(ownerService);
        
        // 测试15: 出售鲜花给商店
        testSellFlowerToStore(ownerService);
    }
    
    private static void testUserLogin(FlowerOwnerService service) {
        System.out.println("1. 测试用户登录：");
        try {
            // 尝试使用测试邮箱登录
            FlowerOwner owner = service.login("test@example.com", "password123");
            if (owner != null) {
                System.out.println("   登录成功: " + owner.getOwnerName() + 
                                 " (ID: " + owner.getOwnerId() + ")");
                System.out.println("   当前余额: " + owner.getMoney() + " 元");
            } else {
                System.out.println("   登录失败或用户不存在");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetMyFlowers(FlowerOwnerService service) {
        System.out.println("2. 测试查看个人鲜花：");
        try {
            List<Flower> myFlowers = service.getMyFlowers(1L); // 假设用户ID为1
            System.out.println("   个人鲜花数量: " + myFlowers.size());
            for (Flower flower : myFlowers) {
                System.out.println("   - " + flower.getFlname() + 
                                 " (" + flower.getFltype() + ") - " + 
                                 flower.getFlcolor() + " - " + 
                                 flower.getFlprice() + "元");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowersInStock(FlowerOwnerService service) {
        System.out.println("3. 测试查看市场鲜花：");
        try {
            List<Flower> marketFlowers = service.getFlowersInStock(1L); // 排除用户1的鲜花
            System.out.println("   市场鲜花数量: " + marketFlowers.size());
            int displayCount = Math.min(5, marketFlowers.size()); // 只显示前5个
            for (int i = 0; i < displayCount; i++) {
                Flower flower = marketFlowers.get(i);
                System.out.println("   - " + flower.getFlname() + 
                                 " (" + flower.getFltype() + ") - " + 
                                 flower.getFlcolor() + " - " + 
                                 flower.getFlprice() + "元 (拥有者ID: " + 
                                 flower.getOwnerId() + ")");
            }
            if (marketFlowers.size() > 5) {
                System.out.println("   ... 还有 " + (marketFlowers.size() - 5) + " 朵鲜花");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testCultivateFlower(FlowerOwnerService service) {
        System.out.println("4. 测试培育新鲜花：");
        try {
            Flower newFlower = service.cultivateFlower(1L, "茉莉花");
            if (newFlower != null) {
                System.out.println("   培育成功: " + newFlower.getFlname() + 
                                 " (" + newFlower.getFltype() + ") - " + 
                                 newFlower.getFlcolor() + " - " + 
                                 newFlower.getFlprice() + "元");
            } else {
                System.out.println("   培育失败");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testCultivateFlowersBatch(FlowerOwnerService service) {
        System.out.println("5. 测试批量培育鲜花：");
        try {
            List<Flower> newFlowers = service.cultivateFlowersBatch(1L, "康乃馨", 3);
            System.out.println("   成功培育 " + newFlowers.size() + " 朵康乃馨");
            for (Flower flower : newFlowers) {
                System.out.println("   - " + flower.getFlname() + 
                                 " - " + flower.getFlcolor() + 
                                 " - " + flower.getFlprice() + "元");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetAllStores(FlowerOwnerService service) {
        System.out.println("6. 测试查看所有商店：");
        try {
            List<FlowerStore> stores = service.getAllStores();
            System.out.println("   商店数量: " + stores.size());
            for (FlowerStore store : stores) {
                System.out.println("   - " + store.getStoreName() + 
                                 " 位于 " + store.getStoreLocation() + 
                                 " (余额: " + store.getBalance() + "元)");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetStoresByLocation(FlowerOwnerService service) {
        System.out.println("7. 测试根据地区查找商店：");
        try {
            List<FlowerStore> stores = service.getStoresByLocation("市中心");
            System.out.println("   在'市中心'地区找到 " + stores.size() + " 个商店");
            for (FlowerStore store : stores) {
                System.out.println("   - " + store.getStoreName() + 
                                 " (联系方式: " + store.getStoreContact() + ")");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowersByStore(FlowerOwnerService service) {
        System.out.println("8. 测试查看特定商店的鲜花：");
        try {
            List<Flower> storeFlowers = service.getFlowersByStore(1L); // 假设商店ID为1
            System.out.println("   商店1的鲜花数量: " + storeFlowers.size());
            int displayCount = Math.min(3, storeFlowers.size());
            for (int i = 0; i < displayCount; i++) {
                Flower flower = storeFlowers.get(i);
                System.out.println("   - " + flower.getFlname() + 
                                 " (" + flower.getFltype() + ") - " + 
                                 flower.getFlprice() + "元");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testSearchFlowersByType(FlowerOwnerService service) {
        System.out.println("9. 测试根据类型搜索鲜花：");
        try {
            List<Flower> roses = service.searchFlowersByType("玫瑰");
            System.out.println("   找到 " + roses.size() + " 朵玫瑰");
            for (Flower flower : roses) {
                System.out.println("   - " + flower.getFlname() + 
                                 " - " + flower.getFlcolor() + 
                                 " - " + flower.getFlprice() + "元");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testSearchFlowersByPriceRange(FlowerOwnerService service) {
        System.out.println("10. 测试根据价格区间搜索鲜花：");
        try {
            List<Flower> affordableFlowers = service.searchFlowersByPriceRange(20.0, 50.0);
            System.out.println("   价格在20-50元之间的鲜花数量: " + affordableFlowers.size());
            for (Flower flower : affordableFlowers) {
                System.out.println("   - " + flower.getFlname() + 
                                 " (" + flower.getFltype() + ") - " + 
                                 flower.getFlprice() + "元");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testSearchFlowersByColor(FlowerOwnerService service) {
        System.out.println("11. 测试根据颜色搜索鲜花：");
        try {
            List<Flower> redFlowers = service.searchFlowersByColor("红色");
            System.out.println("   红色鲜花数量: " + redFlowers.size());
            for (Flower flower : redFlowers) {
                System.out.println("   - " + flower.getFlname() + 
                                 " (" + flower.getFltype() + ") - " + 
                                 flower.getFlprice() + "元");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetPurchaseHistory(FlowerOwnerService service) {
        System.out.println("12. 测试查看购买历史：");
        try {
            List<Account> history = service.getMyPurchaseHistory(1L);
            System.out.println("   购买记录数量: " + history.size());
            for (Account account : history) {
                System.out.println("   - 订单ID: " + account.getAccountId() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 日期: " + account.getDate() + 
                                 ", 状态: " + account.getStatus());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetBalance(FlowerOwnerService service) {
        System.out.println("13. 测试查看余额：");
        try {
            double balance = service.getBalance(1L);
            System.out.println("   用户1的当前余额: " + balance + " 元");
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testPurchaseFlower(FlowerOwnerService service) {
        System.out.println("14. 测试购买鲜花：");
        try {
            // 这里需要实际的鲜花ID和卖家ID，这只是示例
            boolean success = service.purchaseFlower(1L, 2L, 1L, "支付宝");
            System.out.println("   购买" + (success ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testSellFlowerToStore(FlowerOwnerService service) {
        System.out.println("15. 测试出售鲜花给商店：");
        try {
            // 这里需要实际的鲜花ID和商店ID，这只是示例
            boolean success = service.sellFlowerToStore(1L, 1L, 1L);
            System.out.println("   出售" + (success ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
}
