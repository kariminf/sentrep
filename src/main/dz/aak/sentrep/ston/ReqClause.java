package dz.aak.sentrep.ston;

import java.util.HashSet;
import java.util.Set;

public class ReqClause {
	
	private int synSet = 0;
	private Set<Conjunctions> predicates = new HashSet<Conjunctions>();
	
	private static class Conjunctions extends HashSet<String> {
		private static final long serialVersionUID = 1L;
		
		public Conjunctions(Set<String> conjunctions){
			super();
			this.addAll(conjunctions);
		}
		
		public Set<String> getSubstances(){
			Set<String> result = new HashSet<String>();
			result.addAll(this);
			return result;
		}
		
	}
	
	public ReqClause (int synSet){
		this.synSet = synSet;
	}
	
	public void addConjunctedPredicates(Set<String> conjunctions){
		Conjunctions conj = new Conjunctions(conjunctions);
		predicates.add(conj);
	}
	
	public Set<Set<String>> getPredicates(){
		Set<Set<String>> result = new HashSet<Set<String>>();
		for(Conjunctions conjunctions: predicates){
			result.add(conjunctions.getSubstances());
		}
		return result;
	}

}
