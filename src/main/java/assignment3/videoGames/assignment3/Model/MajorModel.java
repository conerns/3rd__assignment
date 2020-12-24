package assignment3.videoGames.assignment3.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class MajorModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String paeseSvolgimento;
	private String organizzatore;
	private int montepremi;
	@ManyToMany
	private List<SquadraModel> squadrePartecipanti;
	
	public MajorModel() {
		super(); 
	}
	public MajorModel(String paeseSvolgimento, String organizzatore, int montepremi, List<SquadraModel> squadrePartecipanti ) {
		super();		
		this.paeseSvolgimento = paeseSvolgimento;
		this.organizzatore = organizzatore;
		this.montepremi = montepremi;
		for(SquadraModel singola : squadrePartecipanti) {
			if (squadrePartecipanti instanceof SquadraProfessionistaModel) {
				SquadraProfessionistaModel new_name = (SquadraProfessionistaModel) squadrePartecipanti;
				squadrePartecipanti.add(new_name);
			}
		}
		
	}
	
	public long getId() {
		return id;
	}
	
	public int getMontepremi() {
		return montepremi;
	}
	public String getOrganizzatore() {
		return organizzatore;
	}
	public String getPaeseSvolgimento() {
		return paeseSvolgimento;
	}
	public void setPaeseSvolgimento(String paeseSvolgimento) {
		this.paeseSvolgimento = paeseSvolgimento;
	}
	public List<SquadraModel> getSquadrePartecipanti() {
		return squadrePartecipanti;
	}
	public void setSquadrePartecipanti(List<SquadraModel> squadrePartecipanti) {
		this.squadrePartecipanti = squadrePartecipanti;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setOrganizzatore(String organizzatore) {
		this.organizzatore = organizzatore;
	}
	public void setMontepremi(int montepremi) {
		this.montepremi = montepremi;
	}
	
}
