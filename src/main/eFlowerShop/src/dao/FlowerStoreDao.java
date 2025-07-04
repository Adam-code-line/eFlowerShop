package main.eFlowerShop.src.dao;
import main.eFlowerShop.src.entity.FlowerStore;
import java.util.List;

public interface FlowerStoreDao {
    // 基本查询方法
    List<FlowerStore> getAllFlowerStores();
    FlowerStore getFlowerStore(String sql, Object[] params);
    int updateFlowerStore(String sql, Object[] params);
    int addFlowerStore(FlowerStore flowerStore);
    boolean deleteFlowerStore(int id);
    
    // 扩展查询方法
    FlowerStore getFlowerStoreById(long storeId);
    FlowerStore getFlowerStoreByName(String storeName);
    FlowerStore getFlowerStoreByEmail(String storeEmail);
    List<FlowerStore> getFlowerStoresByLocation(String location);
    
    // 业务方法
    boolean updateStoreBalance(long storeId, double newBalance);
    boolean updateStoreContact(long storeId, String contact, String email);
    boolean updateStorePassword(long storeId, String newPassword);
    FlowerStore validateStore(String email, String password);
    
    // 更新商店信息
    boolean updateFlowerStore(FlowerStore flowerStore);
}
