package dz.aak.sentrep.ston;

import java.util.ArrayList;
import java.util.List;

public class StonLex {
	
	private static final List<String> tenses = initTense();
	private static final List<String> modals = initModal();
	
	
	private static List<String> initTense(){
		List<String> result = new ArrayList<String>();
		
		for (SentTense tense: SentTense.values())
			result.add(tense.name());
		/*result.add("PA");
		result.add("PR");
		result.add("FU");*/
		
		return result;
	}
	
	private static List<String> initModal(){
		List<String> result = new ArrayList<String>();
		
		for(SentModal modal : SentModal.values())
			result.add(modal.name());
		/*result.add("CAN");
		result.add("MAY");
		result.add("MUST");*/
		
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
	
	public static String getDefaultTense(){
		return tenses.get(0);
	}
	
	public static String getDefaultModal(){
		return "NONE";
	}

}
