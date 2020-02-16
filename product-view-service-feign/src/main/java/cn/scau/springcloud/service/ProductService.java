package cn.scau.springcloud.service;

import cn.scau.springcloud.client.ProductClientFeign;
import cn.scau.springcloud.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/9/17 20:00
 */
@Service
public class ProductService {
    @Autowired
    ProductClientFeign productClientFeign;
    public List<Product> listProducts(){
        return productClientFeign.listProdcuts();

    }
}
