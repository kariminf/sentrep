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
		rc.newSubjectDisjunction();
		rc.addSubject("ate", "mother");
		rc.addSubject("ate", "child");
		rc.newSubjectDisjunction();
		rc.addSubject("ate", "child");
		rc.addSubject("ate", "+goodfood");
		
		rc.newObjectDisjunction();
		rc.addObject("ate", "+goodfood");
		
		System.out.println(rc.getStructuredRequest());

	}

}
