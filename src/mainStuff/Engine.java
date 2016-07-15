package mainStuff;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.lang.Thread.State;

import file.ImageLoader;
import manager.KeyManager;
import manager.MouseManager;
import states.GameState;
import states.StateManager;

public class Engine implements Runnable {

	//Primitive DataTypes Declaration
	
	public String title;
	private int width;
	private int height;
	private boolean running = false;
	
	//Object Declaration
	private Thread thread;
	private Display display;
	private BufferStrategy bs;
	private Graphics g;
	private Handler handler;

	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	private GameState gameState;

	public Engine(String title, int frameX, int frameY) {
		this.width = frameX;
		this.height = frameY;
		this.title = title;
		
		handler = new Handler(this);
		display = new Display(title,width,height);
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
		
		gameState = new GameState(handler);
		
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		
		display.getCanvas().addKeyListener(keyManager);
		display.getCanvas().addMouseListener(mouseManager);
		
		initialize();
	}

	private void initialize() {
		StateManager.setCurrentState(gameState);
	}

	private void update() {
		keyManager.tick();
		if (StateManager.getCurrentState() != null){
			StateManager.getCurrentState().update();
		}
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// CLEARS THE WHOLE SCREEN

		g.clearRect(0, 0, width, height);

		// DRAW HERE
		if (StateManager.getCurrentState()!= null){
			StateManager.getCurrentState().render(g);
		}

		// END DRAWING HERE

		bs.show();
		g.dispose();
	}

	@Override
	public void run() {

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				update();
				render();
				ticks++;
				delta--;
			}

			if (timer >= 1000000000) {
				System.out.println("Ticks and Frames : " + ticks);
				ticks = 0;
				timer = 0;
			}

		}

		stop();
	}

	public synchronized void start() {
		if (running == true) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (running != false) {
			return;
		}
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public BufferStrategy getBs() {
		return bs;
	}

	public void setBs(BufferStrategy bs) {
		this.bs = bs;
	}

	public Graphics getG() {
		return g;
	}

	public void setG(Graphics g) {
		this.g = g;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}

	public void setKeyManager(KeyManager keyManager) {
		this.keyManager = keyManager;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public void setMouseManager(MouseManager mouseManager) {
		this.mouseManager = mouseManager;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

}
