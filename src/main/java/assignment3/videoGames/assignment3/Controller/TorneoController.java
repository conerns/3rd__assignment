package assignment3.videoGames.assignment3.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import assignment3.videoGames.assignment3.Model.TorneoModel;
import assignment3.videoGames.assignment3.Repository.GiocatoreRepository;
import assignment3.videoGames.assignment3.Repository.MajorRespository;
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
	
}
