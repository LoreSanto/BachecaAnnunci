/*
 * @autor Lorenzo Santosuosso 20050494
 */
package model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class TestAnnuncioAcquisto {

    private Utente utente2;
    private AnnuncioAcquisto annuncioAcquisto;

    @BeforeEach
    public void setUp() {
        utente2 = new Utente("jonny@gmail.com", "Jonn");
        annuncioAcquisto = new AnnuncioAcquisto(utente2, "Casco", 30.47, Set.of("sport"));
    }

    @Test
    public void testAnnuncioAcquistoCreazione() {
        assertNotNull(annuncioAcquisto);
        assertEquals("Casco", annuncioAcquisto.getNomeArticolo());
        assertEquals(30.47, annuncioAcquisto.getPrezzo());
        assertTrue(annuncioAcquisto.getParoleChiave().contains("sport"));
    }

    @Test
    public void testAnnuncioAcquistoNonScade() {
        assertFalse(annuncioAcquisto.isScaduto(), "Un annuncio di acquisto non scade mai.");
    }

    @Test
    public void testAnnuncioAcquistoDataScadenza() {
        // Gli annunci di acquisto non dovrebbero mai avere una data di scadenza, quindi non dovrebbero lanciare eccezioni
        assertDoesNotThrow(() -> {
            new AnnuncioAcquisto(utente2, "Portatile", 500.0, Set.of("elettronica"));
        });
    }

    @Test
    public void testAnnuncioAcquistoToString() {
        assertTrue(annuncioAcquisto.toString().contains("Casco"));
        assertTrue(annuncioAcquisto.toString().contains("Acquisto"));
    }

    @Test
    public void testAnnuncioAcquistoConParoleChiaveMultiple() {
        Set<String> parole = Set.of("sport", "esterni", "outdoor");
        AnnuncioAcquisto annuncioAcquistoMultiplo = new AnnuncioAcquisto(utente2, "Guanti", 25.33, parole);
        assertTrue(annuncioAcquistoMultiplo.getParoleChiave().contains("sport"));
        assertTrue(annuncioAcquistoMultiplo.getParoleChiave().contains("esterni"));
        assertTrue(annuncioAcquistoMultiplo.getParoleChiave().contains("outdoor"));
    }
}
