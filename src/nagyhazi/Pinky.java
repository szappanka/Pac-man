package nagyhazi;

/**
* A r�zsasz�n szellem oszt�lya
* @author Szalka Panka - RITH1H
*/

public class Pinky extends Ghost{
	
	/**
	 * Pinky konstruktora. Be�ll�tja a megfelel� �rt�keit a j�t�kkezd�zhez
	 * @param x - kezd� x kordin�ta
	 * @param y - kezd� y kordin�ta
	 * @param sp - a karakter sebess�ge
	 * @param l - az aktu�lis p�lya
	 * @param p - a j�t�kos pacman karaktere
	 * @param o - megmondja pacmanhez k�pest milyen lass�t�ssal mozogjanak
	 */
	public Pinky(int x, int y, int sp, Level l, Pacman p, int o) {
		super(x, y, sp, l, p, o, 400);
		
		this.dir = Direction.right;
		this.saved = Direction.up;
		
		loadImages("\\szellem\\pinky", "szellem\\blue\\blue.png");
	}
	
	/**
	* A karakter mozg�s��rt felel,
	* c�lja, hogy pacman el� jusson
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