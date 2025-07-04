package main.eFlowerShop.src.factory;

import main.eFlowerShop.src.entity.Flower;
import main.eFlowerShop.src.entity.FlowerStore;
import java.util.List;
import java.util.Random;

/**
 * 鲜花工厂 - 负责鲜花的培育和商店管理
 */
public class FlowerFactory {
    
    private static FlowerFactory instance;
    private Random random = new Random();
    
    // 私有构造函数 - 单例模式
    private FlowerFactory() {}
    
    public static FlowerFactory getInstance() {
        if (instance == null) {
            synchronized (FlowerFactory.class) {
                if (instance == null) {
                    instance = new FlowerFactory();
                }
            }
        }
        return instance;
    }
    
    /**
     * 培育新鲜花
     * @param flowerType 鲜花类型
     * @param cultivatorId 培育者ID（商店或顾客）
     * @return 新培育的鲜花
     */
    public Flower cultivateNewFlower(String flowerType, long cultivatorId) {
        Flower newFlower = new Flower();
        
        // 设置基本信息
        newFlower.setFlname(generateFlowerName(flowerType));
        newFlower.setFltype(flowerType);
        newFlower.setFlcolor(generateRandomColor());
        newFlower.setFlprice(generatePrice(flowerType));
        newFlower.setOwnerId(cultivatorId);
        
        return newFlower;
    }
    
    /**
     * 批量培育鲜花
     * @param flowerType 鲜花类型
     * @param quantity 数量
     * @param cultivatorId 培育者ID
     * @return 培育的鲜花列表
     */
    public List<Flower> cultivateFlowersBatch(String flowerType, int quantity, long cultivatorId) {
        List<Flower> flowers = new java.util.ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            flowers.add(cultivateNewFlower(flowerType, cultivatorId));
        }
        return flowers;
    }
    
    /**
     * 创建新的鲜花商店
     * @param storeName 商店名称
     * @param location 位置
     * @param contact 联系方式
     * @param email 邮箱
     * @param password 密码
     * @return 新创建的商店
     */
    public FlowerStore createFlowerStore(String storeName, String location, 
                                       String contact, String email, String password) {
        FlowerStore store = new FlowerStore();
        store.setStoreName(storeName);
        store.setStoreLocation(location);
        store.setStoreContact(contact);
        store.setStoreEmail(email);
        store.setStorePassword(password);
        store.setBalance(10000.0); // 初始资金
        
        return store;
    }
    
    /**
     * 为商店补充库存 - 自动培育鲜花
     * @param storeId 商店ID
     * @param flowerTypes 需要的鲜花类型
     * @return 培育的鲜花列表
     */
    public List<Flower> restockForStore(long storeId, String[] flowerTypes) {
        List<Flower> newStock = new java.util.ArrayList<>();
        
        for (String type : flowerTypes) {
            // 每种类型培育5-15朵
            int quantity = 5 + random.nextInt(11);
            newStock.addAll(cultivateFlowersBatch(type, quantity, storeId));
        }
        
        return newStock;
    }
    
    // 私有辅助方法
    private String generateFlowerName(String type) {
        String[] prefixes = {"精品", "特级", "优质", "高档", "珍稀"};
        return prefixes[random.nextInt(prefixes.length)] + type;
    }
    
    private String generateRandomColor() {
        String[] colors = {"红色", "粉色", "白色", "黄色", "紫色", "蓝色", "橙色", "绿色"};
        return colors[random.nextInt(colors.length)];
    }
    
    private double generatePrice(String flowerType) {
        // 根据鲜花类型设置基础价格
        double basePrice = 20.0;
        switch (flowerType) {
            case "玫瑰":
                basePrice = 25.0;
                break;
            case "百合":
                basePrice = 30.0;
                break;
            case "康乃馨":
                basePrice = 18.0;
                break;
            case "郁金香":
                basePrice = 35.0;
                break;
            case "向日葵":
                basePrice = 22.0;
                break;
            default:
                basePrice = 20.0;
        }
        
        // 加入一些随机变化 (±20%)
        double variation = (random.nextDouble() - 0.5) * 0.4;
        return Math.round((basePrice * (1 + variation)) * 100.0) / 100.0;
    }
}
