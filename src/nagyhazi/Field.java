package nagyhazi;

/**
 *  A p�ly�n tal�lhat� pontok enumja, ilyenek lehetnek egy mez�n
 *  kicsi p�tty, nagy p�tty, nincs
 */
enum Points {
    small, energizer, non
}

/**
 * Egy mez� oszt�lya
 * @author Szalka Panka - RITH1H
 */

public class Field {
	
	/**
	 * A mez� bal fels� sark�nak x kordin�t�ja
	 */
	protected int x;
	
	/**
	 * A mez� bal fels� sark�nak y kordin�t�ja
	 */
	protected int y;
	
	/**
	 * Ha a mez� egy fal, akkor true, am�gy false
	 */
	private boolean wall;
	

	/**
	 * Az adott mez�n milyen pont helyezkedik el alapb�l (kicsi vagy nagy) -> enum
	 */
	private Points point;

	/**
	 * Ha a mez� egy kapu, true, am�gy false
	 */
	private boolean door;
	
	/**
	 * Visszaadja a wall �rt�k�t
	 * @return wall
	 */
	public boolean getWall() {
		return wall;
	}
	
	/**
	 * Visszaadja milyen pont van a mez�n
	 * @return point
	 */
	public Points getPoints() {
		return point;
	}
	
	/**
	 * Visszaadja kapu-e a mez�
	 * @return door
	 */
	public boolean getDoor() {
		return door;
	}
	
	/**
	 * Be�ll�tja a wall �rt�k�t
	 * @param w - fal-e a mez�
	 */
	public void setWall(boolean w) {
		this.wall = w;
	}
	
	/**
	 * Be�ll�tja a point �rt�k�t
	 * @param p - milyen pont van a mez�n
	 */
	public void setPoints(Points p) {
		this.point = p;
	}
	
	/**
	 * Be�ll�tja a door �rt�k�t
	 * @param d - kapu-e a mez�
	 */
	public void setDoor(boolean d) {
		this.door = d;
	}
}
