package com.trip.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrCodeUtil {
	private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色
    private static final int QR_WIDTH = 304;	// 二维码宽度
    private static final int QR_HEIGHT = 304;	// 二维码高度
    private static final int IMAGE_WIDTH = 686;	// 图片宽度
    private static final int IMAGE_HEIGHT = 1045;	// 图片高度
//    private static final String SRC_IMAGE = "src/main/resources/atrip.png";	// 原图地址
    
	public static BufferedImage createQrCode(String contents) throws Exception {
		// 定义二维码参数
		HashMap map = new HashMap();
		// 设置编码
		map.put(EncodeHintType.CHARACTER_SET, "utf-8");
		// 设置纠错等级
		map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		map.put(EncodeHintType.MARGIN, 2);
		
		// 生成二维码
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents
				, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, map);
		BufferedImage image = new BufferedImage(QR_WIDTH, QR_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < QR_WIDTH; x++) {  
            for (int y = 0; y < QR_HEIGHT; y++) {  
                image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);  
            }  
        }
		return image;
	}
	
//	public static BufferedImage creatQrImage(String content, String scrImagePath) throws Exception {
//		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_BGR);
//		Graphics graphics = image.getGraphics();
//		BufferedImage qrCode = createQrCode(content);
//		// 读取原始位图
//        Image srcImage = ImageIO.read(new File(scrImagePath));
//        graphics.drawImage(srcImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
//        // 将原始位图缩小后绘制到image对象中
//        graphics.drawImage(qrCode, 191, 413, QR_WIDTH, QR_HEIGHT, null);
//        // 将image对象输出到磁盘文件中
////        ImageIO.write(image, "jpeg", new File("src/main/resources/atrip.jpg"));
//        return image;
//	}
	
	public static void main(String[] args) throws Exception {
//		String content = "baidu.com";
//		Long agentId = 2L;
//		String srcImagePath = "src/main/resources/atrip.png";
//		BufferedImage image = ImageUtil.creatQrImage(content, srcImagePath);
////		String filePath = "src/main/resources/atrip.jpg";
//		String filePath = "src/main/webapp/image/" + agentId + ".jpg";
//		System.out.println(filePath);
//		ImageIO.write(image, "JPG", new File(filePath));
	}
}
