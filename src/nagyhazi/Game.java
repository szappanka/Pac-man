package nagyhazi;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.*;

/**
* A játékért felelõs szál, ahol az adatok frissülnek 
* és kirajzolódnak a komponensek
* @author Szalka Panka - RITH1H
*/

public class Game extends Thread{
	
	/**
	 * A játékos által irányított pacman karakter
	 */
	private Pacman pm;

	/**
	 * Piros szellem
	 */
	private Blinky blinky;

	/**
	 * Rózsaszín szellem
	 */
	private Pinky pinky;

	/**
	 * Világoskék szellem
	 */
	private Inky inky;

	/**
	 * Narancssárga szellem
	 */
	private Clyde clyde;

	/**
	 * Az aktuális pálya
	 */
	private Level level1;

	/**
	 * Ha megy a játék true, amúgy false (nyerés és vesztés kivételével true)
	 */
	private boolean inGame = true;

	/**
	 * Ha valaki megállítja a játékot, true, amúgy false
	 */
	private boolean paused = false;

	/**
	 * Az ablak és értékei + PAUSE gomb
	 */
	private Display display;

	/**
	 * A graphics osztály a kirajzolást teszi lehetõvé
	 */
	private Graphics g;

	/**
	 * A kirajzolást segíti, létrehoz egy képernyõn kívüli képet
	 */
	private BufferStrategy bs;

	/**
	 * A nyilak, awsd és space billenyû lenyomásért felel
	 */
	private KeyInput ki;

	/**
	 * A játék pontszáma és játékvégi képernyõ
	 */
	private Score score;

	/**
	 * Ha egy energizert (nagy bogyót) megeszik pacman true, amúgy false
	 */
	static public boolean vulnerable;
	
	/**
	* A Game osztály konstruktora, beállítja a megfelelõ karaktereket, értékeket
	* @param t - az ablak neve
	* @param w - az ablak szélessége
	* @param h - az ablak magassága
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
	* A frissítésére felel, amikor a pálya és a karakterek 
	* helyzete vagy állapota változik.
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
	* A egész játék kirajzolásáért felel,
	* minden komponensnek meghívja a kirajzolás függvényét
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
	* A szál futása, ez mûködteti és ellenõrzi folyamatosan a játékot, mozgásokat, eredményeket
	*/
	public void run(){

		int fps = 300;   // megadja, hogy 1 mp-en belül mennyiszer frissüljön
		double timePerTick = 1000000000 / fps;	// 1 mp/fps
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while (inGame) {
			
			/*
			*ez a rész eredetileg nem volt, csak egy sleep(10) rész,
			*viszont így elvileg minden gépen azonos idõnként frissít
			*(nem saját kód részlet, hanem stackowerflow)
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
	* Azt az idõszakot kezeli, amikor pacman megeszik egy energizert és a szellemek segezhetõk lesznek.
	* Ha ennek lejár az ideje, akkor visszaváltoznak, illetve ha pacman megeszik egy szellemet, 
	* akkor a szellem sebezhetetlenül a kezdõpozíciójára ugrik.
	*/
	public void vuln() {
		
		if(vulnerable) {  //ha megevõdött az energizer minden szellem sebezhetõ lesz, ez kapcsolja be
			blinky.setVulnerable(true);
			pinky.setVulnerable(true);
			inky.setVulnerable(true);
			clyde.setVulnerable(true);
			vulnerable = false;
		}
		
		if(clyde.vul<=0 && clyde.blue) {  // ha a szellemek sebezhetõk, és lejár az idejük, akkor visszaállnak sebezhetetlenné
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
		
		blinky.setInt();  //a szellemek számlálói mennek folyamatosan
		pinky.setInt();
		inky.setInt();
		clyde.setInt();
	}
	
	/**
	* Vizsgálja a játék állását, ha valami válozás áll (nyerés/vesztés) új játékot kezd
	*/
	public void check() {		
		
		if(pm.getLives()==0) {  //ha meghal pacman
			pm.setInGame(false);
		}
		
		if(level1.checkWin()) {  //ha elfogynak a bogyók
			pm.setInGame(false);
		}
		
		while(!pm.getInGame() && !paused) {  //ha vége a játéknak, akkor új játékot kezd
			vulnerable = false;
			render();
			newGame();
		}
	}
	
	/**
	* A megállást kezeli (PAUSE), gombnyomásra megáll a játék,
	* még egy gombnyomásra pedig elindul
	*/
	public void pause() {
		
		if(!inGame && paused) {paused=false; System.out.print("idekellejönnöm ");}
		
		if(display.getClicked() && inGame) {
			if(paused) {paused = false;pm.setPaused(false);}
			else if(!paused) {paused = true;pm.setPaused(true);}
			display.setClicked(false);;
		}
	}
	
	/**
	* Új játékot indít, mindenkit a kezdõpozíciójára rak, 
	* és beállítja a megfelelõ értékeket az új kezdéshez
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