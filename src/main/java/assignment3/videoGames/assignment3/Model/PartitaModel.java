package assignment3.videoGames.assignment3.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PartitaModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int homeRounds;
	private int againstRounds;		
	@OneToOne
	SquadraModel home; 	
	@OneToOne	
	SquadraModel against; 	
	
		
	public PartitaModel() {
		super();
	}
	/**
	 * Si presenta nella forma: Squadra vs Squadra : 15 - 14
	 * */
	public PartitaModel(SquadraModel home, SquadraModel against, int homeRounds, int againstRounds) {
		super();
		this.againstRounds = againstRounds;
		this.homeRounds = homeRounds;
		this.home = home;
		this.against = against;
	}
	/**
	 * Storico delle partite di un giocatore
	 * */
	public PartitaModel(GiocatoreModel giocatore, int homeRounds, int againstRounds) {
		super();		
	}
	
	public int getHomeRounds() {
		return homeRounds;
	}
	public void setHomeRounds(int homeRounds) {
		this.homeRounds = homeRounds;
	}
	public int getAgainstRounds() {
		return againstRounds;
	}
	public void setAgainstRounds(int againstRounds) {
		this.againstRounds = againstRounds;
	}
	public SquadraModel getHome() {
		return home;
	}
	public void setHome(SquadraModel home) {
		this.home = home;
	}
	public SquadraModel getAgainst() {
		return against;
	}
	public void setAgainst(SquadraModel against) {
		this.against = against;
	}
	public long getId() {
		return id;
	}
}
