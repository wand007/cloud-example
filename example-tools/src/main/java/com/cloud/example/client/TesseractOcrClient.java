package com.cloud.example.client;

import com.cloud.example.base.BusinessException;
import com.cloud.example.enums.HttpContentTypeEnum;
import com.cloud.example.utils.QRCodeUtils;
import com.cloud.example.utils.file.MultipartFileToFile;
import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2020-02-27
 */
@Slf4j
@RestController
@RequestMapping(value = "/tesseract/ocr")
public class TesseractOcrClient {

    @PostMapping(value = "/qrCodeUrl")
    public void qrCodeUrl(String url) {
        File file = null;
        try {
            //二维码生成路径
            file = new File("/hvyogo/data" + File.separator + UUID.randomUUID() + HttpContentTypeEnum.PNG.getSuffix());

            QRCodeUtils.encodeStream(url, file, true);

        } catch (Exception e) {
            throw new BusinessException("二维码异常");
        } finally {
            if (file != null) {
                file.delete();
            }
        }

    }


    @PostMapping(value = "/uploadIdCard", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadIdCard(@RequestParam("file") MultipartFile file) {
        String result = null;
        try {
            File convFile = MultipartFileToFile.multipartFileToFile(file);

            // 图片和语言库的存放路径
            String path = "/hvyogo/data/Tesseract_OCR/tessdata";
            // 图片路径
            // 创建ITesseract对象
            ITesseract instance = new Tesseract();
            // 设置训练库的位置
            instance.setDatapath(path);
            // 根据需求选择语言库 chi_sim ：简体中文， eng
            instance.setLanguage("chi_sim");


            // 识别开始获取时间戳
            long startTime = System.currentTimeMillis();
            // 图片识别
            result = instance.doOCR(convFile);
            // 识别结束时间戳
            long endTime = System.currentTimeMillis();
            System.out.println("Time is：" + (endTime - startTime) + " 毫秒");
        } catch (TesseractException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 识别信息
        System.out.println("result: ".concat(result));
        return result;
    }


}
