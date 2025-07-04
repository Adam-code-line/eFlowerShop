package main.eFlowerShop.src.test;

import main.eFlowerShop.src.dao.FlowerOwnerDao;
import main.eFlowerShop.src.dao.impl.FlowerOwnerDaoImpl;
import main.eFlowerShop.src.entity.FlowerOwner;
import java.util.List;

public class FlowerOwnerDaoTest {
    public static void main(String[] args) {
        FlowerOwnerDao flowerOwnerDao = new FlowerOwnerDaoImpl();
        
        System.out.println("=== 测试 FlowerOwnerDao 功能 ===\n");
        
        // 测试1: 获取所有花主
        testGetAllFlowerOwners(flowerOwnerDao);
        
        // 测试2: 添加新花主
        testAddFlowerOwner(flowerOwnerDao);
        
        // 测试3: 根据ID查询花主
        testGetFlowerOwnerById(flowerOwnerDao);
        
        // 测试4: 根据名称查询花主
        testGetFlowerOwnerByName(flowerOwnerDao);
        
        // 测试5: 根据邮箱查询花主
        testGetFlowerOwnerByEmail(flowerOwnerDao);
        
        // 测试6: 根据地址查询花主
        testGetFlowerOwnersByAddress(flowerOwnerDao);
        
        // 测试7: 根据余额范围查询花主
        testGetFlowerOwnersByMoneyRange(flowerOwnerDao);
        
        // 测试8: 更新花主余额
        testUpdateOwnerMoney(flowerOwnerDao);
        
        // 测试9: 增加花主余额
        testAddOwnerMoney(flowerOwnerDao);
        
        // 测试10: 减少花主余额
        testReduceOwnerMoney(flowerOwnerDao);
        
        // 测试11: 更新花主联系信息
        testUpdateOwnerContact(flowerOwnerDao);
        
        // 测试12: 更新花主密码
        testUpdateOwnerPassword(flowerOwnerDao);
        
        // 测试13: 验证花主登录
        testValidateOwner(flowerOwnerDao);
        
        // 测试14: 删除花主
        testDeleteFlowerOwner(flowerOwnerDao);
    }
    
    private static void testGetAllFlowerOwners(FlowerOwnerDao dao) {
        System.out.println("1. 测试获取所有花主：");
        try {
            List<FlowerOwner> owners = dao.getAllFlowerOwners();
            System.out.println("   找到 " + owners.size() + " 个花主");
            for (FlowerOwner owner : owners) {
                System.out.println("   - ID: " + owner.getOwnerId() + 
                                 ", 姓名: " + owner.getOwnerName() + 
                                 ", 地址: " + owner.getOwnerAddress() + 
                                 ", 余额: " + owner.getMoney());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testAddFlowerOwner(FlowerOwnerDao dao) {
        System.out.println("2. 测试添加新花主：");
        try {
            FlowerOwner newOwner = new FlowerOwner();
            newOwner.setOwnerName("测试花主");
            newOwner.setOwnerContact("13800138000");
            newOwner.setOwnerAddress("测试地址123号");
            newOwner.setOwnerEmail("test@owner.com");
            newOwner.setOwnerPassword("123456");
            newOwner.setMoney(3000.0);
            
            int result = dao.addFlowerOwner(newOwner);
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
    
    private static void testGetFlowerOwnerById(FlowerOwnerDao dao) {
        System.out.println("3. 测试根据ID查询花主：");
        try {
            FlowerOwner owner = dao.getFlowerOwnerById(1L);
            if (owner != null) {
                System.out.println("   找到花主: " + owner.getOwnerName() + 
                                 " (ID: " + owner.getOwnerId() + ")");
            } else {
                System.out.println("   未找到ID为1的花主");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerOwnerByName(FlowerOwnerDao dao) {
        System.out.println("4. 测试根据名称查询花主：");
        try {
            FlowerOwner owner = dao.getFlowerOwnerByName("张三");
            if (owner != null) {
                System.out.println("   找到花主: " + owner.getOwnerName() + 
                                 " (邮箱: " + owner.getOwnerEmail() + ")");
            } else {
                System.out.println("   未找到名为'张三'的花主");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerOwnerByEmail(FlowerOwnerDao dao) {
        System.out.println("5. 测试根据邮箱查询花主：");
        try {
            FlowerOwner owner = dao.getFlowerOwnerByEmail("zhangsan@owner.com");
            if (owner != null) {
                System.out.println("   找到花主: " + owner.getOwnerName() + 
                                 " (联系方式: " + owner.getOwnerContact() + ")");
            } else {
                System.out.println("   未找到邮箱为'zhangsan@owner.com'的花主");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerOwnersByAddress(FlowerOwnerDao dao) {
        System.out.println("6. 测试根据地址查询花主：");
        try {
            List<FlowerOwner> owners = dao.getFlowerOwnersByAddress("北京");
            System.out.println("   在'北京'地区找到 " + owners.size() + " 个花主");
            for (FlowerOwner owner : owners) {
                System.out.println("   - " + owner.getOwnerName() + " 位于 " + owner.getOwnerAddress());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetFlowerOwnersByMoneyRange(FlowerOwnerDao dao) {
        System.out.println("7. 测试根据余额范围查询花主：");
        try {
            List<FlowerOwner> owners = dao.getFlowerOwnersByMoneyRange(1000.0, 5000.0);
            System.out.println("   余额在1000-5000之间的花主有 " + owners.size() + " 个");
            for (FlowerOwner owner : owners) {
                System.out.println("   - " + owner.getOwnerName() + " 余额: " + owner.getMoney());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testUpdateOwnerMoney(FlowerOwnerDao dao) {
        System.out.println("8. 测试更新花主余额：");
        try {
            boolean result = dao.updateOwnerMoney(1L, 5000.0);
            System.out.println("   更新余额" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testAddOwnerMoney(FlowerOwnerDao dao) {
        System.out.println("9. 测试增加花主余额：");
        try {
            boolean result = dao.addOwnerMoney(1L, 500.0);
            System.out.println("   增加余额500元" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testReduceOwnerMoney(FlowerOwnerDao dao) {
        System.out.println("10. 测试减少花主余额：");
        try {
            boolean result = dao.reduceOwnerMoney(1L, 200.0);
            System.out.println("   减少余额200元" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testUpdateOwnerContact(FlowerOwnerDao dao) {
        System.out.println("11. 测试更新花主联系信息：");
        try {
            boolean result = dao.updateOwnerContact(1L, "13900139000", "新地址456号", "newemail@owner.com");
            System.out.println("   更新联系信息" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testUpdateOwnerPassword(FlowerOwnerDao dao) {
        System.out.println("12. 测试更新花主密码：");
        try {
            boolean result = dao.updateOwnerPassword(1L, "newpassword123");
            System.out.println("   更新密码" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testValidateOwner(FlowerOwnerDao dao) {
        System.out.println("13. 测试验证花主登录：");
        try {
            FlowerOwner owner = dao.validateOwner("zhangsan@owner.com", "ownerpass");
            if (owner != null) {
                System.out.println("   登录验证成功: " + owner.getOwnerName());
            } else {
                System.out.println("   登录验证失败：邮箱或密码错误");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testDeleteFlowerOwner(FlowerOwnerDao dao) {
        System.out.println("14. 测试删除花主：");
        try {
            // 先查找测试花主的ID
            FlowerOwner testOwner = dao.getFlowerOwnerByName("测试花主");
            if (testOwner != null) {
                boolean result = dao.deleteFlowerOwner((int)testOwner.getOwnerId());
                System.out.println("   删除花主" + (result ? "成功" : "失败"));
            } else {
                System.out.println("   未找到要删除的测试花主");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
}
