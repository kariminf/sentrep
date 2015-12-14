package dz.aak.sentrep.ston;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReqCreator {

	private HashMap<String, ReqRolePlayer> players = new HashMap<String, ReqRolePlayer>();
	private HashMap<String, ReqAction> actions = new HashMap<String, ReqAction>();
	
	//private ReqDisjunction subjects = new ReqDisjunction();
	//private ReqDisjunction objects = new ReqDisjunction();
	ReqClause currentTime;
	ReqClause currentPlace;
	
	public ReqCreator() {
	}
	
	public ReqCreator(HashMap<String, ReqRolePlayer> players, HashMap<String, ReqAction> actions){
		this.players = new HashMap<String, ReqRolePlayer>(players);
		this.actions = new HashMap<String, ReqAction>(actions);
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
	
	/*private boolean verifyExistance (String actionId, String roleId){
		actionId = actionId.trim();
		roleId = roleId.trim();
		if (! actions.containsKey(actionId)) return false;
		if (players.containsKey(roleId)) return true;

		
		return false;
	}*/
	
	
	public boolean addTime(String actionId, int synSet){
		if (! actions.containsKey(actionId)) return false;
		
		currentTime = new ReqClause(synSet);
		actions.get(actionId).addTime(currentTime);
		return true;
	}
	
	public boolean addTimeConjunctions(Set<String> timeConjunctions){
		currentTime.addConjunctedPredicates(timeConjunctions);
		return true;
	}
	
	public boolean addPlace(String actionId, int synSet){
		if (! actions.containsKey(actionId)) return false;
		
		currentPlace = new ReqClause(synSet);
		actions.get(actionId).addPlace(currentPlace);
		return true;
	}
	
	public boolean addPlaceConjunctions(Set<String> placeConjunctions){
		currentPlace.addConjunctedPredicates(placeConjunctions);
		return true;
	}
	
	public boolean addSubjectConjunctions(String actionId, Set<String> subjectsIDs){
		if (! actions.containsKey(actionId)) return false;
		
		//For now, we don't verify the existance of each subject in players list
		actions.get(actionId).addSubjects(subjectsIDs);
		return true;
	}
	
	public boolean addObjectConjunctions(String actionId, Set<String> objectsIDs){
		if (! actions.containsKey(actionId)) return false;
		
		//For now, we don't verify the existance of each object in players list
		actions.get(actionId).addObjects(objectsIDs);
		return true;
	}
	
	
	public boolean addVerbSpecif(String actionId, String tense, String modality, boolean progressive, boolean negated){
		actionId = actionId.trim();
		if (! actions.containsKey(actionId)) return false;
		actions.get(actionId).addVerbSpecif(tense, modality, progressive, negated);
		return true;
	}
	
	public boolean addAdjective(String playerId, int adjSynSet, Set<Integer> advSynSets){
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
	
	
	public String getStructuredRequest(){
		
		String result = "@roles:";
		
		if(players.isEmpty()){
			result += " null;\n";
		} else {
			result += " [\n";
			Iterator<ReqRolePlayer> it = players.values().iterator();
			while (it.hasNext()){
				result += it.next().structuredString() + "\n";
			}
				
			result += "]\n";
		}
		
		//////////////////////////
		if(! actions.isEmpty()){
			result += "\n@actions: [\n";
			
			Iterator<ReqAction> it = actions.values().iterator();
			while (it.hasNext()){
				result += it.next().structuredString() + "\n";
			}
				
			result += "]\n";
		}		
		//////////////////////////
		
		return result;
	}
	
	public String getRequest(){
		
		String result = "@roles:";
		
		if(players.isEmpty()){
			result += "null;";
		} else {
			result += "[";
			Iterator<ReqRolePlayer> it = players.values().iterator();
			while (it.hasNext()){
				result += it.next();
			}
				
			result += "]";
		}
		
		//////////////////////////
		if(! actions.isEmpty()){
			result += "@actions:[";
			
			Iterator<ReqAction> it = actions.values().iterator();
			while (it.hasNext()){
				result += it.next();
			}
				
			result += "]";
		}		
		//////////////////////////
		
		return result;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReqCreator rq = new ReqCreator();
		
		rq.addRolePlayer("child1", 1256);
		rq.addAdjective("child1", 15, null);
		
		rq.addRolePlayer("child2", 1256);
		
		Set<Integer> adv = new HashSet<Integer>();
		adv.add(5); adv.add(89);
		rq.addAdjective("child2", 18, adv);
		
		System.out.println(rq.getRequest());

	}
	
	

}
