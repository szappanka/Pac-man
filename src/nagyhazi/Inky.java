package nagyhazi;

/**
* A világoskék szellem osztálya
* @author Szalka Panka - RITH1H
*/

public class Inky extends Ghost{

	/**
	 * A piros szellem
	 */
	private Blinky b;
	
	/**
	 * Inky konstruktora. Beállítja a megfelelõ értékeit a játékkezdézhez
	 * @param x - kezdõ x kordináta
	 * @param y - kezdõ y kordináta
	 * @param sp - a karakter sebessége
	 * @param l - az aktuális pálya
	 * @param p - a játékos pacman karaktere
	 * @param o - megmondja pacmanhez képest milyen lassítással mozogjanak
	 * @param bl - piros szellem
	 */
	public Inky(int x, int y, int sp, Level l, Pacman p, int o, Blinky bl) {
		super(x, y, sp, l, p, o, 200);
		
		this.dir = Direction.up;
		this.saved = Direction.up;
		
		loadImages("\\szellem\\inky", "szellem\\blue\\blue.png");
		this.b= bl;
	}
	
	/**
	* A karakter mozgásáért felel,
	* a célja, hogy pacman pont õ és a piros szellem között legyen
	*/
	public void ai() {

		int x_inky = pac.pos_x;
		int y_inky = pac.pos_y;
		
		if(pac.dir == Direction.left) {x_inky-=26;}
		if(pac.dir == Direction.up) {y_inky-=26;x_inky-=26;}
		if(pac.dir == Direction.right) {x_inky+=26;}
		if(pac.dir == Direction.down) {x_inky+=26;}
		
		x_inky = x_inky+(x_inky-b.pos_x);
		y_inky = y_inky+(y_inky-b.pos_y);
		
		if(!out) {ghostHouse();}
		else {
			target(x_inky, y_inky);
			ghostMove();
		}
	}	
}
