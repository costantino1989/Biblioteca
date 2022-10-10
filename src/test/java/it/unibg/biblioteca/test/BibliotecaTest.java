package it.unibg.biblioteca.test;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;

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

public class BibliotecaTest {
	
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
	
	@Test
	public void creaRichiestaAcquisto() {
		getMapUtenti();
		
		when(ricDao.insert(any())).thenReturn(1);
		when(utenteDao.getAll()).thenReturn(arr);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		//u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste,elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		int oldDisponibilita = libro.getDisponibilita();
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertTrue(libro.equals(g.getGestioneCatalogo().cercaISBN(libro.getIsbn())));
		assertTrue(oldDisponibilita>g.getGestioneCatalogo().cercaISBN(libro.getIsbn()).getDisponibilita());
		
		when(utenteDao.insert(any())).thenReturn(9);
		when(ricDao.update(any())).thenReturn(true);
		Utente u2 = new Utente("Pippo","Pluto","PIpp","12345678");
		assertTrue(g.gestoreCreaRichiesta(1, u2, libro));
	}
	
	@Test
	public void cancellaRichiestaAcquisto() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
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
		assertTrue(g.getElencoRichieste().creaRichiesta(1, u, libro));
		int oldDisponibilita = libro.getDisponibilita();
		g.getGestioneCatalogo().diminuisciDisponibilita("isbn1", 1);
		assertTrue(oldDisponibilita>g.getGestioneCatalogo().cercaISBN("isbn1").getDisponibilita());
		assertTrue(g.getElencoRichieste().cancellaRichiesta(1));
		assertTrue(g.getElencoRichieste().getRichieste().size()==0);
		int newOldDisp = libro.getDisponibilita();
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 1);
		assertTrue(newOldDisp<g.getGestioneCatalogo().cercaISBN("isbn1").getDisponibilita());
		
	}
	
	@Test
	public void confermaRichiestaAcquisto() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.update(any())).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
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
		assertTrue(g.getElencoRichieste().creaRichiesta(1, u, libro));
		int oldDisponibilita = libro.getDisponibilita();
		g.getGestioneCatalogo().diminuisciDisponibilita("isbn1", 1);
		assertTrue(oldDisponibilita>libro.getDisponibilita());
		RichiestaAcquisto r = g.getElencoRichieste().esisteRichiestaLibro(libro);
		r.setStato(2);
		assertTrue(g.getElencoRichieste().updateRic(r));
		assertTrue(g.getElencoRichieste().esisteRichiestaLibro(libro).getStato()==2);
	}
	
	@Test
	public void rimuoviLibroDaCatalogoSeTeminati(){
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(libroDao.delete(any())).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		int oldDisponibilita = libro.getDisponibilita();
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertTrue(oldDisponibilita>libro.getDisponibilita());
		assertTrue(g.getGestioneCatalogo().cercaISBN("isbn1")==null);
	}
	
	@Test
	public void zeroLibriRichiestaCancellata(){
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		when(libroDao.delete(any())).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 800, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		assertTrue(g.getElencoRichieste().creaRichiesta(1, u, libro));
		int oldDisponibilita = libro.getDisponibilita();
		g.getGestioneCatalogo().diminuisciDisponibilita("isbn1", 1);
		assertTrue(oldDisponibilita>libro.getDisponibilita());
		assertTrue(libro.getDisponibilita()==0);
		assertTrue(g.getGestioneCatalogo().cercaISBN("isbn1")==null);
		
		assertTrue(g.gestoreCancellaRichiesta(libro));
		assertTrue(g.getGestioneCatalogo().cercaISBN("isbn1").getDisponibilita()==1);
		
	}
	
	@Test
	public void eliminaUtenteDallaRichiesta() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.update(any())).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Utente u2 = new Utente("Giovanni","Palumbo","Giova60","12345678");
		u2.setCodice(2);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertTrue(g.gestoreCreaRichiesta(1, u2, libro));
		//g.getGestioneCatalogo().diminuisciDisponibilita("isbn1", 1);
		assertTrue(g.getElencoRichieste().getRichieste().size()==1);
		assertTrue(g.getElencoRichieste().esisteRichiestaLibro(libro).getListaUtenti().size()==2);
		assertTrue(libro.equals(g.getGestioneCatalogo().cercaISBN(libro.getIsbn())));
		assertTrue(g.gestoreAggiornaRichiestaEU(libro, u));
		assertTrue(g.getElencoRichieste().esisteRichiestaLibro(libro).getListaUtenti().size()==1);
		assertTrue(g.getElencoRichieste().esisteRichiestaUtente(u2)!=null);
		assertTrue(g.getElencoRichieste().esisteRichiestaUtente(u).size()==0);
	}
	
	@Test
	public void eliminaUtente() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.update(any())).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
		when(utenteDao.delete(1)).thenReturn(true);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Utente u2 = new Utente("Giovanni","Palumbo","Giova60","12345678");
		u2.setCodice(2);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertTrue(g.gestoreCreaRichiesta(1, u2, libro));
		assertTrue(g.gestoreElimintaUtente(u));
	}
	
	@Test
	public void aggiornaUtente() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.update(any())).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
		when(utenteDao.update(any())).thenReturn(true);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Utente u2 = new Utente("Vincenzo","Scopacasa",8,"s.vincenzo","12345678");
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertTrue(g.gestoreCreaRichiesta(1, u2, libro));
		u2.setUsarname("VIVI");
		assertTrue(g.gestoreUpdateUtente(u2));
		assertTrue(g.getElencoUtenti().getListaUtente().get(u2.getCodice()).getUsarname().equals("VIVI"));
	}
	
}
