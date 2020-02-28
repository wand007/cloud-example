package com.cloud.example.enums;

import com.cloud.example.base.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ; lidongdong
 * @Description 此处列出常见的content type, 需要其他类型自行添加, 不提供解析
 * @Date 2020-02-27
 */
@Getter
@AllArgsConstructor
public enum HttpContentTypeEnum {

    //type suffix content-type
    AVI(".avi", "video/avi"),
    BMP(".bmp", "application/x-bmp"),
    DOC(".doc", "application/msword"),
    DOCX(".docx", "application/vnd.ms-word.document.12"),
    JPE(".jpe", "image/jpeg"),
    JPEG(".jpeg", "image/jpeg"),
    JPG(".jpg", "image/jpeg"),
    MP4(".mp4", "video/mpeg4"),
    PNG(".png", "application/x-png"),
    XLS(".xls", "application/x-xls"),
    ZIP(".zip", "application/x-zip-compressed"),
    RAR(".rar", "application/octet-stream"),
    PDF(".pdf", "application/pdf"),
    ICO(".ico", "application/x-ico"),
    XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    ;

    private String suffix;
    private String contentType;


    public static HttpContentTypeEnum parse(String suffix) {
        for (HttpContentTypeEnum item : HttpContentTypeEnum.values()) {
            if (item.getSuffix().equals(suffix)) {
                return item;
            }
        }
        throw new BusinessException("解析文件ContentType失败");
    }
}
