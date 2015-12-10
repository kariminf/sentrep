package dz.aak.sentrep.ston;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReqAction {

	private int verbSynSet;
	private String id;
	private Set<ReqDisjunction> subjects = new HashSet<ReqDisjunction>();
	private Set<ReqDisjunction> objects = new HashSet<ReqDisjunction>();
	private static Set<String> ids = new HashSet<String>();
	
	private String tense = "PRESENT";
	private String modality = "NONE";
	private boolean progressive = false;
	private boolean negated = false;
	
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
	 * @return the subjects
	 */
	public Set<ReqDisjunction> getSubjects() {
		return subjects;
	}

	/**
	 * @return the objects
	 */
	public Set<ReqDisjunction> getObjects() {
		return objects;
	}

	/**
	 * @return the tense
	 */
	public String getTense() {
		return tense;
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
		if(ids.contains(id)) return null;
		ids.add(id);
		return new ReqAction(id, verbSynSet);
	}
	
	public void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated){
		if (tense.matches("PAST|PRESENT|FUTURE")) this.tense = tense;
		if (modality.matches("CAN|MAY|NONE")) this.modality = modality;
		this.progressive = progressive;
		this.negated = negated;
	}
	
	public void addSubjects(Set<ReqDisjunction> disjunctions){
		subjects = disjunctions;
	}
	
	public void addObjects(Set<ReqDisjunction> disjunctions){
		objects = disjunctions;
	}
	
	private String fuseDisjunctions(Set<ReqDisjunction> disjunctions){
		
		String result = disjunctions.toString();
		
		result = result.replace(",", "|");
		result = result.replace(";", ",");
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "act:{";
		
		
		result += "id:" + id ;
		result += ";synSet:" + verbSynSet ;
		result += ";tense:" + tense;
		if (progressive)
			result += ";progressive:YES";
		if (negated)
			result += ";negated:YES";
		if(modality != "NONE")
			result += ";modality:" + modality;
		if(! subjects.isEmpty()) {
			result += ";subjects:";
			result += fuseDisjunctions(subjects);
		}
		
		if(! objects.isEmpty()){
			result += ";objects:";
			result += fuseDisjunctions(objects);
		}
		
		result += "act:}";
		
		return result;
	}
	
	
	public String structuredString() {
		
		String result = "\tact:{\n";
		
		
		result += "\t\tid: " + id ;
		result += ";\n\t\tsynSet: " + verbSynSet ;
		result += ";\n\t\ttense: " + tense;
		if (progressive)
			result += ";\n\t\tprogressive:YES";
		if (negated)
			result += ";\n\t\tnegated:YES";
		if(modality != "NONE")
			result += ";\n\t\tmodality:" + modality;
		if(! subjects.isEmpty()) {
			result += ";\n\t\tsubjects: ";
			result += fuseDisjunctions(subjects);
		}
		
		if(! objects.isEmpty()){
			result += ";\n\t\tobjects: ";
			result += fuseDisjunctions(objects);
		}
		
		result += "\n\tact:}";
		
		return result;
	}

	

}
