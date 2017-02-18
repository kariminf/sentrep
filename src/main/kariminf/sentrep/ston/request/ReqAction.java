package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.List;

import kariminf.sentrep.ston.StonBlocks;
import kariminf.sentrep.ston.StonKeys;

public class ReqAction {
	
	private static List<String> ids = new ArrayList<String>();
	
	private int verbSynSet;
	private String id;
	private ReqDisjunction agents = new ReqDisjunction();
	private ReqDisjunction themes = new ReqDisjunction();
	private List<Integer> advSynSets = new ArrayList<Integer>();
	
	private String tense = "PR";
	private String modality = "NONE";
	private boolean progressive = false;
	private boolean negated = false;
	
	public static void clearIdList(){
		ids.clear();
	}
	
	/*private HashMap<String, List<ReqClause>> relatives = 
			new HashMap<String, List<ReqClause>>();*/
	
	List<ReqClause> relatives = new ArrayList<ReqClause>();
	
	public void addRelative(ReqClause relative){
		/*String type = relative.getType();
		List<ReqClause> rels = (relatives.containsKey(type))?
				relatives.get(type): new ArrayList<ReqClause>();
		relatives.put(type, rels);
		rels.add(relative);*/
		relatives.add(relative);
	}
	
	/**
	 * @return the verbSynSet
	 */
	public int getVerbSynSet() {
		return verbSynSet;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the agents of the action
	 */
	public ReqDisjunction getAgents() {
		//Not secure
		return agents;
	}

	/**
	 * @return the themes of the action
	 */
	public ReqDisjunction getThemes() {
		return themes;
	}

	/**
	 * @return the tense
	 */
	public String getTense() {
		return tense;
	}
	
	/**
	 * @param advSynSets the advSynSets to set
	 */
	public void setAdvSynSets(List<Integer> advSynSets) {
		this.advSynSets.addAll(advSynSets);
	}

	/**
	 * @return the aspect
	 */
	public String getModality() {
		return modality;
	}
	
	private ReqAction(String id, int verbSynSet) {
		this.verbSynSet = verbSynSet;
		this.id = id;
	}
	
	public static ReqAction create(String id, int verbSynSet){
		//protection for same ids
		if(ids.contains(id)){
			System.out.println("id fount:" + id);
			return null;
		}
		ids.add(id);
		return new ReqAction(id, verbSynSet);
	}
	
	public void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated){
		if (tense.matches("PA|PR|FU")) this.tense = tense;
		if (modality.matches("CAN|MAY|NONE")) this.modality = modality;
		this.progressive = progressive;
		this.negated = negated;
	}
	
	/**
	 * Adds conjunctions of agents; those who do the action (verb)
	 * @param conjunctions a set of roles IDs which are separated by "and" 
	 * in the original sentence
	 */
	public void addAgents(List<String> conjunctions){
		agents.addConjunctions(conjunctions);
	}
	
	/**
	 * Add conjunctions of themes; those who undergo the action (verb)
	 * @param conjunctions a set of roles IDs which are separated by "and" 
	 * in the original sentence
	 */
	public void addThemes(List<String> conjunctions){
		themes.addConjunctions(conjunctions);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String result = StonKeys.ACTBL + ":{";
		
		result += StonKeys.ID +  ":" + id;
		result += ";" + StonKeys.SYNSET + ":" + verbSynSet;
		result += ";" + StonKeys.TENSE + ":" + tense;
		
		if (progressive){
			result += ";" + StonKeys.PROGRESSIVE + ":Y";
		}
			
		if (negated){
			result += ";" + StonKeys.NEGATED + ":Y";
		}
			
		if(modality != "NONE"){
			result += ";" + StonKeys.MODAL + ":" + modality;
		}
			
		if ( ! advSynSets.isEmpty()){
			result += ";" + StonKeys.ADVERB + ":";
			result += advSynSets.toString().replace(" ", "");
		}
			
		if(! agents.isEmpty()) {
			result += ";" + StonKeys.AGENT + ":";
			result += agents.toString().replace(" ", "");
		}
		
		if(! themes.isEmpty()){
			result += ";" + StonKeys.THEME + ":";
			result += themes.toString().replace(" ", "");
		}
		
		if (! relatives.isEmpty()){
			result += ";" + StonBlocks.beginREL + ":[";
			for (ReqClause relative: relatives){
				relative.setSpecifs(StonKeys.RELBL, 0);
				result += relative;
			}
			result += StonKeys.RELBL + ":]";
		}
		
		result += StonKeys.ACTBL + ":}";
		
		return result;
	}
	
	
	public boolean replaceRole(String older, String newer){
		if (older == newer) return false;
		agents.replace(older, newer);
		themes.replace(older, newer);
		return true;
	}
	
	
	public String structuredString() {
		
		String result = StonBlocks.getIndentation(1) + StonKeys.ACTBL + ":{";
		
		result += "\n" + StonBlocks.getIndentation(2);
		result += StonKeys.ID +  ":" + id;
		
		result += ";\n" + StonBlocks.getIndentation(2);
		result += StonKeys.SYNSET + ":" + verbSynSet;
		
		result += ";\n" + StonBlocks.getIndentation(2);
		result += StonKeys.TENSE + ":" + tense;
		
		if (progressive){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.PROGRESSIVE + ":Y";
		}
			
		if (negated){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.NEGATED + ":Y";
		}
			
		if(modality != "NONE"){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.MODAL + ":" + modality;
		}
			
		if ( ! advSynSets.isEmpty()){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.ADVERB + ":";
			result += advSynSets.toString().replace(" ", "");
		}
			
		if(! agents.isEmpty()) {
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.AGENT + ":";
			result += agents.toString().replace(" ", "");
		}
		
		if(! themes.isEmpty()){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.THEME + ":";
			result += themes.toString().replace(" ", "");
		}
		
		if (! relatives.isEmpty()){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonBlocks.beginREL + ":[\n";
			for (ReqClause relative: relatives){
				relative.setSpecifs(StonKeys.RELBL, 3);
				result += relative.structuredString();
			}
			result += StonBlocks.getIndentation(2);
			result += StonKeys.RELBL + ":]";
		}
		
		result += "\n" + StonBlocks.getIndentation(1);
		result += StonKeys.ACTBL + ":}";
		
		return result;
		
	}

	

}
