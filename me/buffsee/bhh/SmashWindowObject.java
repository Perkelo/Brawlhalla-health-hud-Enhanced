package me.buffsee.bhh;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import sas.swing.plaf.MultiLineShadowUI;

public class SmashWindowObject {

	private final int playernumber;
	private static final int updateInterval = 16;
	private final JFrame p1;
	private final JLabel label;
	private final int baseFontSize = 70;
	private int fontSizeIncrement;
	private final float baseScaleFactor = 1f;
	private float scaleFactorIncrement;
	private final float baseTransparency = 0.8f;
	private float transparencyIncrement;
	private final float baseXPos;
	private float xIncrement;
	private final float baseYPos;
	private float yIncrement;

	public void resizeText(int increment){
		fontSizeIncrement += increment;
		label.setFont(new Font("Verdana", Font.BOLD, baseFontSize + fontSizeIncrement));
	}

	public void resizeImage(float increment){
		scaleFactorIncrement += increment;
	}

	public void changeTransparency(float increment){
		transparencyIncrement += increment;
		if(baseTransparency + transparencyIncrement > 1){
			transparencyIncrement = 1 - baseTransparency;
		}
		if(baseTransparency + transparencyIncrement < 0){
			transparencyIncrement = 0 - baseTransparency;
		}
		p1.setOpacity(baseTransparency + transparencyIncrement);
	}

	public void moveX(float increment){
		if(Main.twosMode) {
			switch (playernumber) {
				case 1:
					xIncrement -= increment;
					break;
				case 2:
					xIncrement -= increment/3.0f;
					break;
				case 3:
					xIncrement += increment/3.0f;
					break;
				case 4:
					xIncrement += increment;
					break;
			}
		}else{
			switch (playernumber) {
				case 1:
					xIncrement -= increment;
					break;
				case 2:
					xIncrement += increment;
					break;
			}
		}
		p1.setLocation((int) (baseXPos + xIncrement), (int) (baseYPos + yIncrement));
	}

	public void moveY(float increment){
		yIncrement += increment;
		p1.setLocation((int) (baseXPos + xIncrement), (int) (baseYPos + yIncrement));
	}

	public SmashWindowObject(String framename, Rectangle ponerect, int x, int y) {
		baseXPos = x;
		baseYPos = y;
		playernumber = Integer.parseInt(framename.replace("p", ""));

		Main.smashWindows[playernumber - 1] = this;

		p1 = new JFrame(framename);
		
		label = new JLabel("...");
		label.setFont(new Font("Verdana", Font.BOLD, baseFontSize));
		label.setUI(MultiLineShadowUI.labelUI);
		label.setForeground(new Color(255, 255, 255));

		p1.add(label);
		p1.setUndecorated(true);
		p1.setOpacity(baseTransparency);
		p1.setBackground(new Color(255, 255, 255, 0));
		
		p1.setAlwaysOnTop(true);
		p1.setLocation(x, y);
		
		p1.pack();
		p1.setSize(p1.getWidth()*10, (int) (p1.getHeight()*3));
		
		p1.setVisible(true);
		Robot robot = null;
		try {
			robot = new Robot();
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		HealthCalculator healthcalc = new HealthCalculator();
		ImageRounder rounder = new ImageRounder();
		healthcalc.initRobot();

		Main.applySettings(this);

		while(true) {
			int[] rgb = {255,255,255};
			switch (playernumber) {
				case 1:
					rgb = healthcalc.getTopLeftColor();
					break;
				case 2:
					rgb = healthcalc.getTopRightColor();
					break;
				case 3:
					rgb = healthcalc.getBottomLeftColor();
					break;
				case 4:
					rgb = healthcalc.getBottomRightColor();
					break;
			}
			label.setForeground(new Color(rgb[0], rgb[1], rgb[2]));
			label.setText((int) healthcalc.getHealthFromColor(rgb) + "");

			try{
				label.setIcon(new ImageIcon(rounder.getRoundedImage(getScaledImage(robot.createScreenCapture(ponerect), baseScaleFactor + scaleFactorIncrement))));
				Thread.sleep(updateInterval);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private BufferedImage getScaledImage(Image srcImg, float scaleFactor) {
		int w = (int) (srcImg.getWidth(null) * scaleFactor);
		int h = (int) (srcImg.getHeight(null) * scaleFactor);
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2 = resizedImg.createGraphics();
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();
	    return resizedImg;
	}

	public Settings getSettings(){
		return new Settings(
				this.scaleFactorIncrement,
				this.fontSizeIncrement,
				this.transparencyIncrement,
				-this.xIncrement,               //This one is negated because in the case of window 0 the increment is subtracted
				this.yIncrement
		);
	}
}
