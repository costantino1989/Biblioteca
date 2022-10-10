package it.unibg.biblioteca.test.elencorichieste;

import static org.junit.Assert.assertFalse;
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

public class ElencoRichiesteAcquistoTT {

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
	public void updateRicTT() {
		when(ricDao.getAll()).thenReturn(arr);
		when(ricDao.insert(any())).thenReturn(1);
		when(ricDao.delete(1)).thenReturn(true);
		when(ricDao.update(any())).thenReturn(false);
		getMapUtenti();
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Utente u = new Utente("Costantino","Esposito","Costa89","12345678");
		u.setCodice(1);
		Libro libro = new Libro("autore", "Il Signore degli anelli", "editore", 1999, 500, "isbn1");
		Libro libro2 = new Libro("autore", "Lo Hobbit", "editore", 1999, 500, "isbn2");
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste, elencoUtenti);
		g.getGestioneCatalogo().aggiungiLibro(libro);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn1", 5);
		g.getGestioneCatalogo().aggiungiLibro(libro2);
		g.getGestioneCatalogo().aumentaDisponibilita("isbn2", 5);
		when(ricDao.insert(any())).thenReturn(1);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro));
		when(ricDao.insert(any())).thenReturn(2);
		assertTrue(g.gestoreCreaRichiesta(1, u, libro2));
		assertFalse(g.getElencoRichieste().updateRic(g.getElencoRichieste().esisteRichiestaLibro(libro2)));
	}
	
}
