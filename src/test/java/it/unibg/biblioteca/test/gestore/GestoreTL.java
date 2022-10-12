package it.unibg.biblioteca.test.gestore;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Test;

import it.unibg.biblioteca.dao.impl.AcquistoCrudOperation;
import it.unibg.biblioteca.dao.impl.LibroCrudOperation;
import it.unibg.biblioteca.dao.impl.UtenteCrudOperation;
import it.unibg.biblioteca.handling.ElencoRichiesteAcquisto;
import it.unibg.biblioteca.handling.ElencoUtenti;
import it.unibg.biblioteca.handling.GestioneCatalogo;
import it.unibg.biblioteca.model.Gestore;
import it.unibg.biblioteca.model.Libro;
import it.unibg.biblioteca.model.Utente;

public class GestoreTL {

	AcquistoCrudOperation ricDao = mock(AcquistoCrudOperation.class);
	LibroCrudOperation libroDao = mock(LibroCrudOperation.class);
	UtenteCrudOperation utenteDao = mock(UtenteCrudOperation.class);
	HashMap<Integer,Utente> arr = new HashMap<Integer,Utente>();
	
	public void getMapUtenti() {
		
		arr.put(1,new Utente("Costantino","Esposito",1,"Costa89","12345678"));
		arr.put(2,new Utente("Michele","Esposito",2,"Miky63","12345678"));
		arr.put(3,new Utente("Annachiara","Palumbo",3,"annina","12345678"));
		arr.put(4,new Utente("Giovanni","Bulgari",4,"Biggio","12345678"));
		arr.put(5,new Utente("Gianni","Palumbo",5,"Gianpi","12345678"));
		arr.put(6,new Utente("Mina","Palumbo",6,"Pamy","12345678"));
		arr.put(7,new Utente("Edoardo","Zucca",7,"Edo90","12345678"));
		arr.put(8,new Utente("Vincenzo","Scopacasa",8,"s.vincenzo","12345678"));
		arr = spy(arr);
	}
	
	// non possono esistere richieste senza almeno un utente
	// perché c'è il controllo che la lista di ritorno del metodo esisteRichiestaUtente
	// non sia vuota
	@Test
	public void gestoreEliminaUtenteTL() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(utenteDao.getAll()).thenReturn(new HashMap<Integer,Utente>());
		when(ricDao.delete(1)).thenReturn(true);
		when(utenteDao.delete(1)).thenReturn(true);
		when(ricDao.delete(2)).thenReturn(true);
		when(utenteDao.delete(200)).thenReturn(true);
		when(ricDao.update(any())).thenReturn(true);
		when(utenteDao.delete(100)).thenReturn(true);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste,elencoUtenti);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 500, "isbn2");
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		Utente u3 = new Utente("A","B",200,"Costa89","12345678");
		when(utenteDao.insert(any())).thenReturn(200);
		g.getElencoUtenti().creaUtenti(u3);
		assertTrue(g.gestoreElimintaUtente(u3));
		Utente u = new Utente("Pluto","Pippo",100,"Costa89","12345678");
		Utente u2 = new Utente("Costantino","Esposito",1,"Costa89","12345678");
		assertFalse(g.gestoreElimintaUtente(u));
		when(utenteDao.insert(any())).thenReturn(100);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		when(ricDao.insert(any())).thenReturn(2);
		when(utenteDao.insert(any())).thenReturn(1);
		assertTrue(g.gestoreCreaRichiesta(1, u2, libro2));
		assertTrue(g.gestoreCreaRichiesta(1, u2, libro));
		assertTrue(g.gestoreElimintaUtente(u2));
		assertTrue(g.gestoreElimintaUtente(u));
	}
	
}
