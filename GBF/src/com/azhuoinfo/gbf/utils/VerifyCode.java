package com.azhuoinfo.gbf.utils;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
public class VerifyCode {

	
	private static final char[] CHARS = {
		'2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 
		'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
		'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
	};
	
	private static VerifyCode bmpCode;
	
	public static VerifyCode getInstance() {
		if(bmpCode == null)
			bmpCode = new VerifyCode();
		return bmpCode;
	}
	
	//default settings
	private static final int DEFAULT_CODE_LENGTH = 4;
	private static final int DEFAULT_FONT_SIZE = 30;
	private static final int DEFAULT_LINE_NUMBER = 2;
	
	private static final int BASE_PADDING = 15;
	private static final int RANGE_PADDING = 10;
	
	private static final int DEFAULT_WIDTH = DEFAULT_FONT_SIZE*DEFAULT_CODE_LENGTH+BASE_PADDING*2;
	private static final int DEFAULT_HEIGHT = DEFAULT_FONT_SIZE+BASE_PADDING*2;
	
	//settings decided by the layout xml
	
	//canvas width and height
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT; 
	
	//number of chars, lines; font size
	private int codeLength = DEFAULT_CODE_LENGTH;
	private int line_number = DEFAULT_LINE_NUMBER;
	private int font_size = DEFAULT_FONT_SIZE;
	
	//variables
	private Random random = new Random();
	
	/**
	 * 生成验证码图片
	 * @param code
	 * @return
	 */
	public Bitmap createBitmap(String code) {
		Bitmap bp = Bitmap.createBitmap(width, height, Config.ARGB_8888); 
		Canvas c = new Canvas(bp);
		
		c.drawColor(Color.WHITE);
		Paint paint = new Paint();
		paint.setTextSize(font_size);
		
		for (int i = 0; i < code.length(); i++) {
			randomTextStyle(paint);
			c.drawText(code.charAt(i) + "", 
					BASE_PADDING+DEFAULT_FONT_SIZE*i+random.nextInt(RANGE_PADDING), 
					BASE_PADDING+DEFAULT_FONT_SIZE, paint);
		}

		for (int i = 0; i < line_number; i++) {
			drawLine(c, paint);
		}
		
		c.save(Canvas.ALL_SAVE_FLAG );//保存  
		c.restore();
		return bp;
	}
	
	/**
	 * 生成验证码
	 * @return
	 */
	public String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < codeLength; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}
	
	private void drawLine(Canvas canvas, Paint paint) {
		int color = randomColor();
		int startX = random.nextInt(width);
		int startY = random.nextInt(height);
		int stopX = random.nextInt(width);
		int stopY = random.nextInt(height);
		paint.setStrokeWidth(1);
		paint.setColor(color);
		canvas.drawLine(startX, startY, stopX, stopY, paint);
	}
	
	private int randomColor() {
		return randomColor(1);
	}

	private int randomColor(int rate) {
		int red = random.nextInt(256) / rate;
		int green = random.nextInt(256) / rate;
		int blue = random.nextInt(256) / rate;
		return Color.rgb(red, green, blue);
	}
	
	private void randomTextStyle(Paint paint) {
		int color = randomColor();
		paint.setColor(color);
		paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
		float skewX = random.nextInt(11) / 10;
		skewX = random.nextBoolean() ? skewX : -skewX;
		paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
	}
}

