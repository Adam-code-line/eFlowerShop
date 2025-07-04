package main.eFlowerShop.src.entity;

public class Account {
    private long accountId; // 账单ID
    private double amount; // 金额
    private String date; // 日期
    private String paymentMethod; // 支付方式
    private String status; // 状态（如 "已支付"、"未支付"）
    private long buyerId; // 买家ID
    private long sellerId; // 卖家ID

    //默认构造函数
    public Account() {};

    // 参数化构造函数
    public Account(long accountId, double amount, String date, String paymentMethod, String status, long buyerId, long sellerId) {
        this.accountId = accountId;
        this.amount = amount;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public long getSellerId() {
        return sellerId;
    }

    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

}
