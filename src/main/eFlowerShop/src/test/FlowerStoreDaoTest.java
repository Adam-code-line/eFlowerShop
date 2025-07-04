package main.eFlowerShop.src.test;

import main.eFlowerShop.src.dao.FlowerStoreDao;
import main.eFlowerShop.src.dao.impl.FlowerStoreDaoImpl;
import main.eFlowerShop.src.entity.FlowerStore;
import java.util.List;

public class FlowerStoreDaoTest {
    public static void main(String[] args) {
        FlowerStoreDao flowerStoreDao = new FlowerStoreDaoImpl();
        
        System.out.println("=== 测试 FlowerStoreDao 功能 ===\n");
        
        // 测试1: 获取所有商店
        testGetAllFlowerStores(flowerStoreDao);
        
        // 测试2: 添加新商店
        testAddFlowerStore(flowerStoreDao);
        
        // 测试3: 根据ID查询商店
        testGetFlowerStoreById(flowerStoreDao);
        
        // 测试4: 根据名称查询商店
        testGetFlowerStoreByName(flowerStoreDao);
        
        // 测试5: 根据邮箱查询商店
        testGetFlowerStoreByEmail(flowerStoreDao);
        
        // 测试6: 根据地区查询商店
        testGetFlowerStoresByLocation(flowerStoreDao);
        
        // 测试7: 更新商店余额
        testUpdateStoreBalance(flowerStoreDao);
        
        // 测试8: 更新商店联系信息
        testUpdateStoreContact(flowerStoreDao);
        
        // 测试9: 更新商店密码
        testUpdateStorePassword(flowerStoreDao);
        
        // 测试10: 验证商店登录
        testValidateStore(flowerStoreDao);
        
        // 测试11: 删除商店
        testDeleteFlowerStore(flowerStoreDao);
    }
    
    private static void testGetAllFlowerStores(FlowerStoreDao dao) {
        System.out.println("1. 测试获取所有商店：");
        try {
            List<FlowerStore> stores = dao.getAllFlowerStores();
            System.out.println("   找到 " + stores.size() + " 个商店");
            for (FlowerStore store : stores) {
                System.out.println("   - ID: " + store.getStoreId() + 
                                 ", 名称: " + store.getStoreName() + 
                                 ", 位置: " + store.getStoreLocation() + 
                                 ", 余额: " + store.getBalance());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testAddFlowerStore(FlowerStoreDao dao) {
        System.out.println("2. 测试添加新商店：");
        try {
            FlowerStore newStore = new FlowerStore();
            newStore.setStoreName("测试花店");
            newStore.setStoreLocation("测试城市");
            newStore.setStoreContact("13800138000");
            newStore.setStoreEmail("test@flowerstore.com");
            newStore.setStorePassword("123456");
            newStore.setBalance(5000.0);
            
            int result = dao.addFlowerStore(newStore);
            if (result > 0) {
                System.out.println("   添加成功，影响行数: " + result);
            } else {
                System.out.println("   添加失败");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerStoreById(FlowerStoreDao dao) {
        System.out.println("3. 测试根据ID查询商店：");
        try {
            FlowerStore store = dao.getFlowerStoreById(1L);
            if (store != null) {
                System.out.println("   找到商店: " + store.getStoreName() + 
                                 " (ID: " + store.getStoreId() + ")");
            } else {
                System.out.println("   未找到ID为1的商店");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerStoreByName(FlowerStoreDao dao) {
        System.out.println("4. 测试根据名称查询商店：");
        try {
            FlowerStore store = dao.getFlowerStoreByName("测试花店");
            if (store != null) {
                System.out.println("   找到商店: " + store.getStoreName() + 
                                 " (邮箱: " + store.getStoreEmail() + ")");
            } else {
                System.out.println("   未找到名为'测试花店'的商店");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerStoreByEmail(FlowerStoreDao dao) {
        System.out.println("5. 测试根据邮箱查询商店：");
        try {
            FlowerStore store = dao.getFlowerStoreByEmail("test@flowerstore.com");
            if (store != null) {
                System.out.println("   找到商店: " + store.getStoreName() + 
                                 " (联系方式: " + store.getStoreContact() + ")");
            } else {
                System.out.println("   未找到邮箱为'test@flowerstore.com'的商店");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerStoresByLocation(FlowerStoreDao dao) {
        System.out.println("6. 测试根据地区查询商店：");
        try {
            List<FlowerStore> stores = dao.getFlowerStoresByLocation("测试");
            System.out.println("   在'测试'地区找到 " + stores.size() + " 个商店");
            for (FlowerStore store : stores) {
                System.out.println("   - " + store.getStoreName() + " 位于 " + store.getStoreLocation());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testUpdateStoreBalance(FlowerStoreDao dao) {
        System.out.println("7. 测试更新商店余额：");
        try {
            boolean result = dao.updateStoreBalance(1L, 10000.0);
            System.out.println("   更新余额" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testUpdateStoreContact(FlowerStoreDao dao) {
        System.out.println("8. 测试更新商店联系信息：");
        try {
            boolean result = dao.updateStoreContact(1L, "13900139000", "updated@flowerstore.com");
            System.out.println("   更新联系信息" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testUpdateStorePassword(FlowerStoreDao dao) {
        System.out.println("9. 测试更新商店密码：");
        try {
            boolean result = dao.updateStorePassword(1L, "newpassword123");
            System.out.println("   更新密码" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testValidateStore(FlowerStoreDao dao) {
        System.out.println("10. 测试验证商店登录：");
        try {
            FlowerStore store = dao.validateStore("test@flowerstore.com", "123456");
            if (store != null) {
                System.out.println("   登录验证成功: " + store.getStoreName());
            } else {
                System.out.println("   登录验证失败：邮箱或密码错误");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testDeleteFlowerStore(FlowerStoreDao dao) {
        System.out.println("11. 测试删除商店：");
        try {
            // 先查找测试商店的ID
            FlowerStore testStore = dao.getFlowerStoreByName("测试花店");
            if (testStore != null) {
                boolean result = dao.deleteFlowerStore((int)testStore.getStoreId());
                System.out.println("   删除商店" + (result ? "成功" : "失败"));
            } else {
                System.out.println("   未找到要删除的测试商店");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
}
