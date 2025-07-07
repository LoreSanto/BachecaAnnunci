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

public class BachecaCli {
    private Bacheca bacheca;
    private Utente utenteCorrente;

    /**
     * Costruttore di BachecaCLI.
     */
    public BachecaCli() {

    	this.bacheca = GestoreSalvataggi.caricaBacheca();
    	
    }

    /**
     * Avvia il programma.
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
                default -> System.out.println("Scelta non valida!");
            }
        }
    }

    /**
     * Esegue il login dell'utente.
     */
    private void login() {
    	List<Utente> utenti = GestoreSalvataggi.caricaUtenti();

        String email = Input.readString("Inserisci la tua email: ");
        Optional<Utente> utenteTrovato = utenti.stream()
            .filter(u -> u.getEmail().equalsIgnoreCase(email))
            .findFirst();

        if (utenteTrovato.isPresent()) {
            utenteCorrente = utenteTrovato.get();
            System.out.println("Bentornato, " + utenteCorrente.getNome());
        } else {
            String nome = Input.readString("Nuovo utente! Inserisci il tuo nome: ");
            utenteCorrente = new Utente(email, nome);
            utenti.add(utenteCorrente);
            GestoreSalvataggi.salvaUtenti(utenti);
            System.out.println("Registrazione completata. Benvenuto, " + nome);
        }
    }

    /**
     * Mostra il menu principale.
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
     * Inserisce un nuovo annuncio di vendita.
     */
    private void inserisciAnnuncioVendita() {
        String nomeArticolo = Input.readString("Nome articolo: ");
        double prezzo = Input.readDouble("Prezzo: ");
        Set<String> paroleChiave = leggiParoleChiave();
        int anno = Input.readInt("Anno di scadenza (es. 2025): ");
        int mese = Input.readInt("Mese di scadenza (1-12): ");
        int giorno = Input.readInt("Giorno di scadenza (1-31): ");
        LocalDate dataScadenza = LocalDate.of(anno, mese, giorno);

        AnnuncioVendita annuncio = new AnnuncioVendita(utenteCorrente, nomeArticolo, prezzo, paroleChiave, dataScadenza);
        bacheca.aggiungiAnnuncio(annuncio);
        System.out.println("Annuncio di vendita aggiunto!");
    }

    /**
     * Inserisce un nuovo annuncio di acquisto e mostra i match.
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
     * Cerca annunci tramite parole chiave.
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
     * Visualizza tutti gli annunci presenti in bacheca.
     */
    private void visualizzaAnnunci() {
        System.out.println("Annunci attualmente in bacheca:");
        for (Annuncio a : bacheca) {
            System.out.println(a);
        }
    }

    /**
     * Rimuove un annuncio pubblicato dall'utente.
     */
    private void rimuoviAnnuncio() {
        int id = Input.readInt("Inserisci l'ID dell'annuncio da rimuovere: ");
        try {
            bacheca.rimuoviAnnuncio(id, utenteCorrente);
            System.out.println("Annuncio rimosso con successo.");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    /**
     * Pulisce la bacheca rimuovendo gli annunci scaduti.
     */
    private void pulisciBacheca() {
        bacheca.pulisciBacheca();
        System.out.println("Bacheca ripulita dagli annunci scaduti.");
    }

    /**
     * Legge una serie di parole chiave dall'utente.
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
