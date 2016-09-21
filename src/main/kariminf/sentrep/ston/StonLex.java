package kariminf.sentrep.ston;

import java.util.ArrayList;
import java.util.List;

import kariminf.sentrep.ston.types.SComparison;
import kariminf.sentrep.ston.types.SRelation.*;
import kariminf.sentrep.ston.types.SVerbModal;
import kariminf.sentrep.ston.types.SVerbTense;

public class StonLex {
	
	private static final List<String> tenses = initTense();
	private static final List<String> modals = initModal();
	private static final List<String> adpos = initAdpos();
	private static final List<String> advs = initAdvs();
	private static final List<String> rels = initRels();
	private static final List<String> comparisons = initComparison();
	private static final List<String> dets = initDeterminer();
	
	
	private static List<String> initTense(){
		List<String> result = new ArrayList<String>();
		
		for (SVerbTense tense: SVerbTense.values())
			result.add(tense.name());
		
		return result;
	}
	
	
	private static List<String> initModal(){
		List<String> result = new ArrayList<String>();
		
		for(SVerbModal modal : SVerbModal.values())
			result.add(modal.name());
		
		return result;
	}
	

	private static List<String> initRels(){
		List<String> result = new ArrayList<String>();

		for(SRelative relation : SRelative.values()){
			String rname = relation.name();
			if (! rname.endsWith("_")){
				result.add(rname);
				continue;
			}
			
			//this is for indirect object
			for(SAdpositional adp : SAdpositional.values()){
				result.add(rname + adp.name());
			}
			
		}
		
		return result;
	}
	
	
	private static List<String> initAdpos(){
		List<String> result = new ArrayList<String>();
		
		for(SAdpositional relation : SAdpositional.values())
			result.add(relation.name());
		
		return result;
	}
	
	
	private static List<String> initAdvs(){
		List<String> result = new ArrayList<String>();
		
		for(SAdverbial relation : SAdverbial.values())
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
	
	public static boolean isRoleRelation(String relation){
		boolean result;
		result = adpos.contains(relation);
		result = result || rels.contains(relation);
		return result;
	}
	
	public static boolean isActionRelation(String relation){
		boolean result;
		result = adpos.contains(relation);
		result = result || advs.contains(relation);
		return result;
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
	
	public static boolean isPredicateRole(String rel){
		// We can reach a role just by adpositions
		return adpos.contains(rel);
	}
	
}
