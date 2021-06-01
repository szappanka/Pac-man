package nagyhazi;

/**
* A narancs szellem osztálya
* @author Szalka Panka - RITH1H
*/

public class Clyde extends Ghost{
	
	/**
	 * Clyde konstruktora. Beállítja a megfelelõ értékeit a játékkezdézhez
	 * @param x - kezdõ x kordináta
	 * @param y - kezdõ y kordináta
	 * @param sp - a karakter sebessége
	 * @param l - az aktuális pálya
	 * @param p - a játékos pacman karaktere
	 * @param o - megmondja pacmanhez képest milyen lassítással mozogjanak
	 */
	public Clyde(int x, int y, int sp, Level l, Pacman p, int o) {
		super(x, y, sp, l, p, o, 600);
		
		this.dir = Direction.left;
		this.saved = Direction.up;
		
		loadImages("\\szellem\\clyde", "szellem\\blue\\blue.png");
	}
	
	/**
	* A karakter mozgásáért felel,
	* pacmant aktuális helyzetét követi, ha 80 pt-n kívül van,
	* de ha pacman közelbe ér, akkor a bal alsó sarok a célpontja
	*/
	public void ai() {

		
		int x_clyde = pac.pos_x;
		int y_clyde = pac.pos_y;
		
		double distance = Math.sqrt(((this.pos_x-pac.pos_x)*(this.pos_x-pac.pos_x))+((this.pos_x-pac.pos_x)*(this.pos_x-pac.pos_x)));
		
		if(distance<=80) {  //ha pacman bizonyos sugarában van, akkor a bal alsó sarok a célja
			x_clyde = 10; y_clyde = 546;
		}
		
		if(!out) {ghostHouse();}
		else {
			target(x_clyde, y_clyde);
			ghostMove();
		}
	}	
}
