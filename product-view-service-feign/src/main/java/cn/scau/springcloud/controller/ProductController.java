package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.Product;
import cn.scau.springcloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/9/17 20:00
 */
@Controller
//@RefreshScope
public class ProductController {

    @Autowired
    ProductService productService;

    //配置客户端版本
    @Value("${version}")
    String version;

    @RequestMapping("products")
    public Object products(Model m) {
        List<Product> ps = productService.listProducts();
        m.addAttribute("ps", ps);
        m.addAttribute("version", version);
        return "products";
    }
}
