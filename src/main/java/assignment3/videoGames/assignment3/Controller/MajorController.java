package assignment3.videoGames.assignment3.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import assignment3.videoGames.assignment3.Model.GiocatoreModel;
import assignment3.videoGames.assignment3.Model.MajorModel;
import assignment3.videoGames.assignment3.Model.PartitaModel;
import assignment3.videoGames.assignment3.Model.SquadraModel;
import assignment3.videoGames.assignment3.Model.SquadraProfessionistaModel;
import assignment3.videoGames.assignment3.Model.TorneoModel;
import assignment3.videoGames.assignment3.Repository.MajorRespository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;
import assignment3.videoGames.assignment3.Repository.TorneiRepository;

@Controller
public class MajorController {
	@Autowired
	SquadraRepository teamRepo;
	@Autowired
	MajorRespository majorRepo;
	@Autowired 
	TorneiRepository cupRepo;
	@RequestMapping(value="/majors")
	public String teamRepository(Model model) {
		model.addAttribute("majors", majorRepo.findAll());		
		return "majors";
	}
	@RequestMapping(value="/modificaMajor/{majorId}",method=RequestMethod.GET)
	public String modifyMajor(@PathVariable Long majorId,Model model) {
		model.addAttribute("azioneMajor", "modifica");
		model.addAttribute("majorDaAggiornare", majorRepo.findById(majorId).orElse(null));
		return "azioniMajor";
	}
	
	@RequestMapping(value="/aggiornamentoMajor/{majorId}", method=RequestMethod.GET)
	public String aggiornamentoPartita(@PathVariable Long majorId,
			@RequestParam String paeseSvolgimento,
			@RequestParam String organizzatore,
			@RequestParam String montepremi, Model model) {	
		MajorModel daModificare = majorRepo.findById(majorId).orElse(null);		
		TorneoModel torneoMajor = daModificare.getTorneoMajor();
		daModificare.setPaeseSvolgimento(paeseSvolgimento);
		daModificare.setOrganizzatore(organizzatore);
		daModificare.setMontepremi(Integer.parseInt(montepremi));
		majorRepo.save(daModificare);
		torneoMajor.setMajorAppartenenza(daModificare);		
		cupRepo.save(torneoMajor);
		return "redirect:/majors";
	}
	
	@RequestMapping(value="/eliminaMajor/{majorId}", method=RequestMethod.GET)
	public String userDelete(@PathVariable Long majorId) {
		MajorModel daEliminare = majorRepo.findById(majorId).orElse(null);		
		TorneoModel torneoMajor = daEliminare.getTorneoMajor();
		List <PartitaModel> partiteTorneoMajor = torneoMajor.getPartiteTorneo();
		daEliminare.setTorneoMajor(null);
		SquadraModel storicoPartite;
		if(partiteTorneoMajor!=null) {
			for(PartitaModel partita : partiteTorneoMajor) {
				storicoPartite = partita.getAgainst();
				storicoPartite.getPartiteSvolte().remove(partita);
				teamRepo.save(storicoPartite);
				storicoPartite = partita.getHome();
				storicoPartite.getPartiteSvolte().remove(partita);
				teamRepo.save(storicoPartite);
			}	
			torneoMajor.getPartiteTorneo().removeAll(partiteTorneoMajor);
			cupRepo.save(torneoMajor);
		}
		torneoMajor.setMajorAppartenenza(null);
		majorRepo.delete(daEliminare);
		cupRepo.delete(torneoMajor); //se elimino la major, il torneo associato smette di esistere
		return "redirect:/majors";
	}
	
	@RequestMapping(value="/inserimentoMajor", method=RequestMethod.GET)
	public String aggiuntaMajor(Model model) {		
		model.addAttribute("azioneMajor", "inserimento");
		return "azioniMajor";
	}
	@RequestMapping(value="/creaMajor")
	public String majorAggiungi(@RequestParam String paeseSvolgimento,
			@RequestParam String organizzatore,
			@RequestParam String montepremi,
			@RequestParam String nomeTorneo,
			Model model) {
		if(paeseSvolgimento.equals("") || 
				organizzatore.equals("") ||
				montepremi.equals("")||
				nomeTorneo.equals("")) {
			model.addAttribute("erroreGenerato", "Campi mancanti durante la creazione");
			return "/error";
		}
		MajorModel major = new MajorModel(paeseSvolgimento, organizzatore, Integer.parseInt(montepremi), null);
		majorRepo.save(major);
		TorneoModel torneo = new TorneoModel(nomeTorneo, null, null, major);
        cupRepo.save(torneo);    
        majorRepo.save(major);
        return "redirect:/majors";
	}	
	
}
