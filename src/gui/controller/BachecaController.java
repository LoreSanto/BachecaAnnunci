package gui.controller;

import gui.vista.BachecaGUI;
import model.*;

import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

public class BachecaController {
    private Bacheca bacheca;
    private Utente utente;
    private BachecaGUI view;

    public BachecaController(Bacheca bacheca, Utente utente, BachecaGUI view) {
        this.bacheca = bacheca;
        this.utente = utente;
        this.view = view;
    }

    public void aggiornaAnnunci() {
        StringBuilder sb = new StringBuilder();
        for (Annuncio a : bacheca) {
            sb.append(a).append("\n");
        }
        view.mostraAnnunci(sb.toString());
    }

    public void inserisciAnnuncioVendita() {
        String nome = JOptionPane.showInputDialog(view, "Nome articolo:");
        double prezzo = Double.parseDouble(JOptionPane.showInputDialog(view, "Prezzo:"));
        Set<String> parole = leggiParoleChiave();
        
        //Nel caso la data inserita non sia valida/non si inserisca, mi da la scadenza dopo 30 giorni dall'inserimento
        String dataStr = JOptionPane.showInputDialog(view, "Data di scadenza (formato YYYY-MM-DD):");
        java.time.LocalDate dataScadenza;
        try {
            dataScadenza = java.time.LocalDate.parse(dataStr);
        } catch (Exception e) {
            dataScadenza = java.time.LocalDate.now().plusDays(30);
        }
        
        AnnuncioVendita a = new AnnuncioVendita(utente, nome, prezzo, parole, dataScadenza);
        bacheca.aggiungiAnnuncio(a);
        aggiornaAnnunci();
    }

    public void inserisciAnnuncioAcquisto() {
        String nome = JOptionPane.showInputDialog(view, "Nome articolo:");
        double prezzo = Double.parseDouble(JOptionPane.showInputDialog(view, "Prezzo massimo:"));
        Set<String> parole = leggiParoleChiave();
        AnnuncioAcquisto a = new AnnuncioAcquisto(utente, nome, prezzo, parole);
        bacheca.inserisciAnnuncioAcquisto(a);
        aggiornaAnnunci();
    }

    public void cercaAnnunci() {
        Set<String> parole = leggiParoleChiave();
        var risultati = bacheca.cercaPerParoleChiave(parole);
        if (risultati.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nessun annuncio trovato.");
        } else {
            StringBuilder sb = new StringBuilder("Annunci trovati:\n");
            for (Annuncio a : risultati) sb.append(a).append("\n");
            JOptionPane.showMessageDialog(view, sb.toString());
        }
    }

    public void rimuoviAnnuncio() {
        int id = Integer.parseInt(JOptionPane.showInputDialog(view, "ID da rimuovere:"));
        try {
            bacheca.rimuoviAnnuncio(id, utente);
            aggiornaAnnunci();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Errore: " + e.getMessage());
        }
    }

    public void pulisciBacheca() {
        bacheca.pulisciBacheca();
        aggiornaAnnunci();
    }

    public void salvaEdEsci() {
        GestoreSalvataggi.salvaBacheca(bacheca);
        JOptionPane.showMessageDialog(view, "Dati salvati.");
        System.exit(0);
    }

    private Set<String> leggiParoleChiave() {
        Set<String> parole = new HashSet<>();
        while (true) {
            String parola = JOptionPane.showInputDialog(view, "Parola chiave (invio per finire):");
            if (parola == null || parola.isBlank()) break;
            parole.add(parola.trim().toLowerCase());
        }
        return parole;
    }
}
