package assignment3.videoGames.assignment3;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		GiocatoreModel g1 = new GiocatoreModel("dev1ce", "Nicolai Hvilshøj", "Reedtz");
		GiocatoreModel g2 = new GiocatoreModel("dupreeh", "Peter Rothmann", "Rasmussen");
		GiocatoreModel g3 = new GiocatoreModel("Xyp9x", "Andreas", "Højsleth");	
		GiocatoreModel g4 = new GiocatoreModel("gla1ve", "Lukas Egholm", "Rossander");
		GiocatoreModel g5 = new GiocatoreModel("Magisk", "Emil Hoffmann", "Reif");	
		
		playerRepo.saveAll(List.of(teo,danu,io,g1,g2,g3,g4,g5));	
		List <GiocatoreModel> amatoriali = new LinkedList<>();
		List <GiocatoreModel> professionisti = new LinkedList<>();			
		teo.setAmici(List.of(danu, io));
		danu.setAmici(List.of(teo, io));
		io.setAmici(List.of(teo, danu));		
		playerRepo.saveAll(List.of(teo,danu,io,g1,g2,g3,g4,g5));		
		amatoriali.add(danu);		
		amatoriali.add(io);
		amatoriali.add(teo);
		professionisti.addAll(List.of(g1,g2,g3,g4,g5));
		/*Abbiamo una squadra Amatoriale e una professionista*/
		SquadraProfessionistaModel mache = new SquadraProfessionistaModel( "Astralis", "CS:GO", professionisti, 4, 8699634);		
		SquadraAmatorialeModel prova = new SquadraAmatorialeModel("Gli Edd", "CS:GO", amatoriali, "TAXI");
		
		PartitaModel andata = new PartitaModel(mache,prova, 12, 16);
		PartitaModel ritorno = new PartitaModel(prova,mache, 16, 10);		
		
		TorneoModel torneoSingolo = new TorneoModel("Bronze Cup 2014- Winter Edition", List.of(andata, ritorno), prova);
		teamRepo.save(prova);
		teamRepo.save(mache);	
		
		g1.setSquadra(mache);
		g2.setSquadra(mache);
		g3.setSquadra(mache);
		g4.setSquadra(mache);
		g5.setSquadra(mache);
		danu.setSquadra(prova);
		teo.setSquadra(prova);
		io.setSquadra(prova);
		playerRepo.saveAll(List.of(teo,danu,io,g1,g2,g3,g4,g5));
		
		matchRepo.save(andata);	
		matchRepo.save(ritorno);
		mache.setPartiteSvolte(List.of(andata,ritorno));
		prova.setPartiteSvolte(List.of(andata,ritorno));
		teamRepo.save(mache);
		teamRepo.save(prova);
		
		cupRepo.save(torneoSingolo);
		MajorModel kato2014 = new MajorModel("Polonia", "Intel", 50000, List.of(mache));
		majorRepo.save(kato2014);
		TorneoModel torneoMajor = new TorneoModel("Bronze 2015 - Winter Edition", null, null, kato2014);
		cupRepo.save(torneoMajor);
		majorRepo.save(kato2014);
	}
}
