package main.eFlowerShop.src.service.impl;

import main.eFlowerShop.src.service.FlowerStoreService;
import main.eFlowerShop.src.dao.FlowerDao;
import main.eFlowerShop.src.dao.FlowerStoreDao;
import main.eFlowerShop.src.dao.FlowerOwnerDao;
import main.eFlowerShop.src.dao.AccountDao;
import main.eFlowerShop.src.dao.impl.FlowerDaoImpl;
import main.eFlowerShop.src.dao.impl.FlowerStoreDaoImpl;
import main.eFlowerShop.src.dao.impl.FlowerOwnerDaoImpl;
import main.eFlowerShop.src.dao.impl.AccountDaoImpl;
import main.eFlowerShop.src.entity.Flower;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.Account;
import main.eFlowerShop.src.factory.FlowerFactory;

import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 鲜花商店服务实现类
 */
public class FlowerStoreServiceImpl implements FlowerStoreService {
    
    private FlowerDao flowerDao;
    private FlowerStoreDao storeDao;
    private FlowerOwnerDao ownerDao;
    private AccountDao accountDao;
    private FlowerFactory flowerFactory;
    
    public FlowerStoreServiceImpl() {
        this.flowerDao = new FlowerDaoImpl();
        this.storeDao = new FlowerStoreDaoImpl();
        this.ownerDao = new FlowerOwnerDaoImpl();
        this.accountDao = new AccountDaoImpl();
        this.flowerFactory = FlowerFactory.getInstance();
    }
    
    @Override
    public List<Flower> getFlowersInStock(long storeId) {
        try {
            return flowerDao.getFlowersByStoreId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    @Override
    public List<Account> getFlowerStoreBill(long storeId) {
        try {
            return accountDao.getAccountsBySellerId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    @Override
    public List<Flower> getFlowersNewlyCultivated() {
        try {

            // 临时返回所有鲜花
            return flowerDao.getAllFlowers();
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    @Override
    public boolean modifyFlower(Flower flower) {
        try {
            return flowerDao.updateFlower(flower);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean modifyFlowerOwner(FlowerOwner flowerOwner) {
        try {
            return ownerDao.updateFlowerOwner(flowerOwner);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean modifyFlowerStore(FlowerStore flowerStore) {
        try {
            return storeDao.updateFlowerStore(flowerStore);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public FlowerStore getFlowerStoreById(long storeId) {
        try {
            return storeDao.getFlowerStoreById(storeId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public FlowerStore loginAsStore(String email, String password) {
        try {
            return storeDao.validateStore(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public FlowerOwner loginAsCustomer(String email, String password) {
        try {
            return ownerDao.validateOwner(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean addFlowerToStore(Flower flower, long storeId) {
        try {
            flower.setStoreId(storeId);
            return flowerDao.addFlower(flower);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean removeFlowerFromStore(long flowerId, long storeId) {
        try {
            // 验证鲜花是否属于该商店
            Flower flower = flowerDao.getFlowerById(flowerId);
            if (flower != null && flower.getStoreId() == storeId) {
                return flowerDao.deleteFlower((int)flowerId);
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Flower> getFlowersByStoreId(long storeId) {
        try {
            return flowerDao.getFlowersByStoreId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
            return new java.util.ArrayList<>();
        }
    }
    
    @Override
    public boolean createOrder(long buyerId, long sellerId, List<Flower> flowers, String paymentMethod) {
        try {
            // 计算总金额
            double totalAmount = flowers.stream()
                .mapToDouble(Flower::getFlprice)
                .sum();
            
            // 检查买家余额
            FlowerOwner buyer = ownerDao.getFlowerOwnerById(buyerId);
            if (buyer == null || buyer.getMoney() < totalAmount) {
                System.out.println("买家余额不足");
                return false;
            }
            
            // 创建账单
            Account account = new Account();
            account.setAmount(totalAmount);
            account.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            account.setPaymentMethod(paymentMethod);
            account.setStatus("待支付");
            account.setBuyerId(buyerId);
            account.setSellerId(sellerId);
            
            int accountResult = accountDao.addAccount(account);
            
            if (accountResult > 0) {
                // 更新鲜花拥有者
                for (Flower flower : flowers) {
                    flower.setOwnerId(buyerId);
                    modifyFlower(flower);
                }
                return true;
            }
            
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean updateOrderStatus(long accountId, String status) {
        try {
            return accountDao.updateAccountStatus(accountId, status);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 为商店自动补充库存
     */
    public boolean restockStore(long storeId, String[] flowerTypes) {
        try {
            List<Flower> newFlowers = flowerFactory.restockForStore(storeId, flowerTypes);
            
            for (Flower flower : newFlowers) {
                flower.setStoreId(storeId);
                flowerDao.addFlower(flower);
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取商店的收入统计
     */
    public double getStoreRevenue(long storeId) {
        try {
            List<Account> accounts = accountDao.getAccountsBySellerId(storeId);
            return accounts.stream()
                .filter(account -> "已完成".equals(account.getStatus()))
                .mapToDouble(Account::getAmount)
                .sum();
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    /**
     * 获取商店的总订单数
     */
    public int getStoreOrderCount(long storeId) {
        try {
            List<Account> accounts = accountDao.getAccountsBySellerId(storeId);
            return accounts.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * 批量添加鲜花到商店
     */
    public boolean addFlowersToStore(List<Flower> flowers, long storeId) {
        try {
            boolean allSuccess = true;
            for (Flower flower : flowers) {
                flower.setStoreId(storeId);
                if (!flowerDao.addFlower(flower)) {
                    allSuccess = false;
                }
            }
            return allSuccess;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 检查商店库存是否充足
     */
    public boolean checkStoreStock(long storeId, int minimumStock) {
        try {
            List<Flower> flowers = flowerDao.getFlowersByStoreId(storeId);
            return flowers.size() >= minimumStock;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
