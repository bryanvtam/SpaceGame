package come.game.src.main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Bullet {
	
	private double x;
	private double y;
	
	BufferedImage image;
	
	public Bullet(double x, double y, Game game) {
		this.x = x;
		this.y = y;
		
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		image = ss.grabImage(2,1,64,64);
	}
	
	public void tick() {
		y -= 7;
	}
	
	public void render(Graphics g) {
		g.drawImage(image, (int) x, (int) y, null);
	}
	
	public double getY() {
		return y;
	}
}
