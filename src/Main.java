import main.eFlowerShop.src.controller.MainController;
import main.eFlowerShop.src.dao.BaseDao;

/**
 * eFlowerShop 鲜花商店系统主入口
 * 
 * 这是整个系统的启动入口，负责初始化数据库连接并启动主控制器
 */
public class Main {
    
    public static void main(String[] args) {
        try {
            // 系统启动提示
            System.out.println("==========================================");
            System.out.println("         启动 eFlowerShop 系统");
            System.out.println("==========================================");
            System.out.println("正在初始化系统组件...");
            
            // 测试并初始化数据库连接
            System.out.print("正在连接数据库... ");
            BaseDao.testConnection();
            
            // 启动主控制器
            System.out.println("正在启动用户界面...");
            System.out.println();
            
            MainController controller = new MainController();
            controller.start();
            
        } catch (Exception e) {
            System.err.println("系统启动失败：" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}