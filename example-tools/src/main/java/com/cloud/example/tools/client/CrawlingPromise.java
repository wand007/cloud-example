package com.cloud.example.tools.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.example.tools.vo.DishonestCustomerInfo;
import com.cloud.example.utils.JsonUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ; lidongdong
 * @Description 爬取失信人员信息
 * @Date 2020-11-05
 */
public class CrawlingPromise {

    private static Logger logger = LoggerFactory.getLogger(CrawlingPromise.class);
    private static final String UTF_8 = "UTF-8";
    private static final String SPIDER_URL = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php";


    public static void main(String[] args) {
        System.out.println(JsonUtils.toJson(listDishonest("吴晓波", "", "")));
    }

    /**
     * 查询出原始数据，返回字符串
     *
     * @param params
     * @return
     */
    private static String getStringDishonest(Map<String, Object> params) {
        StringBuffer resultBuffer;
        String fullUrl = null;
        // 创建HTTP请求
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        HttpClient client = httpClientBuilder.build();
        BufferedReader bufferedReader = null;
        // 构建请求参数
        StringBuilder buffer = new StringBuilder();
        if (params != null && params.size() > 0) {
            for (Map.Entry entry : params.entrySet()) {
                buffer.append(entry.getKey());
                buffer.append("=");
                try {
                    buffer.append(URLEncoder.encode((String) entry.getValue(), UTF_8));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException("失信人查询编码异常:" + e);
                }
                buffer.append("&");
            }
        }
        // 拼接查询URL
        if (buffer.length() > 0) {
            fullUrl = SPIDER_URL + "?" + buffer.substring(0, buffer.length() - 1);
        }

        // 请求拼接后的地址获取详细信息
        HttpGet httpGet = new HttpGet(fullUrl);
        try {
            // 获得响应数据
            HttpResponse response = client.execute(httpGet);
            // 读入响应数据体文本信息
            // http传输乱码-解决new InputStreamReader(response.getEntity().getContent(),UTF_8)
            bufferedReader = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8));
            String temp;
            resultBuffer = new StringBuffer();
            while ((temp = bufferedReader.readLine()) != null) {
                resultBuffer.append(temp);
            }
            return resultBuffer.toString();
        } catch (IOException e) {
            logger.error("失信工具IO异常：" + e.getMessage());
            throw new RuntimeException("失信工具IO异常:" + e);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    logger.error("失信工具IO关闭异常：" + e.getMessage());
                }
            }
        }
    }

    /**
     * 查询失信人
     *
     * @param performedName 名称
     * @param cardNumber    证件号码
     * @param areaName      省份
     * @return
     */
    public static List listDishonest(String performedName, String cardNumber, String areaName) {

        // 查询条件
        Map<String, Object> map = new HashMap<>();
        map.put("resource_id", "6899");
        map.put("query", "失信被执行人名单");
        map.put("cardNum", cardNumber);
        map.put("iname", performedName);
        map.put("areaName", areaName);
        map.put("ie", "utf-8");
        map.put("oe", "utf-8");
        map.put("format", "json");
        map.put("t", "1524537973200");
        map.put("cb", "jQuery110207319777455577083_1524537959352");
        map.put("_", "1524537959354");

        // 查询结果
        String strResult = getStringDishonest(map);
        strResult = strResult.substring(strResult.indexOf("(") + 1, strResult.lastIndexOf(");"));
        logger.info("FastJson转换字符串为对象：" + JSONObject.parseObject(strResult));
        // json封装
        JSONObject firstMap = JSONObject.parseObject(strResult);
        JSONArray secondMap = (JSONArray) firstMap.get("data");
        // 返回的结果初始化列表
        List infoList = new ArrayList<>();

        if (secondMap != null && secondMap.size() > 0) {
            JSONObject thirdMap = (JSONObject) secondMap.get(0);
            JSONArray forthMap = (JSONArray) thirdMap.get("result");
            for (int i = 0; i < forthMap.size(); i++) {
                JSONObject maps = forthMap.getJSONObject(i);
                logger.info("JSON Object[" + i + "]:" + maps);

                // 自定义类封装
                DishonestCustomerInfo info = new DishonestCustomerInfo();
                info.setPerformedName(performedName);
                info.setCardNumber(cardNumber);
                info.setSex(String.valueOf(maps.get("sexy")).trim());
                info.setAge(Integer.valueOf(String.valueOf(maps.get("age")).trim()));
                info.setAreaName(String.valueOf(maps.get("areaName")).trim());
                info.setCourtName(String.valueOf(maps.get("courtName")).trim());
                info.setGistId((String.valueOf(maps.get("gistId")).trim()));
                info.setRegisterDate(String.valueOf(maps.get("regDate")).trim());
                info.setCaseCode(String.valueOf(maps.get("caseCode")).trim());
                info.setGistInstitution(String.valueOf(maps.get("gistUnit")).trim());
                info.setDuty(String.valueOf(maps.get("duty")).trim());
                info.setPerformance(String.valueOf(maps.get("performance")).trim());
                info.setConcreteReason(String.valueOf(maps.get("disruptTypeName")).trim());
                info.setType(String.valueOf(maps.get("type")).trim());
                info.setPublishedAt(Long.valueOf(String.valueOf(maps.get("publishDateStamp")).trim() + "000"));
                infoList.add(info);
            }
        }
        return infoList;
    }
}
