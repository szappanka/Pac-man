package nagyhazi;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
* A játék közbeni pontszámot és a játék végén feltûnõ képernyõket kezeli
* @author Szalka Panka - RITH1H
*/

public class Score{
	
	/**
	* Az adott pontszámot tárolja szövegként
	*/
	private String sc;
	
	/**
	* A játékos által irányított pacman
	*/
	private Pacman p;
	
	/**
	* Az életek jelöléséhez a pacman balra nnézõ képe
	*/
	private Image im =  new ImageIcon("C:\\Users\\Panka\\Desktop\\BME\\Java\\nagyházi\\karakterek\\pacman\\left.png").getImage();
	
	/**
	* A toplista
	*/
	private TopScores ts = null;
	
	/**
	* Az ablak, gomb és adatai
	*/
	private Display d;
	
	/**
	* A Score osztály konstruktora, beállítja a megfelelõ értékeket
	* @param pac - a játékos által irányított pacman
	* @param d - a játékhoz tartozó ablak + gomb
	*/
			
	public Score(Pacman pac, Display d){
		
		this.p = pac;
		this.sc = String.valueOf(pac.getScore());
		ts = new TopScores();
		this.d = d;
		
	}
	
	/**
	* Frissíti az aktuális kiírást, amit lekér pacmantól
	*/
	public void update(){
		this.sc = String.valueOf(p.getScore());
	}
	
	/**
	* A kirajzolásért felel:
	* - toplista
	* - vesztés utáni képernyõ
	* - nyerés utáni képernyõ
	* - játék közbeni pontszám az ablak alján
	* @param g - Graphics osztály a kirajzoláshoz
	*/
	public void render(Graphics g) {
		
		if(p.getInGame()==false && p.getTops()==true) {  // toplista kirajzolása
			
			g.setColor(java.awt.Color.black);
			g.fillRect(0, 0, 500, 650);
			
			g.setColor(java.awt.Color.yellow);
			g.drawString("TOP SCORES", 210, 20);
			g.drawString("NAME", 110, 40);
			g.drawString("SCORE", 310, 40);
			
			for(int i = 0; i < ts.getList().size(); i++) {
				g.drawString((i+1)+". "+ts.getList().get(i).getName(), 90, i*30+80);
				g.drawString(""+ts.getList().get(i).getPoints(), 310, i*30+80);
			}
			
			g.drawString("PRESS SPACE TO PLAY AGAIN", 160, 330);
		}
		
		else if(p.getLives()==0 && p.getInGame()==false) {  // vesztés utáni képernyõ kirajzolása
			
			g.setColor(java.awt.Color.black);
			g.fillRect(0, 0, 500, 650);
			
			g.setColor(java.awt.Color.yellow);
			g.drawString("GAME OVER", 210, 300);
			g.drawString("PRESS SPACE TO PLAY AGAIN", 160, 330);
			g.drawString("PRESS S TO SEE THE TOP SCORES", 145, 360);
			
			d.setVisibility(false);
			
		}
		else if(p.getLives()!=0 && p.getInGame()==false) {  // nyerés utáni képernyõ kirajzolása
			
			g.setColor(java.awt.Color.black);
			g.fillRect(0, 0, 500, 650);
			
			g.setColor(java.awt.Color.yellow);
			g.drawString("YOU WON", 220, 270);
			g.drawString("YOUR SCORE: "+p.getScore(), 195, 300);
			g.drawString("PRESS SPACE TO PLAY AGAIN", 160, 330);
			g.drawString("PRESS S TO SEE THE TOP SCORES", 145, 360);
			if(!p.getSb()) {ts.save(p.getScore()); p.setSb(true);}
			
			d.setVisibility(false);
		}
		else {  // játék közbeni pontszám kirajzolása az ablak alján
			
			g.setColor(java.awt.Color.black);
			g.fillRect(0, 21*26, 494, 50);
			
			g.setColor(java.awt.Color.yellow);
			g.drawString("SCORE: ", 10, 21*26 + 25);
			g.drawString((String)sc, 60, 21*26 + 25);
			
			for(int i = 0; i <= p.getLives(); i++) {
			g.drawImage(im, 490 - i*26, 21*26 + 15, 23, 23, null);
			
			d.setVisibility(true);
			}
		}
	}
	
	/**
	 * At sc érték gettere
	 * @return sc
	 */
	public String getSc() {return this.sc;}
}