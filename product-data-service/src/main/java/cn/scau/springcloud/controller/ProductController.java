package cn.scau.springcloud.controller;

import cn.scau.springcloud.domain.Product;
import cn.scau.springcloud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/9/17 19:31
 */
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @RequestMapping("/products")
    public Object products() {
        List<Product> ps = productService.listProducts();
        return ps;
    }
}
