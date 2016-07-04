package kariminf.sentrep.ston;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import kariminf.sentrep.ston.Parser;
import kariminf.sentrep.ston.types.SPronoun;

public class StonSimpleParser extends Parser {

	static String testFile = "ston/that.ston";
	
	private boolean firstDisjunction = true;
	
	public StonSimpleParser(String description) {
		this.parse(description);
	}

	@Override
	protected void addAction(String id, int synSet) {
		System.out.println("Adding action: " + id + ", verb: " + synSet);
	}

	@Override
	protected void addVerbSpecif(String tense, String modality,
			boolean progressive, boolean negated) {
		String verbDesc = "\tTense=" + tense;
		verbDesc += (modality.matches("none"))? "" : ", Modality= " + modality;
		verbDesc += (progressive)? ", Progressive": "";
		verbDesc += (negated)? ", Negated": "";
		System.out.println(verbDesc);
	}

	@Override
	protected void actionFail() {
		System.out.println("ACTION FAILED");
	}

	@Override
	protected void beginAgents() {
		System.out.println("\tAgents: ");
		firstDisjunction = true;
	}

	@Override
	protected void beginThemes() {
		System.out.println("\tThemes: ");
		firstDisjunction = true;
	}

	@Override
	protected void addRole(String id, int synSet) {
		System.out.println("Adding role: " + id + ", noun: " + synSet);
	}

	@Override
	protected void addAdjective(int synSet, Set<Integer> advSynSets) {
		System.out.println("\tAdjective: " + synSet + ", adverbs: " + advSynSets);
	}

	@Override
	protected void adjectiveFail() {
		System.out.println("ADJECTIVE FAILED");
	}

	@Override
	protected void roleFail() {
		System.out.println("ROLE FAILED");
	}

	@Override
	protected void addConjunctions(Set<String> IDs) {
		String res = (firstDisjunction)? "\t\t": "\t\tor ";
		System.out.println(res + IDs);
		firstDisjunction = false;
	}

	@Override
	protected void parseSuccess() {
		System.out.println("Congratulations: SUCCESS");
	}

	@Override
	protected void parseFail() {
		System.out.println("General structure not respected");
	}
	
	@Override
	protected void relativeFail() {
		System.out.println("RELATIVE FAILED");
		
	}

	@Override
	protected void addRelative(String type) {
		System.out.println("Add relative clause type: " + type);
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String specification = readFile(testFile);
		new StonSimpleParser(specification);
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

	@Override
	protected void endAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endAgents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endThemes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginSentence(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endSentence() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginActions(boolean mainClause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addComparison(String type, Set<Integer> adjSynSets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addActionAdverb(int advSynSet, Set<Integer> advSynSets) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adverbFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addRoleSpecif(String name, String def, String quantity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addRole(String id, SPronoun pronoun) {
		
		
	}


}
