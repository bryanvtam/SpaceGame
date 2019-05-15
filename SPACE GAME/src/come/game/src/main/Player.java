package come.game.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {

	private double x; //x-cord of player
	private double y; //y-cord of player
	
	private double velX = 0;
	private double velY = 0;
	
	private BufferedImage player;
	
	public Player(double x, double y, Game game) {
		
		this.x = x;
		this.y = y;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		player = ss.grabImage(1,1,64,64);
		
	}
	
	public void tick() { //update method
		x += velX;
		y += velY;
		
		if(x <= 0) {
			x=0;
		}
		
		if( x >= 575) {
			x=575;
		}
		
		if (y <= 0) {
			y=0;
		}
			
		if(y >= 402) {
			y = 402;
		}
	}
	
	public void render(Graphics g) { //draw out image
		g.drawImage(player,(int) x,(int) y, null);
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setVelX(double velX) {
		this.velX = velX;
	}
	public void setVelY(double velY) {
		this.velY = velY;
	}
}
