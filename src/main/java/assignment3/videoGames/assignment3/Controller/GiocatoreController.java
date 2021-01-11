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
		model.addAttribute("squadreAggiornamento",teamRepo.findAll());
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
			@RequestParam String squadraId, 
			Model model) {
		GiocatoreModel giocatore = playerRepo.findById(playerId).orElse(null);
		giocatore.setNikname(nickname);
		giocatore.setNome(nome);
		giocatore.setCognome(cognome);
		SquadraModel removePlayer = null;
		if(squadraId.equals("null")) { //nessuna squadra selezionata
			if(giocatore.getSquadra()!=null) { // ma aveva una squadra
				removePlayer = teamRepo.findById(giocatore.getSquadra().getId()).orElse(null);
				removePlayer.getComponenti().remove(giocatore); //lo rimuovo dalla squadra attuale
				giocatore.setSquadra(null); 
				teamRepo.save(removePlayer);
			}
		}		
		else { 
			//ho una squadra selezionata			
			if(giocatore.getSquadra()!=null) { // aveva una squadra prima 
				removePlayer = teamRepo.findById(Long.parseLong(squadraId)).orElse(null); 
				SquadraModel squadraAttuale = teamRepo.findById(giocatore.getSquadra().getId()).orElse(null);				
				if(squadraAttuale.getId() == removePlayer.getId())//se è quella di prima non faccio nulla
					return "redirect:/giocatore/{playerId}";
				//altrimenti, cambio squadra del giocatore
				squadraAttuale.getComponenti().remove(giocatore);
				removePlayer.getComponenti().add(giocatore); //lo rimuovo dalla squadra attuale
				giocatore.setSquadra(removePlayer);
				teamRepo.save(squadraAttuale);
			}
			else { //ho selezionato una squadra e non ne aveva una prima
				removePlayer = teamRepo.findById(Long.parseLong(squadraId)).orElse(null);					
				removePlayer.getComponenti().add(giocatore);					
				giocatore.setSquadra(removePlayer);	
				teamRepo.save(removePlayer);
			}
		}

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
	
	@RequestMapping(value="/aggiungiAmico/{playerId}")
	public String aggiungiAmico(@PathVariable Long playerId, Model model) {
		model.addAttribute("azionegiocatore", "nuovoAmico");
		GiocatoreModel singolo = playerRepo.findById(playerId).orElse(null);
		List<GiocatoreModel> daScegliere = new ArrayList<GiocatoreModel>();
		for(GiocatoreModel tutti : playerRepo.findAll()) {
			if(!tutti.getAmici().contains(singolo) && !daScegliere.contains(tutti)) 
				daScegliere.add(tutti);			
		}
		daScegliere.remove(singolo);
		model.addAttribute("giocatore", playerRepo.findById(playerId).orElse(null));
		model.addAttribute("amiciScelta" , daScegliere);
		return "azioniGiocatore";
	}
	@RequestMapping(value="/aggiungiAmicoOperazione/{playerId}")
	public String aggiungiAmicoOp(@PathVariable Long playerId, 
			@RequestParam String amicoId,
			Model model) {
		GiocatoreModel giocatore = playerRepo.findById(playerId).orElse(null);
		GiocatoreModel amico = playerRepo.findById(Long.parseLong(amicoId)).orElse(null);
		giocatore.getAmici().add(amico);
		amico.getAmici().add(giocatore);
		playerRepo.saveAll(List.of(giocatore,amico));
		return "redirect:/giocatore/{playerId}";
	}
	@RequestMapping(value="/eliminaAmico/{togliA}/{togliB}")
	public String aggiungiAmicoOp(@PathVariable Long togliA,
			@PathVariable Long togliB,
			Model model) {
		GiocatoreModel giocatore = playerRepo.findById(togliA).orElse(null);
		GiocatoreModel amico = playerRepo.findById(togliB).orElse(null);
		giocatore.getAmici().remove(amico);
		amico.getAmici().remove(giocatore);
		playerRepo.saveAll(List.of(giocatore,amico));
		return "redirect:/giocatore/{togliA}";
	}
	
}

