package assignment3.videoGames.assignment3.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import assignment3.videoGames.assignment3.Model.MajorModel;
import assignment3.videoGames.assignment3.Model.TorneoModel;

public interface MajorRespository extends CrudRepository<MajorModel, Long> {
	/**
	 * Cerca le major all'interno delle quali è presente un Torneo con x partite svolte 
	 *  @param partiteSvolte numero di partite x
	 */
	List<MajorModel> findByTorneoMajorVincitrice_NumeroPartite(long partiteSvolte);
}
