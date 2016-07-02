package kariminf.sentrep;

import kariminf.sentrep.univ.types.*;


public class Maper {
	
	private UnivMap uMap;
	private LangMap lMap;

	public Maper(UnivMap uMap, LangMap lMap) {
		this.lMap = lMap;
		this.uMap = uMap;
	}
	
	public String getTense(String tense){
		VerbTense univ = uMap.mapTense(tense);
		return lMap.getTense(univ);
	}
	
	public String getModal(String modal){
		Modality univ = uMap.mapModal(modal);
		return lMap.getModal(univ);
	}
	
	public String getAdposition(String adpos, String name){
		return null;
	}
	
	public String getDeterminer(String det){
		Determiner univ = uMap.mapDeterminer(det);
		return lMap.getDeterminer(univ);
	}
	


}
