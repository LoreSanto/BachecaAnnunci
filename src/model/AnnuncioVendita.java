/*
 * @author Lorenzo Santosuosso 20050494
 */

package model;

import java.time.LocalDate;
import java.util.Set;

/**
 * <h2>Classe AnnuncioVendita</h2>
 * <p>
 * Estende la classe astratta {@code Annuncio} per rappresentare un annuncio di vendita.
 * L'utente specifica il nome dell'articolo in vendita, il prezzo richiesto, le parole chiave associate e la data di scadenza.
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
        if (dataScadenza.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La data di scadenza non può essere nel passato.");
        }
        this.dataScadenza = dataScadenza;
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
