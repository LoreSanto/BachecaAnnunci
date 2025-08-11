/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

/**
 * Classe di test per {@link Bacheca}.
 * <p>
 * Questa classe verifica:
 * <ul>
 *     <li>Il corretto funzionamento del costruttore della {@code Bacheca}</li>
 *     <li>Il corretto funzionamento dell'aggiunta e rimozione degli annunci</li>
 *     <li>Il corretto funzionamento della ricerca per parole chiavi</li>
 *     <li>Il corretto funzionamento della pulizia della {@code Bacheca}</li>
 * </ul>
 */
class TestBacheca{

	private Bacheca bacheca;
    private Utente utente1;
    private Utente utente2;
    private AnnuncioVendita annuncioVendita;
    private AnnuncioAcquisto annuncioAcquisto;

    @BeforeEach
    public void setUp() {
        bacheca = new Bacheca();
        utente1 = new Utente("Franco","franco@mail.com");
        utente2 = new Utente("Enzo","enzo@mail.com");

        annuncioVendita = new AnnuncioVendita(utente1,"Bici",150.23,Set.of("sport", "outdoor"),LocalDate.now().plusDays(3));
        annuncioAcquisto = new AnnuncioAcquisto(utente2,"Casco",30.40,Set.of("sport")
        );
    }

    @Test
    public void testAggiuntaAnnuncio() {
        bacheca.addAnnuncio(annuncioVendita);
        assertTrue(bacheca.iterator().hasNext());
    }

    @Test
    public void testRimozioneAnnuncioAutorizzata() {
        bacheca.addAnnuncio(annuncioVendita);
        int id = annuncioVendita.getId();
        bacheca.rimuoviAnnuncio(id, utente1);
        assertFalse(bacheca.iterator().hasNext());
    }

    @Test
    public void testRimozioneAnnuncioNonAutorizzata() {
        bacheca.addAnnuncio(annuncioVendita);
        int id = annuncioVendita.getId();
        assertThrows(SecurityException.class, () -> {bacheca.rimuoviAnnuncio(id, utente2);});//eccezione usata per la verifica di un'operazione non autorizzata
    }

    @Test
    public void testCercaPerParoleChiaveConMatch() {
        bacheca.addAnnuncio(annuncioVendita);
        List<Annuncio> risultati = bacheca.cercaPerParoleChiave(Set.of("sport"));
        assertEquals(1, risultati.size());
        assertEquals(annuncioVendita, risultati.get(0));
    }

    @Test
    public void testCercaPerParoleChiaveSenzaMatch() {
        bacheca.addAnnuncio(annuncioVendita);
        List<Annuncio> risultati = bacheca.cercaPerParoleChiave(Set.of("cucina"));
        assertTrue(risultati.isEmpty());
    }

    @Test
    public void testInserimentoAnnuncioAcquistoConMatch() {
        bacheca.addAnnuncio(annuncioVendita);
        List<Annuncio> trovati = bacheca.inserisciAnnuncioAcquisto(annuncioAcquisto);
        assertEquals(1, trovati.size());
        assertEquals(annuncioVendita, trovati.get(0));
    }

    @Test
    public void testPulisciBacheca() {
        AnnuncioVendita scaduto = new AnnuncioVendita(utente1,"Libro",10,Set.of("lettura"),LocalDate.now().minusDays(1));
        bacheca.addAnnuncio(scaduto);
        bacheca.addAnnuncio(annuncioVendita);

        bacheca.pulisciBacheca();

        List<Annuncio> rimasti = new ArrayList<>();
        bacheca.iterator().forEachRemaining(rimasti::add);
        assertEquals(1, rimasti.size());
        assertEquals("Bici", rimasti.get(0).getNomeArticolo());
    }
    

    @Test
    public void testIteratorImmutabile() {
        bacheca.addAnnuncio(annuncioVendita);
        Iterator<Annuncio> it = bacheca.iterator();
        assertThrows(UnsupportedOperationException.class, it::remove);//ecezzione usata per la verifica di operazione non supportata
    }
}
