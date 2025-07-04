package main.eFlowerShop.src.dao;
import main.eFlowerShop.src.entity.FlowerOwner;
import java.util.List;

public interface FlowerOwnerDao {
    // 基础CRUD操作
    List<FlowerOwner> getAllFlowerOwners();
    FlowerOwner getFlowerOwner(String sql, Object[] params);
    int updateFlowerOwner(String sql, Object[] params);
    int addFlowerOwner(FlowerOwner flowerOwner);
    boolean deleteFlowerOwner(int id);
    
    // 扩展查询方法
    FlowerOwner getFlowerOwnerById(long ownerId);
    FlowerOwner getFlowerOwnerByName(String ownerName);
    FlowerOwner getFlowerOwnerByEmail(String ownerEmail);
    List<FlowerOwner> getFlowerOwnersByAddress(String address);
    List<FlowerOwner> getFlowerOwnersByMoneyRange(double minMoney, double maxMoney);
    
    // 业务方法
    boolean updateOwnerMoney(long ownerId, double newMoney);
    boolean updateOwnerContact(long ownerId, String contact, String address, String email);
    boolean updateOwnerPassword(long ownerId, String newPassword);
    FlowerOwner validateOwner(String email, String password);
    boolean addOwnerMoney(long ownerId, double amount);
    boolean reduceOwnerMoney(long ownerId, double amount);
    
    // 更新花主信息
    boolean updateFlowerOwner(FlowerOwner flowerOwner);
}
