/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model;

import java.util.*;
import java.util.stream.Collectors;

public class Bacheca implements Iterable<Annuncio> {
    private List<Annuncio> annunci;

    /**
     * <h2>Costruttore di Bacheca.</h2>
     */
    public Bacheca() {
        this.annunci = new ArrayList<>();
    }

    /**
     * Aggiunge un annuncio alla bacheca.
     * 
     * @param a Annuncio da aggiungere
     */
    public void aggiungiAnnuncio(Annuncio a) {
        annunci.add(a);
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
        if (!annuncio.getUtente().getEmail().equals(utente.getEmail())) {
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
     * Inserisce un annuncio di acquisto e ritorna gli annunci che corrispondono.
     * @param a Annuncio di acquisto
     * @return Lista di annunci che corrispodnono alle parole chiavi di quello inserito
     */
    public List<Annuncio> inserisciAnnuncioAcquisto(AnnuncioAcquisto a) {
        List<Annuncio> annunciTrovati = cercaPerParoleChiave(a.getParoleChiave());
        aggiungiAnnuncio(a);
        return annunciTrovati;
    }

    /**
     * Rimuove gli annunci di vendita scaduti.
     */
    public void pulisciBacheca() {
        annunci.removeIf(a -> a instanceof AnnuncioVendita && a.isScaduto());
    }


    @Override
    public Iterator<Annuncio> iterator() {
        return Collections.unmodifiableList(annunci).iterator();
    }
}
