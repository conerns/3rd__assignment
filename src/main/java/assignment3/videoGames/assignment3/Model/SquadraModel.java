package assignment3.videoGames.assignment3.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class SquadraModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nomeSquadra;
	private String giocoSquadra;	 
	private long dimensioneSquadra = 0;
	@OneToMany
	private List<GiocatoreModel> componenti;
	
	@ManyToMany
	private List<PartitaModel> partiteSvolte;
	
	public SquadraModel() {
		super();		
	}
	public SquadraModel(String nomeSquadra, String giocoSquadra, List<GiocatoreModel> componenti) {
		super();
		this.nomeSquadra = nomeSquadra;
		this.giocoSquadra = giocoSquadra;
		this.componenti = componenti;
		if(componenti!=null)
			this.dimensioneSquadra = componenti.size();
	}
	public List<GiocatoreModel> getComponenti() {
		return componenti;
	}
	public String getGiocoSquadra() {
		return giocoSquadra;
	}
	public long getId() {
		return id;
	}
	public String getNomeSquadra() {
		return nomeSquadra;
	}
	
	public void setComponenti(List<GiocatoreModel> componenti) {
		for(GiocatoreModel g : componenti)
			g.setSquadra(this);
		this.componenti = componenti;
		this.dimensioneSquadra = componenti.size();
	}
	public void setGiocoSquadra(String giocoSquadra) {
		this.giocoSquadra = giocoSquadra;
		
	}
	public void setNomeSquadra(String nomeSquadra) {
		this.nomeSquadra = nomeSquadra;
	}
	public boolean hasPlayer(GiocatoreModel player) {
		for(GiocatoreModel giocatore: getComponenti()) {
			if(giocatore.getId() == player.getId())
				return true;
		}
		return false;
	}
	public List<PartitaModel> getPartiteSvolte() {
		return partiteSvolte;
	}
	public void setPartiteSvolte(List<PartitaModel> partiteSvolte) {
		this.partiteSvolte = partiteSvolte;
	}
	public long getDimensioneSquadra() {
		return dimensioneSquadra;
	}
	public void setDimensioneSquadra(long dimensioneSquadra) {
		this.dimensioneSquadra = dimensioneSquadra;
	}
}
