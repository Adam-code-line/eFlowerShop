package main.eFlowerShop.src.test;

import main.eFlowerShop.src.dao.AccountDao;
import main.eFlowerShop.src.dao.impl.AccountDaoImpl;
import main.eFlowerShop.src.entity.Account;
import java.util.List;

public class AccountDaoTest {
    public static void main(String[] args) {
        AccountDao accountDao = new AccountDaoImpl();
        
        System.out.println("=== 测试 AccountDao 功能 ===\n");
        
        // 测试1: 获取所有账单
        testGetAllAccounts(accountDao);
        
        // 测试2: 添加新账单
        testAddAccount(accountDao);
        
        // 测试3: 根据ID查询账单
        testGetAccountById(accountDao);
        
        // 测试4: 根据买家ID查询账单
        testGetAccountsByBuyerId(accountDao);
        
        // 测试5: 根据卖家ID查询账单
        testGetAccountsBySellerId(accountDao);
        
        // 测试6: 根据状态查询账单
        testGetAccountsByStatus(accountDao);
        
        // 测试7: 根据日期范围查询账单
        testGetAccountsByDateRange(accountDao);
        
        // 测试8: 根据金额范围查询账单
        testGetAccountsByAmountRange(accountDao);
        
        // 测试9: 根据支付方式查询账单
        testGetAccountsByPaymentMethod(accountDao);
        
        // 测试10: 更新账单状态
        testUpdateAccountStatus(accountDao);
        
        // 测试11: 计算买家总支出
        testGetTotalAmountByBuyerId(accountDao);
        
        // 测试12: 计算卖家总收入
        testGetTotalAmountBySellerId(accountDao);
        
        // 测试13: 删除账单
        testDeleteAccount(accountDao);
    }
    
    private static void testGetAllAccounts(AccountDao dao) {
        System.out.println("1. 测试获取所有账单：");
        try {
            List<Account> accounts = dao.getAllAccounts();
            System.out.println("   找到 " + accounts.size() + " 条账单记录");
            for (Account account : accounts) {
                System.out.println("   - ID: " + account.getAccountId() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 日期: " + account.getDate() + 
                                 ", 状态: " + account.getStatus() + 
                                 ", 买家ID: " + account.getBuyerId() + 
                                 ", 卖家ID: " + account.getSellerId());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testAddAccount(AccountDao dao) {
        System.out.println("2. 测试添加新账单：");
        try {
            Account newAccount = new Account();
            newAccount.setAmount(250.0);
            newAccount.setDate("2025-07-04");
            newAccount.setPaymentMethod("微信支付");
            newAccount.setStatus("已支付");
            newAccount.setBuyerId(1L);
            newAccount.setSellerId(2L);
            
            int result = dao.addAccount(newAccount);
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
    
    private static void testGetAccountById(AccountDao dao) {
        System.out.println("3. 测试根据ID查询账单：");
        try {
            Account account = dao.getAccountById(1L);
            if (account != null) {
                System.out.println("   找到账单: ID=" + account.getAccountId() + 
                                 ", 金额=" + account.getAmount() + 
                                 ", 状态=" + account.getStatus());
            } else {
                System.out.println("   未找到ID为1的账单");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetAccountsByBuyerId(AccountDao dao) {
        System.out.println("4. 测试根据买家ID查询账单：");
        try {
            List<Account> accounts = dao.getAccountsByBuyerId(1L);
            System.out.println("   买家ID=1的账单数量: " + accounts.size());
            for (Account account : accounts) {
                System.out.println("   - 账单ID: " + account.getAccountId() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 状态: " + account.getStatus());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetAccountsBySellerId(AccountDao dao) {
        System.out.println("5. 测试根据卖家ID查询账单：");
        try {
            List<Account> accounts = dao.getAccountsBySellerId(2L);
            System.out.println("   卖家ID=2的账单数量: " + accounts.size());
            for (Account account : accounts) {
                System.out.println("   - 账单ID: " + account.getAccountId() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 状态: " + account.getStatus());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetAccountsByStatus(AccountDao dao) {
        System.out.println("6. 测试根据状态查询账单：");
        try {
            List<Account> accounts = dao.getAccountsByStatus("已支付");
            System.out.println("   状态为'已支付'的账单数量: " + accounts.size());
            for (Account account : accounts) {
                System.out.println("   - 账单ID: " + account.getAccountId() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 日期: " + account.getDate());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetAccountsByDateRange(AccountDao dao) {
        System.out.println("7. 测试根据日期范围查询账单：");
        try {
            List<Account> accounts = dao.getAccountsByDateRange("2025-07-01", "2025-07-04");
            System.out.println("   2025-07-01 到 2025-07-04 期间的账单数量: " + accounts.size());
            for (Account account : accounts) {
                System.out.println("   - 日期: " + account.getDate() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 状态: " + account.getStatus());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetAccountsByAmountRange(AccountDao dao) {
        System.out.println("8. 测试根据金额范围查询账单：");
        try {
            List<Account> accounts = dao.getAccountsByAmountRange(100.0, 300.0);
            System.out.println("   金额在100-300之间的账单数量: " + accounts.size());
            for (Account account : accounts) {
                System.out.println("   - 账单ID: " + account.getAccountId() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 支付方式: " + account.getPaymentMethod());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetAccountsByPaymentMethod(AccountDao dao) {
        System.out.println("9. 测试根据支付方式查询账单：");
        try {
            List<Account> accounts = dao.getAccountsByPaymentMethod("信用卡");
            System.out.println("   支付方式为'信用卡'的账单数量: " + accounts.size());
            for (Account account : accounts) {
                System.out.println("   - 账单ID: " + account.getAccountId() + 
                                 ", 金额: " + account.getAmount() + 
                                 ", 日期: " + account.getDate());
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testUpdateAccountStatus(AccountDao dao) {
        System.out.println("10. 测试更新账单状态：");
        try {
            boolean result = dao.updateAccountStatus(2L, "已支付");
            System.out.println("   更新账单状态" + (result ? "成功" : "失败"));
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetTotalAmountByBuyerId(AccountDao dao) {
        System.out.println("11. 测试计算买家总支出：");
        try {
            double total = dao.getTotalAmountByBuyerId(1L);
            System.out.println("   买家ID=1的总支出: " + total);
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testGetTotalAmountBySellerId(AccountDao dao) {
        System.out.println("12. 测试计算卖家总收入：");
        try {
            double total = dao.getTotalAmountBySellerId(2L);
            System.out.println("   卖家ID=2的总收入: " + total);
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testDeleteAccount(AccountDao dao) {
        System.out.println("13. 测试删除账单：");
        try {
            // 先查找一个测试账单
            List<Account> accounts = dao.getAccountsByPaymentMethod("微信支付");
            if (!accounts.isEmpty()) {
                Account testAccount = accounts.get(0);
                boolean result = dao.deleteAccount((int)testAccount.getAccountId());
                System.out.println("   删除账单" + (result ? "成功" : "失败"));
            } else {
                System.out.println("   未找到要删除的测试账单");
            }
        } catch (Exception e) {
            System.out.println("   测试失败: " + e.getMessage());
        }
        System.out.println();
    }
}
