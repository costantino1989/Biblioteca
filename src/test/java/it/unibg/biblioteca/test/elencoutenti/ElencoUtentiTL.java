package it.unibg.biblioteca.test.elencoutenti;

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
import it.unibg.biblioteca.model.Utente;

public class ElencoUtentiTL {

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
	public void cercaUtentiNomeCognTL() {
		getMapUtenti();
		when(ricDao.insert(any())).thenReturn(1);
		when(utenteDao.getAll()).thenReturn(arr);
		when(utenteDao.insert(any())).thenReturn(-1);
		ElencoUtenti elencoUtenti = new ElencoUtenti(utenteDao);
		elencoUtenti = spy(elencoUtenti);
		GestioneCatalogo gestioneCatalogo = new GestioneCatalogo(libroDao);
		ElencoRichiesteAcquisto elencoRichieste = new ElencoRichiesteAcquisto(ricDao);
		Gestore g = new Gestore("Michele", "Esposito", "Mick63", "12345678",gestioneCatalogo, elencoRichieste,elencoUtenti);
	}
	
}
