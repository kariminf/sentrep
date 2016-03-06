package kariminf.sentrep.ston.request;

import java.util.Set;

import kariminf.sentrep.ston.SentType;


public class ReqSentence {
	
	private SentType type = SentType.AFF;
	private ReqDisjunction mainActions = new ReqDisjunction();
	private ReqDisjunction secActions = null;

	public ReqSentence(SentType type) {
		this.type = type;
	}
	
	public void addMainActions(Set<String> conjunctions){
		mainActions.addConjunctions(conjunctions);
	}
	
	public void addSecActions(Set<String> conjunctions){
		if (type != SentType.COND)
			return;
		if (secActions == null)
			secActions = new ReqDisjunction();
		secActions.addConjunctions(conjunctions);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		String result = "st:{";
		
		result += "type:" + type ;
		result += ";act:" + mainActions.toString().replace(" ", "");
		if ((type == SentType.AFF) && (secActions != null))
			result += ";act2:" + secActions.toString().replace(" ", "");
		result += "st:}";
		
		return result;
	}
	
	public String structuredString() {
		
		String result = "\tst:{\n";
		
		result += "\t\ttype:" + type ;
		result += ";\n\t\tact:" + mainActions;
		if ((type == SentType.AFF) && (secActions != null))
			result += ";\n\t\tact2:" + secActions;
		result += "\n\tst:}";
		
		return result;
	}

}
