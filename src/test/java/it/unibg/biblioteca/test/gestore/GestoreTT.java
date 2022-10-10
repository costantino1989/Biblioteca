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

public class GestoreTT {

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
	
	 //gestoreAggiornaRichiestaEU non è possibile raggiungere il 100% poiché, per come è stato implementato, se ci sono richieste allora ci sono utenti.	
	
	@Test
	public void gestoreElimintaUtenteTT() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
		when(ricDao.update(any())).thenReturn(true);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste,elencoUtenti);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		Utente u = new Utente("Costantino","Esposito",100,"Costa89","12345678");
		//assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		assertFalse(g.gestoreElimintaUtente(u));
		Utente u2 = new Utente("a","b",101,"Costa89","12345678");
		assertFalse(g.gestoreElimintaUtente(u2));
	}
	
	@Test
	public void gestoreUpdateUtenteTT() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		when(utenteDao.getAll()).thenReturn(arr);
		when(ricDao.update(any())).thenReturn(true);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste,elencoUtenti);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		Utente u = new Utente("Costantino","Esposito",100,"Costa89","12345678");
		assertFalse(g.gestoreUpdateUtente(u));
		Utente u2 = new Utente("a","b",101,"Costa89","12345678");
		assertFalse(g.gestoreUpdateUtente(u2));
	}
	
}
