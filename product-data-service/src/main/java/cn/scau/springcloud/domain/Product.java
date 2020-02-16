package cn.scau.springcloud.domain;

/**
 * @author 林跃涛
 * @version 1.0
 * @date 2019/9/17 19:29
 */
public class Product {
    private int id;
    private String name;
    private int price;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public Product() {

    }
    public Product(int id, String name, int price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
