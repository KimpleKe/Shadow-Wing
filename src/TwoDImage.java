/* SWEN20003 Object Oriented Software Development
 * Space Game Engine
 * Author: Kimple KE
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

/** The true Superclass of all classes, the TwoDImage class that represents any two d objects in this game.*/
public abstract class TwoDImage{
	/** The image*/
	protected Image img;
	/** The music*/
	protected Sound sound;
	/** The x coordinate in pixels*/
	protected double x;
	/** The y coordinate in pixels*/
	protected double y;
	
	/** Create TwoDImage object*/
    public TwoDImage(double init_x, double init_y){
    	this.x = init_x;
    	this.y = init_y;
    }
    
    /** Access the x coordinate*/
    public double getX() {
		return x;
	}
    
    /** Mutate the x coordinate
     * @param x The updated value of x coordinate
     */
	public void setX(double x) {
		this.x = x;
	}

	/** Access the y coordinate*/
	public double getY() {
		return y;
	}

	/** Mutate the y coordinate
	 * @param y The updated value of y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/** Access the width of a image*/
	public int getImageWidth(){
		return 72;
	}
	
	/** Access the height of a image*/
	public int getImageHeight(){
		return 72;
	}
	
	/** Access the x coordinate of a image object on the screen (in other words, get screen x coordinate)*/
    public int getScreenX(Camera camera)
    {
        return (int)(x - camera.getLeft());
    }

    /** Access the y coordinate of a image object on the screen (in other words, get screen y coordinate)*/
    public int getScreenY(Camera camera)
    {
        return (int)(y - camera.getTop());
    }
    
    /** Access the top of a two d image object*/
    protected double getTop(){
    	return this.getY() - this.getImageHeight() / 2;
    }
    
    /** Access the bottom of a two d image object*/
    protected double getBottom(){
    	return this.getY() + this.getImageHeight() / 2;
    }
    
    /** Access the left of a two d image object*/
    protected double getLeft(){
    	return this.getX() - this.getImageHeight() / 2;
    }
    
    /** Access the right of a two d image object*/
    protected double getRight(){
    	return this.getX() + this.getImageHeight() / 2;
    }
    
    /** Figure out if an image object is on the screen
     * @param camera The visible part of the map.*/
    public boolean isOnScreen(Camera camera){
    	int screen_x = this.getScreenX(camera);
    	int screen_y = this.getScreenY(camera);
    	if(screen_x>0 && screen_x<Game.playwidth() && screen_y>0 && screen_y<Game.playheight()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    /** Draw an Image object on the screen if it is on the screen*/
    public void render(Camera camera){
    	if(this.isOnScreen(camera)){
    		this.img.drawCentered(this.getScreenX(camera),this.getScreenY(camera));
    	}
    }
    
    /** Decide if two TwoDImage have been collided*/
    public boolean isCollisedWith(TwoDImage object){
    	if(this.getTop() <= object.getBottom() && this.getBottom() >= object.getTop() && this.getLeft() <= object.getRight() && this.getRight() >= object.getLeft()){
    		return true;
    	}else{
    		return false;
    	}
    }
	
	/** Missiles collide with other units.
	 * @param object The Missile object.*/
	public boolean missileCollisionWithUnit(Unit object){
		if(object.getLeft() < this.getX() && object.getRight() > this.getX()){
			if(object.getType() == 1){
				return object.getTop() <= this.getY();
			}else if(object.getType() == -1){
				return object.getBottom() >= this.getY();
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
    
    /** Read in the data about "coordinates" from text file and process the data in an organized way
     *  produce a dictionary where Units' and Items' coordinates are stored
     *  @param reader The read in text file that has BufferedReader type
     *  @param starting_line The place in the text file where data are started to be processed*/
    public static Map<String,ArrayList<String[]>> coordinateDict(BufferedReader reader, String starting_line) 
    	throws IOException
    {
    	String line = null;
    	boolean start_to_record = false;
    	Map<String,ArrayList<String[]>> dict = new HashMap<String,ArrayList<String[]>>();
    	//read in data from text file
    	while ((line = reader.readLine()) != null) {
    		//ignore irrelevant data before the starting_line
    		if(line.equals(starting_line)){
    			start_to_record = true;
    		}
    		//start to process relevant information, namely, coordinates data
    		if(start_to_record == true){
    			//record x, y coordinates in a string
    			String coordinates = line.replaceAll("[^0-9]+", " ");
    			//record the unit/item name
    			String unit = line.replaceAll("[^a-zA-z]", "");
    			//store x,y coordinate values in an array
    			String[] c_parts = coordinates.split(" ");
    			//start to store unit/item name as key and x,y coordinates as values in dictionary
    			if(dict.containsKey(unit)){
    				dict.get(unit).add(c_parts);
    				dict.put(unit,dict.get(unit));
    			}else{
    				ArrayList<String[]> list = new ArrayList<String[]>(20);
    				list.add(c_parts);
    				dict.put(unit,list);
    			}
    		}
    	}
    	return dict;
    }
    

    /** Read in the general data from text file and process the data in an organized way
     *  produce a dictionary where Units' and Items' general data are stored
     *  @param reader The read in text file that has BufferedReader type
     *  @param starting_line The place in the text file where data are started to be processed
     *  @param end_line The place in the text file where it should stop continuing to process data*/
    public static Map<String,Map<String,String>> generalDataDict(BufferedReader reader, String starting_line, String end_line)
    	throws IOException
    {
    	String line = null;
    	boolean start_to_record = false;
    	String[] subKeys = null;
    	Map<String,Map<String,String>> dict = new HashMap<String,Map<String,String>>();
    	//read in data from text file and end the process when it reaches end_line
    	while (!(line = reader.readLine()).equals(end_line)) {
    		//ignore irrelevant data before the starting_line
    		if(line.equals(starting_line)){
    			start_to_record = true;
    		}
    		//start to process relevant information, namely, general data
    		if(start_to_record == true){
    			String data = line.replaceAll("  +", "  ").replaceAll("-", "");
    			//record general data per line into an array
    			String[] parts = data.split("  ");
    			//ignore new line
    			if(parts.length != 1){
    				//get headers as starting_line
    				if(line.equals(starting_line)){
    					subKeys = parts;
    				}else{
    					Map<String,String> subDict = new HashMap<String,String>();
    					for(int i=1;i<parts.length;i++){
    						subDict.put(subKeys[i], parts[i]);
    					}
    					dict.put(parts[0], subDict);
    				}
    			}
    		}
    	}
    	return dict;
    }
}