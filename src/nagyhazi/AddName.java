package nagyhazi;

import java.applet.Applet;
import javax.swing.JOptionPane;

/**
* Ez az osztaly keri be es adja vissza a nyertes nevet
* @author Szalka Panka - RITH1H
*/

public class AddName extends Applet{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Meg kell adni egy JOptionPanel-en a játékos nevét 
	 * @return string Visszaadja a megadott nevet
	 */
	public String getName() {
		String string;
		string = JOptionPane.showInputDialog("You win, please enter your name: ");
		return string;
	}

}
