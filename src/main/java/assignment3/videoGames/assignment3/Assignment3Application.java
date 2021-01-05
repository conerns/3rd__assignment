package assignment3.videoGames.assignment3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import assignment3.videoGames.assignment3.Model.GiocatoreModel;
import assignment3.videoGames.assignment3.Model.MajorModel;
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

@SpringBootApplication
public class Assignment3Application implements CommandLineRunner{
	@Autowired
	SquadraRepository teamRepo;
	@Autowired
	GiocatoreRepository playerRepo;
	@Autowired
	MajorRespository majorRepo;
	@Autowired
	PartitaRepository matchRepo;
	@Autowired
	TorneiRepository cupRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(Assignment3Application.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		GiocatoreModel teo = new GiocatoreModel("ed", "Matteo", "Pisano");
		GiocatoreModel danu = new GiocatoreModel("edd", "Andrei", "Taraboi");
		GiocatoreModel io = new GiocatoreModel("eddy", "Daniel", "Taraboi");

		GiocatoreModel g1 = new GiocatoreModel("gl1ve", "Nome", "Cognome");
		GiocatoreModel g2 = new GiocatoreModel("kennyS", "Nome", "Cognome");
		GiocatoreModel g3 = new GiocatoreModel("S1mple", "Nome", "Cognome");
		
		playerRepo.save(teo);
		playerRepo.save(danu);
		playerRepo.save(io);	
		playerRepo.save(g1);
		playerRepo.save(g2);
		playerRepo.save(g3);
		
		List <GiocatoreModel> anchequi = new LinkedList<>();
		List <GiocatoreModel> professionisti = new LinkedList<>();
		
		
		teo.setAmici(List.of(danu, io));
		danu.setAmici(List.of(teo, io));
		io.setAmici(List.of(teo, danu));
		
		playerRepo.save(teo);
		playerRepo.save(danu);
		playerRepo.save(io);	
		playerRepo.save(g1);
		playerRepo.save(g2);
		playerRepo.save(g3);
		
		anchequi.add(danu);		
		anchequi.add(io);
		anchequi.add(teo);
				
		
		professionisti.add(g1);
		professionisti.add(g2);
		professionisti.add(g3);
		
		SquadraModel mache = new SquadraProfessionistaModel( "Astralis", "CS:GO", professionisti, 2, 248447);	
		
		SquadraModel prova = new SquadraAmatorialeModel("Gli Edd", "CS:GO", anchequi,"TAXI");		
		PartitaModel andata = new PartitaModel(mache,prova, 12, 16);
		PartitaModel ritorno = new PartitaModel(prova,mache, 16, 10);
		
		PartitaModel andata2 = new PartitaModel(mache,prova, 13, 16);
		PartitaModel ritorno2 = new PartitaModel(prova,mache, 11, 16);
		
		TorneoModel torneoSingolo = new TorneoModel("Bronze Cup 2014- Winter Edition", List.of(andata, ritorno));
		TorneoModel torneoSingolo2 = new TorneoModel("Bronze Cup 2015- Winter Edition", List.of(andata2, ritorno2));
		teamRepo.save(prova);
		teamRepo.save(mache);	
		
		g1.setSquadra(mache);
		g2.setSquadra(mache);
		g3.setSquadra(mache);
		danu.setSquadra(prova);
		teo.setSquadra(prova);
		io.setSquadra(prova);
		
		playerRepo.save(teo);
		playerRepo.save(danu);
		playerRepo.save(io);	
		playerRepo.save(g1);
		playerRepo.save(g2);
		playerRepo.save(g3);
		
		matchRepo.save(andata);		
		matchRepo.save(ritorno);
		
		matchRepo.save(andata2);		
		matchRepo.save(ritorno2);
		
		cupRepo.save(torneoSingolo);	
		cupRepo.save(torneoSingolo2);	
		List <SquadraModel> prof = new LinkedList<SquadraModel>();
		prof.add(prova);
		MajorModel kato2014 = new MajorModel("Polonia", "Intel", 500000, prof);
		majorRepo.save(kato2014);
	}
}
