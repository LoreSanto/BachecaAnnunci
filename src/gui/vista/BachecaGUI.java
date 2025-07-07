/*
 * @autor Lorenzo Santosuosso 20050494
 */

package gui.vista;

import gui.controller.BachecaController;
import model.*;

import javax.swing.*;
import java.awt.*;

public class BachecaGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
    private JTextArea areaAnnunci;
    private BachecaController controller;

    /**
     * <h2>Carico la bacheca</h2>
     * 
     * @param bacheca presa da i salvataggi (se ci sono)
     * @param utente inserito precedentemente dal login
     */
    public BachecaGUI(Bacheca bacheca, Utente utente) {
        super("Bacheca Annunci");
        this.controller = new BachecaController(bacheca, utente, this);

        setLayout(new BorderLayout());

        //Pulsanti
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6, 1, 5, 5));

        JButton btnInserisciVendita = new JButton("Inserisci Vendita");
        JButton btnInserisciAcquisto = new JButton("Inserisci Acquisto");
        JButton btnCerca = new JButton("Cerca");
        JButton btnRimuovi = new JButton("Rimuovi");
        JButton btnPulisci = new JButton("Pulisci");
        JButton btnSalvaEsci = new JButton("Salva ed Esci");

        btnInserisciVendita.addActionListener(e -> controller.inserisciAnnuncioVendita());
        btnInserisciAcquisto.addActionListener(e -> controller.inserisciAnnuncioAcquisto());
        btnCerca.addActionListener(e -> controller.cercaAnnunci());
        btnRimuovi.addActionListener(e -> controller.rimuoviAnnuncio());
        btnPulisci.addActionListener(e -> controller.pulisciBacheca());
        btnSalvaEsci.addActionListener(e -> controller.salvaEdEsci());

        leftPanel.add(btnInserisciVendita);
        leftPanel.add(btnInserisciAcquisto);
        leftPanel.add(btnCerca);
        leftPanel.add(btnRimuovi);
        leftPanel.add(btnPulisci);
        leftPanel.add(btnSalvaEsci);

        add(leftPanel, BorderLayout.WEST);

        //Annunci
        areaAnnunci = new JTextArea();
        areaAnnunci.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaAnnunci);
        add(scrollPane, BorderLayout.CENTER);

        controller.aggiornaAnnunci();

        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Restituisce gli annunci
     */
    public void mostraAnnunci(String testo) {
        areaAnnunci.setText(testo);
    }
    
    /**
     * Restituisce la bacheca
     */
    public JFrame getFrame() {
        return this;
    }
}
