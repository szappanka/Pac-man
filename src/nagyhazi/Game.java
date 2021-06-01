package nagyhazi;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;

/**
* A j�t�k�rt felel�s sz�l, ahol az adatok friss�lnek 
* �s kirajzol�dnak a komponensek
* @author Szalka Panka - RITH1H
*/

public class Game extends Thread{
	
	/**
	 * A j�t�kos �ltal ir�ny�tott pacman karakter
	 */
	private Pacman pm;

	/**
	 * Piros szellem
	 */
	private Blinky blinky;

	/**
	 * R�zsasz�n szellem
	 */
	private Pinky pinky;

	/**
	 * Vil�gosk�k szellem
	 */
	private Inky inky;

	/**
	 * Narancss�rga szellem
	 */
	private Clyde clyde;

	/**
	 * Az aktu�lis p�lya
	 */
	private Level level1;

	/**
	 * Ha megy a j�t�k true, am�gy false (nyer�s �s veszt�s kiv�tel�vel true)
	 */
	private boolean inGame = true;

	/**
	 * Ha valaki meg�ll�tja a j�t�kot, true, am�gy false
	 */
	private boolean paused = false;

	/**
	 * Az ablak �s �rt�kei + PAUSE gomb
	 */
	private Display display;

	/**
	 * A graphics oszt�ly a kirajzol�st teszi lehet�v�
	 */
	private Graphics g;

	/**
	 * A kirajzol�st seg�ti, l�trehoz egy k�perny�n k�v�li k�pet
	 */
	private BufferStrategy bs;

	/**
	 * A nyilak, awsd �s space billeny� lenyom�s�rt felel
	 */
	private KeyInput ki;

	/**
	 * A j�t�k pontsz�ma �s j�t�kv�gi k�perny�
	 */
	private Score score;

	/**
	 * Ha egy energizert (nagy bogy�t) megeszik pacman true, am�gy false
	 */
	static public boolean vulnerable;
	
	/**
	* A Game oszt�ly konstruktora, be�ll�tja a megfelel� karaktereket, �rt�keket
	* @param t - az ablak neve
	* @param w - az ablak sz�less�ge
	* @param h - az ablak magass�ga
	*/
	public Game(String t, int w, int h){
		
		level1 = new Level();
		
		this.pm = new Pacman(247, 403, 1, level1, 1);
		this.blinky = new Blinky(246, 195, 1, level1, pm, 2);
		this.pinky = new Pinky(221, 247, 1, level1, pm, 2);
		this.inky = new Inky(247, 247, 1, level1, pm, 2, blinky);
		this.clyde = new Clyde(273, 247, 1, level1, pm, 2);
		
		this.ki = new KeyInput(pm);
		this.display = new Display(t, w, h);
		display.getFrame().addKeyListener(ki);
		score = new Score(pm, display);
	}
	
	/**
	* A friss�t�s�re felel, amikor a p�lya �s a karakterek 
	* helyzete vagy �llapota v�ltozik.
	*/
	public void update() {
		
		score.update();
		
		pm.setAct();
		blinky.setAct();
		pinky.setAct();
		inky.setAct();
		clyde.setAct();
		
		if(pm.getAct()==0) {pm.pmMove(); pm.eat();}
		if(blinky.getAct()==0) {blinky.ai();}
		if(pinky.getAct()==0) {pinky.ai();}
		if(inky.getAct()==0) {inky.ai();}
		if(clyde.getAct()==0) {clyde.ai();}
	}
	
	/**
	* A eg�sz j�t�k kirajzol�s��rt felel,
	* minden komponensnek megh�vja a kirajzol�s f�ggv�ny�t
	*/
	public void render() {
		
		bs = display.getCanvas().getBufferStrategy();
		
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();
				
		level1.render(g);
		
		pm.render(g);
		blinky.renderG(g);
		pinky.renderG(g);
		inky.renderG(g);
		clyde.renderG(g);
		
		score.render(g);
		
		bs.show();
		g.dispose();
		
	}
	
	/**
	* A sz�l fut�sa, ez m�k�dteti �s ellen�rzi folyamatosan a j�t�kot, mozg�sokat, eredm�nyeket
	*/
	public void run(){

		int fps = 300;   // megadja, hogy 1 mp-en bel�l mennyiszer friss�lj�n
		double timePerTick = 1000000000 / fps;	// 1 mp/fps
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while (inGame) {
			
			/*
			*ez a r�sz eredetileg nem volt, csak egy sleep(10) r�sz,
			*viszont �gy elvileg minden g�pen azonos id�nk�nt friss�t
			*(nem saj�t k�d r�szlet, hanem stackowerflow)
			*/
			
			now = System.nanoTime();
			delta += (now-lastTime)/timePerTick;
			timer +=now - lastTime;
			lastTime = now;
			
			if(delta >= 1) {
			
				pause();
				check();
				vuln();
				
				
				if(!paused) {
					
					update();
					render();					
				}
				ticks++;
				delta--;
			}
			
			if (timer>= 1000000000) {
				ticks = 0;
				timer = 0;
			}
		}
	}
	
	/**
	* Azt az id�szakot kezeli, amikor pacman megeszik egy energizert �s a szellemek segezhet�k lesznek.
	* Ha ennek lej�r az ideje, akkor visszav�ltoznak, illetve ha pacman megeszik egy szellemet, 
	* akkor a szellem sebezhetetlen�l a kezd�poz�ci�j�ra ugrik.
	*/
	public void vuln() {
		
		if(vulnerable) {  //ha megev�d�tt az energizer minden szellem sebezhet� lesz, ez kapcsolja be
			blinky.setVulnerable(true);
			pinky.setVulnerable(true);
			inky.setVulnerable(true);
			clyde.setVulnerable(true);
			vulnerable = false;
		}
		
		if(clyde.vul<=0 && clyde.blue) {  // ha a szellemek sebezhet�k, �s lej�r az idej�k, akkor vissza�llnak sebezhetetlenn�
			clyde.setVulnerable(false);
		}
		
		if(blinky.vul<=0 && blinky.blue) {
			blinky.setVulnerable(false);
		}
		
		if(pinky.vul<=0 && pinky.blue) {
			pinky.setVulnerable(false);
		}
		
		if(inky.vul<=0 && inky.blue) {
			inky.setVulnerable(false);
		}
		
		blinky.setInt();  //a szellemek sz�ml�l�i mennek folyamatosan
		pinky.setInt();
		inky.setInt();
		clyde.setInt();
	}
	
	/**
	* Vizsg�lja a j�t�k �ll�s�t, ha valami v�loz�s �ll (nyer�s/veszt�s) �j j�t�kot kezd
	*/
	public void check() {		
		
		if(pm.getLives()==0) {  //ha meghal pacman
			pm.setInGame(false);
		}
		
		if(level1.checkWin()) {  //ha elfogynak a bogy�k
			pm.setInGame(false);
		}
		
		while(!pm.getInGame() && !paused) {  //ha v�ge a j�t�knak, akkor �j j�t�kot kezd
			vulnerable = false;
			render();
			newGame();
		}
	}
	
	/**
	* A meg�ll�st kezeli (PAUSE), gombnyom�sra meg�ll a j�t�k,
	* m�g egy gombnyom�sra pedig elindul
	*/
	public void pause() {
		
		if(!inGame && paused) {paused=false; System.out.print("idekellej�nn�m ");}
		
		if(display.getClicked() && inGame) {
			if(paused) {paused = false;pm.setPaused(false);}
			else if(!paused) {paused = true;pm.setPaused(true);}
			display.setClicked(false);;
		}
	}
	
	/**
	* �j j�t�kot ind�t, mindenkit a kezd�poz�ci�j�ra rak, 
	* �s be�ll�tja a megfelel� �rt�keket az �j kezd�shez
	*/
	public void newGame() {
		
		pm.setFirstPos(0);
		pm.setPaused(false);
		paused=false;
		blinky.setFirstPos(Direction.left);
		pinky.setFirstPos(Direction.right);
		inky.setFirstPos(Direction.up);
		clyde.setFirstPos(Direction.left);
		
		try {
			level1.createLevel("palya.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	* A paused gettere
	* @return paused
	*/
	public boolean getPaused() {return this.paused;}
	
	/**
	* Az inGame gettere
	* @return inGame
	*/
	public boolean getInGame() {return this.inGame;}
	
}