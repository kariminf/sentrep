package dz.aak.sentrep.ston;

import java.util.HashSet;

public class ReqDisjunction {

	private HashSet<String> conjunctions = new HashSet<String>();
	
	public ReqDisjunction() {
		// TODO Auto-generated constructor stub
	}
	
	public void addConjunction(String conjunction){
		conjunctions.add(conjunction);
	}
	
	public String getConjunctions(){
		return conjunctions.toString();
	}

}
