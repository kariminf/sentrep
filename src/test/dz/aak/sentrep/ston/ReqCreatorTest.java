package dz.aak.sentrep.ston;

import java.util.HashSet;
import java.util.Set;

public class ReqCreatorTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReqCreator rc = new ReqCreator();
		
		//Roles creation
		rc.addRolePlayer("mother", 10332385);
		rc.addAdjective("mother", 1148283, null); //happy
		rc.addRolePlayer("child", 9917593);
		rc.addRolePlayer("+goodfood", 21265);
		Set<Integer> adv = new HashSet<Integer>();
		adv.add(89408); //highly
		rc.addAdjective("+goodfood", 1123148, adv);
		
		//Actions creation
		rc.addAction("ate", 1168468);
		rc.addVerbSpecif("ate", "PAST", "MAY", true, true);
		
		//Disjunctions of conjunctions
		Set<String> mother_child = new HashSet<String>();
		mother_child.add("mother");
		mother_child.add("child");
		rc.addSubjectConjunctions("ate", mother_child);

		Set<String> child_food = new HashSet<String>();
		child_food.add("child");
		child_food.add("+goodfood");
		rc.addSubjectConjunctions("ate", child_food);
		
		Set<String> foods = new HashSet<String>();
		foods.add("+goodfood");
		rc.addObjectConjunctions("ate", foods);
		
		
		//Times
		rc.addTime("ate", 510249);
		//Here, you can add timeConjjunctions for this time
		
		//Places
		rc.addPlace("ate", 111662);
		//Here, you can add timeConjjunctions for this time
		
		System.out.println(rc.getStructuredRequest());

	}

}
