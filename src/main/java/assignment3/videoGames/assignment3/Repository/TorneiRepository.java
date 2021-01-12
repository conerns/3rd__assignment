package assignment3.videoGames.assignment3.Repository;

import org.springframework.data.repository.CrudRepository;
import assignment3.videoGames.assignment3.Model.TorneoModel;
import java.util.List;

public interface TorneiRepository extends CrudRepository<TorneoModel, Long>{
	/**
	 * Cerca i Tornei all'interno delle quali la squadra vincitrice ha X componenti
	 *  @param componenti x
	 */
	List<TorneoModel> findByVincitrice_DimensioneSquadra(long componenti);
}

