package cn.scau.springcloud.helper;

import cn.scau.springcloud.util.RedisUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author VinceLin
 * 2020/3/31 4:19 下午
 * 验证码工具类
 */
@Component
public class VerifyHelper {

    @Resource
    private RedisUtils redisUtils;

    private static final String PER_VERIFY = "Verify_Code_";

    public String getRandomCode(String userId) {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        redisUtils.set(PER_VERIFY + userId, str.toString(), 30 * 60);
        return str.toString();
    }

    public String getVerifyCode(String userId) {
        Object verifyCode = redisUtils.get(PER_VERIFY + userId);
        if (verifyCode == null) {
            return "";
        }
        return verifyCode.toString();
    }
}
