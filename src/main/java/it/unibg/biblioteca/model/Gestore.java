package it.unibg.biblioteca.model;

import java.util.List;

import it.unibg.biblioteca.handling.ElencoRichiesteAcquisto;
import it.unibg.biblioteca.handling.ElencoUtenti;
import it.unibg.biblioteca.handling.GestioneCatalogo;

public class Gestore extends UserParents{

	private final GestioneCatalogo gestioneCatalogo;
	private final ElencoRichiesteAcquisto elencoRichieste;
	private final ElencoUtenti elencoUtenti;
	
	public Gestore(String usarname, String password,String nome, String cognome, GestioneCatalogo gestioneCatalogo, ElencoRichiesteAcquisto elencoRichieste, ElencoUtenti elencoUtenti) {
		super(nome, cognome, password, usarname);
		this.gestioneCatalogo = gestioneCatalogo;
		this.elencoRichieste = elencoRichieste;
		this.elencoUtenti = elencoUtenti;
	}
	
	public Gestore(String usarname, String password,String nome, String cognome, int codice, GestioneCatalogo gestioneCatalogo, ElencoRichiesteAcquisto elencoRichieste, ElencoUtenti elencoUtenti) {
		super(nome, cognome, codice, password, usarname);
		this.gestioneCatalogo = gestioneCatalogo;
		this.elencoRichieste = elencoRichieste;
		this.elencoUtenti = elencoUtenti;
	}

	public GestioneCatalogo getGestioneCatalogo() {
		return gestioneCatalogo;
	}

	public ElencoRichiesteAcquisto getElencoRichieste() {
		return elencoRichieste;
	}
	
	public ElencoUtenti getElencoUtenti() {
		return elencoUtenti;
	}

	public boolean gestoreCancellaRichiesta(Libro libro) {
		RichiestaAcquisto r = this.elencoRichieste.esisteRichiestaLibro(libro);
		if(r!=null && this.elencoRichieste.cancellaRichiesta(r.getCodice())) {
			if(gestioneCatalogo.cercaISBN(libro.getIsbn())==null) {
				libro.setDisponibilita(r.getQuantita());
				gestioneCatalogo.getListaLibri().add(libro);
			}
			return true;
		}
		
		return false;
	}
	
	public boolean gestoreCreaRichiesta(int quantita, Utente utente, Libro libro) {
			Utente u = null;
			if((u=elencoUtenti.cercaUtenteCodice(utente.getCodice()))==null && (u=elencoUtenti.cercaUtenteNomeCogn(utente.getNome(), utente.getCognome()))==null) {
				if(!this.elencoUtenti.creaUtenti(utente)) {
					return false;
				}
			}else {
				utente = u;
			}
			utente.setQuantita(quantita);
			return 	this.gestioneCatalogo.diminuisciDisponibilita(libro.getIsbn(), quantita) && this.elencoRichieste.creaRichiesta(quantita, utente, libro);
	}
	
	public boolean gestoreAggiornaRichiestaEU(Libro libro, Utente u) {
		RichiestaAcquisto ric = this.elencoRichieste.esisteRichiestaLibro(libro);
		UtenteLibro utenteLibro = ric.getUtenteLibro(u);
		if(this.elencoRichieste.eliminaRichiestaUtente(u)){
			if((elencoUtenti.cercaUtenteCodice(u.getCodice())!=null || elencoUtenti.cercaUtenteNomeCogn(u.getNome(), u.getCognome())!=null) &&
					ric.getListaUtenti().isEmpty()) {
				if(elencoRichieste.cancellaRichiesta(ric.getCodice())) {
					return true;
				}
				ric.getListaUtenti().add(utenteLibro);
				return false;
			}
			ric.setQuantita(ric.getQuantita()-utenteLibro.getQuantita());
			u.setQuantita(0);
			this.gestioneCatalogo.aumentaDisponibilita(libro.getIsbn(), utenteLibro.getQuantita());
			return true;
		}
		return false;
	}
	
	public boolean gestoreElimintaUtente(Utente u) {

		if(((elencoUtenti.cercaUtenteCodice(u.getCodice()))!=null || (elencoUtenti.cercaUtenteNomeCogn(u.getNome(), u.getCognome()))!=null)) {
			List<RichiestaAcquisto> listaR = null;
			if((listaR=this.elencoRichieste.esisteRichiestaUtente(u))!=null && !listaR.isEmpty()) {
				for(RichiestaAcquisto r: listaR) {
					this.gestoreAggiornaRichiestaEU(r.getLibro(),u);
				}
			}
			return this.elencoUtenti.eliminaUtente(u.getCodice());
		}
		return false;
	}
	
	public boolean gestoreUpdateUtente(Utente u) {
		if((elencoUtenti.cercaUtenteCodice(u.getCodice())!=null || elencoUtenti.cercaUtenteNomeCogn(u.getNome(), u.getCognome())!=null) && this.elencoUtenti.updateUtente(u)) {
				//this.elencoUtenti.getListaUtente().put(u.getCodice(), u);
				return true;
		}
		return false;
	}
	
}
