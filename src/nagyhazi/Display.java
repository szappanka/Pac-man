package nagyhazi;

import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
* A kijelzõért és a PAUSE gombért felel
* @author Szalka Panka - RITH1H
*/

public class Display {
	
	/**
	 * Az ablak alapja
	 */
	private JFrame frame;
	
	/**
	 * A vászon, amin a kirajzolás lesz
	 */
	private Canvas canvas;

	/**
	 * A PAUSE gomb
	 */
	private JButton btn;

	/**
	 * Az ablak szélessége
	 */
	private int width;

	/**
	 * Az ablak magassága
	 */
	private int height;
	
	/**
	 * Az ablak/játék címe
	 */
	private String title;
	
	/**
	 * Mutatja, hogy a gomb meg volt-e nyomva
	 */
	private boolean clicked = false;
	
	/**
	 * A Display konstruktora, ahol megadjuk az alapvetõ értékeket és 
	 * beállítódnak a megfelelõ	
	 * @param title Az ablak címe
	 * @param width Az ablak szélessége
	 * @param height Az ablak magassága
	 */
	public Display(String title, int width, int height) {
		this.title = title;
		this.width = width;
		this.height = height;
		
		create();
	}
	
	/**
	 * Beállítja a JFrame, Canvas és JButton megfelelõ értékeit
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
	 * A JButoont állítja a felhasználó által elérhetõvé vagy elérhetetlenné
	 * @param b - a felhasználó elérje-e a gombot
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
	 * @param b - lenyomták-e a gombot
	 */
	public void setClicked(boolean b) {this.clicked = b;}

}