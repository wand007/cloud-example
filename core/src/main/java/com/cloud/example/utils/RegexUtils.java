package com.cloud.example.utils;

import com.cloud.example.enums.PhoneTypeEnum;

import java.util.regex.Pattern;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
public class RegexUtils {

    /**
     * 最小10000
     * <p>
     * 最大位数 10 位
     */
    private static final String REGEX_QQ = "^[1-9]\\d{4,9}$";
    public static final Pattern PATTERN_REGEX_QQ = Pattern.compile(REGEX_QQ);

    /**
     * 手机号检验
     */
    private static final String REGEX_PHONE = "^(1[3-9])\\d{9}$|1";
    public static final Pattern PATTERN_REGEX_PHONE = Pattern.compile(REGEX_PHONE);


    /**
     * 移动号段有134[0-8],135,136,137,138,139,  147,148,  150,151,152,157,158,159, ?172? ,178,182,183 ,184,187,188,  198。
     * <p>
     * 虚拟运营商
     * 1703 1705 1706 1440
     */
    public static final String REGEX_YD_PHONE =
            "^(13[5-9]|14[78]|15[012789]|17[28]|18[23478]|19[8])\\d{8}|^(134[0-8]|1440|1703|1705|1706)\\d{7}$";
    public static final Pattern PATTERN_REGEX_YD_PHONE = Pattern.compile(REGEX_YD_PHONE);


    /**
     * 联通号段有130，131，132， 145，146,  155，156，166, 167, 175, 176, 185，186。
     * <p>
     * 虚拟运营商
     * 1704 1707 1708 1709 171
     */
    public static final String REGEX_LT_PHONE =
            "^(13[0-2]|14[56]|15[56]|16[67]|17[156]|18[56])\\d{8}$|^(170[4789])\\d{7}|$";
    public static final Pattern PATTERN_REGEX_LT_PHONE = Pattern.compile(REGEX_LT_PHONE);


    /**
     * 电信 133，149, 153，173，17400, 177, 180，181，189, 199。
     * <p>
     * 虚拟运营商
     * 1700 1701 1702 1740
     */
    public static final String REGEX_DX_PHONE =
            "^(133|149|153|17[37]|18[019]|199)\\d{8}$|^(170[012]|1740)\\d{7}|$";
    public static final Pattern PATTERN_REGEX_DX_PHONE = Pattern.compile(REGEX_DX_PHONE);


    /**
     * 校验手机号属于哪个运营商
     *
     * @param phone
     * @return
     */
    public static PhoneTypeEnum parsePhone(String phone) {

        if (!PATTERN_REGEX_PHONE.matcher(phone).matches()) {
            return PhoneTypeEnum.ERROR;
        }

        if (PATTERN_REGEX_YD_PHONE.matcher(phone).matches()) {
            return PhoneTypeEnum.CHINA_MOBILE;
        }

        if (PATTERN_REGEX_LT_PHONE.matcher(phone).matches()) {
            return PhoneTypeEnum.CHINA_UNICOM;
        }
        if (PATTERN_REGEX_DX_PHONE.matcher(phone).matches()) {
            return PhoneTypeEnum.CHINA_TELECOM;
        }
        return PhoneTypeEnum.UNKNOWN;
    }


    public static void main(String[] args) {

        String p = "1862591";
        System.out.println("args = [" + parsePhone(p).getDescription() + "]");

//        System.out.println("args = [" + parsePhone(p) + "]");
    }


}
