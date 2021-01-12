package assignment3.videoGames.assignment3.Controller;


import java.util.ArrayList;
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
import assignment3.videoGames.assignment3.Repository.GiocatoreRepository;
import assignment3.videoGames.assignment3.Repository.MajorRespository;
import assignment3.videoGames.assignment3.Repository.PartitaRepository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;
import assignment3.videoGames.assignment3.Repository.TorneiRepository;

@Controller
public class TorneoController {
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
	@RequestMapping(value="/tornei",method=RequestMethod.GET)
	public String torneiRepository(Model model) {
		model.addAttribute("listaTornei", cupRepo.findAll());
		return "tornei";
	}
	
	@RequestMapping(value="/torneo/{id}/partite",method=RequestMethod.GET)
	public String partiteTorneiRepository(@PathVariable Long id , Model model) {
		model.addAttribute("partiteTorneo", cupRepo.findById(id).orElse(null));
		return "partiteTorneo";
	}
	
	@RequestMapping(value="/inserimentoTorneo", method=RequestMethod.GET)
	public String creazioneTorneo(Model model) {		
		model.addAttribute("azioniTorneo", "creaTorneo");
		return "azioniTorneo";
	}
	@RequestMapping(value="/modificaTorneo/{torneoId}", method=RequestMethod.GET)
	public String modificaTorneo( @PathVariable Long torneoId, Model model) {
		TorneoModel aggiorna = cupRepo.findById(torneoId).orElse(null);	
		List<PartitaModel> partiteSvolte = aggiorna.getPartiteTorneo();
		model.addAttribute("azioniTorneo", "updateTorneo");
		model.addAttribute("torneoAggiorna", aggiorna);
		List<SquadraModel> squadrePartecipanti = new ArrayList<>();
		for(SquadraModel squadra : teamRepo.findAll()) {
			if(!squadrePartecipanti.contains(squadra)) {
				for(PartitaModel partita : partiteSvolte) {
					if(partita.getAgainst().equals(squadra) || partita.getHome().equals(squadra))
						if(!squadrePartecipanti.contains(squadra)) {
							squadrePartecipanti.add(squadra);
					}
				}
			}
		}
		model.addAttribute("squadreVincitrice", squadrePartecipanti);
		return "azioniTorneo";
	}
	@RequestMapping(value="/aggiornaTorneo/{torneoId}", method=RequestMethod.GET)
	public String aggiornaTorneo(@PathVariable Long torneoId,
			@RequestParam String nomeTorneo,
			@RequestParam(value = "vincitrice", required = false) String vincitrice,
			Model model) {
		TorneoModel aggiorna = cupRepo.findById(torneoId).orElse(null);
		aggiorna.setNomeTorneo(nomeTorneo);
		if(vincitrice!=null)
			aggiorna.setVincitrice(teamRepo.findById(Long.parseLong(vincitrice)).orElse(null));
		cupRepo.save(aggiorna);
		return "redirect:/tornei";
	}
	
	@RequestMapping(value="/modificaTorneoMajor/{torneoId}", method=RequestMethod.GET)
	public String modificaTorneoMajor(@PathVariable Long torneoId, Model model) {
		TorneoModel aggiorna = cupRepo.findById(torneoId).orElse(null);
		model.addAttribute("azioniTorneo", "updateTorneoMajor");
		model.addAttribute("daAggiornare", aggiorna);
		model.addAttribute("vinceSe", (aggiorna.getMajorAppartenenza().getSquadrePartecipanti().size()>1));
		return "azioniTorneo";
	}
	
	
	@RequestMapping(value="/aggiornaTorneoMajor/{torneoId}", method=RequestMethod.GET)
	public String aggiornaTorneoMajor(@PathVariable Long torneoId,
			@RequestParam String nomeTorneo,
			@RequestParam(value = "vincitrice", required = false) String vincitrice,
			Model model) {
		TorneoModel aggiorna = cupRepo.findById(torneoId).orElse(null);
		SquadraProfessionistaModel vince;
		aggiorna.setNomeTorneo(nomeTorneo);
		if(vincitrice!=null) {
			vince = (SquadraProfessionistaModel) teamRepo.findById(Long.parseLong(vincitrice)).orElse(null);
			aggiorna.setVincitrice(vince);
			vince.setNumeroMajorVinte(vince.getNumeroMajorVinte()+1);
			cupRepo.save(aggiorna);
			teamRepo.save(vince);
		}
		return "redirect:/tornei";
	}
	
	@RequestMapping(value="/eliminaTorneo/{torneoId}", method=RequestMethod.GET)
	public String eliminaTorneo(@PathVariable Long torneoId, Model model) {
		TorneoModel aggiorna = cupRepo.findById(torneoId).orElse(null);
		MajorModel majorAp = aggiorna.getMajorAppartenenza();
		List<PartitaModel> partiteTorneo = aggiorna.getPartiteTorneo();		
		if(partiteTorneo!=null) {
			for(PartitaModel singola: partiteTorneo) {
				singola.getAgainst().getPartiteSvolte().remove(singola);
				singola.getHome().getPartiteSvolte().remove(singola);							
			}
			aggiorna.getPartiteTorneo().removeAll(partiteTorneo);
			matchRepo.deleteAll(partiteTorneo);			
		}
		if(majorAp!=null) { //è il torneo di una major
			majorAp = aggiorna.getMajorAppartenenza();
			majorAp.setTorneoMajor(null);			
			majorRepo.save(majorAp);	
			aggiorna.setMajorAppartenenza(null);
			cupRepo.save(aggiorna);
			majorRepo.delete(majorAp);
		}
		cupRepo.delete(aggiorna);
		return "redirect:/tornei";
	}
	
	@RequestMapping(value="/creaTorneo", method=RequestMethod.POST)
	public String tournAdd(@RequestParam String nomeTorneo, Model model) {
		TorneoModel torneo = new TorneoModel(nomeTorneo, null, null);
        cupRepo.save(torneo);    
        return "redirect:/tornei";
	}	
	
	@RequestMapping(value="/cercaT", method=RequestMethod.GET)
	public String getRequired(@RequestParam String componenti, Model model) {
	model.addAttribute("listaTornei", cupRepo.findByVincitrice_DimensioneSquadra(Long.parseLong(componenti)));
    return "tornei";
	}
	
}
