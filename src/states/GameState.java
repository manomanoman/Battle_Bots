package states;

import java.awt.Graphics;

import entity.World;
import mainStuff.Handler;

public class GameState extends State{

	private World world;
	
	private Handler handler;
	
	public GameState(Handler handler){
		this.handler = handler;
		world = new World( handler);
	}
	
	@Override
	public void update() {
		world.update();
	}

	@Override
	public void render(Graphics g) {
		
		world.render(g);
		
	}

}
