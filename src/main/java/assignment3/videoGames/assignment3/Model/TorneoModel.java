package assignment3.videoGames.assignment3.Model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class TorneoModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String nomeTorneo; 
	@OneToOne
	private SquadraModel vincitrice;
	@OneToMany
	List <PartitaModel> partiteTorneo;
	@OneToOne
	MajorModel majorAppartenenza;
	public TorneoModel() {
		super();
	}
	public TorneoModel(String nomeTorneo, List<PartitaModel> partiteTorneo, SquadraModel squadraVincente, MajorModel majorAppartenenza) {
		super();
		this.nomeTorneo = nomeTorneo;
		this.partiteTorneo = partiteTorneo;
		majorAppartenenza.setTorneoMajor(this);
		this.majorAppartenenza = majorAppartenenza;
		this.vincitrice = squadraVincente;
	}
	public TorneoModel(String nomeTorneo, List<PartitaModel> partiteTorneo, SquadraModel squadraVincente) {
		super();
		this.nomeTorneo = nomeTorneo;
		this.partiteTorneo = partiteTorneo;
		this.vincitrice = squadraVincente;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNomeTorneo() {
		return nomeTorneo;
	}
	public void setNomeTorneo(String nomeTorneo) {
		this.nomeTorneo = nomeTorneo;
	}
	public List<PartitaModel> getPartiteTorneo() {
		return partiteTorneo;
	}
	public void setPartiteTorneo(List<PartitaModel> partiteTorneo) {
		this.partiteTorneo = partiteTorneo;
	}
	public SquadraModel getVincitrice() {
		return vincitrice;
	}
	public void setVincitrice(SquadraModel vincitrice) {
		this.vincitrice = vincitrice;
	}
	public MajorModel getMajorAppartenenza() {
		return majorAppartenenza;
	}
	public void setMajorAppartenenza(MajorModel majorAppartenenza) {
		this.majorAppartenenza = majorAppartenenza;
	}
}
