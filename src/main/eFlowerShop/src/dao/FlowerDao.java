package main.eFlowerShop.src.dao;
import main.eFlowerShop.src.entity.Flower;
import java.util.List;

public interface FlowerDao {
    // 获取所有鲜花信息
    List<Flower> getAllFlowers();

    // 根据ID获取鲜花信息
    Flower getFlowerById(int id);
    Flower getFlowerById(long id);

    // 添加鲜花
    boolean addFlower(Flower flower);

    // 更新鲜花信息
    boolean updateFlower(Flower flower);

    // 删除鲜花
    boolean deleteFlower(int id);
    
    // 扩展查询方法
    List<Flower> getFlowersByType(String fltype);
    List<Flower> getFlowersByStoreId(int storeId);
    List<Flower> getFlowersByStoreId(long storeId);
    List<Flower> getFlowersByPriceRange(double minPrice, double maxPrice);
}

