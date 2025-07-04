package main.eFlowerShop.src.entity;

public class FlowerOwner {
    private String ownerName; // 所有者姓名
    private String ownerContact; // 所有者联系方式
    private String ownerAddress; // 所有者地址
    private String ownerEmail; // 所有者电子邮件
    private long ownerId; // 所有者ID
    private String ownerPassword; // 所有者密码
    private double money; // 所有者余额

    // 默认构造函数
    public FlowerOwner() {
    }

    // 参数化构造函数
    public FlowerOwner(String ownerName, String ownerContact, String ownerAddress, String ownerEmail, long ownerId, String ownerPassword, double money) {
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
        this.ownerAddress = ownerAddress;
        this.ownerEmail = ownerEmail;
        this.ownerId = ownerId;
        this.ownerPassword = ownerPassword;
        this.money = money;
    }

    // Getter 和 Setter 方法
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerContact() {
        return ownerContact;
    }

    public void setOwnerContact(String ownerContact) {
        this.ownerContact = ownerContact;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

}
