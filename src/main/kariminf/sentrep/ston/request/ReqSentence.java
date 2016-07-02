package kariminf.sentrep.ston.request;

import java.util.Set;

import kariminf.sentrep.ston.StonBlocks;
import kariminf.sentrep.ston.StonKeys;
import kariminf.sentrep.ston.types.SSentType;


public class ReqSentence {
	
	private SSentType type = SSentType.AFF;
	private ReqDisjunction mainActions = new ReqDisjunction();
	private ReqDisjunction secActions = null;

	public ReqSentence(SSentType type) {
		this.type = type;
	}
	
	public void addMainActions(Set<String> conjunctions){
		mainActions.addConjunctions(conjunctions);
	}
	
	public void addSecActions(Set<String> conjunctions){
		if (type != SSentType.COND)
			return;
		if (secActions == null)
			secActions = new ReqDisjunction();
		secActions.addConjunctions(conjunctions);
	}
	
	public ReqDisjunction getDisjunction(boolean main){
		if (main)
			return mainActions;
		return secActions;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String result = StonKeys.SENTBL + ":{";
		
		result += StonKeys.TYPE + ":" + type ;
		result += ";" + StonKeys.ACT1 + ":" ;
		result += mainActions.toString().replace(" ", "");
		if ((type == SSentType.AFF) && (secActions != null)){
			result += ";" + StonKeys.ACT2 + ":" ;
			result += secActions.toString().replace(" ", "");
		}
			
		result += StonKeys.SENTBL + ":}";
		
		return result;
	}
	
	public String structuredString() {
		
		String result = StonBlocks.getIndentation(1) + StonKeys.SENTBL + ":{";
		
		result += "\n" + StonBlocks.getIndentation(2);
		result += StonKeys.TYPE + ":" + type ;
		
		result += ";\n" + StonBlocks.getIndentation(2);
		result += StonKeys.ACT1 + ":" ;
		result += mainActions.toString().replace(" ", "");
		
		if ((type == SSentType.AFF) && (secActions != null)){
			result += ";\n" + StonBlocks.getIndentation(2);
			result += StonKeys.ACT2 + ":" ;
			result += secActions.toString().replace(" ", "");
		}
		
		result += "\n" + StonBlocks.getIndentation(1);
		result += StonKeys.SENTBL + ":}";
		
		return result;
		
	}

}
