/*
 * @autor Lorenzo Santosuosso 20050494
 */

package model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Utente;

/**
 * Classe di test per {@link Utente}.
 * <p>
 * Questa classe verifica:
 * <ul>
 *     <li>Il corretto funzionamento del costruttore di {@code Utente}</li>
 *     <li>Il corretto inserimento e controllo del nome utente e della mail (che deve essere valida)</li>
 * </ul>
 */
class TestUtente {

	@Test
    public void testCostruttoreValido() {
        Utente utente = new Utente("Lorenzo","lorenzo@gmail.com");
        assertEquals("lorenzo@gmail.com", utente.getMail());
        assertEquals("Lorenzo", utente.getNome());
    }

    @Test
    public void testEmailNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Utente("Lorenzo",null);});//Ecezzione usata per verifica di validità di un parametro
        assertEquals("La mail e nome non possono essere vuoti", exception.getMessage());
    }

    @Test
    public void testNomeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Utente(null, "lorenzo@gmail.com");});
        assertEquals("La mail e nome non possono essere vuoti", exception.getMessage());
    }
    
    @Test
    public void testInvalidMail() {
    	 Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Utente("lorenzo@com", "lorenzo");});
         assertEquals("La mail non è valida", exception.getMessage());
    }

    @Test
    public void testToString() {
        Utente utente = new Utente("Lorenzo","lorenzo@gmail.com");
        assertEquals("Lorenzo (lorenzo@gmail.com)", utente.toString());
    }

}
