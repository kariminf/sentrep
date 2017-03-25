package kariminf.sentrep.ston.request;

import java.util.List;
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
	
	public void addMainActions(List<String> conjunctions){
		mainActions.addConjunctions(conjunctions);
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
		result += ";" + StonKeys.ACT + ":" ;
		result += mainActions.toString().replace(" ", "");
			
		result += StonKeys.SENTBL + ":}";
		
		return result;
	}
	
	public String structuredString() {
		
		String result = StonBlocks.getIndentation(1) + StonKeys.SENTBL + ":{";
		
		result += "\n" + StonBlocks.getIndentation(2);
		result += StonKeys.TYPE + ":" + type ;
		
		result += ";\n" + StonBlocks.getIndentation(2);
		result += StonKeys.ACT + ":" ;
		result += mainActions.toString().replace(" ", "");
		
		result += "\n" + StonBlocks.getIndentation(1);
		result += StonKeys.SENTBL + ":}";
		
		return result;
		
	}

}
