package dz.aak.sentrep.ston;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReqCreator {

	private HashMap<String, ReqRolePlayer> players = new HashMap<String, ReqRolePlayer>();
	private HashMap<String, ReqAction> actions = new HashMap<String, ReqAction>();
	
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
	
	private boolean verifyExistance (String actionId, String roleId){
		actionId = actionId.trim();
		roleId = roleId.trim();
		if (! actions.containsKey(actionId)) return false;
		if (players.containsKey(roleId)) return true;
		if (actions.containsKey(roleId)) return true;
		
		return false;
	}
	
	public boolean addSubject(String actionId, String subjectId){
		if(! verifyExistance(actionId, subjectId)) return false;
		actions.get(actionId).addSubject(subjectId);
		return true;
	}
	
	public boolean addObject(String actionId, String objectId){
		if(! verifyExistance(actionId, objectId)) return false;
		actions.get(actionId).addObject(objectId);
		return true;
	}
	
	public boolean addVerbSpecif(String actionId, String tense, String aspect){
		actionId = actionId.trim();
		if (! actions.containsKey(actionId)) return false;
		actions.get(actionId).addVerbSpecif(tense, aspect);
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
