package dz.aak.sentrep.ston;

import java.util.Set;

public class ReqClause {
	
	


	private String label = "";
	private String indent = "";
	private int synSet = 0;
	
	private ReqDisjunction predicates = new ReqDisjunction();
	
	public ReqClause (int synSet){
		this.synSet = synSet;
	}
	
	public void setSpecifs(String label, int indentLevel){
		this.label = label;
		
		if (indentLevel > 0)
			indent = new String(new char[indentLevel]).replace("\0", "\t");
	}
	
	public void addConjunctedPredicates(Set<String> conjunctions){
		predicates.addConjunctions(conjunctions);
	}
	
	public Set<Set<String>> getPredicates(){
		return predicates.getAll();
	}
	
	
	public String structuredString() {
		
		String result = indent + label + ":{\n";

		result += indent + "\tsynSet: " + synSet ;
		
		if(! predicates.isEmpty()){
			result += ";\n" + indent + "\tpredicates: " + predicates;
		}
		
		result += "\n" + indent + label + ":}\n";
		
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = label + ":{";

		result += "synSet:" + synSet ;
		
		if(! predicates.isEmpty()){
			result += ";" + "predicates:" + predicates.toString().replace(" ", "");
		}
		
		result += label + ":}";
		
		return result;
	}
	
	

}
