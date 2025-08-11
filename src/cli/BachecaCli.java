/*
 * @autor Lorenzo Santosuosso 20050494
 */

package cli;

import model.*;
import jbook.util.Input;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <h2>Classe BacheCli</h2>
 * <p>
 *  La classe {@code BachecaCli} contiene tutta la logica e le funzioni che servono per avviare la bacheca in modalità CLI
 * </p>
 */
public class BachecaCli {
    private Bacheca bacheca;
    private Utente utenteCorrente;

    /**
     * <h2>Costruttore di BachecaCLI.</h2>
     */
    public BachecaCli() {

    	this.bacheca = GestoreSalvataggi.caricaBacheca();
    	
    }

    /**
     * <h2>Avvia il programma.</h2>
     */
    public void start() {
        System.out.println("Benvenuto nella Bacheca!");

        login();

        boolean running = true;
        while (running) {
            menu();
            int scelta = Input.readInt("Seleziona un'opzione: ");
            switch (scelta) {
                case 1 -> inserisciAnnuncioVendita();
                case 2 -> inserisciAnnuncioAcquisto();
                case 3 -> cercaAnnunci();
                case 4 -> visualizzaAnnunci();
                case 5 -> rimuoviAnnuncio();
                case 6 -> pulisciBacheca();
                case 0 -> {
                    System.out.println("Arrivederci!");
                    GestoreSalvataggi.salvaBacheca(bacheca);
                    running = false;
                }
                default -> System.out.println("ERRORE: Scelta non valida!");
            }
        }
    }

    /**
     * <h2>Esegue il login dell'utente.</h2>
     */
    private void login() {
    	
    	List<Utente> utenti = GestoreSalvataggi.caricaUtenti();
    	
    	boolean running = true;
        while (running) {
        	try {
        		String email = Input.readString("Inserisci la tua email: ");
                Optional<Utente> utenteTrovato = utenti.stream()
                    .filter(u -> u.getMail().equalsIgnoreCase(email))
                    .findFirst();

                if (utenteTrovato.isPresent()) {
                    utenteCorrente = utenteTrovato.get();
                    System.out.println("Bentornato, " + utenteCorrente.getNome());
                } else {
                    String nome = Input.readString("Nuovo utente! Inserisci il tuo nome: ");
                    utenteCorrente = new Utente(nome, email);
                    utenti.add(utenteCorrente);
                    GestoreSalvataggi.salvaUtenti(utenti);
                    System.out.println("Registrazione completata. Benvenuto, " + nome);
                }
                
                running = false;
        	}catch(Exception e) {
        		System.out.println("ERRORE: "+ e.getMessage());
        	}
        }

    }

    /**
     * <h2>Mostra il menu principale.</h2>
     */
    private void menu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Inserisci annuncio di vendita");
        System.out.println("2. Inserisci annuncio di acquisto");
        System.out.println("3. Cerca annunci per parole chiave");
        System.out.println("4. Visualizza tutti gli annunci");
        System.out.println("5. Rimuovi un mio annuncio");
        System.out.println("6. Pulisci annunci scaduti");
        System.out.println("0. Esci");
    }

    /**
     * <h2>Inserisce un nuovo annuncio di vendita.</h2>
     */
    private void inserisciAnnuncioVendita() {
    	
    	AnnuncioVendita annuncio;
        String nomeArticolo = Input.readString("Nome articolo: ");
        double prezzo = Input.readDouble("Prezzo: ");
        Set<String> paroleChiave = leggiParoleChiave();
        
        // Chiedo se l'utente vuole inserire la data
        String risposta = Input.readString("Vuoi inserire una data di scadenza? (s/n): ").trim().toLowerCase();
        if (risposta.equals("s")) {
            try {
                int anno = Input.readInt("Anno di scadenza (es. 2025): ");
                int mese = Input.readInt("Mese di scadenza (1-12): ");
                int giorno = Input.readInt("Giorno di scadenza (1-31): ");
                LocalDate dataScadenza = LocalDate.of(anno, mese, giorno);

                if (dataScadenza.isBefore(LocalDate.now())) {
                    System.out.println("La data è nel passato. Verrà usata la scadenza automatica di 30 giorni.");
                    annuncio = new AnnuncioVendita(utenteCorrente, nomeArticolo, prezzo, paroleChiave);
                } else {
                    annuncio = new AnnuncioVendita(utenteCorrente, nomeArticolo, prezzo, paroleChiave, dataScadenza);
                }
            } catch (Exception e) {
                System.out.println("Data non valida. Verrà usata la scadenza automatica di 30 giorni.");
                annuncio = new AnnuncioVendita(utenteCorrente, nomeArticolo, prezzo, paroleChiave);
            }
        } else {
            // Usa il costruttore senza data
            annuncio = new AnnuncioVendita(utenteCorrente, nomeArticolo, prezzo, paroleChiave);
        }
        bacheca.addAnnuncio(annuncio);
        System.out.println("Annuncio di vendita aggiunto!");
    }

    /**
     * <h2>Inserisce un nuovo annuncio di acquisto e mostra i match.</h2>
     */
    private void inserisciAnnuncioAcquisto() {
        String nomeArticolo = Input.readString("Nome articolo: ");
        double prezzo = Input.readDouble("Prezzo massimo: ");
        Set<String> paroleChiave = leggiParoleChiave();

        AnnuncioAcquisto annuncio = new AnnuncioAcquisto(utenteCorrente, nomeArticolo, prezzo, paroleChiave);
        List<Annuncio> annunciTrovati = bacheca.inserisciAnnuncioAcquisto(annuncio);

        System.out.println("Annuncio di acquisto inserito.");
        System.out.println("Annunci di vendita che potrebbero interessarti:");
        for (Annuncio a : annunciTrovati) {
            System.out.println(a);
        }
    }

    /**
     * <h2>Cerca annunci tramite parole chiave.</h2>
     */
    private void cercaAnnunci() {
        Set<String> paroleChiave = leggiParoleChiave();
        List<Annuncio> annunciTrovati = bacheca.cercaPerParoleChiave(paroleChiave);

        if (annunciTrovati.isEmpty()) {
            System.out.println("Nessun annuncio trovato.");
        } else {
            System.out.println("Annunci trovati:");
            for (Annuncio a : annunciTrovati) {
                System.out.println(a);
            }
        }
    }

    /**
     * <h2>Visualizza tutti gli annunci presenti in bacheca.</h2>
     */
    private void visualizzaAnnunci() {
        System.out.println("Annunci attualmente in bacheca:");
        for (Annuncio a : bacheca) {
            System.out.println(a);
        }
    }

    /**
     * <h2>Rimuove un annuncio pubblicato dall'utente.</h2>
     */
    private void rimuoviAnnuncio() {
        int id = Input.readInt("Inserisci l'ID dell'annuncio da rimuovere: ");
        try {
            bacheca.rimuoviAnnuncio(id, utenteCorrente);
            System.out.println("Annuncio rimosso con successo.");
        } catch (Exception e) {
            System.out.println("ERRORE: " + e.getMessage());
        }
    }

    /**
     * <h2>Pulisce la bacheca rimuovendo gli annunci scaduti.</h2>
     */
    private void pulisciBacheca() {
        bacheca.pulisciBacheca();
        System.out.println("Bacheca ripulita dagli annunci scaduti.");
    }

    /**
     * <h2>Legge una serie di parole chiave dall'utente.</h2>
     * 
     * @return Set di parole chiave
     */
    private Set<String> leggiParoleChiave() {
        System.out.println("Inserisci parole chiave (premi invio su una riga vuota per terminare): ");
        Set<String> paroleChiave = new HashSet<>();
        while (true) {
            String parola = Input.readString("> ");
            if (parola.trim().isEmpty()) {
                break;
            }
            paroleChiave.add(parola.trim().toLowerCase());
        }
        return paroleChiave;
    }

}
