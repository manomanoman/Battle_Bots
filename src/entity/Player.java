package entity;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import mainStuff.Handler;

public class Player extends Character{
	
	

	public Player(Handler handler,BufferedImage b, int x, int y, int width, int height) {
		super(handler,b, x, y, width, height);
	}

	@Override
	public void update() {
		move();
	}

	@Override
	public void render(Graphics g) {
		
		// This rectangle represents the bounding box, aka what we will use to check collisions
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		
		AffineTransform playerRotation = AffineTransform.getTranslateInstance(x, y);
		playerRotation.rotate(Math.toRadians(0),32,32);
		
		Graphics2D gg = (Graphics2D) g;
		gg.drawImage(b,playerRotation,null);
		
		
		g.setColor(Color.red);
		
		//g.drawImage(b, x,y,null);
		
		
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
