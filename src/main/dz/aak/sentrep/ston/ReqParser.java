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
	private String currentActionID = "";
	private String currentRoleID = "";
	private HashMap<String, ReqRolePlayer> players = new HashMap<String, ReqRolePlayer>();
	private HashMap<String, ReqAction> actions = new HashMap<String, ReqAction>();
	
	private Set<ReqDisjunction> disjunctions;
	
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


	// Implementing the methods

	@Override
	protected void beginAction(String id, int synSet) {
		currentAction = ReqAction.create(id, synSet);
		currentActionID = id;
	}

	@Override
	protected void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated) {
		currentAction.addVerbSpecif(tense, modality, progressive, negated);
	}

	@Override
	protected void endAction() {
		actions.put(currentActionID, currentAction);
	}

	@Override
	protected void actionFail() {
	}

	@Override
	protected void beginRole(String id, int synSet) {
		currentPlayer = ReqRolePlayer.create(id, synSet);
		currentRoleID = id;
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
	protected void endRole() {
		players.put(currentRoleID, currentPlayer);

	}

	@Override
	protected void beginActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginRoles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void endRoles() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beginSubject() {
		disjunctions = new HashSet<ReqDisjunction>();
	}

	@Override
	protected void beginObject() {
		disjunctions = new HashSet<ReqDisjunction>();
	}

	@Override
	protected void beginDisjunction() {
		currentDisjunction = new ReqDisjunction();
	}

	@Override
	protected void addConjunction(String roleID) {
		currentDisjunction.addConjunction(roleID);
	}

	@Override
	protected void endDisjunction() {
		
	}

	@Override
	protected void endSubject() {
		currentAction.addSubjects(disjunctions);
		
	}

	@Override
	protected void endObject() {
		currentAction.addSubjects(disjunctions);
		
	}


}
