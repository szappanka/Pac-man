package nagyhazi;

import java.io.IOException;

/**
 * Ez a fõprogram, ahol a játékot és  zenét létrehozzuk
 * @author Szalka Panka - RITH1H
 *
 */

public class Launcher {

	public static void main(String[] args) throws IOException {
		
		Game game = new Game("Pacman", 494, 546+50);  //a pálya 475*525
		game.start();
	}
}