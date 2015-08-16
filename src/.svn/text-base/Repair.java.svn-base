/* SWEN20003 Object Oriented Software Development
 * Repair Class
 * Author: Kimple KE
 */

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/** Repair, one type of Item, will restore player's ship's full shield.*/
public class Repair extends Item
{	
	/** The Repair image*/
	private static final String REPAIR_IMAGE = ITEM_PATH + "repair.png";
	
	/** Create a Repair object
	 * @throws IOException*/
	public Repair(double init_x, double init_y)
		throws SlickException
	{
		super(init_x, init_y);
		this.img = new Image(REPAIR_IMAGE);
		this.sound = new Sound(MUSIC_FILE);
	}
	
	/** Access the coordinates of Repair in an array
	 * @throws IOException*/
	public static ArrayList<String[]> getRepairCoordinateList()
		throws IOException
	{
		return getItemsCoordinateDict().get("Repair");
	}
	
	/** Generate an array of Repair objects, then return the array*/
	public static ArrayList<Repair> getRepairCoordinateObjectList()
		throws IOException, SlickException
	{
		ArrayList<Repair> repair_object_list = new ArrayList<Repair>();
		for(String[] a_set_of_coordinate : getRepairCoordinateList()){
			double init_x = Double.parseDouble(a_set_of_coordinate[1]);
			double init_y = Double.parseDouble(a_set_of_coordinate[2]);
			repair_object_list.add(new Repair(init_x,init_y)); 
		}
		return repair_object_list;
	}
	
	/** Access the general data of Repair in dictionary
	 * @throws IOException*/
	private static Map<String,String> getRepairGeneralDataDict()
		throws IOException
	{
		return getItemsGeneralDataDict().get("Repair");
	}
	
	/** Access the effect of a Repair object
	 * @throws IOException*/
	public static String getRepairEffect()
		throws IOException
	{
		return getRepairGeneralDataDict().get("Effect");
	}
	
	/** The player will restore his full shield if it picks up a Repair object*/
	public void playerPickUpItem(Player player){
		player.setShield(player.getFullShield());
		this.sound.play((float)1, (float)0.4);
	}
}
