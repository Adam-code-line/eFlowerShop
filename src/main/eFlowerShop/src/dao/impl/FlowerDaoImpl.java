package main.eFlowerShop.src.dao.impl;

import main.eFlowerShop.src.dao.BaseDao;
import main.eFlowerShop.src.dao.FlowerDao;
import main.eFlowerShop.src.entity.Flower;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlowerDaoImpl extends BaseDao implements FlowerDao {
    
    // 实现获取所有鲜花信息的方法
    @Override
    public List<Flower> getAllFlowers() {
        String sql = "SELECT * FROM flowers";
        List<Flower> flowers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Flower flower = new Flower();
                flower.setFlid(rs.getInt("flid"));
                flower.setFlname(rs.getString("flname"));
                flower.setFltype(rs.getString("fltype"));
                flower.setFlcolor(rs.getString("flcolor"));
                flower.setFlprice(rs.getDouble("flprice"));
                flower.setOwnerId(rs.getInt("ownerId"));
                
                // 处理storeId：如果为NULL则设置为-1（表示个人拥有）
                Integer storeId = rs.getObject("storeId", Integer.class);
                flower.setStoreId(storeId != null ? storeId : -1);
                
                flowers.add(flower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowers;
    }

    // 实现根据ID获取鲜花信息的方法
    @Override
    public Flower getFlowerById(int id) {
        String sql = "SELECT * FROM flowers WHERE flid = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Flower flower = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                flower = new Flower();
                flower.setFlid(rs.getInt("flid"));
                flower.setFlname(rs.getString("flname"));
                flower.setFltype(rs.getString("fltype"));
                flower.setFlcolor(rs.getString("flcolor"));
                flower.setFlprice(rs.getDouble("flprice"));
                flower.setOwnerId(rs.getInt("ownerId"));
                
                // 处理storeId：如果为NULL则设置为-1（表示个人拥有）
                Integer storeId = rs.getObject("storeId", Integer.class);
                flower.setStoreId(storeId != null ? storeId : -1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flower;
    }

    // 实现根据ID获取鲜花信息的方法 (long类型重载)
    @Override
    public Flower getFlowerById(long id) {
        return getFlowerById((int)id);
    }

    // 实现添加鲜花的方法
    @Override
    public boolean addFlower(Flower flower) {
        try {
            String sql;
            Object[] params;
            
            // 如果storeId为-1，表示个人拥有，插入NULL到storeId字段
            if (flower.getStoreId() == -1) {
                sql = "INSERT INTO flowers (flname, fltype, flcolor, flprice, ownerId, storeId) VALUES (?, ?, ?, ?, ?, NULL)";
                params = new Object[]{
                    flower.getFlname(), 
                    flower.getFltype(), 
                    flower.getFlcolor(), 
                    flower.getFlprice(), 
                    flower.getOwnerId()
                };
            } else {
                sql = "INSERT INTO flowers (flname, fltype, flcolor, flprice, ownerId, storeId) VALUES (?, ?, ?, ?, ?, ?)";
                params = new Object[]{
                    flower.getFlname(), 
                    flower.getFltype(), 
                    flower.getFlcolor(), 
                    flower.getFlprice(), 
                    flower.getOwnerId(), 
                    flower.getStoreId()
                };
            }
            
            return executeUpdate(sql, params) > 0;
        } catch (Exception e) {
            System.out.println("添加鲜花失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 实现更新鲜花信息的方法
    @Override
    public boolean updateFlower(Flower flower) {
        try {
            String sql;
            Object[] params;
            
            // 如果storeId为-1，表示个人拥有，更新storeId为NULL
            if (flower.getStoreId() == -1) {
                sql = "UPDATE flowers SET flname = ?, fltype = ?, flcolor = ?, flprice = ?, ownerId = ?, storeId = NULL WHERE flid = ?";
                params = new Object[]{
                    flower.getFlname(), 
                    flower.getFltype(), 
                    flower.getFlcolor(), 
                    flower.getFlprice(), 
                    flower.getOwnerId(), 
                    flower.getFlid()
                };
            } else {
                sql = "UPDATE flowers SET flname = ?, fltype = ?, flcolor = ?, flprice = ?, ownerId = ?, storeId = ? WHERE flid = ?";
                params = new Object[]{
                    flower.getFlname(), 
                    flower.getFltype(), 
                    flower.getFlcolor(), 
                    flower.getFlprice(), 
                    flower.getOwnerId(), 
                    flower.getStoreId(), 
                    flower.getFlid()
                };
            }
            
            return executeUpdate(sql, params) > 0;
        } catch (Exception e) {
            System.out.println("更新鲜花失败: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // 实现删除鲜花的方法
    @Override
    public boolean deleteFlower(int id) {
        String sql = "DELETE FROM flowers WHERE flid = ?";
        return executeUpdate(sql, id) > 0;
    }

    // 根据鲜花类型查询
    @Override
    public List<Flower> getFlowersByType(String fltype) {
        String sql = "SELECT * FROM flowers WHERE fltype = ?";
        List<Flower> flowers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fltype);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Flower flower = new Flower();
                flower.setFlid(rs.getInt("flid"));
                flower.setFlname(rs.getString("flname"));
                flower.setFltype(rs.getString("fltype"));
                flower.setFlcolor(rs.getString("flcolor"));
                flower.setFlprice(rs.getDouble("flprice"));
                flower.setOwnerId(rs.getInt("ownerId"));
                flower.setStoreId(rs.getInt("storeId"));
                flowers.add(flower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowers;
    }

    // 根据店铺ID查询鲜花
    @Override
    public List<Flower> getFlowersByStoreId(int storeId) {
        String sql = "SELECT * FROM flowers WHERE storeId = ?";
        List<Flower> flowers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, storeId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Flower flower = new Flower();
                flower.setFlid(rs.getInt("flid"));
                flower.setFlname(rs.getString("flname"));
                flower.setFltype(rs.getString("fltype"));
                flower.setFlcolor(rs.getString("flcolor"));
                flower.setFlprice(rs.getDouble("flprice"));
                flower.setOwnerId(rs.getInt("ownerId"));
                
                // 处理storeId：如果为NULL则设置为-1（表示个人拥有）
                Integer storeIdValue = rs.getObject("storeId", Integer.class);
                flower.setStoreId(storeIdValue != null ? storeIdValue : -1);
                
                flowers.add(flower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowers;
    }

    // 根据价格范围查询鲜花
    @Override
    public List<Flower> getFlowersByPriceRange(double minPrice, double maxPrice) {
        String sql = "SELECT * FROM flowers WHERE flprice BETWEEN ? AND ?";
        List<Flower> flowers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, minPrice);
            pstmt.setDouble(2, maxPrice);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Flower flower = new Flower();
                flower.setFlid(rs.getInt("flid"));
                flower.setFlname(rs.getString("flname"));
                flower.setFltype(rs.getString("fltype"));
                flower.setFlcolor(rs.getString("flcolor"));
                flower.setFlprice(rs.getDouble("flprice"));
                flower.setOwnerId(rs.getInt("ownerId"));
                
                // 处理storeId：如果为NULL则设置为-1（表示个人拥有）
                Integer storeIdValue = rs.getObject("storeId", Integer.class);
                flower.setStoreId(storeIdValue != null ? storeIdValue : -1);
                
                flowers.add(flower);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, pstmt, rs);
        }
        
        return flowers;
    }

    // 根据店铺ID查询鲜花 (long类型重载)
    @Override
    public List<Flower> getFlowersByStoreId(long storeId) {
        return getFlowersByStoreId((int)storeId);
    }
}
