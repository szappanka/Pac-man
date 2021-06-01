package nagyhazi;

import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;

/**
* A j�t�kban szerepl� p�ly��rt felel
* @author Szalka Panka - RITH1H
*/

public class Level {
	

	/**
	* A mez�kb�l �ll� lista
	*/
	private Field[][] level;
	
	/**
	* A p�ly�hoz tartoz� k�pek
	*/
	private Image wall, point_sm, point_en, point_no, door;
	
	/**
	 * A Level konstruktora, ahol bet�ltj�k a k�peket
	 * �s mez�kb�l l�trehozza a p�ly�t
	 */
	public Level() {
		this.level = new Field[21][19];
		loadImages();
		
		try {
			createLevel("palya.txt");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	/**
	 * A k�pek bet�lt�s��rt felel�s f�ggv�ny
	 */
	public void loadImages() {
		String folder ="karakterek\\palya\\";
		
		this.wall = new ImageIcon(folder+"wall.png").getImage();   //el�r�si �tvonalak
		this.point_sm = new ImageIcon(folder+"point_sm.png").getImage();
		this.point_en = new ImageIcon(folder+"point_lg.png").getImage();
		this.point_no = new ImageIcon(folder+"point_no.png").getImage();
		this.door = new ImageIcon(folder+"kapu.png").getImage();
	}
	
	/**
	 * L�trehozza a p�ly�t a megadott file alapj�n �s be�ll�tja 
	 * az egyes mez�k k�l�nb�z� �rt�keit
	 * @param s - a file neve, amiben a p�lya van
	 * @throws IOException - ha nem tal�lhat� a filet
	 */
	public void createLevel(String s) throws IOException{

		      File f = new File(s);
		      Scanner sc = new Scanner(f);
		      
		      while (sc.hasNextLine()) {
		    	  for (int i = 0; i < level.length; i++) {
						
						String line = sc.nextLine();
						if (line == null) {break;}
						String array[] = line.split(" ");
						
						for (int j = 0; j < level[0].length; j++) {
							
							this.level[i][j] = new Field();
							this.level[i][j].x = j*26;
							this.level[i][j].y = i*26;
							
							if (array[j].equals("k")) {
								this.level[i][j].setDoor(true);
								this.level[i][j].setWall(false);
								this.level[i][j].setPoints(Points.non);
							}
							else if (array[j].equals("w")) {
								this.level[i][j].setWall(true);
								this.level[i][j].setDoor(false);
								this.level[i][j].setPoints(Points.non);
							}
							else {
								
								this.level[i][j].setWall(false);
								this.level[i][j].setDoor(false);
								
								if (array[j].equals(".")) {
									this.level[i][j].setPoints(Points.small);
								}
								if (array[j].equals("*")) {
									this.level[i][j].setPoints(Points.energizer);
								}
								if (array[j].equals("-")) {
									this.level[i][j].setPoints(Points.non);
								}
							}
						}
					}
		      }
		      sc.close();	     		      
		
	}
	
	/**
	 * A p�lya kirajzol�s�t v�gzi, minden mez�t a param�terei 
	 * �s a tulajdons�gai alapj�n rajzol ki
	 * @param g - Graphics oszt�ly a kirajzol�shoz
	 */
	public void render(Graphics g){
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[0].length; j++) {
				
				if(level[i][j].getWall()&&!level[i][j].getDoor()) {
					g.drawImage(this.wall, level[i][j].x, level[i][j].y, 26, 26, null);
				}
				if(!level[i][j].getWall() && level[i][j].getPoints().equals(Points.small)&&!level[i][j].getDoor()) {
					g.drawImage(this.point_sm, level[i][j].x, level[i][j].y, 26, 26, null);
				}
				if(!level[i][j].getWall() && level[i][j].getPoints().equals(Points.energizer)&&!level[i][j].getDoor()) {
					g.drawImage(this.point_en, level[i][j].x, level[i][j].y, 26, 26, null);
				}
				if(!level[i][j].getWall() && level[i][j].getPoints().equals(Points.non)&&!level[i][j].getDoor()) {
					g.drawImage(this.point_no, level[i][j].x, level[i][j].y, 26, 26, null);
				}
				if(level[i][j].getDoor()) {
					g.drawImage(this.door, level[i][j].x, level[i][j].y, 26, 26, null);
				}
			}
		}
	}
	
	/**
	 * 2 param�ter alapj�n megadja, hogy a pont melyik mez�n bel�l van
	 * @param pos_x - x koordin�ta
	 * @param pos_y - y koordin�ta
	 * @return this.level[x][y] - a keresett mez�
	 */
	public Field getField(double pos_x, double pos_y){
		int x = (int)(pos_y)/26;
		int y = (int)(pos_x)/26;
		if((int)((pos_y)/26)<0) {x=0;}
		if((int)((pos_x)/26)<0) {y=0;}
		if((int)((pos_y)/26)>=21) {x=21;}
		if((int)((pos_x)/26)>=19) {y=19;}
		return this.level[x][y];
	}
	
	/**
	 * 2 param�ter alapj�n az adott mez�r�l elt�vol�tja a pontot
	 * @param pos_x - x koordin�ta
	 * @param pos_y - y koordin�ta
	 */
	public void setEaten(double pos_x, double pos_y){
		level[(int)((pos_y)/26)][(int)((pos_x)/26)].setPoints(Points.non);;
	}
	
	/**
	 * Megvizsg�lja, hogy a p�ly�n van-e m�g nem megevett bogy�
	 * @return boolean - az el�z� k�rd�sre a v�lasszal t�r vissza 
	 */
	public boolean checkWin() {
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[0].length; j++) {
				if(level[i][j].getPoints()!=Points.non) {
					return false;
				}
			}
		}
		return true;
	}
}