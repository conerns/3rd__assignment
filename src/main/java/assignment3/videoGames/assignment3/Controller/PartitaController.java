package assignment3.videoGames.assignment3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	PartitaRepository matchRepo;
	
	@RequestMapping(value="/eliminaPartita/{partitaId}/{torneoId}", method=RequestMethod.GET)
	public String partitaDelete(@PathVariable Long partitaId, @PathVariable Long torneoId) {
		TorneoModel torneoPartita = cupRepo.findById(torneoId).orElse(null);
		PartitaModel partita = matchRepo.findById(partitaId).orElse(null);	
		torneoPartita.getPartiteTorneo().remove(partita);		
		SquadraModel andata = partita.getAgainst();
		andata.getPartiteSvolte().remove(partita);
		SquadraModel ritorno = partita.getHome();
		ritorno.getPartiteSvolte().remove(partita);	
		if(torneoPartita.getPartiteTorneo().size() ==0)
			torneoPartita.setVincitrice(null);
		cupRepo.save(torneoPartita);
		matchRepo.delete(partita);		
		return "redirect:/torneo/{torneoId}/partite";
	}
	@RequestMapping(value="/inserimentoPartita/{torneoId}", method=RequestMethod.GET)
	public String partiteTorneo(@PathVariable Long torneoId, Model model) {		
		model.addAttribute("azioniPartita", "creaPartita");
		model.addAttribute("torneoPartita", cupRepo.findById(torneoId).orElse(null));
		model.addAttribute("squadreScelta", teamRepo.findAll());
		return "azioniPartita";
	}	
	@RequestMapping(value="/creaPartita/{torneoId}", method=RequestMethod.GET)
	public String creaPartita(@PathVariable Long torneoId, 
			@RequestParam String squadraHomeId,
			@RequestParam String squadraAgainstId,
			@RequestParam String puntiHome,
			@RequestParam String puntiAgainst,
			Model model) {	
		if(squadraHomeId.equals(squadraAgainstId)) {
			model.addAttribute("erroreGenerato", "Non si può scegliere la stessa squadra");
			return "/error";
		}
		PartitaModel nuova = new PartitaModel(teamRepo.findById(Long.parseLong(squadraHomeId)).orElse(null), 
				teamRepo.findById(Long.parseLong(squadraAgainstId)).orElse(null), 
				Integer.parseInt(puntiHome), 
				Integer.parseInt(puntiAgainst));		
		TorneoModel torneo = cupRepo.findById(torneoId).orElse(null);
		torneo.getPartiteTorneo().add(nuova);
		nuova.getAgainst().getPartiteSvolte().add(nuova);
		nuova.getHome().getPartiteSvolte().add(nuova);
		matchRepo.save(nuova);
		teamRepo.save(nuova.getAgainst());
		teamRepo.save(nuova.getHome());
		cupRepo.save(torneo);
		return "redirect:/torneo/{torneoId}/partite";
	}
	@RequestMapping(value="/modificaPartita/{torneoId}/{partitaId}", method=RequestMethod.GET)
	public String modificaPartita(@PathVariable Long torneoId, @PathVariable Long partitaId, Model model) {		
		model.addAttribute("azioniPartita", "modificaPartita");				
		model.addAttribute("partitaDaModificare", matchRepo.findById(partitaId).orElse(null));
		model.addAttribute("torneoPartita", cupRepo.findById(torneoId).orElse(null));
		model.addAttribute("squadraScelta", teamRepo.findAll());
		return "azioniPartita";
	}	
	//se possiamo modificare una partita, dobbiamo anche modificare la partita attuale nel torneo
	@RequestMapping(value="/aggiornamentoPartita/{torneoId}/{partitaId}", method=RequestMethod.GET)
	public String aggiornamentoPartita(@PathVariable Long torneoId, @PathVariable Long partitaId,
			@RequestParam String squadraHome,
			@RequestParam String squadraAgainst,
			@RequestParam String turniHome,
			@RequestParam String turniAgainst, Model model) {
		if(squadraAgainst.equals(squadraHome))
			return "error";
		TorneoModel torneoPartita = cupRepo.findById(torneoId).orElse(null);
		PartitaModel daModificare = matchRepo.findById(partitaId).orElse(null);
		torneoPartita.getPartiteTorneo().remove(daModificare);
		cupRepo.save(torneoPartita);
		daModificare.setAgainst(teamRepo.findById(Long.parseLong(squadraAgainst)).orElse(null));
		daModificare.setHome(teamRepo.findById(Long.parseLong(squadraHome)).orElse(null));
		daModificare.setHomeRounds(Integer.parseInt(turniHome));
		daModificare.setAgainstRounds(Integer.parseInt(turniAgainst));
		torneoPartita.getPartiteTorneo().add(daModificare);
		matchRepo.save(daModificare);
		teamRepo.save(daModificare.getAgainst());
		teamRepo.save(daModificare.getHome());		
		cupRepo.save(torneoPartita);
		return "redirect:/torneo/{torneoId}/partite";
	}
}
