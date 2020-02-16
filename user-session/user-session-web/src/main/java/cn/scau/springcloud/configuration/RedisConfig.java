package cn.scau.springcloud.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;

/**
 * redis configuration
 *
 * @author vincelin
 */

@Configuration
@PropertySource(value = {"classpath:application.yml"})
public class RedisConfig {
    @Value("${redis.node.maxTotal}")
    private Integer maxTotal;

    @Value("${redis.node.host}")
    private String host;

    @Value("${redis.node.port}")
    private Integer port;

    public JedisPoolConfig jedisPoolConfig() {
        //这个是修改redis性能的时候需要的对象
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(maxTotal);
        return jedisPoolConfig;
    }

    @Bean  //这个注解注入工厂的名称是方法名
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = jedisPoolConfig();
        return new JedisPool(jedisPoolConfig, host, port);
    }
}
