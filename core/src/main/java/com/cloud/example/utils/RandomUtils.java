package com.cloud.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import static com.cloud.example.utils.VeDate.getStringToday;


/**
 * <p/>
 * Description: 随机数 </br>
 *
 * @author Mr.Bug
 * @version 2017年3月27日
 */
public class RandomUtils {

    public static String random16() {
        Random ran = new Random();
        int a = ran.nextInt(99999999);
        int b = ran.nextInt(99999999);
        long l = a * 10000000L + b;
        String num = String.valueOf(l);
        return num;
    }

    public static String random6() {
        Random random = new Random();
        String result = "";
        for (int i = 0; i < 6; i++) {
            result += random.nextInt(10);
        }
        System.out.print(result);
        return result;
    }

    public static String ordernoRandom(Integer accountid) throws Exception {
        String str_m = String.valueOf(accountid);
        String str = "00000000000";
        str_m = str.substring(0, 11 - str_m.length()) + str_m;
        String ran = getStringToday() + str_m;
        return ran;

    }

    public static String randomCode4() {
        String str = "ABCDEFGHIJKLMNPQRSTUVWXYZ3456789";
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }

    public static String accountnoRandom() throws Exception {
        String str_m = randomCode4();
        String str = "00000000000";
        str_m = str.substring(0, 11 - str_m.length()) + str_m;
        String ran = getStringToday() + str_m;
        return ran;

    }


    /**
     * 　　* 这是典型的随机洗牌算法。
     *
     * 　　* 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域）
     *
     * 　　* 算法时间复杂度O（n）
     *
     * 　　* @return 随机8为不重复数组
     *
     *
     */

    /**
     * 这是典型的随机洗牌算法。 流程是从备选数组中选择一个放入目标数组中，将选取的数组从备选数组移除（放至最后，并缩小选择区域） 算法时间复杂度O(n)
     *
     * @return 随机8为不重复数组
     */
    public static String generateNumber() {
        String no = "";
        // 初始化备选数组
        int[] defaultNums = new int[10];
        for (int i = 0; i < defaultNums.length; i++) {
            defaultNums[i] = i;
        }

        Random random = new Random();
        int[] nums = new int[LENGTH];
        // 默认数组中可以选择的部分长度
        int canBeUsed = 10;
        // 填充目标数组
        for (int i = 0; i < nums.length; i++) {
            // 将随机选取的数字存入目标数组
            int index = random.nextInt(canBeUsed);
            nums[i] = defaultNums[index];
            // 将已用过的数字扔到备选数组最后，并减小可选区域
            swap(index, canBeUsed - 1, defaultNums);
            canBeUsed--;
        }
        if (nums.length > 0) {
            for (int i = 0; i < nums.length; i++) {
                no += nums[i];
            }
        }

        return no;
    }

    private static final int LENGTH = 8;

    private static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static String generateNumber2() {
        String no = "";
        int num[] = new int[8];
        int c = 0;
        for (int i = 0; i < 8; i++) {
            num[i] = new Random().nextInt(10);
            c = num[i];
            for (int j = 0; j < i; j++) {
                if (num[j] == c) {
                    i--;
                    break;
                }
            }
        }
        if (num.length > 0) {
            for (int i = 0; i < num.length; i++) {
                no += num[i];
            }
        }
        return no;
    }

    /**
     * 获取count个随机数
     *
     * @return
     */
    public static String game21() {
        return new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()) + generateNumber();
    }

    /**
     * 16位数字订单号
     *
     * @return
     */
    public static String getOrderIdByUUId() {
        int machineId = 1;//最大支持1-9个集群机器部署
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 15 代表长度为15
        // d 代表参数为正数型
        return machineId + String.format("%015d", hashCodeV);
    }

    public static void main(String[] args) {
        try {
            System.out.println(getOrderIdByUUId());
            String str = game21();
            System.out.println(str.length() + ">>>>>" + str);
            System.out.println(System.currentTimeMillis() + "" + generateNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
