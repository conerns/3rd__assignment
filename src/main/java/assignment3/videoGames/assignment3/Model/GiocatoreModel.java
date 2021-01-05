package assignment3.videoGames.assignment3.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class GiocatoreModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nikname;
	private String Nome;
	private String Cognome;
	
	@ManyToOne
	private SquadraModel squadra;
	
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
		return this.amici;
	}
	
	public void setAmici(List<GiocatoreModel> listaamici) {
		this.amici = listaamici;
	}
	
	public String getNikname() {
		return nikname;
	}
	public void setSquadra(SquadraModel squadra) {
		this.squadra = squadra;
	}
	public String getNome() {
		return Nome;
	}
	public SquadraModel getSquadra() {
		return squadra;
	}
	public long getId() {
		return id;
	}
}
