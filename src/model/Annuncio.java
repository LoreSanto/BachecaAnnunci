/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

import java.util.Set;//Utilizzata al posto di List per definire insieme di oggetti che non ammette dei duplicati

public abstract class Annuncio {
	
	private static int nextId = 1;

    protected int id;
    protected Utente utente;
    protected String nomeArticolo;
    protected double prezzo;
    protected Set<String> paroleChiave;

    /**
     * <h2>Costruttore di Annuncio.</h2>
     * <p>
     * All'interno vengono inseriti i parametri che rappresentano il singolo annuncio.
     * 
     * @param utente Utente che ha creato l'annuncio
     * @param nomeArticolo Nome dell'articolo
     * @param prezzo Prezzo dell'articolo
     * @param paroleChiave Insieme di parole chiave associate
     * @throws IllegalArgumentException generato se vi è null utente o nomeArticolo o parolechiave
     */
    public Annuncio(Utente utente, String nomeArticolo, double prezzo, Set<String> paroleChiave) {
        if (utente == null || nomeArticolo == null || paroleChiave == null) {
            throw new IllegalArgumentException("Parametri non possono essere nulli.");
        }
        this.id = nextId++;
        this.utente = utente;
        this.nomeArticolo = nomeArticolo;
        this.prezzo = prezzo;
        this.paroleChiave = paroleChiave;
    }

    /**
     * Restituisce l'ID dell'annuncio.
     * 
     * @return ID dell'annuncio
     */
    public int getId() {
        return id;
    }

    /**
     * Restituisce l'utente proprietario dell'annuncio.
     * 
     * @return Utente proprietario
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * Restituisce il nome dell'articolo.
     * 
     * @return Nome dell'articolo
     */
    public String getNomeArticolo() {
        return nomeArticolo;
    }

    /**
     * Restituisce il prezzo dell'articolo.
     * 
     * @return Prezzo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * Restituisce l'insieme delle parole chiave associate.
     * 
     * @return Set di parole chiave
     */
    public Set<String> getParoleChiave() {
        return paroleChiave;
    }
    
    /**
     * Metodo che ritorna in formato stringa un annuncio con: id, nomeArticolo, prezzo, utente.
     * <p>
     * La stringa di ritorno racchiude i dettagli dell'articolo, ognuno separato dal carattere "|"
     * 
     * @return Stringa con le caratteristiche dell'annuncio
     */
    @Override
    public String toString() {
        return "ID: " + id + " | " + nomeArticolo + " | Prezzo: " + prezzo + "€ | Utente: " + utente;
    }

    /**
     * Controlla se l'annuncio è scaduto (solo per annunci vendita).
     * 
     * @return true se scaduto, false altrimenti
     */
    public abstract boolean isScaduto();

}
