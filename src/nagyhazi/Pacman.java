package nagyhazi;

import java.util.Arrays;

/**
* A j�t�k f� karaktere, amit a j�t�kos ir�ny�t: pacman
* @author Szalka Panka - RITH1H
*/

public class Pacman extends Individual{
	
	/**
	* Pacman �leteinek sz�ma
	*/
	private int lives;
	
	/**
	* Pacman pontsz�ma
	*/
	private int score = 0;
	
	/**
	* Ha �pp a topscore van megtekintve true, am�gy false
	*/
	private boolean tops = false;
	
	/**
	* ha elmentett�k m�r az aktu�lis nyertes pontsz�m�t, akkor true, am�gy false
	*/
	private boolean save_bool = false;
	
	/**
	* Ha pacman j�t�kban van true, am�gy false
	*/
	private boolean inGame = true;
	
	/**
	* Ha a j�t�k meg van �ll�tva, akkor true, am�gy false
	*/
	private boolean paused = false;
	
	/**
	 * Pacman konstruktora. Be�ll�tja a megfelel� �rt�keit a j�t�kkezd�shez
	 * @param x - kezd� x kordin�ta
	 * @param y - kezd� y kordin�ta
	 * @param sp - a karakter sebess�ge
	 * @param l - az aktu�lis p�lya
	 * @param o - a lass�t�si m�rt�ket jelzi
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
	 * Pacman mozg�s��rt felel, ha a nyilakkar/awsd-vel be�ll�tjuk a k�v�nt ir�nyt, 
	 * akkor a k�vetkez� lehet�s�gn�l arra fordul
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
	 * Pacman bogy� ev�s��rt felel, ha egy adott mez� k�zep�n �ll �s van rajta bogy�, 
	 * akkor azt megeszi �s pontot is kap
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
	 * Ha pacman meghal, a kiindul� poz�ci�j�ra ker�l, �s egy �letet elvesz�t
	 */
	public void death() {
		if(lives > 0){
			this.lives--;
		}
		this.setFirstPos(0);
	}
	
	/**
	 * A score gettere
	 * @return score - pacman aktu�lis pontsz�ma
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * A score settere
	 * @param i - pacman pontsz�m�t erre �ll�tja be
	 */
	public void setScore(int i) {
		this.score=i;
	}
	
	/**
	 * A lives gettere
	 * @return lives - pacman aktu�lis �leteinek sz�ma
	 */
	public int getLives() {
		return this.lives;
	}
	
	/**
	 * A lives settere
	 * @param l - h�ny �letre �ll�tsuk pacmant
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
	 * @param l - ha el lett mentve true, am�gy false
	 */
	public void setSb(boolean l) {
		this.save_bool = l;
	}
	
	/**
	 * A inGame gettere
	 * @return inGame - j�tkban van-e pacman 
	 */
	public boolean getInGame() {
		return this.inGame;
	}
	
	/**
	 * A inGame settere
	 * @param l - megadjuk j�t�kban van-e
	 */
	public void setInGame(boolean l) {
		this.inGame = l;
	}
	
	/**
	 * A paused gettere
	 * @return paused - megkapjuk meg van-e �ll�tva a j�t�k
	 */
	public boolean getPaused() {
		return this.paused;
	}
	
	/**
	 * A paused settere
	 * @param l - megadjuk meg van-e �ll�tva a j�t�k
	 */
	public void setPaused(boolean l) {
		this.paused = l;
	}
	
	/**
	 * A tops gettere
	 * @return tops - megkapjuk �pp a toplist�t n�zz�k-e
	 */
	public boolean getTops() {
		return tops;
	}
	
	/**
	 * A tops settere
	 * @param l - megadjuk �pp a toplist�t n�zz�k-e
	 */
	public void setTops(boolean l) {
		this.tops = l;
	}
}
