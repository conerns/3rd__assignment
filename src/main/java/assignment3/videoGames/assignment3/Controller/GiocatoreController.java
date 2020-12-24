package assignment3.videoGames.assignment3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import assignment3.videoGames.assignment3.Model.GiocatoreModel;
import assignment3.videoGames.assignment3.Repository.GiocatoreRepository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;

@Controller
public class GiocatoreController {
	@Autowired
	SquadraRepository teamRepo;
	@Autowired
	GiocatoreRepository playerRepo;
	
	@RequestMapping("/giocatore/{playerId}")
	public String playerProfile(@PathVariable Long playerId, Model model) {
		model.addAttribute("singoloUtente", playerRepo.findById(playerId).orElse(null));
		model.addAttribute("amiciUtente", playerRepo.findById(playerId).orElse(null).getAmici());
		return "paginaUtente";
	}
	
	@RequestMapping(value="/modificaGiocatore/{playerId}", method=RequestMethod.GET)
	public String updateUser( @PathVariable Long playerId, Model model) {
		GiocatoreModel giocatore = playerRepo.findById(playerId).orElse(null);
		model.addAttribute("azionegiocatore", "aggiornamento");
		model.addAttribute("giocatore", giocatore);
		return "azioniGiocatore";
	}
	@RequestMapping(value="/aggiornaGiocatore/{playerId}", method=RequestMethod.GET)
	public String UserUpdate(@PathVariable Long playerId, @RequestParam String nickname, @RequestParam String nome, @RequestParam String cognome, Model model) {
		GiocatoreModel giocatore = playerRepo.findById(playerId).orElse(null);
		giocatore.setNikname(nickname);
		giocatore.setNome(nome);
		giocatore.setCognome(cognome);        
		  
		playerRepo.save(giocatore);
		model.addAttribute("updatedUser", giocatore);
		return "redirect:/giocatore/{playerId}";
	}
}
