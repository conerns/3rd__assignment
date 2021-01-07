package assignment3.videoGames.assignment3.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import assignment3.videoGames.assignment3.Model.GiocatoreModel;
import assignment3.videoGames.assignment3.Model.PartitaModel;
import assignment3.videoGames.assignment3.Model.SquadraAmatorialeModel;
import assignment3.videoGames.assignment3.Model.SquadraModel;
import assignment3.videoGames.assignment3.Model.SquadraProfessionistaModel;
import assignment3.videoGames.assignment3.Model.TorneoModel;
import assignment3.videoGames.assignment3.Repository.GiocatoreRepository;
import assignment3.videoGames.assignment3.Repository.MajorRespository;
import assignment3.videoGames.assignment3.Repository.PartitaRepository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;
import assignment3.videoGames.assignment3.Repository.TorneiRepository;

@Controller
public class SquadraController {
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
	
	/*@RequestMapping(value="/squadre",method=RequestMethod.GET)
	public String teamRepository(Model model) {
		model.addAttribute("squadre", teamRepo.findAll());
		return "squadre";
	}*/
	
	@RequestMapping(value="/squadre",method=RequestMethod.GET)
	public String profTeam(Model model) {
		List<SquadraModel> soloProf = new ArrayList<SquadraModel>();
		List<SquadraModel> soloAmm = new ArrayList<SquadraModel>();
		for(SquadraModel singola: teamRepo.findAll()) {
			if(singola instanceof SquadraProfessionistaModel)
				soloProf.add(singola);
			else if(singola instanceof SquadraAmatorialeModel)
				soloAmm.add(singola);
		}
		model.addAttribute("squadre", soloProf);
		model.addAttribute("squadreAmm", soloAmm);
		return "squadre";
	}
	
	@RequestMapping("/squadra/{teamId}")
	public String playerProfile(@PathVariable Long teamId, Model model) {
		model.addAttribute("singolaSquadra", teamRepo.findById(teamId).orElse(null));
		return "paginaSquadra";
	}
	
	
}
