package nagyhazi;

/**
* A vil�gosk�k szellem oszt�lya
* @author Szalka Panka - RITH1H
*/

public class Inky extends Ghost{

	/**
	 * A piros szellem
	 */
	private Blinky b;
	
	/**
	 * Inky konstruktora. Be�ll�tja a megfelel� �rt�keit a j�t�kkezd�zhez
	 * @param x - kezd� x kordin�ta
	 * @param y - kezd� y kordin�ta
	 * @param sp - a karakter sebess�ge
	 * @param l - az aktu�lis p�lya
	 * @param p - a j�t�kos pacman karaktere
	 * @param o - megmondja pacmanhez k�pest milyen lass�t�ssal mozogjanak
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
	* A karakter mozg�s��rt felel,
	* a c�lja, hogy pacman pont � �s a piros szellem k�z�tt legyen
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
