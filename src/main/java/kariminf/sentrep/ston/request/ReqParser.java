package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import kariminf.sentrep.ston.Parser;
import kariminf.sentrep.ston.types.SPronoun;
import kariminf.sentrep.ston.types.SSentType;



//import dz.aak.sentrep.ston.RAction;

public class ReqParser extends Parser {

	private ReqRolePlayer currentPlayer;
	private ReqAction currentAction;
	private ReqSentence currentSentence;
	
	private HashMap<String, ReqRolePlayer> players = new HashMap<String, ReqRolePlayer>();
	private HashMap<String, ReqAction> actions = new HashMap<String, ReqAction>();
	private ArrayList<ReqSentence> sentences = new ArrayList<ReqSentence>();
	
	private ReqDisjunction currentDisjunction;
	
	//private boolean mainClause;
	

	/**
	 * 
	 * @param description
	 */
	public ReqParser(String description) {
		parse(description);
	}

	public HashMap<String, ReqRolePlayer> getPlayers(){
		return new HashMap<String, ReqRolePlayer>(players);
	}

	public HashMap<String, ReqAction> getActions(){
		return new HashMap<String, ReqAction>(actions);
	}

	public ArrayList<ReqSentence> getSentences(){
		return new ArrayList<ReqSentence>(sentences);
	}

	

	//=====================================================================
	//================== Implementing methods =============================
	//=====================================================================
	
	
	//=====================================================================
	//======================== ACTION METHODS =============================
	//=====================================================================

	@Override
	protected void beginAction(String id, int synSet) {
		currentAction = ReqAction.create(id, synSet);
		actions.put(id, currentAction);
	}
	
	@Override
	protected void endAction(String id, int synSet) {
	}
	
	@Override
	protected boolean actionFailure() {
		return true;
	}
	
	@Override
	protected void addVerbSpecif(String tense, String modality, boolean progressive, boolean perfect, boolean negated) {
		currentAction.addVerbSpecif(tense, modality, progressive, perfect, negated);
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
	protected void beginAgents() {
		currentDisjunction = currentAction.getAgents();
	}
	
	@Override
	protected void endAgents() {
	}

	@Override
	protected void beginThemes() {
		currentDisjunction = currentAction.getThemes();
	}

	@Override
	protected void endThemes() {
	}
	
	@Override
	protected void beginComparison(String type, List<Integer> adjSynSets) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void endComparison(String type, List<Integer> adjSynSets) {
		// TODO Auto-generated method stub
		
	}
	
	
	//=====================================================================
	//========================= ROLE METHODS ==============================
	//=====================================================================

	@Override
	protected void beginRole(String id, int synSet) {
		currentPlayer = ReqRolePlayer.create(id, synSet);
		players.put(id, currentPlayer);
		currentAction = null;
		//System.out.println("player added: " + id);
	}
	
	@Override
	protected void beginRole(String id, int synSet, String pronoun) {
		currentPlayer = ReqRolePlayer.create(id, synSet, SPronoun.create(pronoun));
		players.put(id, currentPlayer);
		currentAction = null;
	}
	
	@Override
	protected void endRole(String id, int synSet) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean roleFailure() {
		return true;
	}
	
	@Override
	protected void addRoleSpecif(String name, String def, String quantity) {
		currentPlayer.setdefined(def);
		currentPlayer.setProperName(name);
		currentPlayer.setQuantity(quantity);
		
	}

	@Override
	protected void addAdjective(int synSet, List<Integer> advSynSets) {
		currentPlayer.addAdjective(synSet, advSynSets);
	}

	@Override
	protected boolean adjectiveFailure() {
		
		return true;
	}
	
	@Override
	protected void beginPRelatives() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endPRelatives() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//=====================================================================
	//======================= SENTENCE METHODS ============================
	//=====================================================================
	
	@Override
	protected void beginSentence(String type) {
		currentSentence = new ReqSentence(SSentType.valueOf(type.toUpperCase()));
		sentences.add(currentSentence);
		
	}

	@Override
	protected void endSentence(String type) {
		
	}
	
	@Override
	protected void beginActions(boolean mainClause) {
		
		currentDisjunction = currentSentence.getDisjunction(mainClause);
		
	}

	@Override
	protected void endActions(boolean mainClause) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//=====================================================================
	//======================== SHARED METHODS =============================
	//=====================================================================

	@Override
	protected void addConjunctions(List<String> roleIDs) {
		currentDisjunction.addConjunctions(roleIDs);
	}
	
	@Override
	protected void beginRelative(String type) {
		ReqClause relative = new ReqClause(type);
		
		if (currentAction != null ){
			currentAction.addRelative(relative);
		} else {
			if (currentPlayer != null)
				currentPlayer.addRelative(relative);
		}
		
		currentDisjunction = relative.getDisjinction();
		
	}
	
	@Override
	protected void endRelative(String SP) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected boolean relativeFailure() {
		return true;
		
	}
	
	//=====================================================================
	//========================= PARSE METHODS =============================
	//=====================================================================
	
	@Override
	protected void parseSuccess() {
	}

	@Override
	protected void parseFailure() {
		// TODO Auto-generated method stub
		
	}


}
