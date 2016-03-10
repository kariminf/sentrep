package kariminf.sentrep.ston;

import java.util.ArrayList;
import java.util.List;

public class StonLex {
	
	private static final List<String> tenses = initTense();
	private static final List<String> modals = initModal();
	private static final List<String> relations = initRelation();
	private static final List<String> comparisons = initComparison();
	private static final List<String> dets = initDeterminer();
	
	private static List<String> initTense(){
		List<String> result = new ArrayList<String>();
		
		for (VerbTense tense: VerbTense.values())
			result.add(tense.name());
		/*result.add("PA");
		result.add("PR");
		result.add("FU");*/
		
		return result;
	}
	
	private static List<String> initModal(){
		List<String> result = new ArrayList<String>();
		
		for(VerbModal modal : VerbModal.values())
			result.add(modal.name());
		/*result.add("CAN");
		result.add("MAY");
		result.add("MUST");*/
		
		return result;
	}
	
	private static List<String> initRelation(){
		List<String> result = new ArrayList<String>();
		
		for(Relation relation : Relation.values())
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
		
		for(Comp comparison : Comp.values())
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

}
