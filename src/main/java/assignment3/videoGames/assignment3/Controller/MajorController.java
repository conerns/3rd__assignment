package assignment3.videoGames.assignment3.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import assignment3.videoGames.assignment3.Repository.MajorRespository;
import assignment3.videoGames.assignment3.Repository.SquadraRepository;

@Controller
public class MajorController {
	@Autowired
	SquadraRepository teamRepo;
	@Autowired
	MajorRespository majorRepo;
	@RequestMapping(value="/majors",method=RequestMethod.GET)
	public String teamRepository(Model model) {
		model.addAttribute("majors", majorRepo.findAll());
		return "majors";
	}
	
}
