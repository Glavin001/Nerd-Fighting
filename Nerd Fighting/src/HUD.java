import java.awt.Color;
import java.awt.Font;


public class HUD { // HUD = Heads-Up-Display. - Used for player details, such as ammo and FPS.
	
	// Working variables
		public core core;
	
	public HUD (core core)
	{
		// Set core
		this.core = core;
		
	}

	public void drawThis(screen screen, gameLoop gameLoop)
	{
		// Draw HUD on screen.
		 
		// display frames per second...
		screen.graphics2d.setFont(new Font("Times New Roman", Font.BOLD, 12));
		screen.graphics2d.setColor(Color.BLACK);
		screen.graphics2d.drawString("FPS: " + ((int) (gameLoop.fps())), 20 + screen.offsetX, 20 + screen.offsetY);
		
		double damage = 0;
		player player = core.world().allPlayers.get(core.world().playerIndex(core.subconscious.playerID));
		damage = (player.damage);
		screen.graphics2d.setColor(Color.RED);
		screen.graphics2d.drawString(("Damage: " + damage),80 + screen.offsetX, 20 + screen.offsetY);
		double health = 0;
		health = (player.health);
		screen.graphics2d.setColor(Color.GREEN);
		screen.graphics2d.drawString(("Health: " + health),170 + screen.offsetX, 20 + screen.offsetY);
		
	}
	
}
