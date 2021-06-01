package nagyhazi;

/**
* A piros szellem oszt�lya
* @author Szalka Panka - RITH1H
*/

public class Blinky extends Ghost{

	/**
	 * Blinky konstruktora. Be�ll�tja a megfelel� �rt�keit a j�t�kkezd�zhez
	 * @param x - kezd� x kordin�ta
	 * @param y - kezd� y kordin�ta
	 * @param sp - a karakter sebess�ge
	 * @param l - az aktu�lis p�lya
	 * @param p - a j�t�kos pacman karaktere
	 * @param o - megmondja pacmanhez k�pest milyen lass�t�ssal mozogjanak
	 */
	public Blinky(int x, int y, int sp, Level l, Pacman p, int o) {
		super(x, y, sp, l, p, o, 0);
		
		this.dir = Direction.left;
		this.saved = Direction.left;
		
		loadImages("\\szellem\\blinky", "szellem\\blue\\blue.png");
	}
	
	/**
	* A karakter mozg�s��rt felel,
	* pacmant aktu�lis helyzet�t k�veti
	*/
	public void ai() {

		if(!out) {ghostHouse();}
		else {
			target(pac.pos_x, pac.pos_y);
			ghostMove();
		}	
	}
}