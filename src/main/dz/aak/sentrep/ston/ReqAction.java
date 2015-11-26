package dz.aak.sentrep.ston;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReqAction {

	private int verbSynSet;
	private String id;
	private Set<String> subjects = new HashSet<String>();
	private Set<String> objects = new HashSet<String>();
	private static Set<String> ids = new HashSet<String>();
	
	private String tense = "PRESENT";
	private String aspect = "SIMPLE";
	
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
	public Set<String> getSubjects() {
		return subjects;
	}

	/**
	 * @return the objects
	 */
	public Set<String> getObjects() {
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
	public String getAspect() {
		return aspect;
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
	
	public void addVerbSpecif(String tense, String aspect){
		if (tense.matches("PAST|PRESENT|FUTURE")) this.tense = tense;
		if (aspect.matches("SIMPLE|PROGRESSIVE|PERFECT")) this.aspect = aspect;
	}
	
	public void addSubject(String roleId){
		subjects.add(roleId);
	}
	
	public void addObject(String roleId){
		objects.add(roleId);
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
		result += ";aspect:" + aspect;
		if(! subjects.isEmpty()) {
			result += ";subjects:";
		}
		
		if(! objects.isEmpty()){
			result += ";objects:";
			result += objects;
		}
		
		result += "act:}";
		
		return result;
	}
	
	
	public String structuredString() {
		
		String result = "\tact:{\n";
		
		
		result += "\t\tid: " + id ;
		result += ";\n\t\tsynSet: " + verbSynSet ;
		result += ";\n\t\ttense: " + tense;
		result += ";\n\t\taspect: " + aspect;
		if(! subjects.isEmpty()) {
			result += ";\n\t\tsubjects: ";
			result += subjects;
		}
		
		if(! objects.isEmpty()){
			result += ";\n\t\tobjects: ";
			result += objects;
		}
		
		result += "\n\tact:}";
		
		return result;
	}

	

}
