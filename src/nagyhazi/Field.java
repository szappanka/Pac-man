package nagyhazi;

/**
 *  A pályán található pontok enumja, ilyenek lehetnek egy mezõn
 *  kicsi pötty, nagy pötty, nincs
 */
enum Points {
    small, energizer, non
}

/**
 * Egy mezõ osztálya
 * @author Szalka Panka - RITH1H
 */

public class Field {
	
	/**
	 * A mezõ bal felsõ sarkának x kordinátája
	 */
	protected int x;
	
	/**
	 * A mezõ bal felsõ sarkának y kordinátája
	 */
	protected int y;
	
	/**
	 * Ha a mezõ egy fal, akkor true, amúgy false
	 */
	private boolean wall;
	

	/**
	 * Az adott mezõn milyen pont helyezkedik el alapból (kicsi vagy nagy) -> enum
	 */
	private Points point;

	/**
	 * Ha a mezõ egy kapu, true, amúgy false
	 */
	private boolean door;
	
	/**
	 * Visszaadja a wall értékét
	 * @return wall
	 */
	public boolean getWall() {
		return wall;
	}
	
	/**
	 * Visszaadja milyen pont van a mezõn
	 * @return point
	 */
	public Points getPoints() {
		return point;
	}
	
	/**
	 * Visszaadja kapu-e a mezõ
	 * @return door
	 */
	public boolean getDoor() {
		return door;
	}
	
	/**
	 * Beállítja a wall értékét
	 * @param w - fal-e a mezõ
	 */
	public void setWall(boolean w) {
		this.wall = w;
	}
	
	/**
	 * Beállítja a point értékét
	 * @param p - milyen pont van a mezõn
	 */
	public void setPoints(Points p) {
		this.point = p;
	}
	
	/**
	 * Beállítja a door értékét
	 * @param d - kapu-e a mezõ
	 */
	public void setDoor(boolean d) {
		this.door = d;
	}
}
