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
import assignment3.videoGames.assignment3.Model.SquadraModel;
import assignment3.videoGames.assignment3.Repository.GiocatoreRepository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;

@Controller
public class GiocatoreController {
	@Autowired
	SquadraRepository teamRepo;
	@Autowired
	GiocatoreRepository playerRepo;
	/**
	 * Pagina che mostra il profilo utente del giocatore
	 * */
	@RequestMapping("/giocatore/{playerId}")
	public String playerProfile(@PathVariable Long playerId, Model model) {
		model.addAttribute("singoloUtente", playerRepo.findById(playerId).orElse(null));
		// model.addAttribute("amiciUtente", playerRepo.findById(playerId).orElse(null).getAmici());
		return "paginaUtente";
	}
	/**
	 * Pagina che mostra il tutti i giocatori
	 * */
	@RequestMapping("/listaGiocatori")
	public String playersList(Model model) {
		model.addAttribute("GiocatoriLista", playerRepo.findAll());
		return "giocatori";
	}		
	@RequestMapping(value="/modificaGiocatore/{playerId}", method=RequestMethod.GET)
	public String modificaPlayer( @PathVariable Long playerId, Model model) {
		GiocatoreModel giocatore = playerRepo.findById(playerId).orElse(null);
		model.addAttribute("azionegiocatore", "aggiornamento");
		model.addAttribute("giocatore", giocatore);
		return "azioniGiocatore";
	}
	@RequestMapping(value="/inserimentoGiocatore", method=RequestMethod.GET)
	public String aggiuntaGiocatore(Model model) {		
		model.addAttribute("azionegiocatore", "inserimento");
		return "azioniGiocatore";
	}
	@RequestMapping(value="/aggiornaGiocatore/{playerId}", method=RequestMethod.GET)
	public String AzioneUtente(@PathVariable Long playerId, 
			@RequestParam String nickname, 
			@RequestParam String nome, 
			@RequestParam String cognome, 
			Model model) {
		GiocatoreModel giocatore = playerRepo.findById(playerId).orElse(null);
		giocatore.setNikname(nickname);
		giocatore.setNome(nome);
		giocatore.setCognome(cognome);        
		  
		playerRepo.save(giocatore);
		model.addAttribute("updatedUser", giocatore);
		return "redirect:/giocatore/{playerId}";
	}
	
	@RequestMapping(value="/eliminaGiocatore/{playerId}", method=RequestMethod.GET)
	public String userDelete(@PathVariable Long playerId) {
		GiocatoreModel player = playerRepo.findById(playerId).orElse(null);
		SquadraModel eliminaGiocatore = player.getSquadra();
		List<GiocatoreModel> amici = player.getAmici();		
		if(eliminaGiocatore!=null) {
			eliminaGiocatore.getComponenti().remove(player);
			teamRepo.save(eliminaGiocatore);
		}
		if(amici!=null) {
			for(GiocatoreModel amico: amici) {
				amico.getAmici().remove(player);
				playerRepo.save(amico);
			}
		}
		playerRepo.delete(player);
		return "redirect:/listaGiocatori";
	}
	@RequestMapping(value="/creaGiocatore", method=RequestMethod.POST)
	public String UserAdd(@RequestParam String nikname, @RequestParam String nome, @RequestParam String cognome, Model model) {
        GiocatoreModel nuovoGiocatore = new GiocatoreModel(nikname, nome, cognome);
        playerRepo.save(nuovoGiocatore);        
        return "redirect:/listaGiocatori";
  }
	

}

