package nagyhazi;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
* A j�t�k k�zbeni pontsz�mot �s a j�t�k v�g�n felt�n� k�perny�ket kezeli
* @author Szalka Panka - RITH1H
*/

public class Score{
	
	/**
	* Az adott pontsz�mot t�rolja sz�vegk�nt
	*/
	private String sc;
	
	/**
	* A j�t�kos �ltal ir�ny�tott pacman
	*/
	private Pacman p;
	
	/**
	* Az �letek jel�l�s�hez a pacman balra nn�z� k�pe
	*/
	private Image im =  new ImageIcon("C:\\Users\\Panka\\Desktop\\BME\\Java\\nagyh�zi\\karakterek\\pacman\\left.png").getImage();
	
	/**
	* A toplista
	*/
	private TopScores ts = null;
	
	/**
	* Az ablak, gomb �s adatai
	*/
	private Display d;
	
	/**
	* A Score oszt�ly konstruktora, be�ll�tja a megfelel� �rt�keket
	* @param pac - a j�t�kos �ltal ir�ny�tott pacman
	* @param d - a j�t�khoz tartoz� ablak + gomb
	*/
			
	public Score(Pacman pac, Display d){
		
		this.p = pac;
		this.sc = String.valueOf(pac.getScore());
		ts = new TopScores();
		this.d = d;
		
	}
	
	/**
	* Friss�ti az aktu�lis ki�r�st, amit lek�r pacmant�l
	*/
	public void update(){
		this.sc = String.valueOf(p.getScore());
	}
	
	/**
	* A kirajzol�s�rt felel:
	* - toplista
	* - veszt�s ut�ni k�perny�
	* - nyer�s ut�ni k�perny�
	* - j�t�k k�zbeni pontsz�m az ablak alj�n
	* @param g - Graphics oszt�ly a kirajzol�shoz
	*/
	public void render(Graphics g) {
		
		if(p.getInGame()==false && p.getTops()==true) {  // toplista kirajzol�sa
			
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
		
		else if(p.getLives()==0 && p.getInGame()==false) {  // veszt�s ut�ni k�perny� kirajzol�sa
			
			g.setColor(java.awt.Color.black);
			g.fillRect(0, 0, 500, 650);
			
			g.setColor(java.awt.Color.yellow);
			g.drawString("GAME OVER", 210, 300);
			g.drawString("PRESS SPACE TO PLAY AGAIN", 160, 330);
			g.drawString("PRESS S TO SEE THE TOP SCORES", 145, 360);
			
			d.setVisibility(false);
			
		}
		else if(p.getLives()!=0 && p.getInGame()==false) {  // nyer�s ut�ni k�perny� kirajzol�sa
			
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
		else {  // j�t�k k�zbeni pontsz�m kirajzol�sa az ablak alj�n
			
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
	 * At sc �rt�k gettere
	 * @return sc
	 */
	public String getSc() {return this.sc;}
}