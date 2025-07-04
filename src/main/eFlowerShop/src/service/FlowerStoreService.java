package main.eFlowerShop.src.service;

import main.eFlowerShop.src.entity.Flower;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.entity.Account;
import java.util.List;

public interface FlowerStoreService {
    // 查看所有的鲜花
    List<Flower> getFlowersInStock(long storeId);
    
    // 查看鲜花商店账单
    List<Account> getFlowerStoreBill(long storeId);
    
    // 查看新培育的鲜花
    List<Flower> getFlowersNewlyCultivated();
    
    // 修改鲜花信息
    boolean modifyFlower(Flower flower);
    
    // 修改顾客信息
    boolean modifyFlowerOwner(FlowerOwner flowerOwner);
    
    // 修改鲜花商店信息
    boolean modifyFlowerStore(FlowerStore flowerStore);
    
    // 根据商店ID获取鲜花商店信息
    FlowerStore getFlowerStoreById(long storeId);
    
    // 用户登录验证
    FlowerStore loginAsStore(String email, String password);
    FlowerOwner loginAsCustomer(String email, String password);
    
    // 商店管理功能
    boolean addFlowerToStore(Flower flower, long storeId);
    boolean removeFlowerFromStore(long flowerId, long storeId);
    List<Flower> getFlowersByStoreId(long storeId);
    
    // 账单管理
    boolean createOrder(long buyerId, long sellerId, List<Flower> flowers, String paymentMethod);
    boolean updateOrderStatus(long accountId, String status);
}
