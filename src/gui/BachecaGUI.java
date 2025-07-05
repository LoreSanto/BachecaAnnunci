/*
 * @autor Lorenzo Santosuosso 20050494
 */

package gui;

import model.*;
import javax.swing.JFrame;

public class BachecaGUI extends JFrame{
	
	public BachecaGUI(Bacheca bacheca) {
		setTitle("Bacheca Annunci");
        setSize(800,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}

}
