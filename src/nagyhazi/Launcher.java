package nagyhazi;

import java.io.IOException;

/**
 * Ez a f�program, ahol a j�t�kot �s  zen�t l�trehozzuk
 * @author Szalka Panka - RITH1H
 *
 */

public class Launcher {

	public static void main(String[] args) throws IOException {
		
		Game game = new Game("Pacman", 494, 546+50);  //a p�lya 475*525
		game.start();
	}
}