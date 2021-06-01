package nagyhazi;

import java.awt.Graphics;
import java.awt.Image;
import java.io.*;
import java.util.*;

import javax.swing.ImageIcon;

/**
* A játékban szereplõ pályáért felel
* @author Szalka Panka - RITH1H
*/

public class Level {
	

	/**
	* A mezõkbõl álló lista
	*/
	private Field[][] level;
	
	/**
	* A pályához tartozó képek
	*/
	private Image wall, point_sm, point_en, point_no, door;
	
	/**
	 * A Level konstruktora, ahol betöltjük a képeket
	 * és mezõkbõl létrehozza a pályát
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
	 * A képek betöltéséért felelõs függvény
	 */
	public void loadImages() {
		String folder ="karakterek\\palya\\";
		
		this.wall = new ImageIcon(folder+"wall.png").getImage();   //elérési útvonalak
		this.point_sm = new ImageIcon(folder+"point_sm.png").getImage();
		this.point_en = new ImageIcon(folder+"point_lg.png").getImage();
		this.point_no = new ImageIcon(folder+"point_no.png").getImage();
		this.door = new ImageIcon(folder+"kapu.png").getImage();
	}
	
	/**
	 * Létrehozza a pályát a megadott file alapján és beállítja 
	 * az egyes mezõk különbözõ értékeit
	 * @param s - a file neve, amiben a pálya van
	 * @throws IOException - ha nem található a filet
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
	 * A pálya kirajzolását végzi, minden mezõt a paraméterei 
	 * és a tulajdonságai alapján rajzol ki
	 * @param g - Graphics osztály a kirajzoláshoz
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
	 * 2 paraméter alapján megadja, hogy a pont melyik mezõn belül van
	 * @param pos_x - x koordináta
	 * @param pos_y - y koordináta
	 * @return this.level[x][y] - a keresett mezõ
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
	 * 2 paraméter alapján az adott mezõrõl eltávolítja a pontot
	 * @param pos_x - x koordináta
	 * @param pos_y - y koordináta
	 */
	public void setEaten(double pos_x, double pos_y){
		level[(int)((pos_y)/26)][(int)((pos_x)/26)].setPoints(Points.non);;
	}
	
	/**
	 * Megvizsgálja, hogy a pályán van-e még nem megevett bogyó
	 * @return boolean - az elõzõ kérdésre a válasszal tér vissza 
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