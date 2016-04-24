package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ReqRolePlayer {
	private static Set<String> ids = new HashSet<String>();
	private String id;
	private int nounSynSet;
	private String properName = "";
	private String quantity = "1";
	private String defined = "";
	private List<ReqAdjective> adjectives = new ArrayList<ReqAdjective>();
	List<ReqClause> relatives = new ArrayList<ReqClause>();
	
	public void addRelative(ReqClause relative){
		relatives.add(relative);
	}
	
	/**
	 * @return the nounSynSet
	 */
	public int getNounSynSet() {
		return nounSynSet;
	}


	/**
	 * @return the adjectives
	 */
	public List<ReqAdjective> getAdjectives() {
		return adjectives;
	}


	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	
	private ReqRolePlayer(String id, int nounSynSet) {
		this.nounSynSet = nounSynSet;
		this.id = id;
	}
	
	
	public static ReqRolePlayer create(String id, int nounSynSet){
		//protection for same ids
		if(ids.contains(id)) return null;
		ids.add(id);
		return new ReqRolePlayer(id, nounSynSet);
	}
	
	public void addAdjective(int adjSynSet, Set<Integer> advSynSets){
		
		ReqAdjective adjective = new ReqAdjective(adjSynSet);
		if ((advSynSets != null) && ! advSynSets.isEmpty()){
			adjective.setAdvSynSets(advSynSets);
		}
		
		adjectives.add(adjective);
	}
	
	
	public void setQuantity(String quantity){
		this.quantity = quantity;
	}
	
	public void setProperName(String properName){
		this.properName = properName;
	}
	
	public void setdefined(String defined){
		defined = defined.toUpperCase();
		if (defined.matches("Y|N"))
			this.defined = defined;
		else
			this.defined = "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = "r:{";
		
		result += "id:" + id;
		result += ";syn:" + nounSynSet;
		
		if (properName.length() > 0)
			result += ";name:" + properName;
		
		if (! quantity.equals("1"))
			result += ";quant:" + quantity;
		
		if (defined.length()>0)
			result += ";def:" + defined;
		
		if(! adjectives.isEmpty()) {
			result += ";@adj:[";

			Iterator<ReqAdjective> it = adjectives.iterator();
			while(it.hasNext()){
				result += it.next();
				if(it.hasNext())
					result += ",";
			}
			
			result += "adj:]";
		}
		
		if (! relatives.isEmpty()){
			result += ";@rel:[";
			for (ReqClause relative: relatives){
				relative.setSpecifs("rel", 0);
				result += relative;
			}
			result += "rel:]";
		}
		
		result += "r:}";
		
		return result;
	}
	
	
	public String structuredString() {
		String result = "\tr:{";
		
		result += "\n\t\tid: " + id;
		result += ";\n\t\tsyn: " + nounSynSet;
		
		if (properName.length() > 0)
			result += ";\n\t\tname:" + properName;
		
		if (! quantity.equals("1"))
			result += ";\n\t\tquant: " + quantity;
		
		if (defined.length() > 0)
			result += ";\n\t\tdef:" + defined;
		
		if(! adjectives.isEmpty()) {
			result += ";\n\t\t@adj:[\n";
			
			Iterator<ReqAdjective> it = adjectives.iterator();
			while(it.hasNext()){
				result += it.next().structuredString();
				if(it.hasNext())
					result += ",";
				result += "\n";
			}
			
			result += "\t\tadj:]";
		}
		
		if (! relatives.isEmpty()){
			result += ";\n\t\t@rel:[";
			for (ReqClause relative: relatives){
				relative.setSpecifs("rel", 3);
				result += "\n" + relative.structuredString();
			}
			result += "\n\t\trel:]";
		}
		
		result += "\n\tr:}";
		
		return result;
	}


}
