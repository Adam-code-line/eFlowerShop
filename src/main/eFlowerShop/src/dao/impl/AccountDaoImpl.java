package main.eFlowerShop.src.dao.impl;

import main.eFlowerShop.src.dao.BaseDao;
import main.eFlowerShop.src.dao.AccountDao;
import main.eFlowerShop.src.entity.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl extends BaseDao implements AccountDao {

    // 获取所有账单信息
    @Override
    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM accounts";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getLong("accountId"));
                account.setAmount(rs.getDouble("amount"));
                account.setDate(rs.getString("date"));
                account.setPaymentMethod(rs.getString("paymentMethod"));
                account.setStatus(rs.getString("status"));
                account.setBuyerId(rs.getLong("buyerId"));
                
                // 处理sellerId：如果为NULL则设置为-1（表示个人卖家）
                Long sellerId = rs.getObject("sellerId", Long.class);
                account.setSellerId(sellerId != null ? sellerId : -1);
                
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return accounts;
    }

    // 根据SQL语句和参数获取单个账单信息
    @Override
    public Account getAccount(String sql, Object[] params) {
        Account account = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            
            // 设置参数
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                account = new Account();
                account.setAccountId(rs.getLong("accountId"));
                account.setAmount(rs.getDouble("amount"));
                account.setDate(rs.getString("date"));
                account.setPaymentMethod(rs.getString("paymentMethod"));
                account.setStatus(rs.getString("status"));
                account.setBuyerId(rs.getLong("buyerId"));
                
                // 处理sellerId：如果为NULL则设置为-1（表示个人卖家）
                Long sellerId = rs.getObject("sellerId", Long.class);
                account.setSellerId(sellerId != null ? sellerId : -1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return account;
    }

    // 更新账单信息
    @Override
    public int updateAccount(String sql, Object[] params) {
        return executeUpdate(sql, params);
    }

    // 添加账单信息
    @Override
    public int addAccount(Account account) {
        try {
            String sql;
            Object[] params;
            
            // 如果sellerId为-1，表示个人卖家，插入NULL到sellerId字段
            if (account.getSellerId() == -1) {
                sql = "INSERT INTO accounts (amount, date, paymentMethod, status, buyerId, sellerId) VALUES (?, ?, ?, ?, ?, NULL)";
                params = new Object[]{
                    account.getAmount(),
                    account.getDate(),
                    account.getPaymentMethod(),
                    account.getStatus(),
                    account.getBuyerId()
                };
            } else {
                sql = "INSERT INTO accounts (amount, date, paymentMethod, status, buyerId, sellerId) VALUES (?, ?, ?, ?, ?, ?)";
                params = new Object[]{
                    account.getAmount(),
                    account.getDate(),
                    account.getPaymentMethod(),
                    account.getStatus(),
                    account.getBuyerId(),
                    account.getSellerId()
                };
            }
            
            return executeUpdate(sql, params);
        } catch (Exception e) {
            System.out.println("添加账单失败: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    // 删除账单信息
    @Override
    public boolean deleteAccount(int id) {
        String sql = "DELETE FROM accounts WHERE accountId = ?";
        return executeUpdate(sql, id) > 0;
    }

    // 根据ID获取账单信息
    @Override
    public Account getAccountById(long accountId) {
        String sql = "SELECT * FROM accounts WHERE accountId = ?";
        return getAccount(sql, new Object[]{accountId});
    }

    // 根据买家ID获取账单信息
    @Override
    public List<Account> getAccountsByBuyerId(long buyerId) {
        String sql = "SELECT * FROM accounts WHERE buyerId = ?";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, buyerId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getLong("accountId"));
                account.setAmount(rs.getDouble("amount"));
                account.setDate(rs.getString("date"));
                account.setPaymentMethod(rs.getString("paymentMethod"));
                account.setStatus(rs.getString("status"));
                account.setBuyerId(rs.getLong("buyerId"));
                
                // 处理sellerId：如果为NULL则设置为-1（表示个人卖家）
                Long sellerId = rs.getObject("sellerId", Long.class);
                account.setSellerId(sellerId != null ? sellerId : -1);
                
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return accounts;
    }

    // 根据卖家ID获取账单信息
    @Override
    public List<Account> getAccountsBySellerId(long sellerId) {
        String sql = "SELECT * FROM accounts WHERE sellerId = ?";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, sellerId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Account account = new Account();
                account.setAccountId(rs.getLong("accountId"));
                account.setAmount(rs.getDouble("amount"));
                account.setDate(rs.getString("date"));
                account.setPaymentMethod(rs.getString("paymentMethod"));
                account.setStatus(rs.getString("status"));
                account.setBuyerId(rs.getLong("buyerId"));
                
                // 处理sellerId：如果为NULL则设置为-1（表示个人卖家）
                Long sellerIdObj = rs.getObject("sellerId", Long.class);
                account.setSellerId(sellerIdObj != null ? sellerIdObj : -1);
                
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return accounts;
    }

    // 根据状态获取账单信息
    @Override
    public List<Account> getAccountsByStatus(String status) {
        String sql = "SELECT * FROM accounts WHERE status = ?";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Account account = createAccountFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return accounts;
    }

    // 根据日期范围获取账单信息
    @Override
    public List<Account> getAccountsByDateRange(String startDate, String endDate) {
        String sql = "SELECT * FROM accounts WHERE date BETWEEN ? AND ?";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, startDate);
            pstmt.setString(2, endDate);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Account account = createAccountFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return accounts;
    }

    // 根据金额范围获取账单信息
    @Override
    public List<Account> getAccountsByAmountRange(double minAmount, double maxAmount) {
        String sql = "SELECT * FROM accounts WHERE amount BETWEEN ? AND ?";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, minAmount);
            pstmt.setDouble(2, maxAmount);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Account account = createAccountFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return accounts;
    }

    // 更新账单状态
    @Override
    public boolean updateAccountStatus(long accountId, String status) {
        String sql = "UPDATE accounts SET status = ? WHERE accountId = ?";
        return executeUpdate(sql, status, accountId) > 0;
    }

    // 根据支付方式获取账单信息
    @Override
    public List<Account> getAccountsByPaymentMethod(String paymentMethod) {
        String sql = "SELECT * FROM accounts WHERE paymentMethod = ?";
        List<Account> accounts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, paymentMethod);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Account account = createAccountFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return accounts;
    }

    // 计算买家总支出
    @Override
    public double getTotalAmountByBuyerId(long buyerId) {
        String sql = "SELECT SUM(amount) as total FROM accounts WHERE buyerId = ? AND status = '已支付'";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, buyerId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return 0.0;
    }

    // 计算卖家总收入
    @Override
    public double getTotalAmountBySellerId(long sellerId) {
        String sql = "SELECT SUM(amount) as total FROM accounts WHERE sellerId = ? AND status = '已支付'";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, sellerId);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return 0.0;
    }

    /**
     * 从ResultSet创建Account对象的辅助方法
     * 统一处理sellerId的NULL值情况
     */
    private Account createAccountFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setAccountId(rs.getLong("accountId"));
        account.setAmount(rs.getDouble("amount"));
        account.setDate(rs.getString("date"));
        account.setPaymentMethod(rs.getString("paymentMethod"));
        account.setStatus(rs.getString("status"));
        account.setBuyerId(rs.getLong("buyerId"));
        
        // 处理sellerId：如果为NULL则设置为-1（表示个人卖家）
        Long sellerId = rs.getObject("sellerId", Long.class);
        account.setSellerId(sellerId != null ? sellerId : -1);
        
        return account;
    }

}
