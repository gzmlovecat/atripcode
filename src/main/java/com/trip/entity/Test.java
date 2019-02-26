
package com.trip.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class Test {
	
	public static void main(String[] args) throws Exception {
		String[] ignoreLoginUrls = {
	            "/atrip/login",
	            "/atrip/miniProgram/login",
	            "/atrip/miniProgram/register"
//	            ,"/atrip/order/query"
//	            ,"/atrip/supplier/add"
//	            ,"/atrip/order/export"
//	            ,"/atrip/order/update"
	            ,"/atrip/miniProgram/agent/inviterQRCode"
	    };
		
		String s = "/atrip/image/2.jpg";
		if( !Arrays.asList(ignoreLoginUrls).contains(s) && !s.contains("/atrip/image")){

            System.out.println("1");
        } else {
        	System.out.println("2");
        }
		
		String agentName = "to(to(13472486540测试数据，测试数据)";
		int l = agentName.lastIndexOf("(");
		System.out.println(l);
		System.out.println(agentName.substring(l));
		
		
		String agentName1 = "toto(测试)-vip旅游达人";
		int ch1 = agentName1.indexOf("-vip旅游达人");
		int ch2 = agentName1.indexOf("-旅游达人");
		int ch3 = agentName1.indexOf("-创享合伙人");
		System.out.println(ch1);
		System.out.println(ch2);
		System.out.println(ch3);
		System.out.println(agentName1.substring(0,8));
		if(agentName1.indexOf("-旅游达人") > 0 || agentName1.indexOf("-vip旅游达人") > 0|| agentName1.indexOf("-创享合伙人") > 0) {
			
			System.out.println("YYY");
		} else {
			System.out.println("NNN");
		}
		
//		BigDecimal b1 = new BigDecimal(1600);
//		BigDecimal b2 = new BigDecimal(2200);
//		BigDecimal b3 = b1.divide(b2, 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
//		System.out.println(b3.toString());
//		
//		int totalPageSize = 134;
//		int pageSize = 3;
//		int modTotalPageNum = totalPageSize % pageSize;
//		
//		System.out.println(modTotalPageNum);
//		int totalPageNum = modTotalPageNum == 0 ? totalPageSize / pageSize : totalPageSize / pageSize + 1;
//		System.out.println(totalPageNum);
//		
//		String sss = "曹";
//		String ensss = URLEncoder.encode(sss, "utf-8");
//		System.out.println(ensss);
//		String desss = URLDecoder.decode(ensss, "utf-8");
//		System.out.println(desss);
		// https://www.atrip.club/miniprogram/authorizedLoginOrRegister?id=446&agentName=Atrip%E6%97%85%E8%A1%8C_%E5%AE%8B%E5%BF%97%E6%96%87(18818277042)&agentAvatar=https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKCrKHFGicJx4H1MJupDs4OMSXnPJKV0G7w0ua7OyVYSMydlqJqdUP5ibWHPRr2CzEPYsnxwj2ia6NNA/132
//		String url = "https://www.atrip.club/miniprogram/authorizedLoginOrRegister";
//		url = url + "?id=446"+ "&agentName=" + URLEncoder.encode("Atrip旅行_宋志文(18818277042)", "utf-8") + "&agentAvatar=" + URLEncoder.encode("https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKCrKHFGicJx4H1MJupDs4OMSXnPJKV0G7w0ua7OyVYSMydlqJqdUP5ibWHPRr2CzEPYsnxwj2ia6NNA/132","utf-8");
////		url = URLEncoder.encode(url, "utf-8");
//		System.out.println(url);
		
//		String s = new SimpleDateFormat("yyyy/MM/dd").format(null);
//		System.out.println(s);
		
//		System.out.println(System.currentTimeMillis());
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日Eahh点mm分", Locale.CHINA);
//		System.out.println(sdf.format(new Date()));
//		SimpleDateFormat sdf1 = new SimpleDateFormat("E");
//		System.out.println(sdf1.format(new Date()));
//		BigDecimal a = BigDecimal.valueOf(-12);
//		System.out.print(a.compareTo(BigDecimal.ZERO));
//		BufferedImage img = scale("src/main/resources/WechatIMG230.png", IMAGE_HEIGHT, IMAGE_WIDTH, true);
//		ImageIO.write(img,"jpg", new File("src/main/resources/WechatIMG230.jpg"));
		
//		Test.encode("www.baidu.com", IMAGE_WIDTH, IMAGE_HEIGHT, "src/main/resources/WechatIMG230.png", "src/main/resources/WechatIMG230.jpg");
//		ImageIO.write(img,"jpg", new File("src/main/resources/WechatIMG230.jpg"));
//		File file = new File("src/main/resources/WechatIMG230.png");
//		BufferedImage img = ImageIO.read(new File("src/main/resources/WechatIMG230.png"));
//		ImageIO.write(img,"jpg", new File("src/main/resources/WechatIMG230.jpg"));
		
	}
	
	// 插入图片的高度宽度-二维码的高度宽度
	private static final int QR_WIDTH = 304;
	private static final int QR_HEIGHT = 304;
	private static final int IMAGE_WIDTH = 686;
	private static final int IMAGE_HEIGHT  = 1045;
	private static final int IMAGE_X_BEGIN = 191;
	private static final int IMAGE_Y_BEGIN = 413;
	private static final int QR_HALF_WIDTH = QR_WIDTH / 2;
	private static final int FRAME_WIDTH = 2;
	private static final String CONTENT = "www.baidu.com";
 
	// 二维码写码器
	private static MultiFormatWriter mutiWriter = new MultiFormatWriter();
	
	/**
	 * 
	 * @param content	二维码生成地址
	 * @param width		图片高度
	 * @param height	图片宽度
	 * @param srcImagePath	来源地址
	 * @param destImagePath	生成地址
	 */
	public static void encode(String content, int width, int height, String srcImagePath, String destImagePath) {
		try {
			ImageIO.write(genBarcode(content, width, height, srcImagePath),
					"jpg", new File(destImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}
	}
	
	private static BufferedImage genBarcode(String content, int width,
			int height, String srcImagePath) throws WriterException,
			IOException {
		// 读取源图像
//		BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
//				IMAGE_HEIGHT, true);
 
		Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 生成二维码
		BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
				QR_WIDTH, QR_HEIGHT, hint);
		
		BufferedImage scaleImage = scale(srcImagePath, IMAGE_HEIGHT,
				IMAGE_WIDTH, true);
		int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
		for (int i = 0; i < scaleImage.getWidth(); i++) {
			for (int j = 0; j < scaleImage.getHeight(); j++) {
				srcPixels[i][j] = scaleImage.getRGB(i, j);
			}
		}
//		return scaleImage;
		
//		File file = new File(srcImagePath);
//		BufferedImage sourceImg = ImageIO.read(new FileInputStream(file));
//		int[][] sourcePixel = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
//		for(int i = 0; i < IMAGE_WIDTH; i++) {
//			for(int j = 0; j < IMAGE_HEIGHT; j++) {
//				sourcePixel[i][j] = sourceImg.getRGB(i, j);
//			}
//		}
		// 把图像变成以为数组
//		int halfW = sourceImg.getWidth() / 2;
//		int halfH = sourceImg.getHeight() / 2;
		
		
		
		
		// 生成的图片像素大小
		int[] pixels = new int[IMAGE_WIDTH * IMAGE_HEIGHT];
		for(int y = 0; y < IMAGE_HEIGHT; y++) {
			for(int x = 0; x < IMAGE_WIDTH; x++) {
				pixels[x*y] = srcPixels[x][y];
				// 读取图片
				if(x >= IMAGE_X_BEGIN && x < IMAGE_X_BEGIN + QR_WIDTH && y >= IMAGE_Y_BEGIN && y < IMAGE_Y_BEGIN + QR_HEIGHT) {

				// 在图片四周形成边框
				}  else {

				}
			}
		}
 
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		image.getRaster().setDataElements(0, 0, width, height, pixels);
 
		return image;

	}
	
	/**
	 * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标
	 * 
	 * @param srcImageFile
	 *            源文件地址
	 * @param height
	 *            目标高度
	 * @param width
	 *            目标宽度
	 * @param hasFiller
	 *            比例不对时是否需要补白：true为补白; false为不补白;
	 * @throws IOException
	 */
	private static BufferedImage scale(String srcImageFile, int height,
			int width, boolean hasFiller) throws IOException {
		double ratio = 0.0; // 缩放比例
//		URL url = ClassLoader.getSystemResource("/main/resources/WechatIMG230.png");
//		URL url = new URL("http://www.atrip.club/test.png");
		
		File file = new File(srcImageFile);
		System.out.println(file.getName() + "@@@@@" + file.exists());
		BufferedImage srcImage = ImageIO.read(file);
		Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
		// 计算比例
		if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
			if (srcImage.getHeight() > srcImage.getWidth()) {
				ratio = (new Integer(height)).doubleValue()
						/ srcImage.getHeight();
			} else {
				ratio = (new Integer(width)).doubleValue()
						/ srcImage.getWidth();
			}
			AffineTransformOp op = new AffineTransformOp(
					AffineTransform.getScaleInstance(ratio, ratio), null);
			destImage = op.filter(srcImage, null);
		}
		if (hasFiller) {// 补白
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic = image.createGraphics();
			graphic.setColor(Color.white);
			graphic.fillRect(0, 0, width, height);
			if (width == destImage.getWidth(null))
				graphic.drawImage(destImage, 0,
						(height - destImage.getHeight(null)) / 2,
						destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);
			else
				graphic.drawImage(destImage,
						(width - destImage.getWidth(null)) / 2, 0,
						destImage.getWidth(null), destImage.getHeight(null),
						Color.white, null);
			graphic.dispose();
			destImage = image;
		}
		return (BufferedImage) destImage;
	}
	
}
