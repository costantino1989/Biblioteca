package it.unibg.biblioteca.test.elencorichieste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

public class ElencoRichiesteAcquistoTL {

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
	public void cancellaRichiestaTL() {
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
		Libro libro1 = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		when(ricDao.insert(any())).thenReturn(2);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro1));
		assertFalse(g.getElencoRichieste().cancellaRichiesta(g.getElencoRichieste().esisteRichiestaLibro(libro1).getCodice()));
	}
	
	@Test
	public void esisteRichiestaLibroTL() {
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
		Libro libro1 = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		assertTrue(g.getElencoRichieste().esisteRichiestaLibro(libro1)==null);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));


	}
	
	@Test
	public void esisteRichiestaUtenteTL() {
		//non posso fare con zero utenti perché non esiste una richiesta di acquisto senza utente
		//per questo questo test rimane all'83%
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		Utente u2 = new Utente("m","e","e63","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro1 = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		assertTrue(g.getElencoRichieste().esisteRichiestaUtente(u).size()==0);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertTrue(g.getElencoRichieste().esisteRichiestaUtente(u2).size()==0);
		when(ricDao.insert(any())).thenReturn(2);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro1));
		assertTrue(g.getElencoRichieste().esisteRichiestaUtente(u2).size()==0);
		g.getElencoRichieste().esisteRichiestaLibro(libro1).getListaUtenti().add(new UtenteLibro(u2,1));
		assertTrue(g.getElencoRichieste().esisteRichiestaUtente(u2).size()==1);
		
	}
	
	@Test
	public void updateRicTestLoop() {		
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.update(any())).thenReturn(true);
		when(ricDao.delete(1)).thenReturn(true);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro1 = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		List<UtenteLibro> listaUtenti = new ArrayList<UtenteLibro>();
		listaUtenti.add(new UtenteLibro(u,1));
		assertFalse(g.getElencoRichieste().updateRic(new RichiestaAcquisto(libro1, listaUtenti)));
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		when(ricDao.insert(any())).thenReturn(2);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro1));
		RichiestaAcquisto ric = g.getElencoRichieste().esisteRichiestaLibro(libro1);
		ric.setQuantita(10);
		assertTrue(g.getElencoRichieste().updateRic(ric));
	}
	
	@Test
	public void eliminaRichiestaUtenteTL() {
		//non posso fare con zero utenti perché non esiste una richiesta di acquisto senza utente
		//per questo questo test rimane all'83%, il secondo loop non può avere 0 iterazioni
		
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		when(ricDao.update(any())).thenReturn(true);
		
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		Utente u2 = new Utente("m","e","e63","12345678");
		Utente u3 = new Utente("l","d","c63","12345678");
		u.setCodice(1);
		u2.setCodice(2);
		u3.setCodice(3);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro1 = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro1);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		List<Utente> listaUtenti = new ArrayList<Utente>();
		listaUtenti.add(u);
		//assertFalse(g.gestoreAggiornaRichiestaEU(libro, u2));
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertFalse(g.gestoreAggiornaRichiestaEU(libro, u2));
		assertTrue(g.gestoreCreaRichiesta(1, u2, libro));
		assertTrue(g.gestoreAggiornaRichiestaEU(libro, u2));
		assertTrue(g.gestoreCreaRichiesta(1, u3, libro1));
		when(ricDao.update(any())).thenReturn(false);
		assertFalse(g.gestoreAggiornaRichiestaEU(libro1, u3));
		

	}
}
