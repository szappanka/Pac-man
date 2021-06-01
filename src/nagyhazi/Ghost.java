package nagyhazi;

import java.awt.Graphics;
import java.util.Arrays;

/**
* A szellemek osztálya
* @author Szalka Panka - RITH1H
*/

abstract public class Ghost extends Individual{
	
	/**
	 * A játékos által irányított pacman
	 */
	protected Pacman pac;
	
	/**
	 * Ha a szellem sebezhetõ állapotban van true, amúgy false
	 */
	protected boolean blue;
	
	/**
	 * A szellem állapota, ameddig sebezhetõ.
	 * Innen számol vissza folyamatosan, ha 0-hoz ér visszaváltozik
	 */
	protected double vul = 1000;
	
	/**
	 * Az új játékkezdésnél a belépési idõt jelzi,
	 * hogy a szellem mikor indulhat el a szellemlakból 
	 */
	protected double enter;
	
	/**
	 * Szellemlaknál mutatja épp hol tart a viszafele számolás az elinduláshoz
	 */
	protected double waited;
	
	/**
	 * Ha kiért a szellemlakból true, amúgy false
	 */
	protected boolean out = false;
	
	/**
	 * Egy szellem konstruktora. Beállítja a megfelelõ értékeit a játékkezdéshez
	 * @param x - kezdõ x kordináta
	 * @param y - kezdõ y kordináta
	 * @param sp - a karakter sebessége
	 * @param l - az aktuális pálya
	 * @param p - a játékos pacman karaktere
	 * @param o - megmondja pacmanhez képest milyen lassítással mozogjanak
	 * @param e - jelzi, hogy mikor indul el a szellem a játék során a szellemlakból
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
	* A szellem mozgásáért felel, 
	* ebben nincs benne a kanyarokért felelõs algoritmus.
	* Ha pacmannal találkozik a mozgás során, attól függõen, hogy sbezhetõ-e a szellem,
	* vagy õ, vagy pacman meghal.
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
	* Megvizsgálja, hogy egy szellem mikor ér útkeresztezõdéshez, 
	* ahol utat kell választania. 
	* @return int - a választható utak száma
	*/
	public int cross() {
		int x = 0;
		turns();

		for(int i = 0; i < this.turn.length; i++) {
			if(turn[i]) {x++;}
		}
		
		if(x==1 || x==3 || x==4) {return x;}
		else if(x==2 && (!(turn[0]&&turn[2]) && !(turn[1]&&turn[3]))) {return x;}  // ha kanyarnál van(nem egyenes úton)
		else if(x==2 && dir==Direction.up) {return x;}  //ha kijön a szellemlakból
		return 0;
	}
	
	/**
	* A szellemlakban való mozgásért felel
	*/
	public void ghostHouse() {
		
		if(waited<=0) {
			ghostMove();
		}
		if(this.pos_y<=195) {out=true;}
	}
	
	/**
	* A szellemek kirajzolását végzi
	* @param g - Graphics osztály a kirajzoláshoz
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
	* A szellemek sebezhetõvé agy sebezhetetlenné válik
	* @param b - sebezhetõ legyen-e a szellem
	*/
	public void setVulnerable(boolean b) {
		this.blue = b;
		this.vul=1000;
	}
	
	/**
	* A sebezhetõség idejének számolására van.
	* @return this.vuln - épp hol tart a visszaszámolás
	*/
	public double setInt() {
		this.vul -= 0.8;
		if(this.waited>=0){this.waited -= 0.5;}
		return this.vul;
	}
	
	/**
	* A szellemek halálakor visszatérnek a szellemlak fölé 
	* és pontot kap pacman.
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
	* A kezdõpozíciójára kerül a szellem,
	* és a megadott irányba állítódik be.
	* @param d - a kívánt haladási irány
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
	* A szellemek ai mozgása, ami távolságot számol a célpont és a szellem között, 
	* azt az irányt választja ami a legközelebb viszi a célponthoz, és haladhat is arra.
	* Ha valahol ez megegyezik két iránynál, akkor súlyozott sorrend van: fel bal le jobb
	* @param pos_x - a célpont x pozíciója
	* @param pos_y - a célpont y pozíciója
	*/
	public void target(int pos_x, int pos_y) {  //
		
		double[] distance = new double[4];
		
		if(cross()!=0) {  //kiszámítja az egyes elmozdulásokkal milyen messze lenne a célponttól
			
			distance[0]=Math.sqrt((pos_y - this.pos_y) * (pos_y - this.pos_y) + (pos_x - (this.pos_x-14)) * (pos_x - (this.pos_x-14)));
			distance[1]=Math.sqrt((pos_y - (this.pos_y-14)) * (pos_y - (this.pos_y-14)) + (pos_x - this.pos_x) * (pos_x - this.pos_x));
			distance[2]=Math.sqrt((pos_y - this.pos_y) * (pos_y - this.pos_y) + (pos_x - (this.pos_x+14)) * (pos_x - (this.pos_x+14)));
			distance[3]=Math.sqrt((pos_y - (this.pos_y+14)) * (pos_y - (this.pos_y+14)) + (pos_x - this.pos_x) * (pos_x - this.pos_x));
		}
		
		if(cross()!=1){  // ne tudjon megfordulni, csak ha az az egyetlen megoldás
			if(dir==Direction.left) {turn[2]=false;}
			if(dir==Direction.up) {turn[3]=false;}
			if(dir==Direction.right) {turn[0]=false;}
			if(dir==Direction.down) {turn[1]=false;}
		}
		
		double min = 0.0;
		int index = -1;
		int index2 = -1;  // ha egyezés van akkor kell, mert ilyenkor van sorrend: fel bal le jobb
		
		for(int i = 0; i < 4; i++) {
			if(min==0 && turn[i]) {min = distance[i]; index = i;}
			else if(distance[i]<min && turn[i]) {min = distance[i]; index = i;}
			else if(distance[i]==min) {index2=i;}
		}
		
		if(index2==-1) { //ha nincsen egyezés a távolságok között
			setDirection(index);
		}
		
		if(index2!=-1) { //ha nincsen egyezés a távolságok között
			if(index==1||index2==1) {setDirection(1);}
			else if(index==0||index2==0) {setDirection(0);}
			else if(index==3||index2==3) {setDirection(3);}
			else if(index==2||index2==2) {setDirection(2);}
		}
	}
}
