package nagyhazi;

import java.util.Arrays;

/**
* A játék fõ karaktere, amit a játékos irányít: pacman
* @author Szalka Panka - RITH1H
*/

public class Pacman extends Individual{
	
	/**
	* Pacman életeinek száma
	*/
	private int lives;
	
	/**
	* Pacman pontszáma
	*/
	private int score = 0;
	
	/**
	* Ha épp a topscore van megtekintve true, amúgy false
	*/
	private boolean tops = false;
	
	/**
	* ha elmentették már az aktuális nyertes pontszámát, akkor true, amúgy false
	*/
	private boolean save_bool = false;
	
	/**
	* Ha pacman játékban van true, amúgy false
	*/
	private boolean inGame = true;
	
	/**
	* Ha a játék meg van állítva, akkor true, amúgy false
	*/
	private boolean paused = false;
	
	/**
	 * Pacman konstruktora. Beállítja a megfelelõ értékeit a játékkezdéshez
	 * @param x - kezdõ x kordináta
	 * @param y - kezdõ y kordináta
	 * @param sp - a karakter sebessége
	 * @param l - az aktuális pálya
	 * @param o - a lassítási mértéket jelzi
	 */
	public Pacman(int x, int y, int sp, Level l, int o) {

		this.firstpos_x = x;
		this.firstpos_y = y;
		
		this.pos_x = x;
		this.pos_y = y;
		
		this.speed = sp;
		this.dir = Direction.left;
		this.saved = Direction.left;
		this.turn = new boolean[4];
		Arrays.fill(turn, false);
		
		this.level = l;	
		loadImages("pacman", "pacman\\pm_zart.png");
		setLives(3);
		this.own = o;
		this.actual = o;
	}
	
	
	/**
	 * Pacman mozgásáért felel, ha a nyilakkar/awsd-vel beállítjuk a kívánt irányt, 
	 * akkor a következõ lehetõségnél arra fordul
	 */
	public void pmMove() {
		
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
	 * Pacman bogyó evéséért felel, ha egy adott mezõ közepén áll és van rajta bogyó, 
	 * akkor azt megeszi és pontot is kap
	 */
	public void eat(){
		
		Field help = level.getField(this.pos_x, this.pos_y);
		
		if((help.x + 13 == this.pos_x) && (help.y + 13 == this.pos_y)) {
			if(help.getPoints() == Points.small) {
				level.setEaten(this.pos_x, this.pos_y);
				score += 100;
			}
			
			if(help.getPoints() == Points.energizer) {
				level.setEaten(this.pos_x, this.pos_y);
				Game.vulnerable=true;
				score += 150;
			}
		}
	}
	
	/**
	 * Ha pacman meghal, a kiinduló pozíciójára kerül, és egy életet elveszít
	 */
	public void death() {
		if(lives > 0){
			this.lives--;
		}
		this.setFirstPos(0);
	}
	
	/**
	 * A score gettere
	 * @return score - pacman aktuális pontszáma
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * A score settere
	 * @param i - pacman pontszámát erre állítja be
	 */
	public void setScore(int i) {
		this.score=i;
	}
	
	/**
	 * A lives gettere
	 * @return lives - pacman aktuális életeinek száma
	 */
	public int getLives() {
		return this.lives;
	}
	
	/**
	 * A lives settere
	 * @param l - hány életre állítsuk pacmant
	 */
	public void setLives(int l) {
		this.lives = l;
	}
	
	/**
	 * A save_bool gettere
	 * @return save_bool - el van-e mentve a score
	 */
	public boolean getSb() {
		return this.save_bool;
	}
	
	/**
	 * A save_bool settere
	 * @param l - ha el lett mentve true, amúgy false
	 */
	public void setSb(boolean l) {
		this.save_bool = l;
	}
	
	/**
	 * A inGame gettere
	 * @return inGame - játkban van-e pacman 
	 */
	public boolean getInGame() {
		return this.inGame;
	}
	
	/**
	 * A inGame settere
	 * @param l - megadjuk játékban van-e
	 */
	public void setInGame(boolean l) {
		this.inGame = l;
	}
	
	/**
	 * A paused gettere
	 * @return paused - megkapjuk meg van-e állítva a játék
	 */
	public boolean getPaused() {
		return this.paused;
	}
	
	/**
	 * A paused settere
	 * @param l - megadjuk meg van-e állítva a játék
	 */
	public void setPaused(boolean l) {
		this.paused = l;
	}
	
	/**
	 * A tops gettere
	 * @return tops - megkapjuk épp a toplistát nézzük-e
	 */
	public boolean getTops() {
		return tops;
	}
	
	/**
	 * A tops settere
	 * @param l - megadjuk épp a toplistát nézzük-e
	 */
	public void setTops(boolean l) {
		this.tops = l;
	}
}
