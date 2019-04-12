package com.cloud.example.config;

import com.cloud.example.utils.PropertiesUtils;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
public class Config {

    //SNOW_FLAKE_WORKER 工作ID
    public static final String SNOW_FLAKE_WORKER = "SNOW_FLAKE_WORKER";
    public static final String SNOW_FLAKE_WORKER_MAP = "SNOW_FLAKE_WORKER_MAP";


    public static String title = PropertiesUtils.getProperty("config", "mail.title.pre", null); //邮箱标识
    public static String[] earlyMails = PropertiesUtils.getProperty("config", "sp_activation_runing.mail", null).split(",");
}
