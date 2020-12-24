package assignment3.videoGames.assignment3.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class GiocatoreModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nikname;
	private String Nome;
	private String Cognome;
	@ManyToMany
	private List<GiocatoreModel> amici;
	
	public GiocatoreModel() {
		super();
	}
	public GiocatoreModel(String nikname, String nome, String cognome) {
		super();
		this.nikname = nikname;
		this.Nome = nome;
		this.Cognome = cognome;		
	}
	public void setNikname(String nikname) {
		this.nikname = nikname;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public List<GiocatoreModel> getAmici() {
		return amici;
	}
	
	public void setAmici(List<GiocatoreModel> amici) {
		this.amici = amici;
	}
	
	public String getNikname() {
		return nikname;
	}
	public String getNome() {
		return Nome;
	}
	public boolean hasTeam(SquadraModel squadra) {
		//if(squadra.getId() =  ) 
		return true;
	}
	public long getId() {
		return id;
	}
}
