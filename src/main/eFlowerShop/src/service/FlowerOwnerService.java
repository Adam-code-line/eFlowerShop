package main.eFlowerShop.src.service;

import main.eFlowerShop.src.entity.Flower;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.entity.Account;
import java.util.List;

public interface FlowerOwnerService {
    // 查看所有的鲜花
    List<Flower> getFlowersInStock(long ownerId);
    
    // 获取所有鲜花（包括所有商店和个人的）
    List<Flower> getAllFlowers();
    
    // 查看个人拥有的鲜花
    List<Flower> getMyFlowers(long ownerId);
    
    // 查看购买记录/账单
    List<Account> getMyPurchaseHistory(long ownerId);
    
    // 修改个人信息
    boolean modifyOwnerInfo(FlowerOwner flowerOwner);
    
    // 根据ID获取花主信息
    FlowerOwner getFlowerOwnerById(long ownerId);
    
    // 购买鲜花
    boolean purchaseFlower(long buyerId, long sellerId, long flowerId, String paymentMethod);
    
    // 批量购买鲜花
    boolean purchaseFlowers(long buyerId, long sellerId, List<Long> flowerIds, String paymentMethod);
    
    // 出售鲜花给商店
    boolean sellFlowerToStore(long ownerId, long storeId, long flowerId);
    
    // 培育新鲜花
    Flower cultivateFlower(long ownerId, String flowerType);
    
    // 批量培育鲜花
    List<Flower> cultivateFlowersBatch(long ownerId, String flowerType, int quantity);
    
    // 查看所有商店
    List<FlowerStore> getAllStores();
    
    // 根据地区查找商店
    List<FlowerStore> getStoresByLocation(String location);
    
    // 查看特定商店的鲜花
    List<Flower> getFlowersByStore(long storeId);
    
    // 充值
    boolean recharge(long ownerId, double amount);
    
    // 查看余额
    double getBalance(long ownerId);
    
    // 用户登录验证
    FlowerOwner login(String email, String password);
    
    // 用户注册
    boolean register(FlowerOwner flowerOwner);
    
    // 修改密码
    boolean changePassword(long ownerId, String oldPassword, String newPassword);
    
    // 根据鲜花类型搜索
    List<Flower> searchFlowersByType(String flowerType);
    
    // 根据价格区间搜索鲜花
    List<Flower> searchFlowersByPriceRange(double minPrice, double maxPrice);
    
    // 根据颜色搜索鲜花
    List<Flower> searchFlowersByColor(String color);
}
