/*
 * Copyright (c) 2021 dfove.com Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package common.tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
 
import javax.imageio.ImageIO;

import java.util.Base64;
 
 
  
  
/**
 *  
 * @ClassName: ValidateCodeUtil
 * @Description: 验证码生成工具类
 * @author chenhx
 * @date 2017年11月14日 上午11:00:07
 */
public class ValidateCodeUtil {  
    private static Validate validate = null;                  //验证码类，用于最后返回此对象，包含验证码图片base64和真值
    private static Random random = new Random();              //随机类，用于生成随机参数
    private static String randString = "0123456789abcdefghjkmnpqrtyABCDEFGHJLMNQRTY";//随机生成字符串的取值范围  
      
    /*private static int width = 80;     //图片宽度  
    private static int height = 34;    //图片高度  
    private static int StringNum = 4;  //字符的数量  
    */    
    private static int lineSize = 40;  //干扰线数量  
      
 
    //将构造函数私有化 禁止new创建
    private ValidateCodeUtil() {
		super();
	}
 
    /** 
     * 获取随机字符,并返回字符的String格式 
     * @param index (指定位置) 
     * @return 
     */  
    private static String getRandomChar(int index) {  
        //获取指定位置index的字符，并转换成字符串表示形式  
        return String.valueOf(randString.charAt(index));  
    }  
    /**
     * 获取随机指定区间的随机数 
     * @param min (指定最小数) 
     * @param max (指定最大数) 
     * @return 
     */
    private static int getRandomNum(int min,int max) {
    	Random rand = new Random();
    	return rand.nextInt(max - min + 1) + min;
    }  
      
    /** 
     * 获得字体 
     * @return 
     */  
    private static Font getFont() {  
        return new Font("Fixedsys", Font.CENTER_BASELINE, 25);  //名称、样式、磅值  
    }  
      
    /** 
     * 获得颜色 
     * @param fc 
     * @param bc 
     * @return 
     */  
    private static Color getRandColor(int frontColor, int backColor) {  
        if(frontColor > 255) {
            frontColor = 255;
        }
        if(backColor > 255) {
            backColor = 255;
        }
          
        int red = frontColor + random.nextInt(backColor - frontColor - 16);  
        int green = frontColor + random.nextInt(backColor - frontColor -14);  
        int blue = frontColor + random.nextInt(backColor - frontColor -18);  
        return new Color(red, green, blue);  
    }  
      
    /** 
     * 绘制字符串,返回绘制的字符串 
     * @param g 
     * @param randomString 
     * @param i 
     * @return 
     */  
    private static String drawString(Graphics g, String randomString, int i) { 
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setFont(getFont());   //设置字体  
    	g2d.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));//设置颜色  
        String randChar = String.valueOf(getRandomChar(random.nextInt(randString.length())));  
        randomString += randChar;   //组装  
        int rot = getRandomNum(5,5);
        g2d.translate(random.nextInt(3), random.nextInt(3)); 
        g2d.rotate(rot * Math.PI / 180);
        g2d.drawString(randChar, 13*i, 20);   
        g2d.rotate(-rot * Math.PI / 180);
        return randomString;  
    }  
      
    /** 
     * 绘制干扰线 
     * @param g 
     */  
    private static void drawLine(Graphics g, Integer width, Integer height) {  
        //起点(x,y)  偏移量x1、y1  
        int x = random.nextInt(width);  
        int y = random.nextInt(height);  
        int xl = random.nextInt(13);  
        int yl = random.nextInt(15);  
        g.setColor(new Color(random.nextFloat(), random.nextFloat(), random.nextFloat()));
        g.drawLine(x, y, x + xl, y + yl);  
    }  
      
    /**
     * 
     * @MethodName: getRandomCode
     * @Description: 生成Base64图片验证码
     * @param key
     * @return String 返回base64
     */
    public static Validate getRandomCode(Integer stringNum, Integer width, Integer height) {
    	validate = validate==null?new Validate():validate;
    	
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);  
        Graphics g = image.getGraphics();// 获得BufferedImage对象的Graphics对象  
        g.fillRect(0, 0, width, height);//填充矩形  
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));//设置字体  
        g.setColor(getRandColor(110, 133));//设置颜色  
        //绘制干扰线  
        for(int i = 0; i <= lineSize; i++) {  
            drawLine(g, width, height);  
        }  
        //绘制字符  
        String randomString = "";  
        for(int i = 1; i <= stringNum; i++) {
            randomString = drawString(g, randomString, i); 
            validate.setValue(randomString);
        }  
          
        g.dispose();//释放绘图资源  
        ByteArrayOutputStream bs = null;
        try {  
            bs = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bs);//将绘制得图片输出到流  
            String imgsrc = Base64.getEncoder().encodeToString(bs.toByteArray());
            validate.setBase64Str(imgsrc);
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
        	try {
				bs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				bs = null;
			}
        }     
        return validate;
    }  
    
    /**
     * 
     * @MethodName: codeCompare
     * @Description: 比较图片验证码是否正确，不区分o和0，i和1，l和1
     * @param code1,code2
     * @return Boolean
     */
    public static Boolean codeCompare(String code1,String code2) {
    	Boolean flag = false;
    	if (code1 != null && code2 != null) {
    		code1 = code1.toLowerCase().replace("o", "0").replace("i", "1").replace("l", "1");
    		code2 = code2.toLowerCase().replace("o", "0").replace("i", "1").replace("l", "1");
    		if (code1.equals(code2)) {
    			flag = true;
    		}
    	}
        return flag;
    }  
    
    /*public static void main(String[] args) throws Exception {
    	System.out.println(codeCompare("OIL","011"));
    }*/
    
    /**
     * 
     * @ClassName: Validate
     * @Description: 验证码类
     * @author chenhx
     * @date 2017年11月14日 上午11:35:34
     */
    public static class Validate implements Serializable{
    	private static final long serialVersionUID = 1L;
    	private String base64Str;		//Base64 值
    	private String value;			//验证码值
    	
    	public String getBase64Str() {
    		return base64Str;
    	}
    	public void setBase64Str(String base64Str) {
    		this.base64Str = base64Str;
    	}
    	public String getValue() {
    		return value;
    	}
    	public void setValue(String value) {
    		this.value = value;
    	}
    }
}  
 
