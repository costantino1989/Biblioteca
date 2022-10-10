package it.unibg.biblioteca.test.gestionecatalogo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.spy;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
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

public class GestioneCatalogoTestBranch {

	LibroDao libroDao = mock(LibroDao.class);
	ArrayList<Libro> arr = spy(ArrayList.class);
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
	
	@Test
	public void rimuoviLibroTestBranch() {
		
		when(libroDao.getAll()).thenReturn(arr);
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		doReturn(false).when(arr).remove(libro);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertFalse(g.getGestioneCatalogo().rimuoviLibro("isbn1"));
	}
	
	@Test
	public void updateLibroTestBranch() {
		
		when(libroDao.getAll()).thenReturn(arr);
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		Libro libro3 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		doReturn(false).when(arr).remove(libro);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertFalse(g.getGestioneCatalogo().updateLibro(libro3));
		doReturn(false).when(arr).remove(libro);
		assertFalse(g.getGestioneCatalogo().updateLibro(libro));
		doReturn(true).when(arr).remove(libro);
		doReturn(false).when(arr).add(libro);
		assertFalse(g.getGestioneCatalogo().updateLibro(libro));
	}

	@Test
	public void diminuisciDisponibilitaTB() {
		when(libroDao.getAll()).thenReturn(arr);
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		doReturn(false).when(arr).remove(libro);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertFalse(g.getGestioneCatalogo().diminuisciDisponibilita("isbn5", 1));
	}
}
