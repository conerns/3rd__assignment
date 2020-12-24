package assignment3.videoGames.assignment3.Model;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class SquadraProfessionistaModel extends SquadraModel{
	private long numeroMajorVinte = 0;
	private long totalWinning = 0;
	
	public SquadraProfessionistaModel() {
		super();
	}
	public SquadraProfessionistaModel(String nomeSquadra, String giocoSquadra, List<GiocatoreModel> componenti, int major, int totalWinning) {
		super(nomeSquadra, giocoSquadra, componenti);
		this.numeroMajorVinte = major;
		this.totalWinning = totalWinning;
	}
	
	public long getNumeroMajorVinte() {
		return numeroMajorVinte;
	}
	public long getTotalWinning() {
		return totalWinning;
	}
	public void setNumeroMajorVinte(long numeroMajorVinte) {
		this.numeroMajorVinte = numeroMajorVinte;
	}
	public void setTotalWinning(long totalWinning) {
		this.totalWinning = totalWinning;
	}
}
