package dz.aak.sentrep.ston;

import java.util.HashSet;
import java.util.Set;

public class ReqDisjunction {


	private HashSet<String> conjunctions = new HashSet<String>();
	
	
	public ReqDisjunction() {
		// TODO Auto-generated constructor stub
	}
	
	//TODO this class will be extends HashSet<HashSet<String>>
	/*private String fuseDisjunctions(Set<ReqDisjunction> disjunctions){
		
		String result = disjunctions.toString();
		
		result = result.replace(",", "|");
		result = result.replace(";", ",");
		return result;
	}*/
	
	public void addConjunction(String conjunction){
		conjunctions.add(conjunction);
	}
	
	public String getConjunctions(){
		if (conjunctions.size() < 1 ) return "";
		String result = conjunctions.toString();
		result = result.substring(1, result.length()-1);
		result = result.replace(",", ";");
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getConjunctions();
	}

}
