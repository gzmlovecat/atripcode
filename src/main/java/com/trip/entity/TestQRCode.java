package com.trip.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class TestQRCode {
	private static final int BLACK = 0xFF000000;//用于设置图案的颜色
    private static final int WHITE = 0xFFFFFFFF; //用于背景色
	public static void main(String[] args) { 
		try { 
        	String content="大家好，我是李庆文，很高兴认识大家";
    	    String logUri="src/main/resources/atrip.png";
    	    String outFileUri="src/main/resources/atrip.jpg";
    	    int[]  size=new int[]{430,430};
    	    String format = "jpg";
//            draw(); 
//        	drawImage();
        	CreatQrImage(content, format, outFileUri, logUri,size);
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
	}
	
	public static void CreatQrImage(String content,String format,String outFileUri,String logUri, int ...size) throws IOException, WriterException{
	       int width = 304; // 二维码图片宽度 304 
	       int height = 304; // 二维码图片高度304 
	       
	       Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
	        // 指定纠错等级,纠错级别（L 7%、M 15%、Q 25%、H 30%）
	       hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
	       // 内容所使用字符集编码
	       hints.put(EncodeHintType.CHARACTER_SET, "utf-8");   
	       //hints.put(EncodeHintType.MAX_SIZE, 350);//设置图片的最大值
	       //hints.put(EncodeHintType.MIN_SIZE, 100);//设置图片的最小值
	       hints.put(EncodeHintType.MARGIN, 1);//设置二维码边的空度，非负数
	       
	       BitMatrix bitMatrix = new MultiFormatWriter().encode(content,//要编码的内容
	               //编码类型，目前zxing支持：Aztec 2D,CODABAR 1D format,Code 39 1D,Code 93 1D ,Code 128 1D,
	               //Data Matrix 2D , EAN-8 1D,EAN-13 1D,ITF (Interleaved Two of Five) 1D,
	               //MaxiCode 2D barcode,PDF417,QR Code 2D,RSS 14,RSS EXPANDED,UPC-A 1D,UPC-E 1D,UPC/EAN extension,UPC_EAN_EXTENSION
	               BarcodeFormat.QR_CODE,
	               width, //条形码的宽度
	               height, //条形码的高度
	               hints);//生成条形码时的一些配置,此项可选
	           
	       // 生成二维码图片文件
	       File outputFile = new File(outFileUri);
	       
	       //指定输出路径    
	       System.out.println("输出文件路径为"+outputFile.getPath());
	       drawImage(bitMatrix);
	}
	
	public static void drawImage(BitMatrix bitMatrix) throws IOException {
//		BufferedImage logo = ImageIO.read(new File("src/main/resources/atrip.png"));
//        Graphics2D g2 = (Graphics2D)logo.createGraphics(); 
//        BufferedImage image = toBufferedImage(bitMatrix);
//        g2.drawImage(image,191, 413, 304, 304, null);
//        g2.dispose();//释放此图形的上下文并释放它所使用的所有系统资源  
//        FileOutputStream out=new FileOutputStream("src/main/resources/atrip.jpg");  
//        ImageIO.write(logo,"JPG",out);  
//          
//        out.flush();  
//        out.close(); 
		BufferedImage image = new BufferedImage(686, 1045, BufferedImage.TYPE_INT_BGR);
	    Graphics graphics = image.getGraphics();
	    BufferedImage qrCode = toBufferedImage(bitMatrix);
		
		// 读取原始位图
        Image srcImage = ImageIO.read(new File("src/main/resources/atrip.png"));
        graphics.drawImage(srcImage, 0, 0, 686, 1045, null);
        // 将原始位图缩小后绘制到image对象中
        graphics.drawImage(qrCode, 191, 413, 304, 304, null);
        // 将image对象输出到磁盘文件中
        ImageIO.write(image, "jpeg", new File("src/main/resources/atrip.jpg"));
        
//        graphics.drawImage(
//        		originalBufferedImage.getScaledInstance(rectangle.width,
//        		rectangle.height, Image.SCALE_SMOOTH),
//        		rectangle.x, rectangle.y, null);
        
	}
	
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y,  (matrix.get(x, y) ? BLACK : WHITE));
//              image.setRGB(x, y,  (matrix.get(x, y) ? Color.YELLOW.getRGB() : Color.CYAN.getRGB()));
            }
        }
        return image;
    }

}
