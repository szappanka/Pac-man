package nagyhazi;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener; 

/**
* A billenty�zeten l�v� gombok lenyom�s��rt felel
* @author Szalka Panka - RITH1H
*/

public class KeyInput implements KeyListener {     
	

	/**
	* A j�t�kos �ltal ir�ny�tott pacman
	*/
	private Pacman p;
	
	/**
	 * A KeyInput konstruktora, ahol megadjuk pacmant
	 * @param p - a j�t�kos �ltal ir�ny�tott pacman
	 */
	public KeyInput(Pacman p){this.p = p;}

	/**
	 * Ez a f�ggv�ny kezeli ha lenyomnak egy gombot
	 * Ezzel lehet ir�nyitani pacmant (awsd+nyilak)
	 * �s ha v�ge egy j�t�knak �gy lehet v�ltani a toplist�ra �s az �j j�t�kra
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
	 * Ez a f�ggv�ny nincs megh�vva
	 */
	public void keyReleased(KeyEvent e) {
	
	}
	
	/**
	 * Ez a f�ggv�ny nincs megh�vva
	 */
	public void keyTyped(KeyEvent e) {
		
	}
}