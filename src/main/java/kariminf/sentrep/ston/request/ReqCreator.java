package kariminf.sentrep.ston.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import kariminf.sentrep.ston.StonBlocks;
import kariminf.sentrep.ston.StonKeys;
import kariminf.sentrep.ston.types.SPronoun;
import kariminf.sentrep.ston.types.SSentType;


public class ReqCreator {

	private HashMap<String, ReqRolePlayer> players = new HashMap<String, ReqRolePlayer>();
	private HashMap<String, ReqAction> actions = new HashMap<String, ReqAction>();
	private ArrayList<ReqSentence> sentences = new ArrayList<ReqSentence>();
	
	//private ReqDisjunction subjects = new ReqDisjunction();
	//private ReqDisjunction objects = new ReqDisjunction();
	private ReqClause currentRelative;
	private ReqSentence currentSentence;
	
	public ReqCreator() {
		ReqAction.clearIdList();
		ReqRolePlayer.clearIdList();
	}
	
	public ReqCreator(HashMap<String, ReqRolePlayer> players, 
			HashMap<String, ReqAction> actions, ArrayList<ReqSentence> sentences){
		
		this.players = new HashMap<String, ReqRolePlayer>(players);
		this.actions = new HashMap<String, ReqAction>(actions);
		this.sentences = new ArrayList<ReqSentence>(sentences);
	}
	
	
	public boolean addRolePlayer(String id, int nounSynSet){
		id = id.trim();
		ReqRolePlayer player = ReqRolePlayer.create(id, nounSynSet);
		if (player == null){
			//System.out.println("id already exists");
			return false;
		}
		players.put(id.trim(), player);
		return true;
	}
	
	public boolean addRolePlayer(ReqRolePlayer player){
		if (player == null){
			//System.out.println("id already exists");
			return false;
		}
		players.put(player.getID(), player);
		return true;
	}
	
	
	public boolean addPronounRolePlayer(String id, int nounSynSet, String pronoun){
		id = id.trim();
		ReqRolePlayer player = ReqRolePlayer.create(id, nounSynSet, SPronoun.create(pronoun));
		if (player == null){
			//System.out.println("id already exists");
			return false;
		}
		players.put(id.trim(), player);
		return true;
	}
	
	/**
	 * 
	 * @param playerId
	 * @param name
	 * @return
	 */
	public boolean setRoleProperName(String playerId, String name){
		playerId = playerId.trim();
		if (! players.containsKey(playerId)) return false;

		players.get(playerId).setProperName(name);
		
		return true;
		
	}
	
	public ReqRolePlayer getReqRolePlayerCopie(String playerId, String newID){
		playerId = playerId.trim();
		if (! players.containsKey(playerId)) return null;
		
		ReqRolePlayer original = players.get(playerId);
		return ReqRolePlayer.create(newID, original);
	}
	
	/**
	 * <b>Unsafe: </b>
	 * @param playerId
	 * @return
	 */
	public ReqRolePlayer getReqRolePlayer(String playerId){
		playerId = playerId.trim();
		if (! players.containsKey(playerId)) return null;
		
		return players.get(playerId);
	}
	
	
	public boolean setDefined (String playerId, String def){
		playerId = playerId.trim();
		if (! players.containsKey(playerId)) return false;
		
		players.get(playerId).setdefined(def);
		return true;
	}
	
	
	public boolean addAction(String id, int verbSynSet){
		id = id.trim();
		ReqAction action = ReqAction.create(id, verbSynSet);
		if (action == null){
			//System.out.println("id already exists");
			return false;
		}
		actions.put(id, action);
		return true;
	}
	
	//Add new Sentence
	public boolean addSentence(String type){
		
		SSentType stype;
		try{
			stype = SSentType.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e){
			return false;
		}
		
		
		ReqSentence sentence = new ReqSentence(stype);
		sentences.add(sentence);
		currentSentence = sentence;
		return true;
	}
	
	public boolean addSentActionConjunctions(List<String> actConjunctions){
		
		if (currentSentence == null)
			return false;
		currentSentence.addMainActions(actConjunctions);
		
		return true;
	}
	
	public boolean addSentMainActConjunctions(String... actConjunctions){
		if (currentSentence == null)
			return false;
		ArrayList<String> actionConjunctions = new ArrayList<String>();
		for (String rel: actConjunctions)
			actionConjunctions.add(rel);
		return addSentActionConjunctions(actionConjunctions);
	}
	
	
	
	/*private boolean verifyExistance (String actionId, String roleId){
		actionId = actionId.trim();
		roleId = roleId.trim();
		if (! actions.containsKey(actionId)) return false;
		if (players.containsKey(roleId)) return true;

		
		return false;
	}*/
	
	
	
	public boolean addRelative(String type, String id){

		currentRelative = new ReqClause(type);
		
		if (actions.containsKey(id)){
			actions.get(id).addRelative(currentRelative);
		} else {
			if (players.containsKey(id)){
				players.get(id).addRelative(currentRelative);
			} else{
				return false;
			}
		}

		return true;
	}
	
	public boolean addRelativeConjunctions(List<String> relativeConjunctions){
		currentRelative.addConjunctedPredicates(relativeConjunctions);
		return true;
	}
	
	public boolean addRelativeConjunctions(String... relativeConjunctions){
		ArrayList<String> relConjunctions = new ArrayList<String>();
		for (String rel: relativeConjunctions)
			relConjunctions.add(rel);
		return addRelativeConjunctions(relConjunctions);
	}
	
	public boolean addAgentConjunctions(String actionId, List<String> agentsIDs){
		if (! actions.containsKey(actionId)) return false;
		
		//For now, we don't verify the existance of each subject in players list
		actions.get(actionId).addAgents(agentsIDs);
		return true;
	}
	
	public boolean addThemeConjunctions(String actionId, List<String> themesIDs){
		if (! actions.containsKey(actionId)) return false;
		
		//For now, we don't verify the existance of each object in players list
		actions.get(actionId).addThemes(themesIDs);
		return true;
	}
	
	
	public boolean addVerbSpecif(String actionId, String tense, String modality, boolean progressive, boolean perfect, boolean negated){
		actionId = actionId.trim();
		if (! actions.containsKey(actionId)) return false;
		actions.get(actionId).addVerbSpecif(tense, modality, progressive, perfect, negated);
		return true;
	}
	
	public boolean addActionAdverbs(String actionId, List<Integer> advSynSets){
		actionId = actionId.trim();
		if (! actions.containsKey(actionId)) return false;
		actions.get(actionId).setAdvSynSets(advSynSets);
		return true;
	}
	
	public boolean addActionAdverb(String actionId, Integer advSynSets){
		actionId = actionId.trim();
		if (! actions.containsKey(actionId)) return false;
		actions.get(actionId).addAdvSynSet(advSynSets);
		return true;
	}
	
	public boolean addAdjective(String playerId, int adjSynSet, List<Integer> advSynSets){
		playerId = playerId.trim();
		if (! players.containsKey(playerId)) return false;
		
		ReqRolePlayer player = players.get(playerId);
		player.addAdjective(adjSynSet, advSynSets);
		return true;
	}
	
	public boolean setQuantity(String playerId, String quantity){
		playerId = playerId.trim();
		if (! players.containsKey(playerId)) return false;
		
		players.get(playerId).setQuantity(quantity);
		return true;
	}
	
	public boolean setQuantity(String playerId){
		playerId = playerId.trim();
		if (! players.containsKey(playerId)) return false;
		
		players.get(playerId).setPlural();
		return true;
	}
	
	/**
	 * 
	 * @param actID
	 * @param subID
	 * @param id
	 * @return
	 */
	public boolean replaceRoleInAction(String actID, String subID, String id){
		if(!players.containsKey(subID)) return false;
		if(!players.containsKey(id)) return false;
		if(!actions.containsKey(actID)) return false;
		
		actions.get(actID).replaceRole(subID, id);
		
		return true;
	}
	
	
	public String getStructuredRequest(){
		
		//////////////////////////
		
		String result = StonBlocks.beginROLE + ":[\n";
		{
			Iterator<ReqRolePlayer> it = players.values().iterator();
			while (it.hasNext()){
				result += it.next().structuredString() + "\n";
			}
		}
		result += StonKeys.ROLEBL + ":]\n";
		
		//////////////////////////
		
		result += StonBlocks.beginACTION + ":[\n";
		{
			Iterator<ReqAction> it = actions.values().iterator();
			while (it.hasNext()){
				result += it.next().structuredString() + "\n";
			}
		}
		result += StonKeys.ACTBL + ":]\n";
		
		//////////////////////////
		
		result += StonBlocks.beginSENT + ":[\n";
		{	
			for (ReqSentence sentence: sentences){
				result += sentence.structuredString() + "\n";
			}
		}
		result += StonKeys.SENTBL + ":]";
		//////////////////////////
		
		return result;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getRequest(){
		
		//////////////////////////
		
		String result = StonBlocks.beginROLE + ":[";
		{
			Iterator<ReqRolePlayer> it = players.values().iterator();
			while (it.hasNext()){
				result += it.next();
			}
		}	
		result += StonKeys.ROLEBL + ":]";
		
		//////////////////////////
		
		result += StonBlocks.beginACTION  + ":[";
		{	
			Iterator<ReqAction> it = actions.values().iterator();
			while (it.hasNext()){
				result += it.next();
			}
		}
		result += StonKeys.ACTBL + "act:]";
		
		//////////////////////////
		result += StonBlocks.beginSENT  + ":[";
		{	
			for (ReqSentence sentence: sentences){
				result += sentence;
			}
		}
		result += StonKeys.SENTBL + ":]";
		//////////////////////////
		
		return result;
	}
	
	

}
