package nagyhazi;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
* A nyertesek toplist�j�t tartalmazza �s kezeli
* @author Szalka Panka - RITH1H
*/

public class TopScores{
    
    /**
	 * A top eredm�nyek list�ja
	 */
	private static ArrayList<OneScore> scores = new ArrayList<OneScore>();
    
    /**
	 * A maximum t�rolt eredm�nyek sz�ma
	 */
    private int max = 5;
    
    /**
	 * A TopScores konstruktora, inici�ljuk az �rt�keket
	 */
    public TopScores() {
    	init();
    }
    
    /**
	 * Egy megnyert j�t�k v�g�n ez menti el azt az aktu�lsi pontsz�mot,
	 * miut�n bek�rte a nevet, majd elmenti egy file-ba is
	 * @param act - a nyertes pontsz�m
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
	 * Inicializ�lja a p�ly�t, beolvassa a szerializ�lt 
	 * eredm�nyeket, amik m�r el voltak mentve
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
	 * Visszaadja a OneScore-okb�l �ll� ArrayList-et
	 * @return scores - a toplista eredm�nyei
	 */
    public ArrayList<OneScore> getList(){
    	return scores;
    }
    
    /**
	 * Visszaadja a maximum t�rolt eredm�nyek sz�m�t
	 * @return max
	 */
    public int getMax() {return this.max;}
}
