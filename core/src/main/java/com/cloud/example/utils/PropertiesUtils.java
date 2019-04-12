package com.cloud.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

/*
 * @Author lidongdong
 * @Description //TODO $end$
 * @Date 2018/8/25
 */
public class PropertiesUtils {
    private static Logger log = LoggerFactory.getLogger(PropertiesUtils.class);

    public PropertiesUtils() {
    }

    public static String getProperty(String file, String key, String defaultValue) {
        ResourceBundle rb = ResourceBundle.getBundle(file);

        try {
            String var5 = rb.getString(key);
            log.info(key + "=" + var5);
            return var5;
        } catch (Exception var51) {
            return defaultValue;
        }
    }

    public static String getProperty(String file, String key, String encode, String defaultValue) {
        ResourceBundle rb = ResourceBundle.getBundle(file);

        try {
            String var6 = rb.getString(key);
            var6 = new String(var6.getBytes("ISO-8859-1"), encode);
            log.info(key + "=" + var6);
            return var6;
        } catch (Exception var61) {
            return defaultValue;
        }
    }

    public static Map<String, String> getValuesByPrefix(String file, String prefix) {
        HashMap values = new HashMap();
        ResourceBundle rb = ResourceBundle.getBundle(file);

        try {
            Iterator var7 = rb.keySet().iterator();

            while(var7.hasNext()) {
                String key = (String)var7.next();
                if(key.startsWith(prefix)) {
                    String value = rb.getString(key);
                    log.info(key + "=" + value);
                    values.put(key, value);
                }
            }

            return values;
        } catch (Exception var71) {
            return values;
        }
    }
}
