package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.List;

import kariminf.sentrep.ston.StonBlocks;
import kariminf.sentrep.ston.StonKeys;

public class ReqAdjective {
	
	private int adjSynSet = 0;
	private List<Integer> advSynSets = new ArrayList<Integer>();
	
	
	public ReqAdjective(int adjSynSet) {
		this.adjSynSet = adjSynSet;
	}
	
	/**
	 * @return the adjSynSet
	 */
	public int getAdjSynSet() {
		return adjSynSet;
	}

	/**
	 * @return the advSynSets
	 */
	public List<Integer> getAdvSynSets() {
		//Alert: Security problem
		return advSynSets;
	}
	
	/**
	 * @param advSynSets the advSynSets to set
	 */
	public void setAdvSynSets(List<Integer> advSynSets) {
		this.advSynSets.addAll(advSynSets);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String result = StonKeys.ADJBL + ":{";
		result += StonKeys.SYNSET + ":" + adjSynSet;
		
		if ( ! advSynSets.isEmpty()){
			result += ";" + StonKeys.ADVERB + ":" + advSynSets;
		}
		result += StonKeys.ADJBL + ":}";
		return result;
	}
	
	
	public String structuredString(){
		
		String result = StonBlocks.getIndentation(3) + StonKeys.ADJBL + ":{\n";
		
		result += StonBlocks.getIndentation(4);
		result += StonKeys.SYNSET + ":" + adjSynSet;
		
		if ( ! advSynSets.isEmpty()){
			result += ";\n" + StonBlocks.getIndentation(4);
			result += StonKeys.ADVERB + ":" + advSynSets;
		}
		
		result += "\n" + StonBlocks.getIndentation(3);
		result += StonKeys.ADJBL + ":}";
		return result;
		
	}
	
	

}
