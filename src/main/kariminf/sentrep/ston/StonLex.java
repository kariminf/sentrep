package kariminf.sentrep.ston;

import java.util.ArrayList;
import java.util.List;

import kariminf.sentrep.ston.types.SComparison;
import kariminf.sentrep.ston.types.SPronoun;
import kariminf.sentrep.ston.types.SPronoun.SPProperty;
import kariminf.sentrep.ston.types.SRelation;
import kariminf.sentrep.ston.types.SVerbModal;
import kariminf.sentrep.ston.types.SVerbTense;

public class StonLex {
	
	private static final List<String> tenses = initTense();
	private static final List<String> modals = initModal();
	private static final List<String> relations = initRelation();
	private static final List<String> comparisons = initComparison();
	private static final List<String> dets = initDeterminer();
	
	
	private static List<String> initTense(){
		List<String> result = new ArrayList<String>();
		
		for (SVerbTense tense: SVerbTense.values())
			result.add(tense.name());
		/*result.add("PA");
		result.add("PR");
		result.add("FU");*/
		
		return result;
	}
	
	private static List<String> initModal(){
		List<String> result = new ArrayList<String>();
		
		for(SVerbModal modal : SVerbModal.values())
			result.add(modal.name());
		/*result.add("CAN");
		result.add("MAY");
		result.add("MUST");*/
		
		return result;
	}
	
	private static List<String> initRelation(){
		List<String> result = new ArrayList<String>();
		
		for(SRelation relation : SRelation.values())
			result.add(relation.name());
		
		return result;
	}
	
	private static List<String> initDeterminer(){
		List<String> result = new ArrayList<String>();
		
		result.add("NONE");
		result.add("Y");
		result.add("N");
		
		return result;
	}
	
	
	private static List<String> initComparison(){
		List<String> result = new ArrayList<String>();
		
		for(SComparison comparison : SComparison.values())
			result.add(comparison.name());
		
		return result;
	}
	
	public static int getTenseIndex(String tenseString){
		return tenses.indexOf(tenseString);
	}
	
	public static int getModalIndex(String modalString){
		return modals.indexOf(modalString);
	}
	
	public static boolean isTense(String tenseString){
		return tenses.contains(tenseString);
	}
	
	public static boolean isModal(String modalString){
		return modals.contains(modalString);
	}
	
	public static boolean isRelation(String relationString){
		return relations.contains(relationString);
	}
	
	public static boolean isComparison(String compString){
		return comparisons.contains(compString);
	}
	
	public static boolean isDeterminer(String detString){
		return dets.contains(detString);
	}
	
	public static String getDefaultTense(){
		return tenses.get(0);
	}
	
	public static String getDefaultModal(){
		return modals.get(0);
	}
	
	public static String getDefaultDeterminer(){
		return dets.get(0);
	}
	
	public static int getDetIndex(String DetString){
		return dets.indexOf(DetString);
	}
	
	public static int getCompIndex(String compString){
		return comparisons.indexOf(compString);
	}
	

	//Head: Demonstrative (D) {this, etc.}, Personnel (P) {I, me, etc.}
	//Number: No one (0), singular(1), dual(2), plural(3)
	//Gender: Feminine (F), Masculine (M), Neuter (N)
	//Formality: Rude(0), Casual (1), Formal (2), Polite (3)
	//Proximity: Distal(D), Medial(M), Proximal(P), Not-defined(N)
	public static void getPronounSpecif(String type){

	}
	

}
