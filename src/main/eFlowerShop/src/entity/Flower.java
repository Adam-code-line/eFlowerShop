package main.eFlowerShop.src.entity;

public class Flower   {
    private String flname; // 鲜花名称
    private String fltype; // 鲜花类型
    private long flid; // 鲜花ID
    private String flcolor; // 鲜花颜色
    private double flprice; // 鲜花价格
    private long ownerId; // 所有者ID
    private long storeId; // 商店

    // 默认构造函数
    public Flower() {
    }

    // 参数化构造函数
    public Flower(String flname, String fltype, long flid, String flcolor, double flprice, long ownerId, long storeId) {
        this.flname = flname;
        this.fltype = fltype;
        this.flid = flid;
        this.flcolor = flcolor;
        this.flprice = flprice;
        this.ownerId = ownerId;
        this.storeId = storeId;
    }

    // Getter 和 Setter 方法
    public String getFlname() {
        return flname;
    }

    public void setFlname(String flname) {
        this.flname = flname;
    }

    public String getFltype() {
        return fltype;
    }

    public void setFltype(String fltype) {
        this.fltype = fltype;
    }

    public long getFlid() {
        return flid;
    }

    public void setFlid(long flid) {
        this.flid = flid;
    }

    public String getFlcolor() {
        return flcolor;
    }

    public void setFlcolor(String flcolor) {
        this.flcolor = flcolor;
    }

    public double getFlprice() {
        return flprice;
    }

    public void setFlprice(double flprice) {
        this.flprice = flprice;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
