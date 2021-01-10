package assignment3.videoGames.assignment3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import assignment3.videoGames.assignment3.Model.MajorModel;
import assignment3.videoGames.assignment3.Model.PartitaModel;
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
	public String teamRepository(@PathVariable Long majorId,Model model) {
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
	
	
}
