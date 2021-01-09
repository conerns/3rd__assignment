package assignment3.videoGames.assignment3.Model;

import java.util.List;

import javax.persistence.Entity;

/**
 * *
 * @author Andrei
 * La squadra amatoriale, può avere dai 2 ai n giocatori.
 */
@Entity
public class SquadraAmatorialeModel extends SquadraModel{
	private String nameTag; // la squadra professionale ha il proprio nome, una amaoriale condivide un nametag
	/**
	 * Ad esempio, se la squadra avesse nome "Eddys", in gioco si troverebbe "Giocatore 1" senza necessità di nameTag
	 * Un amatoriale sarebbe riportato "[NameTag] - Giocatore 1", anche se fa parte di una squadra. 
	 * */
	public SquadraAmatorialeModel() {
		super();
	}
	
	public SquadraAmatorialeModel(String nomeSquadra, String giocoSquadra, List<GiocatoreModel> componenti,String nameTag) {		
		super(nomeSquadra, giocoSquadra, componenti);
		this.nameTag = nameTag;
	}
	
	public String getNameTag() {
		return nameTag;
	}
	public void setNameTag(String nameTag) {
		this.nameTag = nameTag;
	}
}
