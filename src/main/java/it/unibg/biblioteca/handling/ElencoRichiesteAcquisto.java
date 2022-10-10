package it.unibg.biblioteca.handling;

import java.util.ArrayList;
import java.util.List;

import it.unibg.biblioteca.dao.impl.AcquistoCrudOperation;
import it.unibg.biblioteca.model.Libro;
import it.unibg.biblioteca.model.RichiestaAcquisto;
import it.unibg.biblioteca.model.Utente;
import it.unibg.biblioteca.model.UtenteLibro;

public class ElencoRichiesteAcquisto {

	private final List<RichiestaAcquisto> richieste;
	private final AcquistoCrudOperation ricDao;

	public ElencoRichiesteAcquisto(AcquistoCrudOperation ricDao) {
		this.ricDao=ricDao;
		this.richieste=ricDao.getAll();
	}

	public List<RichiestaAcquisto> getRichieste() {
		return richieste;
	}
	

	public boolean creaRichiesta(int quantita, Utente utente, Libro libro) {
		
		if((libro.getDisponibilita())<0) {
			return false;
		}
		
		RichiestaAcquisto r = null;
		UtenteLibro utenteLibro = new UtenteLibro(utente,quantita);
		if(this.richieste.size()>0 && (r=this.esisteRichiestaLibro(libro))!=null) {
			r.getListaUtenti().add(utenteLibro);
			r.setQuantita(r.getQuantita()+quantita);
			return this.updateRic(r);
		}else {
			List<UtenteLibro> listaUtenti = new ArrayList<UtenteLibro>();
			listaUtenti.add(utenteLibro);
			r = new RichiestaAcquisto(libro, listaUtenti);
			r.setQuantita(quantita);
			int codice = ricDao.insert(r);
			if(codice==-1) {
				return false;
			}
			r.setCodice(codice);
		}
		return this.richieste.add(r);
	}
	
	public boolean cancellaRichiesta(int codice) {
		for(RichiestaAcquisto ric: richieste) {
			if(ric.getCodice()==codice) {
				if(richieste.remove(ric)) {
					if(ricDao.delete(codice)) {
						return true;
					}
					richieste.add(ric);
				}
				return false;
			}
		}
		return false;
	}
	
	public boolean eliminaRichiestaUtente(Utente utente) {
		for(RichiestaAcquisto ric:richieste) {
			for(UtenteLibro u : ric.getListaUtenti()) {
				if(u.getUtente().equals(utente)) {
					ric.getListaUtenti().remove(u);
					if(updateRic(ric)) {
						return true;
					}
					ric.getListaUtenti().add(u);
					return false;
				}
			}
		}
		return false;
	}
	
	public RichiestaAcquisto esisteRichiestaLibro(Libro libro) {
		for(RichiestaAcquisto ric:richieste) {
			if(ric.getLibro().equals(libro)) {
				return ric;
			}
		}
		return null;
	}
	
	public List<RichiestaAcquisto> esisteRichiestaUtente(Utente u) {
		List<RichiestaAcquisto> listaRic = new ArrayList<RichiestaAcquisto>();
		for(RichiestaAcquisto ric:richieste) {
			for(UtenteLibro ul: ric.getListaUtenti()) {
				if(ul.getUtente().getCodice()==u.getCodice()) {
					listaRic.add(ric);
				}
			}
		}
		return listaRic;
	}
	
	public boolean updateRic(RichiestaAcquisto r) {
		for(RichiestaAcquisto ric:richieste) {
			if(ric.getCodice()==r.getCodice()) {
				if(richieste.remove(ric)) {
					if(richieste.add(r) && ricDao.update(r)) {
							return true;
					}
					richieste.add(ric);
				}
				return false;
			}
		}
		return false;
	}
	
}
