package main.eFlowerShop.src.dao.impl;

import main.eFlowerShop.src.dao.BaseDao;
import main.eFlowerShop.src.dao.FlowerStoreDao;
import main.eFlowerShop.src.entity.FlowerStore;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerStoreDaoImpl extends BaseDao implements FlowerStoreDao {

    // 获取所有鲜花商店信息
    @Override
    public List<FlowerStore> getAllFlowerStores() {
        String sql = "SELECT * FROM flowerstores";
        List<FlowerStore> flowerStores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                FlowerStore flowerStore = new FlowerStore();
                flowerStore.setStoreId(rs.getLong("storeId"));
                flowerStore.setStoreName(rs.getString("storeName"));
                flowerStore.setStoreLocation(rs.getString("storeLocation"));
                flowerStore.setStoreContact(rs.getString("storeContact"));
                flowerStore.setStoreEmail(rs.getString("storeEmail"));
                flowerStore.setStorePassword(rs.getString("storePassword"));
                flowerStore.setBalance(rs.getDouble("balance"));
                flowerStores.add(flowerStore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowerStores;
    }

    // 根据SQL语句和参数获取单个鲜花商店信息
    @Override
    public FlowerStore getFlowerStore(String sql, Object[] params) {
        FlowerStore flowerStore = null;
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
                flowerStore = new FlowerStore();
                flowerStore.setStoreId(rs.getLong("storeId"));
                flowerStore.setStoreName(rs.getString("storeName"));
                flowerStore.setStoreLocation(rs.getString("storeLocation"));
                flowerStore.setStoreContact(rs.getString("storeContact"));
                flowerStore.setStoreEmail(rs.getString("storeEmail"));
                flowerStore.setStorePassword(rs.getString("storePassword"));
                flowerStore.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowerStore;
    }

    // 更新鲜花商店信息
    @Override
    public int updateFlowerStore(String sql, Object[] params) {
        return executeUpdate(sql, params);
    }

    // 添加鲜花商店信息
    @Override
    public int addFlowerStore(FlowerStore flowerStore) {
        String sql = "INSERT INTO flowerstores (storeName, storeLocation, storeContact, storeEmail, storePassword, balance) VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql, 
            flowerStore.getStoreName(),
            flowerStore.getStoreLocation(),
            flowerStore.getStoreContact(),
            flowerStore.getStoreEmail(),
            flowerStore.getStorePassword(),
            flowerStore.getBalance());
    }

    // 删除鲜花商店信息
    @Override
    public boolean deleteFlowerStore(int id) {
        String sql = "DELETE FROM flowerstores WHERE storeId = ?";
        return executeUpdate(sql, id) > 0;
    }

    // 根据ID获取鲜花商店信息
    @Override
    public FlowerStore getFlowerStoreById(long storeId) {
        String sql = "SELECT * FROM flowerstores WHERE storeId = ?";
        return getFlowerStore(sql, new Object[]{storeId});
    }

    // 根据商店名称获取鲜花商店信息
    @Override
    public FlowerStore getFlowerStoreByName(String storeName) {
        String sql = "SELECT * FROM flowerstores WHERE storeName = ?";
        return getFlowerStore(sql, new Object[]{storeName});
    }

    // 根据邮箱获取鲜花商店信息（用于登录）
    @Override
    public FlowerStore getFlowerStoreByEmail(String storeEmail) {
        String sql = "SELECT * FROM flowerstores WHERE storeEmail = ?";
        return getFlowerStore(sql, new Object[]{storeEmail});
    }

    // 更新商店余额
    @Override
    public boolean updateStoreBalance(long storeId, double newBalance) {
        String sql = "UPDATE flowerstores SET balance = ? WHERE storeId = ?";
        return executeUpdate(sql, newBalance, storeId) > 0;
    }

    // 更新商店联系信息
    @Override
    public boolean updateStoreContact(long storeId, String contact, String email) {
        String sql = "UPDATE flowerstores SET storeContact = ?, storeEmail = ? WHERE storeId = ?";
        return executeUpdate(sql, contact, email, storeId) > 0;
    }

    // 更新商店密码
    @Override
    public boolean updateStorePassword(long storeId, String newPassword) {
        String sql = "UPDATE flowerstores SET storePassword = ? WHERE storeId = ?";
        return executeUpdate(sql, newPassword, storeId) > 0;
    }

    // 根据地区查询商店
    @Override
    public List<FlowerStore> getFlowerStoresByLocation(String location) {
        String sql = "SELECT * FROM flowerstores WHERE storeLocation LIKE ?";
        List<FlowerStore> flowerStores = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + location + "%");
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                FlowerStore flowerStore = new FlowerStore();
                flowerStore.setStoreId(rs.getLong("storeId"));
                flowerStore.setStoreName(rs.getString("storeName"));
                flowerStore.setStoreLocation(rs.getString("storeLocation"));
                flowerStore.setStoreContact(rs.getString("storeContact"));
                flowerStore.setStoreEmail(rs.getString("storeEmail"));
                flowerStore.setStorePassword(rs.getString("storePassword"));
                flowerStore.setBalance(rs.getDouble("balance"));
                flowerStores.add(flowerStore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowerStores;
    }

    // 验证商店登录
    @Override
    public FlowerStore validateStore(String email, String password) {
        String sql = "SELECT * FROM flowerstores WHERE storeEmail = ? AND storePassword = ?";
        return getFlowerStore(sql, new Object[]{email, password});
    }

    // 更新商店信息
    @Override
    public boolean updateFlowerStore(FlowerStore flowerStore) {
        String sql = "UPDATE flowerstores SET storeName = ?, storeLocation = ?, storeContact = ?, storeEmail = ?, storePassword = ?, balance = ? WHERE storeId = ?";
        return executeUpdate(sql, 
            flowerStore.getStoreName(),
            flowerStore.getStoreLocation(),
            flowerStore.getStoreContact(),
            flowerStore.getStoreEmail(),
            flowerStore.getStorePassword(),
            flowerStore.getBalance(),
            flowerStore.getStoreId()) > 0;
    }
}
