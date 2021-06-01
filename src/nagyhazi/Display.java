package nagyhazi;

import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
* A kijelz��rt �s a PAUSE gomb�rt felel
* @author Szalka Panka - RITH1H
*/

public class Display {
	
	/**
	 * Az ablak alapja
	 */
	private JFrame frame;
	
	/**
	 * A v�szon, amin a kirajzol�s lesz
	 */
	private Canvas canvas;

	/**
	 * A PAUSE gomb
	 */
	private JButton btn;

	/**
	 * Az ablak sz�less�ge
	 */
	private int width;

	/**
	 * Az ablak magass�ga
	 */
	private int height;
	
	/**
	 * Az ablak/j�t�k c�me
	 */
	private String title;
	
	/**
	 * Mutatja, hogy a gomb meg volt-e nyomva
	 */
	private boolean clicked = false;
	
	/**
	 * A Display konstruktora, ahol megadjuk az alapvet� �rt�keket �s 
	 * be�ll�t�dnak a megfelel�	
	 * @param title Az ablak c�me
	 * @param width Az ablak sz�less�ge
	 * @param height Az ablak magass�ga
	 */
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		create();
	}
	
	/**
	 * Be�ll�tja a JFrame, Canvas �s JButton megfelel� �rt�keit
	 */
	private void create() {

		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);


		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setFocusable(false);
		
		frame.add(canvas);
		
		btn = new JButton("PAUSE");
		btn.setSize(300, 200);
		btn.setBackground(Color.black);
	    btn.setForeground(Color.yellow);
	    
	    btn.addActionListener(new ActionListener() { 
	    	public void actionPerformed(ActionEvent e) {
	    		    clicked = true;
	    	}
	    });
	    btn.setFocusable(false);
		frame.add(btn, BorderLayout.SOUTH);
		
		frame.pack();
	}
	
	/**
	 * A JButoont �ll�tja a felhaszn�l� �ltal el�rhet�v� vagy el�rhetetlenn�
	 * @param b - a felhaszn�l� el�rje-e a gombot
	 */
	public void setVisibility(boolean b) {
		if(b) {btn.setVisible(true);btn.setEnabled(true);}
		else {btn.setVisible(false);btn.setEnabled(false);}
		
	}
	
	/**
	 * Visszaadja a Canvas-t
	 * @return canvas
	 */
	public Canvas getCanvas(){
		return canvas;
	}
	
	/**
	 * Visszaadja a JFrame-t
	 * @return canvas
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	/**
	 * A clicked gettere
	 * @return clicked
	 */
	public boolean getClicked() {return this.clicked;}

	/**
	 * A clicked settere
	 * @param b - lenyomt�k-e a gombot
	 */
	public void setClicked(boolean b) {this.clicked = b;}

}