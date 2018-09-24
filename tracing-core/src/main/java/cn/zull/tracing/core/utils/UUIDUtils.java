package cn.zull.tracing.core.utils;

import java.util.Random;
import java.util.UUID;

/**
 * @author zurun
 * @date 2018/6/21 15:30:46
 */
public class UUIDUtils {

    /**
     * 去横线的uuid
     *
     * @return
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static String genRandomNum(int pwdLen) {
        //35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        //生成的随机数
        int i;
        //生成的密码的长度
        int count = 0;
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer pwd = new StringBuffer();
        Random r = new Random();
        while (count < pwdLen) {
            //生成随机数，取绝对值，防止生成负数，
            //生成的数最大为36-1
            i = Math.abs(r.nextInt(maxNum));

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }
        return pwd.toString();

    }
}
