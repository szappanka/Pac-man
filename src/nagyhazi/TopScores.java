package nagyhazi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
* A nyertesek toplistáját tartalmazza és kezeli
* @author Szalka Panka - RITH1H
*/

public class TopScores{
    
    /**
	 * A top eredmények listája
	 */
	private static ArrayList<OneScore> scores = new ArrayList<OneScore>();
    
    /**
	 * A maximum tárolt eredmények száma
	 */
    private int max = 5;
    
    /**
	 * A TopScores konstruktora, iniciáljuk az értékeket
	 */
    public TopScores() {
    	init();
    }
    
    /**
	 * Egy megnyert játék végén ez menti el azt az aktuálsi pontszámot,
	 * miután bekérte a nevet, majd elmenti egy file-ba is
	 * @param act - a nyertes pontszám
	 */
    public void save(int act) {
		
    	AddName an = new AddName();
		scores.add(new OneScore(an.getName(), act));
		
		Collections.sort(scores);		
		
		if(scores.size()>max) {scores.remove(max);}
		
		try {
			FileOutputStream fos = new FileOutputStream("savescores.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(scores);
			oos.close();
			fos.close();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
	 * Inicializálja a pályát, beolvassa a szerializált 
	 * eredményeket, amik már el voltak mentve
	 */
    public static void init(){
		
		try {
			FileInputStream fis = new FileInputStream("savescores.ser");
	        ObjectInputStream ois = new ObjectInputStream(fis);
	        scores = (ArrayList<OneScore>)ois.readObject();
	        ois.close();
	        fis.close();
	    }catch(IOException ioe){
	         ioe.printStackTrace();
		}catch(ClassNotFoundException c){
            System.out.println("Class not found");
            c.printStackTrace();
        }
	}
    
    /**
	 * Visszaadja a OneScore-okból álló ArrayList-et
	 * @return scores - a toplista eredményei
	 */
    public ArrayList<OneScore> getList(){
    	return scores;
    }
    
    /**
	 * Visszaadja a maximum tárolt eredmények számát
	 * @return max
	 */
    public int getMax() {return this.max;}
}
