package entity;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import file.ImageLoader;
import mainStuff.Handler;

public class Player extends Character{
	
	// Default Player width and height is 64x64
	
	private float mouseX,mouseY;
	private double theta;
	private int quadrant;

	public Player(Handler handler,BufferedImage b, int x, int y, int width, int height) {
		super(handler,b, x, y, width, height);
		mouseX = 0;
		mouseY = 0;
	}

	@Override
	public void update() {
		move();
		int centerPNG = 64/2;
		mouseX = handler.getMouseManager().getMouseX();
		mouseY = handler.getMouseManager().getMouseY();
		if (y-mouseY + centerPNG > 0 && x-mouseX+centerPNG > 0){
			theta = Math.atan((y-mouseY + centerPNG )/(x-mouseX+centerPNG )) + Math.PI;
			quadrant = 1;
		} else if (y-mouseY + centerPNG > 0 && x-mouseX+centerPNG < 0) {
			theta = Math.atan((y-mouseY + centerPNG )/(x-mouseX+centerPNG));
			quadrant = 2;
		} else if (y-mouseY + centerPNG < 0 && x-mouseX+centerPNG < 0) {
			theta = Math.atan((y-mouseY + centerPNG )/(x-mouseX+centerPNG));
			quadrant = 3;
		} else if (y-mouseY + centerPNG < 0 && x-mouseX+centerPNG > 0) {
			theta = Math.atan((y-mouseY + centerPNG )/(x-mouseX+centerPNG)) + Math.PI;
			quadrant = 4;
		}
	}

	@Override
	public void render(Graphics g) {
		
		// This rectangle represents the bounding box, aka what we will use to check collisions
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		
		//User Info
		g.drawString("Player X " + x, 10, 10);
		g.drawString("Player Y " + y, 10, 25);
		g.drawString("MouseX " + mouseX, 10, 40);
		g.drawString("MouseY " + mouseY, 10, 55);
	
		
		AffineTransform playerRotation = AffineTransform.getTranslateInstance(x, y);
		if (mouseX == 0 || mouseY == 0){
			mouseX ++;
			mouseY++;
		}
		
		
		float mouseRelativeY = y-mouseY;
		float mouseRelativeX = x-mouseX;
		g.drawString("Theta " + Math.toDegrees(theta), 10, 65);		
		g.drawString("Mouse Relative Y" + mouseRelativeY, 10, 75);	
		g.drawString("Mouse Relative X" + mouseRelativeX, 10, 85);	
		g.drawString("Quandrant " + quadrant, 10, 95);				
		playerRotation.rotate(theta,32,32);
		
		Graphics2D gg = (Graphics2D) g;
		gg.drawImage(b,playerRotation,null);
		
		
		g.setColor(Color.red);
		
		//g.drawImage(b, x,y,null);
		
		
	}
	
	public void shoot(){
		int barrelLength = 10;
		double barrelTip_X = barrelLength*Math.cos(theta) + x-mouseX + 32;
		double barrelTip_Y = barrelLength*Math.sin(theta) + y-mouseY + 32;
		Projectile p = new Projectile(handler, ImageLoader.loadImage("res\\entities\\object\\projectile\\projectile_1.png"), (int)barrelTip_X,(int) barrelTip_Y, 16, 16);
		World.allThings.add(p);
	}
	
	private void move(){
		if (handler.getKeyManager().left){
			x--;
		}
		if (handler.getKeyManager().right){
			x++;
		}
		if (handler.getKeyManager().up){
			y--;
		}
		if (handler.getKeyManager().down){
			y++;
		}
	}

}
