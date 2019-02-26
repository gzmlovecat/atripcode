package com.trip.entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

public class ImageUtil {
	private static final int IMAGE_WIDTH = 686;	// 图片宽度
    private static final int IMAGE_HEIGHT = 1045;	// 图片高度
    
    private static final int QR_WIDTH = 304;	// 二维码宽度
    private static final int QR_HEIGHT = 304;	// 二维码高度
    
    private static final String str = " 邀请你成为推广达人";
	
    /**
     * 图片镶嵌二维码
     * @param content		要生成二维码的URL
     * @param scrImagePath	保存的地址
     * @param imageUrl		头像URL
     * @param nickName		昵称
     * @return
     * @throws Exception
     */
	public static BufferedImage creatQrImage(String content, String scrImagePath, String imageUrl, String nickName) throws Exception {
		BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_BGR);
		Graphics graphics = image.getGraphics();
		BufferedImage qrCode = QrCodeUtil.createQrCode(content);
		// 圆图片
		BufferedImage ovalImage = createOvalImage(imageUrl);
		// 读取原始位图
        Image srcImage = ImageIO.read(new File(scrImagePath));
        graphics.drawImage(srcImage, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, null);
        // 将原始位图缩小后绘制到image对象中
        graphics.drawImage(qrCode, 191, 413, QR_WIDTH, QR_HEIGHT, null);
        
        graphics.drawImage(ovalImage, 120, 745, ovalImage.getWidth(), ovalImage.getHeight(), null);
        // 文字颜色
		graphics.setColor(Color.BLACK);
		Font font = new Font("宋体", Font.BOLD, 24);
		graphics.setFont(font);
		System.out.println("################## nickName : " + nickName);
		System.out.println("################## str : " + str);
        graphics.drawString(nickName + str, 200, 785);
        // 将image对象输出到磁盘文件中
//        ImageIO.write(image, "jpeg", new File("src/main/resources/atrip.jpg"));
        return image;
	}
	
	public static BufferedImage createDefaultHeadImage(String name, Long agentId) throws Exception {
		// 默认头像的大小 132X132
		int width = 132;
		int height = 132;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = (Graphics)image.getGraphics();
		// 填充图片颜色
		graphics.setColor(Color.GREEN);
		graphics.fillRect(0, 0, width, height);
		
		// 文字颜色
		graphics.setColor(Color.WHITE);
		Font font = new Font("宋体", Font.BOLD,60);
		graphics.setFont(font);
		graphics.drawString(name, 40, 80);
		
//		String filePath = "src/main/webapp/agent/" + agentId + ".jpg";
//		System.out.println(filePath);
//		ImageIO.write(image, "JPG", new File(filePath));
//		return "https://www.atrip.club/atrip/agent/"+agentId+".jpg";
		return image;
	}
	
	public static BufferedImage createOvalImage(String url) throws Exception {
		BufferedImage avatarImage = ImageIO.read(new URL(url));
		int width = 68;
		
		// 透明底的图片
        BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D graphics = formatAvatarImage.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 68, 68);
        
        //把图片切成一个圓
        {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //留一个像素的空白区域，这个很重要，画圆的时候把这个覆盖
            int border = 1;
            //图片是一个圆型
            Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, width - border * 2);
            //需要保留的区域
            graphics.setClip(shape);
            graphics.drawImage(avatarImage, border, border, width - border * 2, width - border * 2, null);
            graphics.dispose();
        }
        
//        BufferedImage srcImg = ImageIO.read(new URL(imageUrl));
        BufferedImage srcImg = new BufferedImage(68, 68, BufferedImage.TYPE_INT_BGR);
        //scrImg加载完之后没有任何颜色
        BufferedImage blankImage = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_INT_RGB);
        graphics = blankImage.createGraphics();
        graphics.drawImage(srcImg, 0, 0, null);
        
        int x = (blankImage.getWidth() - width) / 2;
        int y = (blankImage.getHeight() - width) / 2;
        graphics.drawImage(formatAvatarImage, x, y, width, width, null);
		
		
		return blankImage;
	}
	
	public static void main(String[] args) throws Exception {
//		createDefaultHeadImage("创", 3L);
//		String imageUrl = "https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIUyFOiaEZ0MueddCj5OlaOfnRXz3miahZOG2ibOzLPWydt1b1csjD8fPRkIibunYIictlIJJyAqQKv10Q/132";
//		BufferedImage image = createOvalImage(imageUrl);
//		ImageIO.write(image, "jpeg", new File("src/main/resources/oval1.jpg"));
		// 原图地址
    	String srcImagePath = "src/main/resources/Atrip@1x.png";
    	String requestUrl = "www.baidu.com";
		
//		BufferedImage image = creatQrImage(requestUrl, srcImagePath);
//		ImageIO.write(image, "jpeg", new File("src/main/resources/oval1.jpg"));
	}
}
