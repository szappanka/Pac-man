package nagyhazi;

import java.awt.Graphics;
import java.util.Arrays;

/**
* A szellemek oszt�lya
* @author Szalka Panka - RITH1H
*/

abstract public class Ghost extends Individual{
	
	/**
	 * A j�t�kos �ltal ir�ny�tott pacman
	 */
	protected Pacman pac;
	
	/**
	 * Ha a szellem sebezhet� �llapotban van true, am�gy false
	 */
	protected boolean blue;
	
	/**
	 * A szellem �llapota, ameddig sebezhet�.
	 * Innen sz�mol vissza folyamatosan, ha 0-hoz �r visszav�ltozik
	 */
	protected double vul = 1000;
	
	/**
	 * Az �j j�t�kkezd�sn�l a bel�p�si id�t jelzi,
	 * hogy a szellem mikor indulhat el a szellemlakb�l 
	 */
	protected double enter;
	
	/**
	 * Szellemlakn�l mutatja �pp hol tart a viszafele sz�mol�s az elindul�shoz
	 */
	protected double waited;
	
	/**
	 * Ha ki�rt a szellemlakb�l true, am�gy false
	 */
	protected boolean out = false;
	
	/**
	 * Egy szellem konstruktora. Be�ll�tja a megfelel� �rt�keit a j�t�kkezd�shez
	 * @param x - kezd� x kordin�ta
	 * @param y - kezd� y kordin�ta
	 * @param sp - a karakter sebess�ge
	 * @param l - az aktu�lis p�lya
	 * @param p - a j�t�kos pacman karaktere
	 * @param o - megmondja pacmanhez k�pest milyen lass�t�ssal mozogjanak
	 * @param e - jelzi, hogy mikor indul el a szellem a j�t�k sor�n a szellemlakb�l
	 */
	public Ghost(int x, int y, int sp, Level l, Pacman p, int o, double e) {
		this.blue = false;

		this.firstpos_x = x;
		this.firstpos_y = y;
		
		this.pos_x = x;
		this.pos_y = y;
		
		this.speed = sp;
		this.turn = new boolean[4];
		Arrays.fill(turn, false);
		
		this.level = l;
		this.pac = p;

		this.own = o;
		this.actual = o;
		
		this.enter = e;
		this.waited = e;
	}
	
	/**
	* A szellem mozg�s��rt felel, 
	* ebben nincs benne a kanyarok�rt felel�s algoritmus.
	* Ha pacmannal tal�lkozik a mozg�s sor�n, att�l f�gg�en, hogy sbezhet�-e a szellem,
	* vagy �, vagy pacman meghal.
	*/
	public void ghostMove() {
		
		if(Math.abs(pac.pos_x-this.pos_x)<5 && Math.abs(pac.pos_y-this.pos_y)<5 && this.blue == false){pac.death();}
		if(Math.abs(pac.pos_x-this.pos_x)<5 && Math.abs(pac.pos_y-this.pos_y)<5 && this.blue == true){this.death();}
		turns();
		
		if(!teleport()){
			this.turns();
			switch(this.saved) {
			
				case left:
					
					if(this.turn[0]) {this.dir = Direction.left;}
					this.move();
					break;
				
				case up: 
					
					if(this.turn[1]) {this.dir = Direction.up;}
					this.move();
					break;
					
				case right:
					
					if(this.turn[2]) {this.dir = Direction.right;}
					this.move();
					break;
		
				case down: 
					
					if(this.turn[3]) {this.dir = Direction.down;}
					this.move();
					break;
				
				default:
				break;
			}
		}
	}
	
	/**
	* Megvizsg�lja, hogy egy szellem mikor �r �tkeresztez�d�shez, 
	* ahol utat kell v�lasztania. 
	* @return int - a v�laszthat� utak sz�ma
	*/
	public int cross() {
		int x = 0;
		turns();

		for(int i = 0; i < this.turn.length; i++) {
			if(turn[i]) {x++;}
		}
		
		if(x==1 || x==3 || x==4) {return x;}
		else if(x==2 && (!(turn[0]&&turn[2]) && !(turn[1]&&turn[3]))) {return x;}  // ha kanyarn�l van(nem egyenes �ton)
		else if(x==2 && dir==Direction.up) {return x;}  //ha kij�n a szellemlakb�l
		return 0;
	}
	
	/**
	* A szellemlakban val� mozg�s�rt felel
	*/
	public void ghostHouse() {
		
		if(waited<=0) {
			ghostMove();
		}
		if(this.pos_y<=195) {out=true;}
	}
	
	/**
	* A szellemek kirajzol�s�t v�gzi
	* @param g - Graphics oszt�ly a kirajzol�shoz
	*/
	public void renderG(Graphics g){
		
		if(blue) {
			g.drawImage(this.b, this.pos_x-13, this.pos_y-13, 25, 25, null);
		}
		else {
			this.render(g);
		}
	}
	
	/**
	* A szellemek sebezhet�v� agy sebezhetetlenn� v�lik
	* @param b - sebezhet� legyen-e a szellem
	*/
	public void setVulnerable(boolean b) {
		this.blue = b;
		this.vul=1000;
	}
	
	/**
	* A sebezhet�s�g idej�nek sz�mol�s�ra van.
	* @return this.vuln - �pp hol tart a visszasz�mol�s
	*/
	public double setInt() {
		this.vul -= 0.8;
		if(this.waited>=0){this.waited -= 0.5;}
		return this.vul;
	}
	
	/**
	* A szellemek hal�lakor visszat�rnek a szellemlak f�l� 
	* �s pontot kap pacman.
	*/
	public void death() {  // a 
		
		this.pos_x = 246;
		this.pos_y = 195;
		pac.setScore(pac.getScore()+200);
		this.dir = Direction.left;
		this.saved = Direction.left;

		this.setVulnerable(false);
	}
	
	/**
	* A kezd�poz�ci�j�ra ker�l a szellem,
	* �s a megadott ir�nyba �ll�t�dik be.
	* @param d - a k�v�nt halad�si ir�ny
	*/
	public void setFirstPos(Direction d) {  // 
		
		this.pos_x = this.firstpos_x;
		this.pos_y = this.firstpos_y;
		this.dir = d;
		this.saved = Direction.up;
		this.waited = this.enter;
		this.out = false;

		this.setVulnerable(false);
	}
	
	/**
	* A szellemek ai mozg�sa, ami t�vols�got sz�mol a c�lpont �s a szellem k�z�tt, 
	* azt az ir�nyt v�lasztja ami a legk�zelebb viszi a c�lponthoz, �s haladhat is arra.
	* Ha valahol ez megegyezik k�t ir�nyn�l, akkor s�lyozott sorrend van: fel bal le jobb
	* @param pos_x - a c�lpont x poz�ci�ja
	* @param pos_y - a c�lpont y poz�ci�ja
	*/
	public void target(int pos_x, int pos_y) {  //
		
		double[] distance = new double[4];
		
		if(cross()!=0) {  //kisz�m�tja az egyes elmozdul�sokkal milyen messze lenne a c�lpontt�l
			
			distance[0]=Math.sqrt((pos_y - this.pos_y) * (pos_y - this.pos_y) + (pos_x - (this.pos_x-14)) * (pos_x - (this.pos_x-14)));
			distance[1]=Math.sqrt((pos_y - (this.pos_y-14)) * (pos_y - (this.pos_y-14)) + (pos_x - this.pos_x) * (pos_x - this.pos_x));
			distance[2]=Math.sqrt((pos_y - this.pos_y) * (pos_y - this.pos_y) + (pos_x - (this.pos_x+14)) * (pos_x - (this.pos_x+14)));
			distance[3]=Math.sqrt((pos_y - (this.pos_y+14)) * (pos_y - (this.pos_y+14)) + (pos_x - this.pos_x) * (pos_x - this.pos_x));
		}
		
		if(cross()!=1){  // ne tudjon megfordulni, csak ha az az egyetlen megold�s
			if(dir==Direction.left) {turn[2]=false;}
			if(dir==Direction.up) {turn[3]=false;}
			if(dir==Direction.right) {turn[0]=false;}
			if(dir==Direction.down) {turn[1]=false;}
		}
		
		double min = 0.0;
		int index = -1;
		int index2 = -1;  // ha egyez�s van akkor kell, mert ilyenkor van sorrend: fel bal le jobb
		
		for(int i = 0; i < 4; i++) {
			if(min==0 && turn[i]) {min = distance[i]; index = i;}
			else if(distance[i]<min && turn[i]) {min = distance[i]; index = i;}
			else if(distance[i]==min) {index2=i;}
		}
		
		if(index2==-1) { //ha nincsen egyez�s a t�vols�gok k�z�tt
			setDirection(index);
		}
		
		if(index2!=-1) { //ha nincsen egyez�s a t�vols�gok k�z�tt
			if(index==1||index2==1) {setDirection(1);}
			else if(index==0||index2==0) {setDirection(0);}
			else if(index==3||index2==3) {setDirection(3);}
			else if(index==2||index2==2) {setDirection(2);}
		}
	}
}
