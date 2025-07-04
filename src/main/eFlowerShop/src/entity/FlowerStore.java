package main.eFlowerShop.src.entity;

public class FlowerStore {
    private String storeName; // 商店名称
    private String storeLocation; // 商店位置
    private String storeContact; // 商店联系方式
    private String storeEmail; // 商店电子邮件
    private long storeId; // 商店ID
    private String storePassword; // 商店密码
    private double balance; // 商店余额

    // 默认构造函数
    public FlowerStore() {
    }

    // 参数化构造函数
    public FlowerStore(String storeName, String storeLocation, String storeContact, String storeEmail, long storeId, String storePassword, double balance) {
        this.storeName = storeName;
        this.storeLocation = storeLocation;
        this.storeContact = storeContact;
        this.storeEmail = storeEmail;
        this.storeId = storeId;
        this.storePassword = storePassword;
        this.balance = balance;
    }

    // Getter 和 Setter 方法
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;
    }

    public String getStoreContact() {
        return storeContact;
    }

    public void setStoreContact(String storeContact) {
        this.storeContact = storeContact;
    }

    public String getStoreEmail() {
        return storeEmail;
    }

    public void setStoreEmail(String storeEmail) {
        this.storeEmail = storeEmail;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
