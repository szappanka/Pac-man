package nagyhazi;

import java.io.Serializable;

/**
* Egy nyertes j�t�k adatait tartalmazza
* @author Szalka Panka - RITH1H
*/

public class OneScore implements Serializable, Comparable<OneScore>{
    
	private static final long serialVersionUID = 1L;
	
	/**
	* Egy nyertes j�t�kos neve
	*/
	private String name;
	
	/**
	* Egy nyertes j�t�kos pontsz�ma
	*/
    private int points;

    /**
	 * A OneScore konstruktora, ahol megadjuk a megfelel� �rt�keket
	 * @param name - a j�t�kos neve
	 * @param points - a j�t�kos nyertes pontsz�ma
	 */
    public OneScore(String name, int points) {
        this.name = name;
        this.points = points;
    }
    
    /**
	 * Visszaadja a name-t
	 * @return name
	 */
    public String getName() {
        return name;
    }
    
    /**
	 * Visszaadja a points-ot
	 * @return points
	 */
    public int getPoints() {
        return points;
    }
    
    /**
	 * OneScore-okat hasonl�t �ssze a pontsz�muk alapj�n,
	 * a magasabb pontsz�m van el�r�bb
	 * @param o - m�sik OneScore egyed
	 * @return score-this.points
	 */
	@Override
	public int compareTo(OneScore o) {
		
		int score=((OneScore)o).getPoints();
		return score-this.points;
	}
}