package com.cloud.example.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ; lidongdong
 * @Description 生成二维码工具类
 * @Date 2020-02-20
 */
@Slf4j
public class QRCodeUtils {
    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    private static BufferedImage createImage(String content, InputStream imgPath, boolean needCompress) throws Exception {
        Map<EncodeHintType, Object> hints = new HashMap();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        //LOGO对象
        if (imgPath == null) {
            return image;
        }
        // 插入图片
        QRCodeUtils.insertImage(image, imgPath, needCompress);
        return image;
    }

    private static void insertImage(BufferedImage source, InputStream imgPath, boolean needCompress) throws Exception {
        if (imgPath == null) {
            log.info("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(imgPath);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static void encode(String content, InputStream imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    public static BufferedImage encode(String content, InputStream imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, imgPath, needCompress);
        return image;
    }

    public static void encodeStream(String content, File file, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, null, needCompress);
        InputStream inputStream = bufferedImageToInputStream(image);
        inputStreamToFile(inputStream, file);
    }

    public static void encodeStream(String content, File file, InputStream imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, imgPath, needCompress);
        InputStream inputStream = bufferedImageToInputStream(image);
        inputStreamToFile(inputStream, file);
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encode(String content, InputStream imgPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCodeUtils.createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static void encode(String content, OutputStream output) throws Exception {
        QRCodeUtils.encode(content, null, output, false);
    }

    /**
     * 将BufferedImage转换为InputStream
     *
     * @param image
     * @return
     */
    public static InputStream bufferedImageToInputStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            log.error("BufferedImage转换为InputStream异常:", e);
        }
        return null;
    }

    /**
     * 将inputStream转换为File
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static void inputStreamToFile(InputStream inputStream, File file) throws IOException {

        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        inputStream.close();

    }


}
