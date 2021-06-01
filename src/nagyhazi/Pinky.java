package nagyhazi;

/**
* A rózsaszín szellem osztálya
* @author Szalka Panka - RITH1H
*/

public class Pinky extends Ghost{
	
	/**
	 * Pinky konstruktora. Beállítja a megfelelõ értékeit a játékkezdézhez
	 * @param x - kezdõ x kordináta
	 * @param y - kezdõ y kordináta
	 * @param sp - a karakter sebessége
	 * @param l - az aktuális pálya
	 * @param p - a játékos pacman karaktere
	 * @param o - megmondja pacmanhez képest milyen lassítással mozogjanak
	 */
	public Pinky(int x, int y, int sp, Level l, Pacman p, int o) {
		super(x, y, sp, l, p, o, 400);
		
		this.dir = Direction.right;
		this.saved = Direction.up;
		
		loadImages("\\szellem\\pinky", "szellem\\blue\\blue.png");
	}
	
	/**
	* A karakter mozgásáért felel,
	* célja, hogy pacman elé jusson
	*/
	public void ai() {

		int x_pinky = pac.pos_x;
		int y_pinky = pac.pos_y;
		
		if(pac.dir == Direction.left) {x_pinky-=52;}
		if(pac.dir == Direction.up) {y_pinky-=52;x_pinky-=52;}
		if(pac.dir == Direction.right) {x_pinky+=52;}
		if(pac.dir == Direction.down) {x_pinky+=52;}
		
		
		if(!out) {ghostHouse();}
		else {
			target(x_pinky, y_pinky);
			ghostMove();			
		}
	}
}