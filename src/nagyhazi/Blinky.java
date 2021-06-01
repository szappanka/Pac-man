package nagyhazi;

/**
* A piros szellem osztálya
* @author Szalka Panka - RITH1H
*/

public class Blinky extends Ghost{

	/**
	 * Blinky konstruktora. Beállítja a megfelelõ értékeit a játékkezdézhez
	 * @param x - kezdõ x kordináta
	 * @param y - kezdõ y kordináta
	 * @param sp - a karakter sebessége
	 * @param l - az aktuális pálya
	 * @param p - a játékos pacman karaktere
	 * @param o - megmondja pacmanhez képest milyen lassítással mozogjanak
	 */
	public Blinky(int x, int y, int sp, Level l, Pacman p, int o) {
		super(x, y, sp, l, p, o, 0);
		
		this.dir = Direction.left;
		this.saved = Direction.left;
		
		loadImages("\\szellem\\blinky", "szellem\\blue\\blue.png");
	}
	
	/**
	* A karakter mozgásáért felel,
	* pacmant aktuális helyzetét követi
	*/
	public void ai() {

		if(!out) {ghostHouse();}
		else {
			target(pac.pos_x, pac.pos_y);
			ghostMove();
		}	
	}
}