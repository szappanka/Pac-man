package nagyhazi;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.ImageIcon;


/**
* Az irányokra vonatkozó enumok a mozgás megkönnyítésére
* bal, fent, jobb, le
*/
enum Direction {
     left, up, right, down
}

/**
 * A mozgó lények osztálya, legyen az pacman vagy egy szellem
 * @author Szalka Panka - RITH1H
 */

abstract public class Individual {
	
	/**
	 * A karakter kezdõpozíciójának x kordinátája
	 */
	protected int firstpos_x;
	
	/**
	 * A karakter kezdõpozíciójának y kordinátája
	 */
	protected int firstpos_y;
	
	/**
	 * A karakter pozíciójának x kordinátája
	 */
	protected int pos_x;

	/**
	 * A karakter pozíciójának y kordinátája
	 */
	protected int pos_y;

	/**
	 * A karakter sebessége
	 */
	protected int speed;

	/**
	 * A karakter éppen melyik irányba megy (enum)
	 */
	protected Direction dir;

	/**
	 * A karakter elmentett iránya
	 */
	protected Direction saved;

	/**
	 * Megmutatja, hogy a karakter az adott helyzetben 
	 * melyik irányba tud továbbhaladni
	 */
	protected boolean[] turn;

	/**
	 * A karakterhez tartozó képek
	 */
	protected Image u, d, l, r, b;

	/**
	 * Megmutatja milyen lassítással mozogjon a karakter
	 */
	protected int own;

	/**
	 * azt 'own' visszaszámlálásának aktuálsi értékét mutatja
	 */
	protected int actual;

	/**
	 * Az aktuális pálya
	 */
	protected Level level;
	
	/**
	* A karaktert áthelyezi a teleportálás során a másik oldalra, és jelzi, ha ez megtörtént
	* @return boolean - épp teleportál-e 
	*/
	public boolean teleport() {
		
		if(this.pos_x-13==0 && this.dir == Direction.left) {
			this.pos_x = 480;
			return true;
		}
		else if(this.pos_x+13 == 493 && this.dir == Direction.right) {
			this.pos_x = 12;
			return true;
		}
		return false;
	}
	
	/**
	* Megvizsgálja, hogy a karakter az adott helyen melyik irány(ok)ba fordulhat,
	* és ez jelzi egy boolean tömbbel. Ha az adott indexû tömb true, arra szabad az út
	*/
	public void turns() {
		
		Arrays.fill(turn, false);
		
		if(!level.getField(this.pos_x-13-this.speed, this.pos_y).getWall() && (this.pos_y-13)%26==0) {
			this.turn[0] = true;	//left
		}
		
		if(!level.getField(this.pos_x, this.pos_y-13-this.speed).getWall() && (this.pos_x-13)%26==0) {
			this.turn[1] = true;	//up
		}
		
		if(!level.getField(this.pos_x+12+this.speed, this.pos_y).getWall() && (this.pos_y-13)%26==0) {
			this.turn[2] = true;	//right
		}
		
		if(!level.getField(this.pos_x, this.pos_y+12+this.speed).getWall() && (this.pos_x-13)%26==0) {
			this.turn[3] = true;	//down
		}
		
		if(level.getField(this.pos_x, this.pos_y+12+this.speed).getDoor()) {this.turn[3] = false;}
		
	}
	
	public void move() {  // a karakter mozgásáért felel
		
		if(this.dir == Direction.left && this.turn[0]==true) {this.pos_x -= this.speed;}
		if(this.dir == Direction.up && this.turn[1]==true) {this.pos_y -= this.speed;}
		if(this.dir == Direction.right && this.turn[2]==true) {this.pos_x += this.speed;}
		if(this.dir == Direction.down && this.turn[3]==true) {this.pos_y += this.speed;}
		
	}
	
	/**
	* Az adott karakter kirajzolásáért felel, amelyik irányba néz, azt a képet rajzolja ki
	* @param g - Graphics osztály a kirajzoláshoz
	*/
	public void render(Graphics g){
		
		if(this.dir == Direction.up) {
			g.drawImage(this.u, this.pos_x-13, this.pos_y-13, 25, 25, null);
		}
		else if(this.dir == Direction.down ) {
			g.drawImage(this.d, this.pos_x-13, this.pos_y-13, 25, 25, null);
		}
		else if(this.dir == Direction.right) {
			g.drawImage(this.r, this.pos_x-13, this.pos_y-13, 25, 25, null);
		}
		else if(this.dir == Direction.left) {
			g.drawImage(this.l, this.pos_x-13, this.pos_y-13, 25, 25,null);
		}
	}
	
	/**
	* Ez az irányváltoztatást segíti, a kívánt irányt állítja be
	* @param i - 0:bal, 1:fel, 2:jobb, 3:le
	*/
	public void setDirection(int i){
		if(i == 0) {this.saved = Direction.left;}
		else if(i == 1) {this.saved = Direction.up;}
		else if(i == 2) {this.saved = Direction.right;}
		else if(i == 3) {this.saved = Direction.down;}
	}
	
	/**
	* A karaktert az elsõ pozíciójába rakja és a megadott irányba állítja
	* @param i - 0:bal, 1:fel, 2:jobb, 3:le
	*/
	public void setFirstPos(int i) {
		this.pos_x = this.firstpos_x;
		this.pos_y = this.firstpos_y;
		this.setDirection(i);
	}
	
	/**
	* A karakter képeit tölti be
	* @param name - a karaker neve a megfelelõ mappával
	* @param plus - kiegészítõ kép, pacmannél a zárt, szellemnél a kék szellem
	*/
	public void loadImages(String name, String plus) {
		
		this.l = new ImageIcon("karakterek\\"+name+"\\left.png").getImage();
		this.u = new ImageIcon("karakterek\\"+name+"\\up.png").getImage();
		this.r = new ImageIcon("karakterek\\"+name+"\\right.png").getImage();
		this.d = new ImageIcon("karakterek\\"+name+"\\down.png").getImage();
		this.b = new ImageIcon("karakterek\\"+plus).getImage();
	}
	
	/**
	* Visszaadja az actual értékét
	* @return actual
	*/
	public int getAct() {return actual;}
	
	/**
	* Az actual settere, ami frissítésenként eggyel csökken
	*/
	public void setAct() {if(actual!=0) {actual-=1;}else{actual=own;}}
}
