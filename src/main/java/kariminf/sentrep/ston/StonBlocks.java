package kariminf.sentrep.ston;

import java.util.regex.Pattern;

public class StonBlocks {

	//Indentation character: it can be \t or 4 spaces or anything else
	public static final String INDENT = "    ";
	
	// These are the characters to be ignored 
	public static final String BL = "[\\t \\n\\r]+";
	
	private static final String Block = 
			StonKeys.BLOCKSIGN + "%1\\:\\[(.*)%1\\:\\]";

	private static final String InternBlock = 
			Block + ";?";
	
	// Adjective
	//TODO modify to [aA][dD][jJ]
	public static final String ADJblock = 
			InternBlock.replaceAll("%1", StonKeys.ADJBL);
	
	public static final String beginADJ = 
			StonKeys.BLOCKSIGN + StonKeys.ADJBL;

	public static final String ADVblock = 
			InternBlock.replaceAll("%1", StonKeys.ADVBL);
			//"@adv\\:\\[(.*)adv\\:\\];?";

	public static final String beginROLE = 
			StonKeys.BLOCKSIGN + StonKeys.ROLEBL;
	
	public static final String beginACTION = 
			StonKeys.BLOCKSIGN + StonKeys.ACTBL;
	
	public static final String beginSENT = 
			StonKeys.BLOCKSIGN + StonKeys.SENTBL;
	
	public static final String beginADV = 
			StonKeys.BLOCKSIGN + StonKeys.ADVBL;
	
	// Relative clauses
	public static final String RELblock = 
			InternBlock.replaceAll("%1", StonKeys.RELBL);
			//"@rel\\:\\[(.*)rel\\:\\];?";
	
	public static final String beginREL = 
			StonKeys.BLOCKSIGN + StonKeys.RELBL;

	// Comparison block
	public static final String CMPblock = 
			InternBlock.replaceAll("%1", StonKeys.COMPBL);
			//"@cmp\\:\\[(.*)cmp\\:\\];?";
	
	public static final String beginCMP = 
			StonKeys.BLOCKSIGN + StonKeys.COMPBL;

	public static final String ROLEblock = 
			Block.replaceAll("%1", StonKeys.ROLEBL);
	
	public static final String ACTblock = 
			Block.replaceAll("%1", StonKeys.ACTBL);
	
	public static final String SENTblock = 
			Block.replaceAll("%1", StonKeys.SENTBL);
	
	// This is the regular expression used to separate main blocks
	public static final Pattern CONT = 
			Pattern.compile(ROLEblock + ACTblock + SENTblock);
			//Pattern.compile("@r\\:\\[(.+)r\\:\\]@act\\:\\[(.+)act\\:\\]@st\\:\\[(.+)st\\:\\]");

	public static final String IGNOREblock = 
			"\\" + StonKeys.BCOMMENT + "[^\\" + StonKeys.ECOMMENT
			+"]*\\" + StonKeys.ECOMMENT;
	
	/**
	 * Return the desired indentation
	 * @param n this is the level of indentation
	 * @return the string of indentation
	 */
	public static String getIndentation(int n){
		
		if (n <= 0)
			return "";
		
		return new String(new char[n]).replace("\0", INDENT);
		/*
		String result = "";
		for (int i=0; i< n; i++)
			result += INDENT;
		return result;
		*/
	}

	public static void main(String[] args) {
		System.out.println(CMPblock);
	}
}
