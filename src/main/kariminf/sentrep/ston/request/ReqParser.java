package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import kariminf.sentrep.ston.Parser;



//import dz.aak.sentrep.ston.RAction;

public class ReqParser extends Parser {

	private ReqRolePlayer currentPlayer;
	private ReqAction currentAction;
	
	private HashMap<String, ReqRolePlayer> players = new HashMap<String, ReqRolePlayer>();
	private HashMap<String, ReqAction> actions = new HashMap<String, ReqAction>();
	private ArrayList<ReqSentence> sentences = new ArrayList<ReqSentence>();
	
	private ReqDisjunction currentDisjunction;
	

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

	// Implementing the methods


	@Override
	protected void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated) {
		currentAction.addVerbSpecif(tense, modality, progressive, negated);
	}

	@Override
	protected void actionFail() {
	}

	@Override
	protected void addAdjective(int synSet, Set<Integer> advSynSets) {
		currentPlayer.addAdjective(synSet, advSynSets);
	}

	@Override
	protected void adjectiveFail() {
	}

	@Override
	protected void roleFail() {
	}

	@Override
	protected void parseSuccess() {
	}

	/*
	@Override
	protected void addTime(int synSet) {
		ReqClause time = new ReqClause(synSet);
		currentAction.addTime(time);
		currentDisjunction = time.getDisjinction();
	}*/


	@Override
	protected void addConjunctions(Set<String> roleIDs) {
		currentDisjunction.addConjunctions(roleIDs);
	}

	@Override
	protected void addAction(String id, int synSet) {
		currentAction = ReqAction.create(id, synSet);
		actions.put(id, currentAction);
	}


	@Override
	protected void addRole(String id, int synSet) {
		currentPlayer = ReqRolePlayer.create(id, synSet);
		players.put(id, currentPlayer);
		currentAction = null;
		//System.out.println("player added: " + id);
	}

	/*
	@Override
	protected void addPlace(int synSet) {
		ReqClause place = new ReqClause(synSet);
		currentAction.addPlace(place);
		currentDisjunction = place.getDisjinction();
	}*/

	@Override
	protected void addSubjects() {
		currentDisjunction = currentAction.getSubjects();
	}

	@Override
	protected void addObjects() {
		currentDisjunction = currentAction.getObjects();
	}

	@Override
	protected void parseFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void relativeFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addRelative(String type) {
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
	protected void endAction() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endSubjects() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endObjects() {
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
	protected void addRoleSpecif(String name, String def, String quantity) {
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


}
