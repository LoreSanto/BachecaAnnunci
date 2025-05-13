/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

import java.time.LocalDate;
import java.util.Set;

public class AnnuncioVendita extends Annuncio {
	
    private LocalDate dataScadenza;

    /**
     * <h2>Costruttore di AnnuncioVendita.</h2>
     * <p>
     * All'interno vengono inseriti i parametri che rappresentano il singolo annuncio per la vendita.
     * </p>
     * 
     * @param utente Utente che vende
     * @param nomeArticolo Nome dell'articolo
     * @param prezzo Prezzo di vendita
     * @param paroleChiave Parole chiave associate
     * @param dataScadenza Data di scadenza della vendita
     * @throws IllegalArgumentException generato se la data di scadenta è null, quindi non valida
     */
    public AnnuncioVendita(Utente utente, String nomeArticolo, double prezzo, Set<String> paroleChiave, LocalDate dataScadenza) {
        super(utente, nomeArticolo, prezzo, paroleChiave);
        if (dataScadenza == null) {
            throw new IllegalArgumentException("Data di scadenza non può essere nulla.");
        }
        this.dataScadenza = dataScadenza;
    }

    /**
     * Restituisce la data di scadenza dell'annuncio.
     * 
     * @return Data di scadenza
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
