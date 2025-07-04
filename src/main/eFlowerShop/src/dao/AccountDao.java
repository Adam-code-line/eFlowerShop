package main.eFlowerShop.src.dao;
import main.eFlowerShop.src.entity.Account;
import java.util.List;

public interface AccountDao {
    // 基础CRUD操作
    List<Account> getAllAccounts();
    Account getAccount(String sql, Object[] params);
    int updateAccount(String sql, Object[] params);
    int addAccount(Account account);
    boolean deleteAccount(int id);
    
    // 扩展查询方法
    Account getAccountById(long accountId);
    List<Account> getAccountsByBuyerId(long buyerId);
    List<Account> getAccountsBySellerId(long sellerId);
    List<Account> getAccountsByStatus(String status);
    List<Account> getAccountsByDateRange(String startDate, String endDate);
    List<Account> getAccountsByAmountRange(double minAmount, double maxAmount);
    List<Account> getAccountsByPaymentMethod(String paymentMethod);
    
    // 业务方法
    boolean updateAccountStatus(long accountId, String status);
    double getTotalAmountByBuyerId(long buyerId);
    double getTotalAmountBySellerId(long sellerId);
}
