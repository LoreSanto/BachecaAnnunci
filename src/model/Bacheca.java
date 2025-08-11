/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

import java.util.*;
import java.util.stream.Collectors;


public class Bacheca implements Iterable<Annuncio> {
    
	private List<Annuncio> annunci;//Lista di tutti gli annunci

    /**
     * <h2>Costruttore di Bacheca.</h2>
     * 
     * <p>
     * Mediante questo creiamo una nuova bacheca vuota che caricherà gli annunci precedentemente salvati.
     * Se non vi è nessun annuncio, semplicemente crea una lista di annunci vuoti.
     * </p>		
     * 
     */
    public Bacheca() {
        this.annunci = new ArrayList<>();
    }
    
    /**
     * <h2>Aggiungi annuncio</h2>
     * 
     * <p>
     * La funzione aggiunge un annuncio creato all'interno della lista degli annunci della bacheca
     * </p>
     * 
     * @param annuncio Annuncio da aggiungere
     */
    public void addAnnuncio(Annuncio annuncio) {
    	annunci.add(annuncio);
    }

    /**
     * <h2>Rimuove un annuncio specificato dall'utente proprietario.</h2>
     * 
     * <p>
     * Il metodo crea uno stream temporaneo che filtra tutti gli annunci e prende
     * l'id corrispondente (il primo trovato) e nel caso non ci sia 
     * lancia un'eccezione
     * </p>
     * 
     * @param id ID dell'annuncio
     * @param utente Utente che vuole rimuovere
     * @throws SecurityException generato se un utente prova a cancellare un qualsiasi annuncio di un altro utente
     */
    public void rimuoviAnnuncio(int id, Utente utente) {
        Annuncio annuncio = annunci.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Annuncio non trovato."));
        if (!annuncio.getUtente().getMail().equals(utente.getMail())) {
            throw new SecurityException("Non puoi rimuovere annunci di altri utenti.");
        }
        annunci.remove(annuncio);
    }

    /**
     * <h2>Cerca annunci che contengono almeno una parola chiave specificata.<h2>
     * 
     * <p>
     * Il metodo crea uno stream temporaneo che verifica se vi siano
     * elementi in comune tra le parolechiavi e le parole selezionate
     * (messa negata perchè la funzione da se NON c'è corrspondenza)
     * infine tutti i risultati vengono raccolti e trasformati in una lista
     * </p>
     * 
     * @param parole Set di parole chiave
     * @return Lista di annunci trovati
     */
    public List<Annuncio> cercaPerParoleChiave(Set<String> parole) {
        return annunci.stream()
                .filter(a -> !Collections.disjoint(a.getParoleChiave(), parole))
                .collect(Collectors.toList());
    }

    /**
     * <h2>Inserisce un annuncio di acquisto</h2>
     * <p> 
     * Mediante questa funzione si inserisce un annincio di acquisto e vengono restituiti
     * gli annunci che corrispondono alle parole chiavi inserite.
     * </p>
     * @param a Annuncio di acquisto
     * @return Lista di annunci che corrispodnono alle parole chiavi di quello inserito
     */
    public List<Annuncio> inserisciAnnuncioAcquisto(AnnuncioAcquisto a) {
        List<Annuncio> annunciTrovati = cercaPerParoleChiave(a.getParoleChiave());
        addAnnuncio(a);
        return annunciTrovati;
    }

    /**
     * <h2>Rimuove gli annunci di vendita scaduti.</h2>
     */
    public void pulisciBacheca() {
        annunci.removeIf(a -> a instanceof AnnuncioVendita && a.isScaduto());
    }


    @Override
    public Iterator<Annuncio> iterator() {
        return Collections.unmodifiableList(annunci).iterator();
    }
}
