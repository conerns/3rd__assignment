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
	
	@RequestMapping(value="/squadre")
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
		model.addAttribute("distinzioneSchermata", new String((teamRepo.findById(teamId).orElse(null) instanceof SquadraProfessionistaModel) +""));
		return "paginaSquadra";
	}
	
	@RequestMapping(value="/eliminaSquadra/{teamId}", method=RequestMethod.GET)
	public String teamDelete(@PathVariable Long teamId) {
		SquadraModel squadra = teamRepo.findById(teamId).orElse(null);
		List<GiocatoreModel> componenti = squadra.getComponenti();
		//le partite sono state giocate, non avrebbe senso che non siano presenti
		//devono però sparire i collegamenti tra squadra e giocatori
		if(componenti!=null) {
			for(GiocatoreModel singolo: componenti) {
				singolo.setSquadra(null);
				playerRepo.save(singolo);
			}
		}
		squadra.getComponenti().removeAll(componenti);
		squadra.setNomeSquadra("Ex - " + squadra.getNomeSquadra());
		teamRepo.save(squadra);		
		return "redirect:/squadre";
	}
	
	@RequestMapping(value="/inserimentoSquadraProf", method=RequestMethod.GET)
	public String aggiuntaSquadraProf(Model model) {		
		model.addAttribute("azioneSquadra", "inserimentoProf");
		return "azioniSquadra";
	}
	@RequestMapping(value="/inserimentoSquadraAmm", method=RequestMethod.GET)
	public String aggiuntaSquadraAmm(Model model) {		
		model.addAttribute("azioneSquadra", "inserimentoAmm");
		return "azioniSquadra";
	}	
	@RequestMapping(value="/creaSquadraProfessionale", method=RequestMethod.POST)
	public String profTeamAdd(@RequestParam String nomeSquadra, 
			//@RequestParam String giocoSquadra, forse CSGO default
			@RequestParam String major, @RequestParam String totalWin, Model model) {
		SquadraModel prof = new SquadraProfessionistaModel(nomeSquadra,"CS:GO", null, 
				Integer.parseInt(major),
				Integer.parseInt(totalWin));		
        teamRepo.save(prof);    
        return "redirect:/squadre";
	}	
	@RequestMapping(value="/creaSquadraAmatoriale", method=RequestMethod.POST)
	public String ammTeamAdd(@RequestParam String nomeSquadra, 
			@RequestParam String nameTag, Model model) {
		SquadraModel prof = new SquadraAmatorialeModel(nomeSquadra,"CS:GO", null, nameTag);
        teamRepo.save(prof);    
        return "redirect:/squadre";
	}
	
	@RequestMapping(value="/modificaSquadraProf/{squadraId}", method=RequestMethod.GET)
	public String modificaSquadraProf(@PathVariable Long squadraId, Model model) {		
		model.addAttribute("azioneSquadra", "modificaProf");
		model.addAttribute("squadra", teamRepo.findById(squadraId).orElse(null));
		return "azioniSquadra";
	}
	@RequestMapping(value="/modificaSquadraAm/{squadraId}", method=RequestMethod.GET)
	public String modificaSquadraAmm(@PathVariable Long squadraId, Model model) {		
		model.addAttribute("azioneSquadra", "modificaAm");
		model.addAttribute("squadra", teamRepo.findById(squadraId).orElse(null));
		return "azioniSquadra";
	}
	@RequestMapping(value="/aggiornamentoSquadraProf/{squadraId}", method=RequestMethod.GET)
	public String aggiornamentoSquadraProf(@PathVariable Long squadraId,
			@RequestParam String nomeSquadra,
			@RequestParam String numeroMajorVinte,
			@RequestParam String totalWinning, Model model) {
		SquadraProfessionistaModel attuale = (SquadraProfessionistaModel) teamRepo.findById(squadraId).orElse(null);
		attuale.setNomeSquadra(nomeSquadra);
		attuale.setNumeroMajorVinte(Integer.parseInt(numeroMajorVinte));
		attuale.setTotalWinning(Integer.parseInt(totalWinning));
		teamRepo.save(attuale);
		return "redirect:/squadra/{squadraId}";
	}
	@RequestMapping(value="/aggiornamentoSquadraAmm/{squadraId}", method=RequestMethod.GET)
	public String aggiornamentoSquadraAmm(@PathVariable Long squadraId,
			@RequestParam String nomeSquadra,
			@RequestParam String nameTag, Model model) {
		SquadraAmatorialeModel attuale = (SquadraAmatorialeModel) teamRepo.findById(squadraId).orElse(null);
		attuale.setNomeSquadra(nomeSquadra);
		attuale.setNameTag(nameTag);
		teamRepo.save(attuale);
		return "redirect:/squadra/{squadraId}";
	}
}
