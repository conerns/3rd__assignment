package assignment3.videoGames.assignment3.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.GregorianCalendar;
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
	private LocalDate dataTorneo;
	@ManyToMany
	private List<SquadraModel> squadrePartecipanti;
	
	public MajorModel() {
		super(); 
	}
	
	public MajorModel(String paeseSvolgimento, String organizzatore, int montepremi, List<SquadraModel> squadrePartecipanti) {
		this(paeseSvolgimento, organizzatore, montepremi, squadrePartecipanti, (LocalDate) java.time.LocalDate.now());				
	}

	public MajorModel(String paeseSvolgimento, String organizzatore, int montepremi, List<SquadraModel> squadrePartecipanti, LocalDate dataTorneo) {
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
		this.dataTorneo = dataTorneo;
		
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
	public LocalDate getDataTorneo() {
		return dataTorneo;
	}
}
