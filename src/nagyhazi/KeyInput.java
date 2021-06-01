package nagyhazi;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 

/**
* A billentyûzeten lévõ gombok lenyomásáért felel
* @author Szalka Panka - RITH1H
*/

public class KeyInput implements KeyListener {     
	

	/**
	* A játékos által irányított pacman
	*/
	private Pacman p;
	
	/**
	 * A KeyInput konstruktora, ahol megadjuk pacmant
	 * @param p - a játékos által irányított pacman
	 */
	public KeyInput(Pacman p){this.p = p;}

	/**
	 * Ez a függvény kezeli ha lenyomnak egy gombot
	 * Ezzel lehet irányitani pacmant (awsd+nyilak)
	 * és ha vége egy játéknak így lehet váltani a toplistára és az új játékra
	 * @param e - KeyEvent, a megnyomott gomb
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_S && p.getInGame()==false) {p.setTops(true);}
		
		else if (key == KeyEvent.VK_SPACE && p.getInGame()==false) {p.setInGame(true); p.setLives(3); p.setScore(0); p.setTops(false); p.setSb(false); p.setPaused(false);}

		else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {p.setDirection(0);}

	    else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) {p.setDirection(1);}

	    else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {p.setDirection(2);}

	    else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) {p.setDirection(3);}
	}
	
	/**
	 * Ez a függvény nincs meghívva
	 */
	public void keyReleased(KeyEvent e) {
	
	}
	
	/**
	 * Ez a függvény nincs meghívva
	 */
	public void keyTyped(KeyEvent e) {
		
	}
}