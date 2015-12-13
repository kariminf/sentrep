package dz.aak.sentrep.ston;

import java.util.Set;

public class ReqClause {
	
	private int synSet = 0;
	private ReqDisjunction predicates = new ReqDisjunction();
	
	public ReqClause (int synSet){
		this.synSet = synSet;
	}
	
	public void addConjunctedPredicates(Set<String> conjunctions){
		predicates.addConjunctions(conjunctions);
	}
	
	public Set<Set<String>> getPredicates(){
		return predicates.getAll();
	}

}
