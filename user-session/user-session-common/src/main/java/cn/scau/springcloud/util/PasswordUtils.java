package cn.scau.springcloud.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.util.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
public class PasswordUtils {

    /**
     * 生成加密后的密码和盐值
     *
     * @param pwd 密码
     * @return Map => md5Pwd:加密后的密码， salt：盐值
     */
    public static Map<String, String> generateNewPassword(String pwd) {
        Map<String, String> encodeMap = new HashMap<>(2);
        String randStr = "$#*aKbc^)J47GD#%W5RSG80";
        char[] chars = randStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(randStr.length());
            sb.append(chars[index]);
        }
        String salt = sb.toString();
        encodeMap.put("salt", salt);
        encodeMap.put("md5Pwd", DigestUtils.md5Hex(pwd + salt));
        return encodeMap;
    }

    /**
     * 根据盐值和明文生成加密密码
     */
    public static String generateMd5Pwd(String pwd, String salt) {
        return DigestUtils.md5Hex(pwd + salt);
    }
}
