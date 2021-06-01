package nagyhazi;

import java.io.Serializable;

/**
* Egy nyertes játék adatait tartalmazza
* @author Szalka Panka - RITH1H
*/

public class OneScore implements Serializable, Comparable<OneScore>{
    
	private static final long serialVersionUID = 1L;
	
	/**
	* Egy nyertes játékos neve
	*/
	private String name;
	
	/**
	* Egy nyertes játékos pontszáma
	*/
    private int points;

    /**
	 * A OneScore konstruktora, ahol megadjuk a megfelelõ értékeket
	 * @param name - a játékos neve
	 * @param points - a játékos nyertes pontszáma
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
	 * OneScore-okat hasonlít össze a pontszámuk alapján,
	 * a magasabb pontszám van elõrébb
	 * @param o - másik OneScore egyed
	 * @return score-this.points
	 */
	@Override
	public int compareTo(OneScore o) {
		
		int score=((OneScore)o).getPoints();
		return score-this.points;
	}
}