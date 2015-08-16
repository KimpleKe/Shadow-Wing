/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/** Enemy of the Vaaj Empire, including Drone, Fighter, Asteroid, Boss.*/
public abstract class Enemy extends Unit{
	/** every Enemy object should have a list where its coordinates are stored*/
	public ArrayList<String[]> list;
	
    /** Create a enemy object
     * @throws SlickException */
    public Enemy(double init_x, double init_y) 
    	throws SlickException
    {
    	super(init_x, init_y);
    	this.sound = new Sound(EXPLOSION_FILE);
    }
    
    /** Return the type of this unit, enemy has type -1*/
    public int getType(){
    	return -1;
    }
    
    /** Update the status of Enemy object.
     * @param world The world that Enemy object is on.
     * @param camera The visible part of the map.
     * @param delta The time passed since last frame.*/
    public abstract void update(World world, Camera camera, int delta);
    
    /** The number of pixels the enemy may move per millisecond. */
    protected double getSpeed()
    {
        return 0.2;
    }
    
    /** Check if two Enemy objects are identical.*/
    public boolean isTheSame(Enemy object){
    	return this.getX()==object.getX() && this.getY()== object.getY();
    }
    
    /** Store all Enemy objects into a enemy list and return the list
     * @param world The Enemy objects are on.*/
    public static ArrayList<Enemy> getEnemyCoordinateObjectList(World world) 
    	throws IOException, SlickException
    {
    	ArrayList<Enemy> enemy_object_list = new ArrayList<Enemy>();
    	for(Fighter fighter_object : Fighter.getFighterCoordinateObjectList()){
    		enemy_object_list.add((Enemy)fighter_object);
    	}
    	for(Asteroid asteroid_object : Asteroid.getAsteroidCoordinateObjectList()){
    		enemy_object_list.add((Enemy)asteroid_object);
    	}
    	for(Drone drone_object : Drone.getDroneCoordinateObjectList()){
    		enemy_object_list.add((Enemy)drone_object);
    	}
    	for(Boss boss_object : Boss.getBossCoordinateObjectList(world)){
    		enemy_object_list.add((Enemy)boss_object);
    	}
    	return enemy_object_list;
    }
    
    /** */
    public void render(Graphics g, Camera camera){
    	if(this.getShield() <= 0){
    		g.drawAnimation(this.explosion_animation, getScreenX(camera) - 36, getScreenY(camera) - 36);
    	}else if(this.isOnScreen(camera)){
    		this.img.drawCentered(this.getScreenX(camera),this.getScreenY(camera));
    	}
    }
}