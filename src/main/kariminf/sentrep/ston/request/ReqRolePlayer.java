package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import kariminf.sentrep.ston.StonBlocks;
import kariminf.sentrep.ston.StonKeys;
import kariminf.sentrep.ston.types.SPronoun;

public class ReqRolePlayer {
	private static Set<String> ids = new HashSet<String>();
	private String id;
	private int nounSynSet;
	private String properName = "";
	private String quantity = "1";
	private String defined = "";
	private List<ReqAdjective> adjectives = new ArrayList<ReqAdjective>();
	private List<ReqClause> relatives = new ArrayList<ReqClause>();

	private SPronoun pronoun;
	private ReqDisjunction references = new ReqDisjunction();

	public static void clearIdList(){
		ids.clear();
	}

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

	private ReqRolePlayer(String id, int nounSynSet, SPronoun pronoun) {
		this.pronoun = pronoun;
		this.id = id;
	}

	public void addPronRef(List<String> conjunctions){
		references.addConjunctions(conjunctions);
	}


	public static ReqRolePlayer create(String id, int nounSynSet){
		//protection for same ids
		if(ids.contains(id)) return null;
		ids.add(id);
		return new ReqRolePlayer(id, nounSynSet);
	}
	
	public static ReqRolePlayer create(String id, ReqRolePlayer rrp){
		//protection for same ids
		if(ids.contains(id)) return null;
		ids.add(id);
		ReqRolePlayer result = new ReqRolePlayer(id, rrp.nounSynSet);
		result.properName = rrp.properName;
		result.quantity = rrp.quantity;
		result.defined = rrp.defined;
		result.adjectives = new ArrayList<ReqAdjective>();
		result.adjectives.addAll(rrp.adjectives);
		
		//TODO verify this
		//result.relatives = new ArrayList<ReqClause>();
		result.pronoun = rrp.pronoun;
		//result.references = new ReqDisjunction();
		return result;
	}

	public static ReqRolePlayer create(String id, int nounSynSet, SPronoun pronoun){
		//protection for same ids
		if(ids.contains(id)) return null;
		ids.add(id);
		return new ReqRolePlayer(id, nounSynSet, pronoun);
	}

	public void addAdjective(int adjSynSet, List<Integer> advSynSets){

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
		String result = StonKeys.ROLEBL + ":{";

		result += StonKeys.ID +  ":" + id;
		if (nounSynSet > 0)
			result += ";" + StonKeys.SYNSET + ":" + nounSynSet;
		if(pronoun != null){
			result += ";" + StonKeys.TYPE + ":" + pronoun;

			if (! references.isEmpty()){
				result += ";" + StonKeys.REFERENCE + ":" + references;
			}
		} 

		if (properName.length() > 0)
			result += ";" + StonKeys.NAME + ":" + properName;

		if (!quantity.isEmpty() && !quantity.equals("1"))
			result += ";" + StonKeys.QUANTITY + ":" + quantity;

		if (defined.length()>0)
			result += ";" + StonKeys.DEFINED + ":" + defined;

		if(! adjectives.isEmpty()) {
			result += ";" + StonBlocks.beginADJ + ":[";

			Iterator<ReqAdjective> it = adjectives.iterator();
			while(it.hasNext()){
				result += it.next();
				if(it.hasNext())
					result += ",";
			}

			result += StonKeys.ADJBL + ":]";
		}


		if (! relatives.isEmpty()){
			result += ";" + StonBlocks.beginREL + ":[";
			for (ReqClause relative: relatives){
				relative.setSpecifs(StonKeys.RELBL, 0);
				result += relative;
			}
			result += StonKeys.RELBL + ":]";
		}

		result += StonKeys.ROLEBL +  ":}";

		return result;
	}


	public String structuredString() {

		String result = StonBlocks.getIndentation(1) + StonKeys.ROLEBL + ":{";

		result += "\n" + StonBlocks.getIndentation(2);
		result += StonKeys.ID +  ":" + id;

		if (nounSynSet > 0){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.SYNSET + ":" + nounSynSet;
		}
		

		if(pronoun != null){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.TYPE + ":" + pronoun;

			if (! references.isEmpty()){
				result += ";\n" + StonBlocks.getIndentation(2);
				result += StonKeys.REFERENCE + ":" + references;
			}
		} 

		if (properName.length() > 0){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.NAME + ":" + properName;
		}

		if (!quantity.isEmpty() && !quantity.equals("1")){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.QUANTITY + ":" + quantity;
		}

		if (defined.length()>0){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.DEFINED + ":" + defined;
		}

		if(! adjectives.isEmpty()) {
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonBlocks.beginADJ + ":[\n";

			Iterator<ReqAdjective> it = adjectives.iterator();
			while(it.hasNext()){
				result += it.next().structuredString();
				if(it.hasNext())
					result += ",\n";
			}

			result += "\n" + StonBlocks.getIndentation(2);
			result += StonKeys.ADJBL + ":]";
		}

		if (! relatives.isEmpty()){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonBlocks.beginREL + ":[\n";
			
			Iterator<ReqClause> it = relatives.iterator();
			while(it.hasNext()){
				ReqClause relative = it.next();
				relative.setSpecifs(StonKeys.RELBL, 3);
				result += relative.structuredString();
				if(it.hasNext())
					result += ",\n";
			}
			result += StonBlocks.getIndentation(2);
			result += StonKeys.RELBL + ":]";
		}

		result += "\n" + StonBlocks.getIndentation(1);
		result += StonKeys.ROLEBL +  ":}";

		return result;

	}


}