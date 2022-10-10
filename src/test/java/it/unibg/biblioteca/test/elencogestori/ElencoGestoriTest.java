package it.unibg.biblioteca.test.elencogestori;

import static org.mockito.Mockito.mock;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import it.unibg.biblioteca.dao.impl.AcquistoCrudOperation;
import it.unibg.biblioteca.dao.impl.GestoreCrudOperation;
import it.unibg.biblioteca.dao.impl.LibroCrudOperation;
import it.unibg.biblioteca.dao.impl.UtenteCrudOperation;
import it.unibg.biblioteca.handling.ElencoGestori;
import it.unibg.biblioteca.handling.ElencoRichiesteAcquisto;
import it.unibg.biblioteca.handling.ElencoUtenti;
import it.unibg.biblioteca.handling.GestioneCatalogo;
import it.unibg.biblioteca.model.Gestore;
import it.unibg.biblioteca.model.Utente;

public class ElencoGestoriTest {

	GestoreCrudOperation gDao = mock(GestoreCrudOperation.class);
	
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
	public void creaGestoreTest() {
		when(gDao.getAll()).thenReturn(new HashMap<>());
		when(gDao.insert(any())).thenReturn(1);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoGestori eg = new ElencoGestori(gDao);
		assertTrue(eg.creaGestore("username", "password", "Costantino", "Esposito", gestioneCatalogo, elencoRichieste,elencoUtenti));
		
		
	}
	
	@Test
	public void eliminaGestoreTest() {
		when(gDao.getAll()).thenReturn(new HashMap<>());
		when(gDao.insert(any())).thenReturn(1);
		when(gDao.delete(1)).thenReturn(true);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoGestori eg = new ElencoGestori(gDao);
		assertTrue(eg.creaGestore("username", "password", "Costantino", "Esposito", gestioneCatalogo, elencoRichieste,elencoUtenti));
		Gestore g = eg.eliminaGestore(1);
		assertNotNull(g);
		assertTrue(g.getCodice()==1);
	}
	
	@Test
	public void getListaGestoriTest() {
		when(gDao.getAll()).thenReturn(new HashMap<>());
		when(gDao.insert(any())).thenReturn(1);
		when(gDao.delete(1)).thenReturn(true);
		ElencoGestori eg = new ElencoGestori(gDao);
		assertNotNull(eg.getListaGestori());
	}
	
	@Test
	public void autenticaGestoreTest() {
		when(gDao.getAll()).thenReturn(new HashMap<>());
		when(gDao.insert(any())).thenReturn(1);
		when(gDao.delete(1)).thenReturn(true);
		when(gDao.update(any())).thenReturn(true);
		when(gDao.autentica(any())).thenReturn(true);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoGestori eg = new ElencoGestori(gDao);
		assertTrue(eg.creaGestore("username", "password", "Costantino", "Esposito", gestioneCatalogo, elencoRichieste,elencoUtenti));
		
		Gestore gNew = new Gestore("username", "password", "Costantino", "Esposito", gestioneCatalogo, elencoRichieste,elencoUtenti);
		gNew.setCodice(1);
		assertTrue(eg.autenticaGestore(gNew));
	}
	
	@Test
	public void modificaGestoreTest() {
		when(gDao.getAll()).thenReturn(new HashMap<>());
		when(gDao.insert(any())).thenReturn(1);
		when(gDao.delete(1)).thenReturn(true);
		when(gDao.update(any())).thenReturn(true);
		
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		
		ElencoGestori eg = new ElencoGestori(gDao);
		assertTrue(eg.creaGestore("username", "password", "Costantino", "Esposito", gestioneCatalogo, elencoRichieste,elencoUtenti));
		
		Gestore gNew = new Gestore("username", "password", "Giovanni", "Esposito", gestioneCatalogo, elencoRichieste,elencoUtenti);
		gNew.setCodice(1);
		assertTrue(eg.modificaGestore(gNew));
		
		assertTrue(eg.getListaGestori().get(1).getNome().equals("Giovanni"));
	}
	
}
