package nagyhazi;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Arrays;

import javax.swing.ImageIcon;


/**
* Az ir�nyokra vonatkoz� enumok a mozg�s megk�nny�t�s�re
* bal, fent, jobb, le
*/
enum Direction {
     left, up, right, down
}

/**
 * A mozg� l�nyek oszt�lya, legyen az pacman vagy egy szellem
 * @author Szalka Panka - RITH1H
 */

abstract public class Individual {
	
	/**
	 * A karakter kezd�poz�ci�j�nak x kordin�t�ja
	 */
	protected int firstpos_x;
	
	/**
	 * A karakter kezd�poz�ci�j�nak y kordin�t�ja
	 */
	protected int firstpos_y;
	
	/**
	 * A karakter poz�ci�j�nak x kordin�t�ja
	 */
	protected int pos_x;

	/**
	 * A karakter poz�ci�j�nak y kordin�t�ja
	 */
	protected int pos_y;

	/**
	 * A karakter sebess�ge
	 */
	protected int speed;

	/**
	 * A karakter �ppen melyik ir�nyba megy (enum)
	 */
	protected Direction dir;

	/**
	 * A karakter elmentett ir�nya
	 */
	protected Direction saved;

	/**
	 * Megmutatja, hogy a karakter az adott helyzetben 
	 * melyik ir�nyba tud tov�bbhaladni
	 */
	protected boolean[] turn;

	/**
	 * A karakterhez tartoz� k�pek
	 */
	protected Image u, d, l, r, b;

	/**
	 * Megmutatja milyen lass�t�ssal mozogjon a karakter
	 */
	protected int own;

	/**
	 * azt 'own' visszasz�ml�l�s�nak aktu�lsi �rt�k�t mutatja
	 */
	protected int actual;

	/**
	 * Az aktu�lis p�lya
	 */
	protected Level level;
	
	/**
	* A karaktert �thelyezi a teleport�l�s sor�n a m�sik oldalra, �s jelzi, ha ez megt�rt�nt
	* @return boolean - �pp teleport�l-e 
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
	* Megvizsg�lja, hogy a karakter az adott helyen melyik ir�ny(ok)ba fordulhat,
	* �s ez jelzi egy boolean t�mbbel. Ha az adott index� t�mb true, arra szabad az �t
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
	
	public void move() {  // a karakter mozg�s��rt felel
		
		if(this.dir == Direction.left && this.turn[0]==true) {this.pos_x -= this.speed;}
		if(this.dir == Direction.up && this.turn[1]==true) {this.pos_y -= this.speed;}
		if(this.dir == Direction.right && this.turn[2]==true) {this.pos_x += this.speed;}
		if(this.dir == Direction.down && this.turn[3]==true) {this.pos_y += this.speed;}
		
	}
	
	/**
	* Az adott karakter kirajzol�s��rt felel, amelyik ir�nyba n�z, azt a k�pet rajzolja ki
	* @param g - Graphics oszt�ly a kirajzol�shoz
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
	* Ez az ir�nyv�ltoztat�st seg�ti, a k�v�nt ir�nyt �ll�tja be
	* @param i - 0:bal, 1:fel, 2:jobb, 3:le
	*/
	public void setDirection(int i){
		if(i == 0) {this.saved = Direction.left;}
		else if(i == 1) {this.saved = Direction.up;}
		else if(i == 2) {this.saved = Direction.right;}
		else if(i == 3) {this.saved = Direction.down;}
	}
	
	/**
	* A karaktert az els� poz�ci�j�ba rakja �s a megadott ir�nyba �ll�tja
	* @param i - 0:bal, 1:fel, 2:jobb, 3:le
	*/
	public void setFirstPos(int i) {
		this.pos_x = this.firstpos_x;
		this.pos_y = this.firstpos_y;
		this.setDirection(i);
	}
	
	/**
	* A karakter k�peit t�lti be
	* @param name - a karaker neve a megfelel� mapp�val
	* @param plus - kieg�sz�t� k�p, pacmann�l a z�rt, szellemn�l a k�k szellem
	*/
	public void loadImages(String name, String plus) {
		
		this.l = new ImageIcon("karakterek\\"+name+"\\left.png").getImage();
		this.u = new ImageIcon("karakterek\\"+name+"\\up.png").getImage();
		this.r = new ImageIcon("karakterek\\"+name+"\\right.png").getImage();
		this.d = new ImageIcon("karakterek\\"+name+"\\down.png").getImage();
		this.b = new ImageIcon("karakterek\\"+plus).getImage();
	}
	
	/**
	* Visszaadja az actual �rt�k�t
	* @return actual
	*/
	public int getAct() {return actual;}
	
	/**
	* Az actual settere, ami friss�t�senk�nt eggyel cs�kken
	*/
	public void setAct() {if(actual!=0) {actual-=1;}else{actual=own;}}
}
