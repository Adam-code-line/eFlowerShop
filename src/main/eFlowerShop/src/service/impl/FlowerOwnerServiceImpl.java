package main.eFlowerShop.src.service.impl;

import main.eFlowerShop.src.service.FlowerOwnerService;
import main.eFlowerShop.src.dao.FlowerDao;
import main.eFlowerShop.src.dao.FlowerOwnerDao;
import main.eFlowerShop.src.dao.FlowerStoreDao;
import main.eFlowerShop.src.dao.AccountDao;
import main.eFlowerShop.src.dao.impl.FlowerDaoImpl;
import main.eFlowerShop.src.dao.impl.FlowerOwnerDaoImpl;
import main.eFlowerShop.src.dao.impl.FlowerStoreDaoImpl;
import main.eFlowerShop.src.dao.impl.AccountDaoImpl;
import main.eFlowerShop.src.entity.Flower;
import main.eFlowerShop.src.entity.FlowerOwner;
import main.eFlowerShop.src.entity.FlowerStore;
import main.eFlowerShop.src.entity.Account;
import main.eFlowerShop.src.factory.FlowerFactory;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 花主服务实现类
 */
public class FlowerOwnerServiceImpl implements FlowerOwnerService {
    
    private FlowerDao flowerDao;
    private FlowerOwnerDao ownerDao;
    private FlowerStoreDao storeDao;
    private AccountDao accountDao;
    private FlowerFactory flowerFactory;
    
    public FlowerOwnerServiceImpl() {
        this.flowerDao = new FlowerDaoImpl();
        this.ownerDao = new FlowerOwnerDaoImpl();
        this.storeDao = new FlowerStoreDaoImpl();
        this.accountDao = new AccountDaoImpl();
        this.flowerFactory = FlowerFactory.getInstance();
    }
    
    @Override
    public List<Flower> getFlowersInStock(long ownerId) {
        try {
            // 获取市场上所有可购买的鲜花（不包括该用户自己的）
            List<Flower> allFlowers = flowerDao.getAllFlowers();
            List<Flower> availableFlowers = new ArrayList<>();
            
            for (Flower flower : allFlowers) {
                if (flower.getOwnerId() != ownerId) {
                    availableFlowers.add(flower);
                }
            }
            
            return availableFlowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Flower> getAllFlowers() {
        try {
            return flowerDao.getAllFlowers();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Flower> getMyFlowers(long ownerId) {
        try {
            // 获取所有鲜花，然后筛选出属于该用户的
            List<Flower> allFlowers = flowerDao.getAllFlowers();
            List<Flower> myFlowers = new ArrayList<>();
            
            for (Flower flower : allFlowers) {
                if (flower.getOwnerId() == ownerId) {
                    myFlowers.add(flower);
                }
            }
            
            return myFlowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Account> getMyPurchaseHistory(long ownerId) {
        try {
            return accountDao.getAccountsByBuyerId(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public boolean modifyOwnerInfo(FlowerOwner flowerOwner) {
        try {
            String sql = "UPDATE flowerowners SET ownerName = ?, ownerContact = ?, ownerAddress = ?, ownerEmail = ? WHERE ownerId = ?";
            Object[] params = {
                flowerOwner.getOwnerName(),
                flowerOwner.getOwnerContact(),
                flowerOwner.getOwnerAddress(),
                flowerOwner.getOwnerEmail(),
                flowerOwner.getOwnerId()
            };
            return ownerDao.updateFlowerOwner(sql, params) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public FlowerOwner getFlowerOwnerById(long ownerId) {
        try {
            return ownerDao.getFlowerOwnerById(ownerId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean purchaseFlower(long buyerId, long sellerId, long flowerId, String paymentMethod) {
        try {
            // 获取鲜花信息
            Flower flower = flowerDao.getFlowerById((int)flowerId);
            if (flower == null) {
                System.out.println("鲜花不存在");
                return false;
            }
            
            // 检查买家余额
            FlowerOwner buyer = ownerDao.getFlowerOwnerById(buyerId);
            if (buyer == null || buyer.getMoney() < flower.getFlprice()) {
                System.out.println("余额不足");
                return false;
            }
            
            // 创建账单
            Account account = new Account();
            account.setAmount(flower.getFlprice());
            account.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            account.setPaymentMethod(paymentMethod);
            account.setStatus("已支付");
            account.setBuyerId(buyerId);
            account.setSellerId(sellerId);
            
            // 添加账单记录
            int accountResult = accountDao.addAccount(account);
            if (accountResult <= 0) {
                System.out.println("创建订单失败");
                return false;
            }
            
            // 更新买家余额
            boolean buyerUpdate = ownerDao.reduceOwnerMoney(buyerId, flower.getFlprice());
            if (!buyerUpdate) {
                System.out.println("更新买家余额失败");
                return false;
            }
            
            // 更新卖家余额（如果卖家是花主而不是商店）
            if (sellerId != flower.getStoreId()) {
                ownerDao.addOwnerMoney(sellerId, flower.getFlprice());
            } else {
                // 如果是商店，更新商店余额
                storeDao.updateStoreBalance(sellerId, 
                    storeDao.getFlowerStoreById(sellerId).getBalance() + flower.getFlprice());
            }
            
            // 转移鲜花所有权
            flower.setOwnerId(buyerId);
            flower.setStoreId(-1); // 设置为-1表示个人拥有，DAO层会转换为NULL
            flowerDao.updateFlower(flower);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean purchaseFlowers(long buyerId, long sellerId, List<Long> flowerIds, String paymentMethod) {
        try {
            double totalAmount = 0;
            List<Flower> flowers = new ArrayList<>();
            
            // 计算总金额并验证鲜花
            for (Long flowerId : flowerIds) {
                Flower flower = flowerDao.getFlowerById(flowerId.intValue());
                if (flower == null) {
                    System.out.println("鲜花ID " + flowerId + " 不存在");
                    return false;
                }
                flowers.add(flower);
                totalAmount += flower.getFlprice();
            }
            
            // 检查买家余额
            FlowerOwner buyer = ownerDao.getFlowerOwnerById(buyerId);
            if (buyer == null || buyer.getMoney() < totalAmount) {
                System.out.println("余额不足，需要: " + totalAmount + "，当前: " + (buyer != null ? buyer.getMoney() : 0));
                return false;
            }
            
            // 创建批量订单
            Account account = new Account();
            account.setAmount(totalAmount);
            account.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            account.setPaymentMethod(paymentMethod);
            account.setStatus("已支付");
            account.setBuyerId(buyerId);
            account.setSellerId(sellerId);
            
            int accountResult = accountDao.addAccount(account);
            if (accountResult <= 0) {
                return false;
            }
            
            // 更新余额
            ownerDao.reduceOwnerMoney(buyerId, totalAmount);
            
            // 更新卖家余额
            if (sellerId != 0) {
                FlowerStore store = storeDao.getFlowerStoreById(sellerId);
                if (store != null) {
                    storeDao.updateStoreBalance(sellerId, store.getBalance() + totalAmount);
                } else {
                    ownerDao.addOwnerMoney(sellerId, totalAmount);
                }
            }
            
            // 转移所有鲜花所有权
            for (Long flowerId : flowerIds) {
                Flower flower = flowerDao.getFlowerById(flowerId.intValue());
                if (flower != null) {
                    flower.setOwnerId(buyerId);
                    flower.setStoreId(-1); // 设置为-1表示个人拥有，DAO层会转换为NULL
                    flowerDao.updateFlower(flower);
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean sellFlowerToStore(long ownerId, long storeId, long flowerId) {
        try {
            // 验证鲜花所有权
            Flower flower = flowerDao.getFlowerById((int)flowerId);
            if (flower == null || flower.getOwnerId() != ownerId) {
                System.out.println("您不拥有这朵鲜花");
                return false;
            }
            
            // 验证商店存在
            FlowerStore store = storeDao.getFlowerStoreById(storeId);
            if (store == null) {
                System.out.println("商店不存在");
                return false;
            }
            
            // 计算收购价格（市场价的80%）
            double sellPrice = flower.getFlprice() * 0.8;
            
            // 检查商店余额
            if (store.getBalance() < sellPrice) {
                System.out.println("商店余额不足");
                return false;
            }
            
            // 创建销售记录
            Account account = new Account();
            account.setAmount(sellPrice);
            account.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            account.setPaymentMethod("商店收购");
            account.setStatus("已支付");
            account.setBuyerId(storeId); // 商店是买家
            account.setSellerId(-1); // 设置为-1表示个人卖家，DAO层会处理为NULL
            
            accountDao.addAccount(account);
            
            // 更新余额
            ownerDao.addOwnerMoney(ownerId, sellPrice);
            storeDao.updateStoreBalance(storeId, store.getBalance() - sellPrice);
            
            // 转移鲜花到商店
            flower.setOwnerId(storeId);
            flower.setStoreId(storeId);
            flowerDao.updateFlower(flower);
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Flower cultivateFlower(long ownerId, String flowerType) {
        try {
            // 首先验证ownerId是否存在
            FlowerOwner owner = ownerDao.getFlowerOwnerById(ownerId);
            if (owner == null) {
                System.out.println("错误：拥有者ID " + ownerId + " 不存在");
                return null;
            }
            
            Flower newFlower = flowerFactory.cultivateNewFlower(flowerType, ownerId);
            // 个人拥有的鲜花，storeId设置为-1表示不属于任何商店
            newFlower.setStoreId(-1); 
            
            boolean result = flowerDao.addFlower(newFlower);
            if (result) {
                System.out.println("成功添加鲜花到数据库，拥有者ID: " + ownerId);
                return newFlower;
            } else {
                System.out.println("添加鲜花到数据库失败");
            }
            return null;
        } catch (Exception e) {
            System.out.println("培育鲜花失败: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<Flower> cultivateFlowersBatch(long ownerId, String flowerType, int quantity) {
        try {
            List<Flower> newFlowers = flowerFactory.cultivateFlowersBatch(flowerType, quantity, ownerId);
            List<Flower> successFlowers = new ArrayList<>();
            
            for (Flower flower : newFlowers) {
                flower.setStoreId(0); // 个人拥有
                boolean result = flowerDao.addFlower(flower);
                if (result) {
                    successFlowers.add(flower);
                }
            }
            
            return successFlowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<FlowerStore> getAllStores() {
        try {
            return storeDao.getAllFlowerStores();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<FlowerStore> getStoresByLocation(String location) {
        try {
            return storeDao.getFlowerStoresByLocation(location);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Flower> getFlowersByStore(long storeId) {
        try {
            // 获取所有鲜花，然后筛选出属于该商店的
            List<Flower> allFlowers = flowerDao.getAllFlowers();
            List<Flower> storeFlowers = new ArrayList<>();
            
            for (Flower flower : allFlowers) {
                if (flower.getStoreId() == storeId) {
                    storeFlowers.add(flower);
                }
            }
            
            return storeFlowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public boolean recharge(long ownerId, double amount) {
        try {
            return ownerDao.addOwnerMoney(ownerId, amount);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public double getBalance(long ownerId) {
        try {
            FlowerOwner owner = ownerDao.getFlowerOwnerById(ownerId);
            return owner != null ? owner.getMoney() : 0.0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }
    
    @Override
    public FlowerOwner login(String email, String password) {
        try {
            return ownerDao.validateOwner(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean register(FlowerOwner flowerOwner) {
        try {
            // 检查邮箱是否已存在
            FlowerOwner existing = ownerDao.getFlowerOwnerByEmail(flowerOwner.getOwnerEmail());
            if (existing != null) {
                System.out.println("邮箱已存在");
                return false;
            }
            
            // 设置默认余额
            if (flowerOwner.getMoney() == 0) {
                flowerOwner.setMoney(1000.0); // 新用户奖励
            }
            
            int result = ownerDao.addFlowerOwner(flowerOwner);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean changePassword(long ownerId, String oldPassword, String newPassword) {
        try {
            FlowerOwner owner = ownerDao.getFlowerOwnerById(ownerId);
            if (owner == null || !owner.getOwnerPassword().equals(oldPassword)) {
                System.out.println("原密码错误");
                return false;
            }
            
            return ownerDao.updateOwnerPassword(ownerId, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<Flower> searchFlowersByType(String flowerType) {
        try {
            List<Flower> allFlowers = flowerDao.getAllFlowers();
            List<Flower> typeFlowers = new ArrayList<>();
            
            for (Flower flower : allFlowers) {
                if (flower.getFltype() != null && flower.getFltype().contains(flowerType)) {
                    typeFlowers.add(flower);
                }
            }
            
            return typeFlowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Flower> searchFlowersByPriceRange(double minPrice, double maxPrice) {
        try {
            List<Flower> allFlowers = flowerDao.getAllFlowers();
            List<Flower> priceRangeFlowers = new ArrayList<>();
            
            for (Flower flower : allFlowers) {
                if (flower.getFlprice() >= minPrice && flower.getFlprice() <= maxPrice) {
                    priceRangeFlowers.add(flower);
                }
            }
            
            return priceRangeFlowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<Flower> searchFlowersByColor(String color) {
        try {
            List<Flower> allFlowers = flowerDao.getAllFlowers();
            List<Flower> colorFlowers = new ArrayList<>();
            
            for (Flower flower : allFlowers) {
                if (flower.getFlcolor() != null && flower.getFlcolor().contains(color)) {
                    colorFlowers.add(flower);
                }
            }
            
            return colorFlowers;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
