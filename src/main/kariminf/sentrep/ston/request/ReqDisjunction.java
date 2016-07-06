package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ReqDisjunction {


	private ArrayList<Conjunctions> disjunctions = new ArrayList<Conjunctions>();
	
	private static class Conjunctions  {
		private List<String> conjunctions;
		
		public Conjunctions(List<String> conjunctions){
			this.conjunctions = new ArrayList<String>();
			this.conjunctions.addAll(conjunctions);
		}
		
		public Set<String> getAll(){
			Set<String> result = new HashSet<String>();
			result.addAll(conjunctions);
			return result;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			
			if (conjunctions.size() < 1 ) return "";
			String result = conjunctions.toString();
			result = result.substring(1, result.length()-1);
			result = result.replace(",", ";");
			return result;
		}
		
	}
	
	public ReqDisjunction() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void addConjunctions(List<String> conjunctions){
		if (conjunctions.isEmpty()) return;
		Conjunctions conj = new Conjunctions(conjunctions);
		disjunctions.add(conj);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String result = disjunctions.toString();
		
		result = result.replace(",", "|");
		result = result.replace(";", ",");
		return result;
	}
	
	public boolean isEmpty(){
		return (disjunctions.isEmpty());
		
	}
	
	public Set<Set<String>> getAll(){
		Set<Set<String>> result = new HashSet<Set<String>>();
		
		for (Conjunctions conjunctions: disjunctions){
			result.add(conjunctions.getAll());
		}
		
		return result;
	}
	

}
