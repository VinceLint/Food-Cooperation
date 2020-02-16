package cn.scau.springcloud.client;

import cn.scau.springcloud.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/10/2 14:07
 */
@Component
public class ProductClientFeignHystrix implements ProductClientFeign{
    @Override
    public List<Product> listProdcuts() {
        List<Product> result = new ArrayList<>();
        result.add(new Product(0,"产品数据微服务不可用",0));
        return result;
    }
}
