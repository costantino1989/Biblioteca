package it.unibg.biblioteca.test.gestionecatalogo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import it.unibg.biblioteca.dao.impl.AcquistoCrudOperation;
import it.unibg.biblioteca.dao.impl.LibroCrudOperation;
import it.unibg.biblioteca.dao.impl.LibroDao;
import it.unibg.biblioteca.dao.impl.UtenteCrudOperation;
import it.unibg.biblioteca.handling.ElencoRichiesteAcquisto;
import it.unibg.biblioteca.handling.ElencoUtenti;
import it.unibg.biblioteca.handling.GestioneCatalogo;
import it.unibg.biblioteca.model.Gestore;
import it.unibg.biblioteca.model.Libro;
import it.unibg.biblioteca.model.Utente;

public class GestioneCatalogoTestStat {

	AcquistoCrudOperation ricDao = mock(AcquistoCrudOperation.class);
	LibroCrudOperation libroDao = mock(LibroCrudOperation.class);
	
	UtenteCrudOperation utenteDao = mock(UtenteCrudOperation.class);
	HashMap<Integer,Utente> arr = spy(HashMap.class);
	
	public void getMapUtenti() {
		
		arr.put(1,new Utente("Costantino","Esposito",1,"Costa89","12345678"));
		arr.put(2,new Utente("Michele","Esposito",2,"Miky63","12345678"));
		arr.put(3,new Utente("Annachiara","Palumbo",3,"annina","12345678"));
		arr.put(4,new Utente("Giovanni","Bulgari",4,"Biggio","12345678"));
		arr.put(5,new Utente("Gianni","Palumbo",5,"Gianpi","12345678"));
		arr.put(6,new Utente("Mina","Palumbo",6,"Pamy","12345678"));
		arr.put(7,new Utente("Edoardo","Zucca",7,"Edo90","12345678"));
		arr.put(8,new Utente("Vincenzo","Scopacasa",8,"s.vincenzo","12345678"));
	}
	
	@Test
	public void aggiungiLibroStatement() {
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.update(any())).thenReturn(true);
		
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		when(libroDao.insert(libro)).thenReturn(-1);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		
		//Libro l = new Libro("autore", "titolo", "Editore", 1989, 300, "isbn");
		
		assertFalse(g.getGestioneCatalogo().aggiungiLibro(libro));
	}
	
	@Test
	public void getListaLibriStat() {
		//GestioneCatalogo g = new GestioneCatalogo(libroDao);
		//Libro l = new Libro("autore", "titolo", "Editore", 1989, 300, "isbn");
		when(libroDao.getAll()).thenReturn(new ArrayList<Libro>());
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		getMapUtenti();
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		when(libroDao.insert(libro)).thenReturn(1);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		when(libroDao.insert(libro)).thenReturn(-1);
		assertFalse(g.getGestioneCatalogo().aggiungiLibro(libro));
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		
		
		assertNotNull(g.getGestioneCatalogo().getListaLibri());
	}
	
	@Test
	public void rimuoviLibroStat() {
		when(libroDao.getAll()).thenReturn(new ArrayList<Libro>());
		when(libroDao.delete(any())).thenReturn(false);
		getMapUtenti();
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		
		g.getGestioneCatalogo().aggiungiLibro(new Libro("autore", "titolo", "Editore", 1989, 300, "isbn"));
		assertFalse(g.getGestioneCatalogo().rimuoviLibro("isbn2"));
		assertFalse(g.getGestioneCatalogo().rimuoviLibro("isbn"));
	}
	
	@Test
	public void updateLibroStat() {
		when(libroDao.getAll()).thenReturn(new ArrayList<Libro>());
		getMapUtenti();
		
		//	Libro l = new Libro("autore", "titolo", "Editore", 1989, 300, "isbn");
		//GestioneCatalogo g = new GestioneCatalogo(libroDao);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		when(libroDao.insert(libro)).thenReturn(1);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		when(libroDao.insert(libro)).thenReturn(-1);
		assertFalse(g.getGestioneCatalogo().aggiungiLibro(libro));
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		
		assertFalse(g.getGestioneCatalogo().updateLibro(libro));
		when(libroDao.insert(libro)).thenReturn(1);
		assertTrue(g.getGestioneCatalogo().aggiungiLibro(libro));
		when(libroDao.update(libro)).thenReturn(false);
		assertFalse(g.getGestioneCatalogo().updateLibro(libro));
		when(libroDao.update(libro)).thenReturn(true);
		assertTrue(g.getGestioneCatalogo().updateLibro(libro));
	}
	
	@Test
	public void diminuisciDisponibilitaTS() {
		when(ricDao.insert(any())).thenReturn(1);
		getMapUtenti();
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		when(libroDao.insert(libro)).thenReturn(-1);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		assertFalse(g.getGestioneCatalogo().diminuisciDisponibilita("isbn2", 5));
	}
	
	
	@Test
	public void aumentaDisponibilitaTS() {
		when(ricDao.insert(any())).thenReturn(1);
		getMapUtenti();
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		when(libroDao.insert(libro)).thenReturn(-1);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		assertFalse(g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5));
	}
}
