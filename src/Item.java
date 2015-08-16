/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.newdawn.slick.SlickException;

/** Item for player.*/
public abstract class Item extends TwoDImage{
	
	/** The original items text file*/
	private static final String ITEM_FILE = "data/items.txt";
	/** The items directory*/
	public static final String ITEM_PATH = Game.ASSETS_PATH + "/items/";
	/** Music directory*/
	public static final String MUSIC_FILE = "music/item/item.ogg";
	
	/** Create an item object*/
	public Item(double init_x, double init_y){
		super(init_x, init_y);
	}
	
	/** Read in the items text file and return a BufferedReader object
	 * @throws IOException*/
	private static BufferedReader getItemsReader()
		throws IOException
	{
			return new BufferedReader(new FileReader(ITEM_FILE));
	}
	
	/** Access the items' coordinates in a dictionary
	 * @throws IOException*/
	public static Map<String,ArrayList<String[]>> getItemsCoordinateDict()
		throws IOException
	{
		//the second parameter is the starting_line where data start to be processed
		return coordinateDict(getItemsReader(), "Shield		824, 11052");
	}
	
	 /** Store all Item objects into a enemy list and return the list*/
	public static ArrayList<Item> getItemCoordinateObjectList() 
	    throws IOException, SlickException
	{
	   	ArrayList<Item> item_object_list = new ArrayList<Item>();
	    for(Repair repair_object : Repair.getRepairCoordinateObjectList()){
	    	item_object_list.add((Item)repair_object);
	    }
	    for(Shield shield_object : Shield.getShieldCoordinateObjectList()){
	    	item_object_list.add((Item)shield_object);
	    }
	    for(Firepower firepower_object : Firepower.getFirepowerCoordinateObjectList()){
	    	item_object_list.add((Item)firepower_object);
	    }
	    return item_object_list;
	}
	
	/** Access the items' general data in a dictionary
	 * @throws IOException*/
	public static Map<String,Map<String,String>> getItemsGeneralDataDict()
		throws IOException
	{
		//the second parameter is the starting_line and the third is the end_line
		return generalDataDict(getItemsReader(), "Item type       Image file          Effect","Specific item data");
	}
	
	/** The player picked up items and items have effects on the player*/
	public abstract void playerPickUpItem(Player player);
	
	/** Player can pick up a Item object by moving within 50 pixels of its position
	 * @param player The player controlled*/
	public boolean isCollidedWithPlayer(Player player){
		double dist_x = this.getX() - player.getX();
		double dist_y = this.getY() - player.getY();
		double total_dist = Math.sqrt(dist_x*dist_x + dist_y*dist_y);
		if(total_dist <= 50){
			return true;
		}else{
			return false;
		}
	}
}
