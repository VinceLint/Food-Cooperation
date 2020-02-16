package cn.scau.springcloud;

import cn.hutool.core.util.NetUtil;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/10/2 14:20
 */
@SpringBootApplication
@EnableHystrixDashboard
public class ProductServiceHystrixDashboardApplication {
    public static void main(String[] args) {
        int port = 8020;
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(ProductServiceHystrixDashboardApplication.class).properties("server.port=" + port).run(args);
    }
}
