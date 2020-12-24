package assignment3.videoGames.assignment3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import assignment3.videoGames.assignment3.Repository.GiocatoreRepository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;

@Controller
public class SquadraController {
	@Autowired
	SquadraRepository teamRepo;
	@Autowired
	GiocatoreRepository playerRepo;
	
	@RequestMapping(value="/squadre",method=RequestMethod.GET)
	public String teamRepository(Model model) {
		model.addAttribute("squadre", teamRepo.findAll());
		return "squadre";
	}
}
