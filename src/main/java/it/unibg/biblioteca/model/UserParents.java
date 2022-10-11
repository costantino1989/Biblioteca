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
		
		UserParents other = (UserParents) obj;
		if (other==null || autenticato != other.autenticato || codice != other.codice || (cognome == null && other.cognome != null)
				|| (cognome!=null && !cognome.equals(other.cognome)) || (nome == null && other.nome != null) || 
				(nome!=null && !nome.equals(other.nome)) || 
				(password == null && other.password != null) || (password!=null && !password.equals(other.password)) || 
				(usarname == null && other.usarname != null) || (usarname!=null &&!usarname.equals(other.usarname))){
			return false;
		}

		return true;
	}
	@Override
	public String toString() {
		return "UserParents [codice=" + codice + ", nome=" + nome + ", cognome=" + cognome + ", usarname=" + usarname
				+ ", password=" + password + ", autenticato=" + autenticato + "]";
	}

}
