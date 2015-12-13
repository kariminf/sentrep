package dz.aak.sentrep.ston;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import dz.aak.sentrep.ston.RAction;

public class ReqParser extends Parser {

	private ReqRolePlayer currentPlayer;
	private ReqAction currentAction;
	
	private HashMap<String, ReqRolePlayer> players = new HashMap<String, ReqRolePlayer>();
	private HashMap<String, ReqAction> actions = new HashMap<String, ReqAction>();
	
	private boolean subject = true;
	private ReqClause currentClause;

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


	@Override
	protected void timesFail() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addTime(int synSet) {
		currentClause = new ReqClause(synSet);
		currentAction.addTime(currentClause);
	}

	@Override
	protected void addTimeConjunctions(Set<String> predicatesIDs) {
		currentClause.addConjunctedPredicates(predicatesIDs);
	}

	@Override
	protected void addConjunctions(Set<String> roleIDs) {
		if (subject){
			currentAction.addSubjects(roleIDs);
		} else {
			currentAction.addObjects(roleIDs);
		}
		
	}

	@Override
	protected void addAction(String id, int synSet) {
		currentAction = ReqAction.create(id, synSet);
		actions.put(id, currentAction);
		//System.out.println("action added: " + id);
	}

	@Override
	protected void addSubjects() {
		subject = true;
	}

	@Override
	protected void addObjects() {
		subject = false;
	}

	@Override
	protected void addRole(String id, int synSet) {
		currentPlayer = ReqRolePlayer.create(id, synSet);
		players.put(id, currentPlayer);
		//System.out.println("player added: " + id);
	}


}
