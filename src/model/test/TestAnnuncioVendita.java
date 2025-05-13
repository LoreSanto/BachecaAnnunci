/*
 * @autor Lorenzo Santosuosso 20050494
 */
package model.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.*;

class TestAnnuncioVendita {

    private Utente utente1;
    private AnnuncioVendita annuncioVendita;

    @BeforeEach
    public void setUp() {
        utente1 = new Utente("franco@gmail.com", "Franco");
        annuncioVendita = new AnnuncioVendita(utente1, "Bici", 170.73, Set.of("sport", "bike"), LocalDate.now().plusDays(3));
    }

    @Test
    public void testAnnuncioVenditaCreazione() {
        assertNotNull(annuncioVendita);
        assertEquals("Bici", annuncioVendita.getNomeArticolo());
        assertEquals(170.73, annuncioVendita.getPrezzo());
        assertTrue(annuncioVendita.getParoleChiave().contains("sport"));
        assertTrue(annuncioVendita.getParoleChiave().contains("bike"));
    }

    @Test
    public void testAnnuncioVenditaScadenza() {
        assertFalse(annuncioVendita.isScaduto(), "L'annuncio di vendita non è scaduto");

        AnnuncioVendita annuncioVenditaScaduto = new AnnuncioVendita(utente1, "Libro", 10.15, Set.of("lettura"), LocalDate.now().minusDays(1));
        assertTrue(annuncioVenditaScaduto.isScaduto(), "L'annuncio di vendita è scaduto");
    }

    @Test
    public void testAnnuncioVenditaDataScadenzaNulla() {
        assertThrows(IllegalArgumentException.class, () -> {new AnnuncioVendita(utente1, "Telefono", 200.99, Set.of("elettronica"), null);});
    }

    @Test
    public void testAnnuncioVenditaToString() {
        assertTrue(annuncioVendita.toString().contains("Bici"));
        assertTrue(annuncioVendita.toString().contains("Vendita"));
        assertTrue(annuncioVendita.toString().contains("Scadenza"));
    }

    @Test
    public void testAnnuncioVenditaConDataFutura() {
        LocalDate futureDate = LocalDate.now().plusDays(5);
        AnnuncioVendita annuncioVenditaFuturo = new AnnuncioVendita(utente1, "Tavolo", 100.0, Set.of("arredamento"), futureDate);
        assertFalse(annuncioVenditaFuturo.isScaduto(), "L'annuncio di vendita non è scaduto.");
    }
}
