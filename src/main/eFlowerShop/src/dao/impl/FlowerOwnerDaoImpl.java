package main.eFlowerShop.src.dao.impl;

import main.eFlowerShop.src.dao.BaseDao;
import main.eFlowerShop.src.dao.FlowerOwnerDao;
import main.eFlowerShop.src.entity.FlowerOwner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerOwnerDaoImpl extends BaseDao implements FlowerOwnerDao {

    // 获取所有花主信息
    @Override
    public List<FlowerOwner> getAllFlowerOwners() {
        String sql = "SELECT * FROM flowerowners";
        List<FlowerOwner> flowerOwners = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                FlowerOwner flowerOwner = new FlowerOwner();
                flowerOwner.setOwnerId(rs.getLong("ownerId"));
                flowerOwner.setOwnerName(rs.getString("ownerName"));
                flowerOwner.setOwnerContact(rs.getString("ownerContact"));
                flowerOwner.setOwnerAddress(rs.getString("ownerAddress"));
                flowerOwner.setOwnerEmail(rs.getString("ownerEmail"));
                flowerOwner.setOwnerPassword(rs.getString("ownerPassword"));
                flowerOwner.setMoney(rs.getDouble("money"));
                flowerOwners.add(flowerOwner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowerOwners;
    }

    // 根据SQL语句和参数获取单个花主信息
    @Override
    public FlowerOwner getFlowerOwner(String sql, Object[] params) {
        FlowerOwner flowerOwner = null;
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
                flowerOwner = new FlowerOwner();
                flowerOwner.setOwnerId(rs.getLong("ownerId"));
                flowerOwner.setOwnerName(rs.getString("ownerName"));
                flowerOwner.setOwnerContact(rs.getString("ownerContact"));
                flowerOwner.setOwnerAddress(rs.getString("ownerAddress"));
                flowerOwner.setOwnerEmail(rs.getString("ownerEmail"));
                flowerOwner.setOwnerPassword(rs.getString("ownerPassword"));
                flowerOwner.setMoney(rs.getDouble("money"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowerOwner;
    }

    // 更新花主信息
    @Override
    public int updateFlowerOwner(String sql, Object[] params) {
        return executeUpdate(sql, params);
    }

    // 添加花主信息
    @Override
    public int addFlowerOwner(FlowerOwner flowerOwner) {
        String sql = "INSERT INTO flowerowners (ownerName, ownerContact, ownerAddress, ownerEmail, ownerPassword, money) VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql, 
            flowerOwner.getOwnerName(),
            flowerOwner.getOwnerContact(),
            flowerOwner.getOwnerAddress(),
            flowerOwner.getOwnerEmail(),
            flowerOwner.getOwnerPassword(),
            flowerOwner.getMoney());
    }

    // 删除花主信息
    @Override
    public boolean deleteFlowerOwner(int id) {
        String sql = "DELETE FROM flowerowners WHERE ownerId = ?";
        return executeUpdate(sql, id) > 0;
    }

    // 根据ID获取花主信息
    @Override
    public FlowerOwner getFlowerOwnerById(long ownerId) {
        String sql = "SELECT * FROM flowerowners WHERE ownerId = ?";
        return getFlowerOwner(sql, new Object[]{ownerId});
    }

    // 根据花主名称获取花主信息
    @Override
    public FlowerOwner getFlowerOwnerByName(String ownerName) {
        String sql = "SELECT * FROM flowerowners WHERE ownerName = ?";
        return getFlowerOwner(sql, new Object[]{ownerName});
    }

    // 根据邮箱获取花主信息（用于登录）
    @Override
    public FlowerOwner getFlowerOwnerByEmail(String ownerEmail) {
        String sql = "SELECT * FROM flowerowners WHERE ownerEmail = ?";
        return getFlowerOwner(sql, new Object[]{ownerEmail});
    }

    // 更新花主余额
    @Override
    public boolean updateOwnerMoney(long ownerId, double newMoney) {
        String sql = "UPDATE flowerowners SET money = ? WHERE ownerId = ?";
        return executeUpdate(sql, newMoney, ownerId) > 0;
    }

    // 更新花主联系信息
    @Override
    public boolean updateOwnerContact(long ownerId, String contact, String address, String email) {
        String sql = "UPDATE flowerowners SET ownerContact = ?, ownerAddress = ?, ownerEmail = ? WHERE ownerId = ?";
        return executeUpdate(sql, contact, address, email, ownerId) > 0;
    }

    // 更新花主密码
    @Override
    public boolean updateOwnerPassword(long ownerId, String newPassword) {
        String sql = "UPDATE flowerowners SET ownerPassword = ? WHERE ownerId = ?";
        return executeUpdate(sql, newPassword, ownerId) > 0;
    }

    // 根据地址查询花主
    @Override
    public List<FlowerOwner> getFlowerOwnersByAddress(String address) {
        String sql = "SELECT * FROM flowerowners WHERE ownerAddress LIKE ?";
        List<FlowerOwner> flowerOwners = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + address + "%");
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                FlowerOwner flowerOwner = new FlowerOwner();
                flowerOwner.setOwnerId(rs.getLong("ownerId"));
                flowerOwner.setOwnerName(rs.getString("ownerName"));
                flowerOwner.setOwnerContact(rs.getString("ownerContact"));
                flowerOwner.setOwnerAddress(rs.getString("ownerAddress"));
                flowerOwner.setOwnerEmail(rs.getString("ownerEmail"));
                flowerOwner.setOwnerPassword(rs.getString("ownerPassword"));
                flowerOwner.setMoney(rs.getDouble("money"));
                flowerOwners.add(flowerOwner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowerOwners;
    }

    // 根据余额范围查询花主
    @Override
    public List<FlowerOwner> getFlowerOwnersByMoneyRange(double minMoney, double maxMoney) {
        String sql = "SELECT * FROM flowerowners WHERE money BETWEEN ? AND ?";
        List<FlowerOwner> flowerOwners = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, minMoney);
            pstmt.setDouble(2, maxMoney);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                FlowerOwner flowerOwner = new FlowerOwner();
                flowerOwner.setOwnerId(rs.getLong("ownerId"));
                flowerOwner.setOwnerName(rs.getString("ownerName"));
                flowerOwner.setOwnerContact(rs.getString("ownerContact"));
                flowerOwner.setOwnerAddress(rs.getString("ownerAddress"));
                flowerOwner.setOwnerEmail(rs.getString("ownerEmail"));
                flowerOwner.setOwnerPassword(rs.getString("ownerPassword"));
                flowerOwner.setMoney(rs.getDouble("money"));
                flowerOwners.add(flowerOwner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowerOwners;
    }

    // 验证花主登录
    @Override
    public FlowerOwner validateOwner(String email, String password) {
        String sql = "SELECT * FROM flowerowners WHERE ownerEmail = ? AND ownerPassword = ?";
        return getFlowerOwner(sql, new Object[]{email, password});
    }

    // 增加花主余额
    @Override
    public boolean addOwnerMoney(long ownerId, double amount) {
        String sql = "UPDATE flowerowners SET money = money + ? WHERE ownerId = ?";
        return executeUpdate(sql, amount, ownerId) > 0;
    }

    // 减少花主余额
    @Override
    public boolean reduceOwnerMoney(long ownerId, double amount) {
        String sql = "UPDATE flowerowners SET money = money - ? WHERE ownerId = ? AND money >= ?";
        return executeUpdate(sql, amount, ownerId, amount) > 0;
    }

    // 更新花主信息
    @Override
    public boolean updateFlowerOwner(FlowerOwner flowerOwner) {
        String sql = "UPDATE flowerowners SET ownerName = ?, ownerContact = ?, ownerAddress = ?, ownerEmail = ?, ownerPassword = ?, money = ? WHERE ownerId = ?";
        return executeUpdate(sql, 
            flowerOwner.getOwnerName(),
            flowerOwner.getOwnerContact(),
            flowerOwner.getOwnerAddress(),
            flowerOwner.getOwnerEmail(),
            flowerOwner.getOwnerPassword(),
            flowerOwner.getMoney(),
            flowerOwner.getOwnerId()) > 0;
    }
}
