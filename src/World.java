/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.tiled.TiledMap;
import java.io.IOException;
import java.util.*;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
    /** Map, containing terrain tiles. */
    private TiledMap map;
    /** The player's ship. */
    protected Player player;
    /** List of Enemy objects created*/
    private ArrayList<Enemy> Enemy_object_list;
    /** List of Item objects created*/
    private ArrayList<Item> Item_object_list;
    /** List of Missile objects created*/
    protected ArrayList<Missile> Missile_object_list;
    /** The boss*/
	private Boss boss;
	/** The camera. */
    private Camera camera;
    /** The panel for player*/
    private Panel player_panel;
    /** The panel for boss*/
    private Panel boss_panel;
    /** Pause parameter determines if the game is paused*/
    private boolean pause;
    /** Updated horizontal direction where player would go*/
    private double dir_x;
    /** Updated vertical direction where player would go*/
    private double dir_y;
    /** Fire that determines if player wants to fire missiles*/
    private boolean fire;
    /** State one music*/
    private Sound background_music;
    /** State two music*/
    private Sound boss_background_music;
    /** Victory music 1*/
    private Sound victory;
    /** Victory background music*/
    private Sound victory_background;
    /** Has played the victory musics*/
    private boolean has_played;
    
  
    /** Access the boss object*/
    public Boss getBoss() {
		return boss;
	}

    /** Mutate the boss object*/
	public void setBoss(Boss boss) {
		this.boss = boss;
	}
    
    /** Access the updated horizontal direction where player would go*/
    public double getDirX(){
    	return this.dir_x;
    }
    
    /** Access the updated vertical direction where player would go*/
    public double getDirY(){
    	return this.dir_y;
    }
    
    /** Access if player wants to fire missiles*/
    public boolean getFire(){
    	return fire;
    }

    /** Get the width of the game world in pixels. */
    public int getWidth()
    {
        return map.getWidth() * map.getTileWidth();
    }

    /** Get the height of the game world in pixels. */
    public int getHeight()
    {
        return map.getHeight() * map.getTileHeight();
    }

    /** Create a new World object. */
    public World()
    throws SlickException, IOException
    {
    	has_played = false;
        map = new TiledMap(Game.ASSETS_PATH + "/map.tmx", Game.ASSETS_PATH);
        Player.setTimeToBeCreated(1);
        player = new Player(1296, 13716);
        // Create a camera, centred and with the player at the bottom
        camera = new Camera(this, player);
        Enemy_object_list = Enemy.getEnemyCoordinateObjectList(this);
        boss = (Boss) Enemy_object_list.get(Enemy_object_list.size()-1);
        player_panel = new Panel(player);
        boss_panel = new Panel(boss);
        pause = false;
        Item_object_list = Item.getItemCoordinateObjectList();
        Missile_object_list = new ArrayList<Missile>();
        background_music = new Sound("music/background/star_wars_(soundtracks).ogg");
        boss_background_music = new Sound("music/boss/perfect_boss_background_music.ogg");
        victory = new Sound("music/victory/victory.ogg");
        victory_background = new Sound("music/victory/star_war_victory.ogg");
        background_music.play();
    }

    /** True if the camera has reached the top of the map. */
    public boolean reachedTop()
    {
        return camera.getTop() <= 0;
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param pause The indication that user wants to pause the game.
     * @param fire The boolean type variable that determine if player wants to fire missiles.
     * @throws IOException 
     */
    public void update(double dir_x, double dir_y, int delta, boolean pause, boolean fire)
    throws SlickException, IOException
    {
    	this.dir_x = dir_x;
    	this.dir_y = dir_y;
    	this.fire = fire;
    	
        // Move the camera automatically unless the user pauses the game
        if(pause == true){
        	camera.update(0);
        }else{
        	camera.update(delta);
        }

        //check collision between Missile objects and Player object and Enemy objects
        Missile missile_object;
        Enemy enemy_object;
        for(Iterator<Missile> missile_iterator = Missile_object_list.iterator(); missile_iterator.hasNext();){
	        missile_object = (Missile)missile_iterator.next();
	        for(Iterator<Enemy> enemy_iterator = Enemy_object_list.iterator(); enemy_iterator.hasNext();){
	        	enemy_object = (Enemy)enemy_iterator.next();
        		if(missile_object.missileCollisionWithUnit(enemy_object)){
            		missile_object.setShield(missile_object.getShield() - enemy_object.getDamage());
            		enemy_object.setShield(enemy_object.getShield() - missile_object.getDamage());
            	}
        	}
	        if(missile_object.missileCollisionWithUnit(player)){
	        	missile_object.setShield(missile_object.getShield() - player.getDamage());
	        	player.setShield(player.getShield() - missile_object.getDamage());
	        }
        }
        
        //check collision between Enemy objects and themselves as well as between Enemy objects and the Player object.
        Enemy enemy_object1;
        Enemy enemy_object2;
        for(Iterator<Enemy> enemy_iterator1 = Enemy_object_list.iterator(); enemy_iterator1.hasNext();){
        	enemy_object1 = (Enemy)enemy_iterator1.next();
        	for(Iterator<Enemy> enemy_iterator2 = Enemy_object_list.iterator(); enemy_iterator2.hasNext();){
	        	enemy_object2 = (Enemy)enemy_iterator2.next();
	        	if(!enemy_object1.isTheSame(enemy_object2)){
	        		if(enemy_object1.isCollisedWith(enemy_object2)){
	        			enemy_object1.setShield(enemy_object1.getShield() - enemy_object2.getDamage());
	            		enemy_object2.setShield(enemy_object2.getShield() - enemy_object1.getDamage());
	        		}
	        	}
        	}
        	if(enemy_object1.isCollisedWith(player)){
	        	enemy_object1.setShield(enemy_object1.getShield() - player.getDamage());
	        	player.setShield(player.getShield() - enemy_object1.getDamage());
	        }
        }
        
        //update the Item objects, if they are picked up by player, they'll be removed from item_object_list
        Item item_object;
        for(Iterator<Item> item_iterator = Item_object_list.iterator(); item_iterator.hasNext();){
        	item_object = (Item)item_iterator.next();
        	if(item_object.isCollidedWithPlayer(player)){
        		item_object.playerPickUpItem(player);
        		item_iterator.remove();
        	}
        }
        
        //update the Enemy objects, if object is destroyed, it will be removed.
        Enemy enemy_object3;
        for(Iterator<Enemy> enemy_iterator3 = Enemy_object_list.iterator(); enemy_iterator3.hasNext(); ){
        	enemy_object3 = (Enemy)enemy_iterator3.next();
        	enemy_object3.update(this, camera, delta);
        	if(enemy_object3.isDestroyed()){
        		if(enemy_object3.isOnScreen(camera)){
        			enemy_object3.sound.play(1,(float)0.5);
        		}
        		enemy_iterator3.remove();
        	}
        }
        
      //update the Missile objects, if object is destroyed, it will be removed.
        Missile missile_object2;
        for(Iterator<Missile> missile_iterator2 = Missile_object_list.iterator(); missile_iterator2.hasNext();){
        	missile_object2 = (Missile)missile_iterator2.next();
        	missile_object2.update(this, camera, delta);
        	if(missile_object2.isDestroyed()){
        		missile_iterator2.remove();
        	}
        }
        
        // Move the player automatically, and manually (by dir_x, dir_y)
        player.update(this, camera, delta);
        if(player.getY() > 1800 && player.getY() < 1820){
        	background_music.stop();
        	boss_background_music.play();
        }
        
        if(boss.getShield() <= 0 && !has_played){
        	boss_background_music.stop();
        	victory.play();
        	victory_background.loop();
        	has_played = true;
        }
        
        //if player dies, return to a nearest checkpoint
        if(player.getShield() <= 0){
        	player.sound.play(1,(float)0.5);
        	Player.setPrevFullShield(player.getFullShield());
        	Player.setPrevFirepower(player.getFirepower());
        	
        	player.calculateCheckpoint();
        	Player.setTimeToBeCreated(Player.getTimeToBeCreated()+1);
        	player = new Player(player.getX(), player.getY());
        	camera = new Camera(this, player);
        	Enemy_object_list = Enemy.getEnemyCoordinateObjectList(this);
            Missile_object_list.clear();
        }
        
        // Centre the camera (in x-axis) over the player and bound to map
        camera.follow(player);
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     * @param textrenderer A TextRenderer object.
     * @throws IOException 
     */
    public void render(Graphics g)
    throws SlickException, IOException
    {
        // Calculate the camera location (in tiles) and offset (in pixels)
        int cam_tile_x = (int) camera.getLeft() / map.getTileWidth();
        int cam_offset_x = (int) camera.getLeft() % map.getTileWidth();
        int cam_tile_y = (int) camera.getTop() / map.getTileHeight();
        int cam_offset_y = (int) camera.getTop() % map.getTileHeight();
        // Render w+1 x h+1 tiles (where w and h are 12x9; add one tile extra
        // to account for the negative offset).
        map.render(-cam_offset_x, -cam_offset_y, cam_tile_x, cam_tile_y,
            getScreenTileWidth()+1, getScreenTileHeight()+1);
        
        //render all Item objects.
        Item item_object;
        for(Iterator<Item> item_iterator = Item_object_list.iterator(); item_iterator.hasNext(); item_object.render(camera)){
        	item_object = (Item)item_iterator.next();
        }
        
        //render all Enemy objects.
        Enemy enemy_object;
        for(Iterator<Enemy> enemy_iterator = Enemy_object_list.iterator(); enemy_iterator.hasNext(); enemy_object.render(g, camera)){
        	enemy_object = (Enemy)enemy_iterator.next();
        }
        
        //render all Missile objects.
        Missile missile_object;
        for(Iterator<Missile> missile_iterator = Missile_object_list.iterator(); missile_iterator.hasNext(); missile_object.render(camera)){
        	missile_object = (Missile)missile_iterator.next();
        }
        //render the player panel
        if(player.isOnScreen(camera)){
        	player_panel.render(g, player.getShield(), player.getFullShield(), player.getFirepower());
        }
        
        //render the boss panel
        if(boss.isOnScreen(camera) && boss.getShield() > 0){
        	boss_panel.render(g, boss.getShield(), boss.getFullShield(), boss.getFirepower());
        }
        
        // Render the player
        player.render(g, camera);	
        
    }

    /** Determines whether a particular map location blocks movement due to
     * terrain.
     * @param x Map x coordinate (in pixels).
     * @param y Map y coordinate (in pixels).
     * @return true if the location blocks movement due to terrain.
     */
    public boolean terrainBlocks(double x, double y)
    {
        int tile_x = (int) x / map.getTileWidth();
        int tile_y = (int) y / map.getTileHeight();
        // Check if the location is off the map. If so, assume it doesn't
        // block (potentially allowing ships to fly off the map).
        if (tile_x < 0 || tile_x >= map.getWidth()
            || tile_y < 0 || tile_y >= map.getHeight())
            return false;
        // Get the tile ID and check whether it blocks movement.
        int tileid = map.getTileId(tile_x, tile_y, 0);
        String block = map.getTileProperty(tileid, "block", "0");
        return Integer.parseInt(block) != 0; 
    }

    /** Get the width of the screen in tiles, rounding up.
     * For a width of 800 pixels and a tilewidth of 72, this is 12.
     */
    private int getScreenTileWidth()
    {
        return (Game.screenwidth / map.getTileWidth()) + 1;
    }

    /** Get the height of the screen in tiles, rounding up.
     * For a height of 600 pixels and a tileheight of 72, this is 9.
     */
    private int getScreenTileHeight()
    {
        return (Game.screenheight / map.getTileHeight()) + 1;
    }
    
    /** Access the pause value which determines if the game is paused.
     *  Return the current value of pause.
     */
    public boolean getPause(){
    	return this.pause;
    }
    
    /** Mutator to reset pause value if user has updated the game.
     * @param pause The new updated value for pause.
     */
    public void setPause(boolean pause){
    	this.pause = pause;
    }
}
