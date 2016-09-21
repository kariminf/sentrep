package kariminf.sentrep.ston;

import java.util.ArrayList;

import kariminf.sentrep.ston.request.ReqCreator;


public class ReqCreatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReqCreator rc = new ReqCreator();
		
		//Roles creation
		rc.addRolePlayer("mother", 10332385);
		rc.addAdjective("mother", 1148283, null); //happy
		
		rc.addRelative("SBJ", "mother");
		ArrayList<String> mother_sing = new ArrayList<String>();
		mother_sing.add("sing");
		rc.addRelativeConjunctions(mother_sing);
		//rc.addRelativeConjunctions(new String[]{"sing"});
		
		rc.addRolePlayer("child", 9917593);
		rc.addRolePlayer("+goodfood", 21265);
		ArrayList<Integer> adv = new ArrayList<Integer>();
		adv.add(89408); //highly
		rc.addAdjective("+goodfood", 1123148, adv);
		
		//Actions creation
		rc.addAction("ate", 1168468);
		rc.addVerbSpecif("ate", "PAST", "MAY", true, true);
		
		rc.addAction("sing", 937208);
		rc.addVerbSpecif("sing", "PRESENT", "NONE", false, false);
		
		
		//Disjunctions of conjunctions
		ArrayList<String> mother_child = new ArrayList<String>();
		mother_child.add("mother");
		mother_child.add("child");
		rc.addAgentConjunctions("ate", mother_child);

		ArrayList<String> child_food = new ArrayList<String>();
		child_food.add("child");
		child_food.add("+goodfood");
		rc.addAgentConjunctions("ate", child_food);
		
		ArrayList<String> foods = new ArrayList<String>();
		foods.add("+goodfood");
		rc.addThemeConjunctions("ate", foods);
		
		//TODO complete this
		// Adpositional phrases
		rc.addRolePlayer("noon", 15165490);
		rc.addRelative("IN", "ate");
		rc.addRelativeConjunctions(new String[]{"noon"});
		
		rc.addSentence("AFF");
		rc.addSentMainActConjunctions(new String[]{"ate"});
		
		
		System.out.println(rc.getStructuredRequest());

	}

}
