package dz.aak.sentrep.ston;

import java.util.HashMap;
import java.util.HashSet;
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
	protected void addVerbSpecif(int tense, int aspect) {
		currentAction.addVerbSpecif(tense, aspect);
	}

	@Override
	protected void addSubject(String subjectID) {
		currentAction.addSubject(subjectID);
	}

	@Override
	protected void addObject(String objectID) {
		currentAction.addObject(objectID);
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


}
