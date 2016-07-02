package kariminf.sentrep.ston.request;

import java.util.Set;

import kariminf.sentrep.ston.StonBlocks;
import kariminf.sentrep.ston.StonKeys;

public class ReqClause {
	
	


	private String label = "";
	private String indent = "";
	private String type = "";
	
	private ReqDisjunction predicates = new ReqDisjunction();
	
	public ReqClause (String type){
		this.type = type;
	}
	
	public void setSpecifs(String label, int indentLevel){
		this.label = label;
		indent = StonBlocks.getIndentation(indentLevel);
		
		/*if (indentLevel > 0)
			indent = new String(new char[indentLevel]).replace("\0", "\t");*/
	}
	
	public void addConjunctedPredicates(Set<String> conjunctions){
		predicates.addConjunctions(conjunctions);
	}
	
	public Set<Set<String>> getPredicates(){
		return predicates.getAll();
	}
	
	public String getType(){
		return type;
	}
	
	
	public String structuredString() {
		
		String result = indent + label + ":{\n";

		result += indent + StonBlocks.INDENT;
		result += StonKeys.TYPE + ":" + type ;
		
		if(! predicates.isEmpty()){
			result += ";\n" + indent + StonBlocks.INDENT;
			result += StonKeys.REFERENCE + ":";
			result += predicates.toString().replace(" ", "");
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

		result += StonKeys.TYPE + ":" + type ;
		
		if(! predicates.isEmpty()){
			result += ";" + StonKeys.REFERENCE + ":";
			result += predicates.toString().replace(" ", "");
		}
		
		result += label + ":}";
		
		return result;
	}
	
	
	protected ReqDisjunction getDisjinction(){
		return predicates;
	}
	
	

}
