/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

import java.util.Set;

public class AnnuncioAcquisto extends Annuncio {

    /**
     * Costruttore di AnnuncioAcquisto.
     * @param utente Utente che vuole acquistare
     * @param nomeArticolo Nome dell'articolo
     * @param prezzo Prezzo di acquisto
     * @param paroleChiave Parole chiave associate
     */
    public AnnuncioAcquisto(Utente utente, String nomeArticolo, double prezzo, Set<String> paroleChiave) {
        super(utente, nomeArticolo, prezzo, paroleChiave);
    }
    
    @Override
    public String toString() {
        return super.toString() + " | Acquisto " ;
    }

    @Override
    public boolean isScaduto() {
        return false; // Un annuncio di acquisto non scade
    }
}
