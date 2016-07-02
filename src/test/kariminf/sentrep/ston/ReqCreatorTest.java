package kariminf.sentrep.ston;

import java.util.HashSet;
import java.util.Set;

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
		
		rc.addRelative("subj", "mother");
		Set<String> mother_sing = new HashSet<String>();
		mother_sing.add("sing");
		rc.addRelativeConjunctions(mother_sing);
		//rc.addRelativeConjunctions(new String[]{"sing"});
		
		rc.addRolePlayer("child", 9917593);
		rc.addRolePlayer("+goodfood", 21265);
		Set<Integer> adv = new HashSet<Integer>();
		adv.add(89408); //highly
		rc.addAdjective("+goodfood", 1123148, adv);
		
		//Actions creation
		rc.addAction("ate", 1168468);
		rc.addVerbSpecif("ate", "PAST", "MAY", true, true);
		
		rc.addAction("sing", 937208);
		rc.addVerbSpecif("sing", "PRESENT", "NONE", false, false);
		
		
		//Disjunctions of conjunctions
		Set<String> mother_child = new HashSet<String>();
		mother_child.add("mother");
		mother_child.add("child");
		rc.addAgentConjunctions("ate", mother_child);

		Set<String> child_food = new HashSet<String>();
		child_food.add("child");
		child_food.add("+goodfood");
		rc.addAgentConjunctions("ate", child_food);
		
		Set<String> foods = new HashSet<String>();
		foods.add("+goodfood");
		rc.addThemeConjunctions("ate", foods);
		
		//TODO complete this
		// Adpositional phrases
		rc.addRolePlayer("noon", 15165490);
		rc.addRelative("t_at", "ate");
		rc.addRelativeConjunctions(new String[]{"noon"});
		
		rc.addSentence("AFF");
		rc.addSentMainActConjunctions(true, new String[]{"ate"});
		
		
		System.out.println(rc.getStructuredRequest());

	}

}
