package it.unibg.biblioteca.test.elencorichieste;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import it.unibg.biblioteca.dao.impl.AcquistoCrudOperation;
import it.unibg.biblioteca.dao.impl.LibroCrudOperation;
import it.unibg.biblioteca.dao.impl.UtenteCrudOperation;
import it.unibg.biblioteca.handling.ElencoRichiesteAcquisto;
import it.unibg.biblioteca.handling.ElencoUtenti;
import it.unibg.biblioteca.handling.GestioneCatalogo;
import it.unibg.biblioteca.model.Gestore;
import it.unibg.biblioteca.model.Libro;
import it.unibg.biblioteca.model.RichiestaAcquisto;
import it.unibg.biblioteca.model.Utente;
import it.unibg.biblioteca.model.UtenteLibro;

public class ElencoRichiesteAcquistoTS {

	AcquistoCrudOperation ricDao = mock(AcquistoCrudOperation.class);
	List<RichiestaAcquisto> arr = spy(ArrayList.class);
	LibroCrudOperation libroDao = mock(LibroCrudOperation.class);
	
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
	public void cancellaRichiestaTestStatement() {
		
		when(ricDao.getAll()).thenReturn(arr);
		when(ricDao.insert(any())).thenReturn(1);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		assertFalse(g.getElencoRichieste().cancellaRichiesta(1));
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertFalse(g.getElencoRichieste().cancellaRichiesta(2));
		doReturn(false).when(arr).remove(any());
		assertFalse(g.getElencoRichieste().cancellaRichiesta(1));
		doReturn(true).when(arr).remove(any());
		when(ricDao.delete(1)).thenReturn(false);
		assertFalse(g.getElencoRichieste().cancellaRichiesta(1));
	}
	
	@Test
	public void creaRichiestaTS() {
		when(ricDao.getAll()).thenReturn(arr);
		when(ricDao.insert(any())).thenReturn(-1);
		when(ricDao.delete(1)).thenReturn(true);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		assertFalse(g.gestoreCreaRichiesta(1, u, libro));
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		assertFalse(g.gestoreCreaRichiesta(1, u, libro));
		
	}
	
	@Test
	public void eliminaRichiestaUtenteTS() {
		when(ricDao.getAll()).thenReturn(arr);
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(false);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Utente u2 = new Utente("m","e","e34","12345678");
		u.setCodice(2);
		Utente u3 = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(3);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		
		assertFalse(g.getElencoRichieste().eliminaRichiestaUtente(u3));
		
	}
	
	@Test
	public void esisteRichiestaLibroTS() {
		when(ricDao.getAll()).thenReturn(arr);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 500, "isbn2");
		Libro libro3 = new Libro("autore", "Lo Hobbit", "editore", 1999, 500, "isbn3");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		when(ricDao.insert(any())).thenReturn(1);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		when(ricDao.insert(any())).thenReturn(2);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro2));
		assertFalse(g.getElencoRichieste().esisteRichiestaLibro(libro3)!=null);
		
	}
	
	@Test 
	public void updateRicTS() {
		when(ricDao.getAll()).thenReturn(arr);
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 500, "isbn2");
		Libro libro3 = new Libro("autore", "Lo Hobbit", "editore", 1999, 500, "isbn3");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		when(ricDao.insert(any())).thenReturn(1);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		when(ricDao.insert(any())).thenReturn(2);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro2));
		List<UtenteLibro> listaUtenti = new ArrayList<UtenteLibro>();
		listaUtenti.add(new UtenteLibro(u,1));
		assertFalse(g.getElencoRichieste().updateRic(new RichiestaAcquisto(libro3, listaUtenti)));
		doReturn(false).when(arr).remove(any());
		assertFalse(g.getElencoRichieste().updateRic(g.getElencoRichieste().esisteRichiestaLibro(libro2)));
		doReturn(true).when(arr).remove(any());
		doReturn(false).when(arr).add(any());
		assertFalse(g.getElencoRichieste().updateRic(g.getElencoRichieste().esisteRichiestaLibro(libro2)));

	}
	
}
