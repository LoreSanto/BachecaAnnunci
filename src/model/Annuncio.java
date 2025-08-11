/*
 * @author Lorenzo Santosuosso 20050494
 */

package model;

import java.util.Set;

/**
 * <h2>Classe astratta Annuncio</h2>
 * <p>
 * La classe rappresenta un annuncio generico creato da un utente.
 * Ogni annuncio ha un ID univoco, un nome, un prezzo e un insieme di parole chiave.
 * </p>
 */
public abstract class Annuncio {

    private static int nextId = 1; // Contatore statico per assegnare ID univoci agli annunci

    private int id; // ID univoco dell'annuncio
    private Utente utente; // Utente che ha creato l'annuncio
    private String nomeArticolo; // Nome dell'articolo
    private double prezzo; // Prezzo dell'articolo
    private Set<String> paroleChiave; // Parole chiave associate all'annuncio

    /**
     * <h2>Costruttore di Annuncio</h2>
     * <p>
     * Crea un nuovo annuncio con i parametri specificati.
     * I parametri non devono essere nulli o vuoti, e il prezzo non può essere negativo.
     * </p>
     *
     * @param utente        Utente che ha creato l'annuncio
     * @param nomeAnnuncio  Nome dell'articolo
     * @param prezzo        Prezzo dell'articolo
     * @param paroleChiave  Insieme di parole chiave associate
     * @throws IllegalArgumentException se uno dei parametri è nullo, vuoto o non valido
     */
    public Annuncio(Utente utente, String nomeArticolo, double prezzo, Set<String> paroleChiave) {
        if (utente == null || nomeArticolo == null || nomeArticolo.isBlank() || paroleChiave == null) {
            throw new IllegalArgumentException("Utente, nome annuncio e parole chiave non possono essere nulli o vuoti.");
        }
        if (prezzo < 0) {
            throw new IllegalArgumentException("Il prezzo non può essere negativo.");
        }

        this.id = nextId++;
        this.utente = utente;
        this.nomeArticolo = nomeArticolo;
        this.prezzo = prezzo;
        this.paroleChiave = paroleChiave;
    }

    /**
     * <h2>Restituzione ID</h2>
     * <p>
     * Restituisce l'identificativo univoco dell'annuncio.
     * </p>
     *
     * @return ID dell'annuncio
     */
    public int getId() {
        return id;
    }

    /**
     * <h2>Restituzione utente</h2>
     * <p>
     * Restituisce l'utente che ha creato l'annuncio.
     * </p>
     *
     * @return Utente proprietario dell'annuncio
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * <h2>Restituzione nome Articolo</h2>
     * <p>
     * Restituisce il nome dell'articolo associato all'annuncio.
     * </p>
     *
     * @return Nome dell'annuncio
     */
    public String getNomeArticolo() {
        return nomeArticolo;
    }

    /**
     * <h2>Restituzione prezzo</h2>
     * <p>
     * Restituisce il prezzo dell'articolo indicato nell'annuncio.
     * </p>
     *
     * @return Prezzo dell'articolo
     */
    public double getPrezzo() {
        return prezzo;
    }

    /**
     * <h2>Restituzione parole chiave</h2>
     * <p>
     * Restituisce l'insieme delle parole chiave associate all'annuncio.
     * </p>
     *
     * @return Set di parole chiave
     */
    public Set<String> getParoleChiave() {
        return paroleChiave;
    }

    /**
     * <h2>Impostazione nextId</h2>
     * <p>
     * Imposta manualmente il valore del prossimo ID da assegnare.
     * Utile per sincronizzazioni o ripristini.
     * </p>
     *
     * @param id nuovo valore per nextId
     */
    public static void setNextId(int id) {
        nextId = id;
    }

    /**
     * <h2>Restituzione nextId</h2>
     * <p>
     * Restituisce il valore corrente del prossimo ID da assegnare.
     * </p>
     *
     * @return Valore di nextId
     */
    public static int getNextId() {
        return nextId;
    }
    
    /**
     * <h2>Verifica se l'annuncio è scaduto.</h2>
     * <p>
     * Un annuncio è considerato scaduto se la data corrente è successiva alla data di scadenza.
     * </p>
     *
     * @return {@code true} se l'annuncio è scaduto, {@code false} altrimenti
     */
    public abstract boolean isScaduto();

    /**
     * <h2>Rappresentazione testuale</h2>
     * <p>
     * Restituisce una stringa con ID, nome annuncio, prezzo, utente ed eventuale data di scadenza, separati da "|".
     * </p>
     *
     * @return Stringa descrittiva dell'annuncio
     */
    @Override
    public String toString() {
        return "ID: " + id + " | " + nomeArticolo + " | Prezzo: " + prezzo + "€ | Utente: " + utente;
    }
}
