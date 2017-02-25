package kariminf.sentrep.ston;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ParserSpeed extends Parser {

	static String testFile = "/home/kariminf/Github/Nolporas/STON/bio/LouisdeBroglie_Bio/LouisdeBroglie_Bio.ston";
	//static String testFile = "ston/NaguibMahfouz_bio/NaguibMahfouz_Bio.ston";
	
	@Override
	protected void beginAction(String id, int synSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addVerbSpecif(String tense, String modality,
			boolean progressive, boolean perfect, boolean negated) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean actionFailure() {
		return true;

	}

	@Override
	protected void beginAgents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void endAgents() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addActionAdverb(int advSynSet, List<Integer> advSynSets) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean adverbFailure() {
		return true;

	}

	@Override
	protected void beginThemes() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void endThemes() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void endAction(String id, int synSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beginRole(String id, int synSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beginRole(String id, int synSet, String pronoun) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addRoleSpecif(String name, String def, String quantity) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addAdjective(int synSet, List<Integer> advSynSets) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean adjectiveFailure() {
		return true;

	}

	@Override
	protected boolean roleFailure() {
		return true;

	}

	@Override
	protected void parseFailure() {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean relativeFailure() {
		return true;

	}

	@Override
	protected void beginRelative(String SP) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beginComparison(String type, List<Integer> adjSynSets) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void addConjunctions(List<String> IDs) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void parseSuccess() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beginSentence(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void endSentence(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beginActions(boolean mainClause) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void endActions(boolean mainClause) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beginPRelatives() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void endPRelatives() {
		// TODO Auto-generated method stub

	}
	
	public static String readFile (String f) {
		try {
			String contents = "";

			BufferedReader input = new BufferedReader(new FileReader(f));

			
			for(String line = input.readLine(); line != null; line = input.readLine()) {
				contents += line + "\n";
			}
			input.close();

			return contents;

		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		} 
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String specif = readFile(testFile);
		
		ParserSpeed p = new ParserSpeed();
		long st = System.currentTimeMillis();
		p.parse(specif);
		long nd = System.currentTimeMillis();
		System.out.println(p.parsed());
		System.out.println("time: " + (nd-st));
	}

	@Override
	protected void endRole(String id, int synSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endRelative(String SP) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endComparison(String type, List<Integer> adjSynSets) {
		// TODO Auto-generated method stub
		
	}

}
