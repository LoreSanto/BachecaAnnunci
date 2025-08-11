/*
 * @autor Lorenzo Santosuosso 20050494
 */

import cli.BachecaCli;

public class MainCli {
	
	/**
	 * <h2>Start bachecaCLI</h2>
	 * 
	 * <p>
	 * 		Da qui faccio partire la bacheca in versione CLI
	 * </p>
	*/
	public static void main(String[] args) {
    	BachecaCli cli = new BachecaCli();
        cli.start();
    }
	
}
