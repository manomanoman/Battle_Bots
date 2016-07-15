package entity;

import java.awt.Graphics;
import java.util.LinkedList;

import file.ImageLoader;
import mainStuff.Handler;


// This is the world which is created when the gamestate is created. This handles the updating and rendering for all entities

public class World {
	
	private Player player;
	private Handler handler;
	
	//This is the Entity LinkedList which stores all the current entities
	public static LinkedList<Entity> allThings = new LinkedList<Entity>();
	
	public World(Handler handler){
		this.handler = handler;
		this.player = new Player(handler,ImageLoader.loadImage("res\\base\\Base_Tier_1.png"), 100, 100, 64, 64);
		
		allThings.add(player);
		
	}
	
	// This loops through the linkedList and updates every entity in it
	public void update(){
		for (Entity e : allThings){
			e.update();
		}
	}
	
	// This loops through the linkedList and renders every entity in it
	public void render(Graphics g){
		for (Entity e : allThings){
			e.render(g);
		}
	}

}