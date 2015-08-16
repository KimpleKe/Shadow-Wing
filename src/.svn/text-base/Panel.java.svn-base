/* SWEN20003 Object Oriented Software Development
 * Shadow Wing
 * Author: Kimple KE
 */

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/** The status panel.
 * Renders itself to a fixed position at the bottom of the screen, displaying
 * the given stats for the player. (See the render() method.)
 */
public class Panel
{
    /** Height of the status panel in pixels. */
    public static final int PANEL_HEIGHT = 70;

    /** Maximum allowed value for the player's shield.
     * Used for rendering the shield bar. */
    private static final int PLAYER_MAX_SHIELD = 540;
    /** Maximum allowed value for the boss's shield.
     * Used for rendering the shield bar. */
    private static final int BOSS_MAX_SHIELD = 2000;
    

    /** Image for the panel background. */
    private Image panel;
    /** Image for the firepower icon. */
    private Image fp_icon;
    /** type of object*/
    private int type;
    
    /** Access the type*/
    public int getType() {
		return type;
	}

    /** Mutate the type
     * @param type The type*/
	public void setType(int type) {
		this.type = type;
	}

	/** Creates a new Panel. */
    public Panel(Unit unit) throws SlickException
    {
    	if(unit.getType() == 1){
    		panel = new Image(Game.ASSETS_PATH + "/panel.png");
    	}else if(unit.getType() == -1){
    		panel = new Image(Game.ASSETS_PATH + "/boss_panel.png");
    	}
        fp_icon = new Image(Game.ASSETS_PATH + "/items/firepower.png");
        setType(unit.getType());
    }

    /** Renders the status panel for the player.
     * @param g The current Slick graphics context.
     * @param shield The player's current Shield level.
     * @param full_shield The player's current Full-Shield level.
     * @param firepower The player's current Firepower level.
     */
    public void render(Graphics g, int shield, int full_shield, int firepower)
    {
    	int MAX_SHIELD = 0;
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.6f);   // Black, transp
        Color BAR_FS = new Color(1.0f, 0.3f, 0.3f, 0.05f);  // Red, transp
        Color BAR_SH = new Color(Color.green);         
        if(type == 1){
        	BAR_SH = new Color(0.3f, 0.7f, 0.2f);// Green default colour
        	MAX_SHIELD = PLAYER_MAX_SHIELD;
        }else if(type == -1){
        	BAR_SH = new Color(Color.blue);         // blue
        	MAX_SHIELD = BOSS_MAX_SHIELD;
        }

        // Variables for layout
        int panel_top;              // Top y coordinate of panel
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int full_shield_bar_width;  // Size of red (Full-Shield) rectangle
        int shield_bar_width;       // Size of green (Shield) rectangle
        int fp_x, fp_y;             // Coordinates to draw firepower

        float full_shield_percent;  // Player's Full-Shield, % of MAX_SHIELD
        float shield_percent;       // Player's Shield, % of MAX_SHIELD

        panel_top = Game.screenheight - PANEL_HEIGHT; //default panel_top value
        if (type == -1){
        	panel_top = 0;
        }

        // Panel background image
        panel.draw(0, panel_top);

        // Display the player's shields
        text_x = 15;
        text_y = panel_top + 29;
        g.setColor(LABEL);
        g.drawString("Shields:", text_x, text_y);
        text = shield + "/" + full_shield;

        bar_x = 100;
        bar_y = panel_top + 24;
        bar_width = 410;
        bar_height = 30;
        full_shield_percent = (float) full_shield / MAX_SHIELD;
        shield_percent = (float) shield / MAX_SHIELD;
        full_shield_bar_width = (int) (bar_width * full_shield_percent);
        shield_bar_width = (int) (bar_width * shield_percent);
        text_x = bar_x +
            (full_shield_bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR_FS);
        g.fillRect(bar_x, bar_y, full_shield_bar_width, bar_height);
        g.setColor(BAR_SH);
        g.fillRect(bar_x, bar_y, shield_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's firepower
        g.setColor(LABEL);
        g.drawString("Firepower:", 525, text_y);
        bar_x = 626;
        bar_y = panel_top + 14;
        bar_width = 152;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        fp_x = 626;
        fp_y = panel_top + 4;
        for (int i=0; i<firepower; i++)
        {
            fp_icon.draw(fp_x, fp_y);
            fp_x += 40;
        }
    }
}
