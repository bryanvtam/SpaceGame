package come.game.src.main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Controller {

		private LinkedList<Bullet> b = new LinkedList<Bullet>();
		
		Bullet TempBullet;
		
		Game game;
		
		public Controller(Game game) {
			this.game = game;
		}
		
		public void tick() {
			for(int i = 0; i < b.size(); i++) {
				TempBullet = b.get(i);
				
				if(TempBullet.getY() < 0 )
					removeBullet(TempBullet);
				
				TempBullet.tick();
			}
		}
		
		public void render (Graphics g) {
			for(int i = 0; i < b.size(); i++) {
				TempBullet = b.get(i);
				
				TempBullet.render(g);
			}
		}
		
		public void addBullet(Bullet block) {
			b.add(block);
		}
		
		public void removeBullet(Bullet block) {
			b.remove(block);
		}
}
