package cn.scau.springcloud.client;

import cn.scau.springcloud.domain.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/9/17 19:58
 */
@FeignClient(value = "PRODUCT-DATA-SERVICE",fallback = ProductClientFeignHystrix.class)
public interface ProductClientFeign {
    @GetMapping("/products")
    public List<Product> listProdcuts();
}
