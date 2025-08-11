/*
 * @author Lorenzo Santosuosso 20050494
 */

package model;

import java.time.LocalDate;
import java.util.Set;

/**
 * <h2>Classe AnnuncioVendita</h2>
 * <p>
 * Estende la classe astratta {@code Annuncio} per rappresentare un annuncio di vendita.<br>
 * L'utente specifica il nome dell'articolo in vendita, il prezzo richiesto, le parole chiave associate e la data di scadenza.<br>
 * Vi è un secondo costruttore che permette di inserire un articolo da vedere senza inserire la data di scadenza. In questo caso quest'ultima verrà inserita in automatico a 30 gg.
 * </p>
 */
public class AnnuncioVendita extends Annuncio {

    /** Data di scadenza dell'annuncio di vendita */
    private LocalDate dataScadenza;

    /**
     * <h2>Costruttore di AnnuncioVendita</h2>
     * <p>
     * Crea un annuncio di vendita con i parametri specificati.
     * </p>
     *
     * @param utente        Utente che mette in vendita l'articolo
     * @param nomeArticolo  Nome dell'articolo in vendita
     * @param prezzo        Prezzo richiesto
     * @param paroleChiave  Parole chiave associate all'annuncio
     * @param dataScadenza  Data di scadenza dell'annuncio
     * @throws IllegalArgumentException se {@code dataScadenza} è {@code null} o precedente alla data odierna
     */
    public AnnuncioVendita(Utente utente, String nomeArticolo, double prezzo, Set<String> paroleChiave, LocalDate dataScadenza) {
        super(utente, nomeArticolo, prezzo, paroleChiave);
        if (dataScadenza == null) {
            throw new IllegalArgumentException("La data di scadenza non può essere nulla.");
        }
        /*Per eventualmente non poter far inserire una data che sia precedente a quella odierna
        if (dataScadenza.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data di scadenza non può essere nel passato.");
        }*/
        this.dataScadenza = dataScadenza;
    }
    
    /**
     * <h2>Costruttore di AnnuncioVendita senza scadenza</h2>
     * <p>
     * Crea un annuncio di vendita con i parametri specificati.
     * <br>
     * In questo caso la data di scadenza viene assegnata in automatico a 30 gg da quando è stato inserito l'articolo
     * </p>
     *
     * @param utente        Utente che mette in vendita l'articolo
     * @param nomeArticolo  Nome dell'articolo in vendita
     * @param prezzo        Prezzo richiesto
     * @param paroleChiave  Parole chiave associate all'annuncio
     */
    public AnnuncioVendita(Utente utente, String nomeArticolo, double prezzo, Set<String> paroleChiave) {
        this(utente, nomeArticolo, prezzo, paroleChiave, LocalDate.now().plusDays(30));
    }

    /**
     * <h2>Restituisce la data di scadenza dell'annuncio.</h2>
     *
     * @return la data di scadenza
     */
    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    @Override
    public boolean isScaduto() {
        return LocalDate.now().isAfter(dataScadenza);
    }

    @Override
    public String toString() {
        return super.toString() + " | Vendita | Scadenza: " + dataScadenza;
    }
}
