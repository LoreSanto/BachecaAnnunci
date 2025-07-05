/*
 * @autor Lorenzo Santosuosso 20050494
 */

import gui.BachecaGUI;
import model.Bacheca;
import model.GestoreSalvataggi;

public class MainGUI {
	
	
	public static Bacheca bacheca = new Bacheca();
	/**
	 * <h2>Start bachecaGUI</h2>
	 * 
	 * <p>
	 * 		Da qui faccio partire la bacheca in versione GUI
	 * </p>
	*/
	public static void main(String[] args) {

		bacheca = GestoreSalvataggi.caricaBacheca();
		
		new BachecaGUI(bacheca);
		

	}

}
