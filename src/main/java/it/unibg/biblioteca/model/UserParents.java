package it.unibg.biblioteca.model;

public abstract class UserParents {
	private int codice;
	private String nome;
	private String cognome;
	private String usarname;
	private String password;
	private boolean autenticato;
	
	public UserParents(String nome, String cognome, String password, String usarname) {
		super();
		this.codice = -1;
		this.nome = nome;
		this.cognome = cognome;
		this.autenticato = false;
		this.password = password;
		this.usarname = usarname;
	}
	public UserParents(String nome, String cognome, int codice, String password, String usarname) {
		super();
		this.codice = codice;
		this.nome = nome;
		this.cognome = cognome;
		this.autenticato = false;
		this.password = password;
		this.usarname = usarname;
	}
	
	public String getUsarname() {
		return usarname;
	}
	public void setUsarname(String usarname) {
		this.usarname = usarname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAutenticato() {
		return autenticato;
	}
	public void setAutenticato(boolean autenticato) {
		this.autenticato = autenticato;
	}
	public int getCodice() {
		return codice;
	}
	public void setCodice(int codice) {
		this.codice = codice;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserParents other = (UserParents) obj;
		if (autenticato != other.autenticato)
			return false;
		if (codice != other.codice)
			return false;
		if (cognome == null) {
			if (other.cognome != null)
				return false;
		} else if (!cognome.equals(other.cognome))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (usarname == null) {
			if (other.usarname != null)
				return false;
		} else if (!usarname.equals(other.usarname))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "UserParents [codice=" + codice + ", nome=" + nome + ", cognome=" + cognome + ", usarname=" + usarname
				+ ", password=" + password + ", autenticato=" + autenticato + "]";
	}

}
