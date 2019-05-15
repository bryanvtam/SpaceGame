package come.game.src.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{ //taking everything in Canvas class(in library) bringing into 'Game' class

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320, HEIGHT = WIDTH / 12 * 9, SCALE = 2;
	public final String TITLE = "2D Space Game";
	
	private boolean running = false;
	private Thread thread; //Initializes thread
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB); //buffers the images before displaying
	private BufferedImage spriteSheet = null;
	
	private Player p;
	private Controller c;
	
	
	public void init() {
		requestFocus();
		BufferedImageLoadear loader = new BufferedImageLoadear();
		try {
			
			spriteSheet = loader.loadImage("/sprite_sheet.png");
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(new KeyInput(this));
		
		p = new Player(200,200,this);
		c = new Controller(this);
		
		
	}
	
	private synchronized void start() { //starts the thread
		if(running) {
			return; //get out of this method if 'running' is TRUE
		}
		running = true;
		thread = new Thread(this); // 'this' refers to this run method
		thread.start();
	}
	
	private synchronized void stop(){ //'synchronized' is dealing with any type of thread
		if(!running)
			return;
	
		running = false;
		try { //try and catch is it it fails to join the threads
			thread.join();//joins the threads together until they die
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public void run() { //heart of the game
		init();
		long lastTime = System.nanoTime(); // long is the same as an int that stores a higher positive # and a lower negative #
		final double amountOfTicks = 60.0; //60fps
		double ns = 1000000000 / amountOfTicks;
		double delta = 0; // catch up if the ticks are running behind
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			//this would be the game loop
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, FPS " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick() { //updates
		p.tick();
		c.tick();
	}
	private void render() { //rendering method of the game
		BufferStrategy bs = this.getBufferStrategy();//buffer strategy handles buffering
		
		if(bs == null) {
			createBufferStrategy(3); //'3' means there are 3 buffers
			return;
			
		}
		Graphics g = bs.getDrawGraphics(); //draws out the buffers
		////////////////////////////////////////////////////////////
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		p.render(g);
		c.render(g);
		
		////////////////////////////////////////////////////////////
		g.dispose();
		bs.show();
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		int key2 = e.getKeyCode();
		if (key == KeyEvent.VK_RIGHT) {
			p.setVelX(4);
			
		} else if (key == KeyEvent.VK_LEFT) {
			p.setVelX(-4);
		} else if (key == KeyEvent.VK_DOWN) {
			p.setVelY(4);
		} else if (key == KeyEvent.VK_UP) {
			p.setVelY(-4);
		}else if(key2 == KeyEvent.VK_SPACE) 
			c.addBullet(new Bullet(p.getX(),(p.getY()-50),this));
			
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_RIGHT) {
			p.setVelX(0);
		} else if (key == KeyEvent.VK_LEFT) {
			p.setVelX(0);
		} else if (key == KeyEvent.VK_DOWN) {
			p.setVelY(0);
		} else if (key == KeyEvent.VK_UP) {
			p.setVelY(0);
		}
	}
		
	public static void main(String args[]) {
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT *SCALE)); //Initializes a dimension to specific width and height
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT *SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT *SCALE));
		
		JFrame frame = new JFrame(game.TITLE);
		frame.add(game); //take the dimensions above
		frame.pack(); //takes all of the components in it and sizes the frame according to the sizes of its components﻿
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //allows the 'X' button to close the program
		frame.setResizable(false); //makes it so it can't be resized
		frame.setLocationRelativeTo(null); //puts the window in the middle of the screen
		frame.setVisible(true);
		
		game.start();
	}
	
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	
}