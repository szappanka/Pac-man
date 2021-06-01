package nagyhazi;

/**
* A narancs szellem oszt�lya
* @author Szalka Panka - RITH1H
*/

public class Clyde extends Ghost{
	
	/**
	 * Clyde konstruktora. Be�ll�tja a megfelel� �rt�keit a j�t�kkezd�zhez
	 * @param x - kezd� x kordin�ta
	 * @param y - kezd� y kordin�ta
	 * @param sp - a karakter sebess�ge
	 * @param l - az aktu�lis p�lya
	 * @param p - a j�t�kos pacman karaktere
	 * @param o - megmondja pacmanhez k�pest milyen lass�t�ssal mozogjanak
	 */
	public Clyde(int x, int y, int sp, Level l, Pacman p, int o) {
		super(x, y, sp, l, p, o, 600);
		
		this.dir = Direction.left;
		this.saved = Direction.up;
		
		loadImages("\\szellem\\clyde", "szellem\\blue\\blue.png");
	}
	
	/**
	* A karakter mozg�s��rt felel,
	* pacmant aktu�lis helyzet�t k�veti, ha 80 pt-n k�v�l van,
	* de ha pacman k�zelbe �r, akkor a bal als� sarok a c�lpontja
	*/
	public void ai() {

		
		int x_clyde = pac.pos_x;
		int y_clyde = pac.pos_y;
		
		double distance = Math.sqrt(((this.pos_x-pac.pos_x)*(this.pos_x-pac.pos_x))+((this.pos_x-pac.pos_x)*(this.pos_x-pac.pos_x)));
		
		if(distance<=80) {  //ha pacman bizonyos sugar�ban van, akkor a bal als� sarok a c�lja
			x_clyde = 10; y_clyde = 546;
		}
		
		if(!out) {ghostHouse();}
		else {
			target(x_clyde, y_clyde);
			ghostMove();
		}
	}	
}
