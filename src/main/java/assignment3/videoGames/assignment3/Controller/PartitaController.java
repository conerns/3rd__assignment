package assignment3.videoGames.assignment3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import assignment3.videoGames.assignment3.Model.GiocatoreModel;
import assignment3.videoGames.assignment3.Model.PartitaModel;
import assignment3.videoGames.assignment3.Model.SquadraModel;
import assignment3.videoGames.assignment3.Model.TorneoModel;
import assignment3.videoGames.assignment3.Repository.GiocatoreRepository;
import assignment3.videoGames.assignment3.Repository.MajorRespository;
import assignment3.videoGames.assignment3.Repository.PartitaRepository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;
import assignment3.videoGames.assignment3.Repository.TorneiRepository;

@Controller
public class PartitaController {	
	@Autowired
	SquadraRepository teamRepo;
	@Autowired
	MajorRespository majorRepo;
	@Autowired
	GiocatoreRepository playerRepo;
	@Autowired
	TorneiRepository cupRepo;
	@Autowired
	PartitaRepository gameRepo;
	
	@RequestMapping(value="/eliminaPartita/{partitaId}/{torneoId}", method=RequestMethod.GET)
	public String userDelete(@PathVariable Long partitaId, @PathVariable Long torneoId) {
		TorneoModel torneoPartita = cupRepo.findById(torneoId).orElse(null);
		PartitaModel partita = gameRepo.findById(partitaId).orElse(null);	
		torneoPartita.getPartiteTorneo().remove(partita);
		
		SquadraModel andata = partita.getAgainst();
		andata.getPartiteSvolte().remove(partita);
		SquadraModel ritorno = partita.getHome();
		ritorno.getPartiteSvolte().remove(partita);			
		gameRepo.delete(partita);
		
		return "redirect:/torneo/{torneoId}/partite";
	}
	
}
