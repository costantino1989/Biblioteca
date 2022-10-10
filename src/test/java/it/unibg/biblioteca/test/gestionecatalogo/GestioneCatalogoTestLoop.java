package it.unibg.biblioteca.test.gestionecatalogo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import it.unibg.biblioteca.dao.impl.AcquistoCrudOperation;
import it.unibg.biblioteca.dao.impl.LibroDao;
import it.unibg.biblioteca.dao.impl.UtenteCrudOperation;
import it.unibg.biblioteca.handling.ElencoRichiesteAcquisto;
import it.unibg.biblioteca.handling.ElencoUtenti;
import it.unibg.biblioteca.handling.GestioneCatalogo;
import it.unibg.biblioteca.model.Gestore;
import it.unibg.biblioteca.model.Libro;
import it.unibg.biblioteca.model.Utente;

public class GestioneCatalogoTestLoop {

	LibroDao libroDao = mock(LibroDao.class);

	AcquistoCrudOperation ricDao = mock(AcquistoCrudOperation.class);
	
	UtenteCrudOperation utenteDao = mock(UtenteCrudOperation.class);
	HashMap<Integer,Utente> mapU = new HashMap<Integer,Utente>(); 
	
	public void getMapUtenti() {
		
		mapU.put(1,new Utente("Costantino","Esposito",1,"Costa89","12345678"));
		mapU.put(2,new Utente("Michele","Esposito",2,"Miky63","12345678"));
		mapU.put(3,new Utente("Annachiara","Palumbo",3,"annina","12345678"));
		mapU.put(4,new Utente("Giovanni","Bulgari",4,"Biggio","12345678"));
		mapU.put(5,new Utente("Gianni","Palumbo",5,"Gianpi","12345678"));
		mapU.put(6,new Utente("Mina","Palumbo",6,"Pamy","12345678"));
		mapU.put(7,new Utente("Edoardo","Zucca",7,"Edo90","12345678"));
		mapU.put(8,new Utente("Vincenzo","Scopacasa",8,"s.vincenzo","12345678"));
		mapU = spy(mapU);
	}
	
	//caso di più din un elemento nell'array
	@Test
	public void cercaLibrroISBN() {
		
		when(libroDao.getAll()).thenReturn(new ArrayList<Libro>());
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		assertNull(g.getGestioneCatalogo().cercaISBN("12345"));
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		assertNull(g.getGestioneCatalogo().cercaISBN("12345"));
		assertNotNull(g.getGestioneCatalogo().cercaISBN("isbn2"));

		
	}
	
	//caso di più din un elemento nell'array
	@Test
	public void rimuoviLibroTest() {
	
		when(libroDao.getAll()).thenReturn(new ArrayList<Libro>());
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		assertFalse(g.getGestioneCatalogo().rimuoviLibro("1234"));
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		assertFalse(g.getGestioneCatalogo().rimuoviLibro("1234"));
		
	}
	
	@Test
	public void cercaIsbnTL() {
		when(libroDao.getAll()).thenReturn(new ArrayList<Libro>());
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		assertTrue(g.getGestioneCatalogo().cercaISBN("123")==null);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		assertTrue(g.getGestioneCatalogo().cercaISBN("123")==null);
		
	}
	
}
