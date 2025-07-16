package model.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Utente;

class TestUtente_ {

	@Test
    public void testCostruttoreValido() {
        Utente utente = new Utente("lorenzo@gmail.com", "Lorenzo");
        assertEquals("lorenzo@gmail.com", utente.getEmail());
        assertEquals("Lorenzo", utente.getNome());
    }

    @Test
    public void testEmailNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Utente(null, "Lorenzo");});//Ecezzione usata per verifica di validità di un parametro
        assertEquals("Email e nome non possono essere vuoti.", exception.getMessage());
    }

    @Test
    public void testNomeNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Utente("lorenzo@gmail.com", null);});
        assertEquals("Email e nome non possono essere vuoti.", exception.getMessage());
    }

    @Test
    public void testToString() {
        Utente utente = new Utente("lorenzo@gmail.com", "Lorenzo");
        assertEquals("Lorenzo (lorenzo@gmail.com)", utente.toString());
    }

}
