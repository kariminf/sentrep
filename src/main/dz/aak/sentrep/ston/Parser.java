package dz.aak.sentrep.ston;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Abdelkrime Aries
 *
 */
public abstract class Parser {

	// These are the characters to be ignored 
	private static String BL = "[\\t \\n\\r]+";
	
	// This is the times block regular expression
	private static final String TIMESblock = "@times\\:t\\:\\[(.*)t\\:\\];?";
	
	// This is the places block regular expression
	private static final String PLACESblock = "@places\\:p\\:\\[(.*)p\\:\\];?";
	
	// This is the regular expression used to separate main blocks
	private static final Pattern CONT = 
			Pattern.compile("@roles\\:r\\:\\[(.+)r\\:\\]@actions\\:act\\:\\[(.+)act\\:\\]");
	
	//This is true when the parsing is a success
	private boolean success = false;
	
	
	/**
	 * The constructor of the parser
	 */
	public Parser() {
		success = false;
	}
	
	/**
	 * The method to parse a STON description
	 * @param description STON description
	 */
	public void parse(String description){
		
		description = description.replaceAll(BL, "");
		
		//System.out.println(description);
		Matcher m = CONT.matcher(description);
		if (m.find()) {
			String roles =  m.group(1);
			String actions =  m.group(2);
			if (! parseRoles(roles)) return;
			if (! parseActions(actions)) return;
        }
		
		success = true;
		
		parseSuccess();
	}
	
	/**
	 * This function tells us if the parsing goes well or not
	 * @return True: if the parsing succeed, False: else.
	 */
	public boolean parsed(){
		return success;
	}
	
	
	/**
	 * Parses the roles' block
	 * @param description STON description of roles
	 * @return True if there is no error
	 */
	private boolean parseRoles(String description){
		
		int idx;
		while ((idx = description.indexOf("r:}")) >= 0) {
			String role =  description.substring(3, idx);
			description = description.substring(idx+3);
			//System.out.println(role);
			if (! parseRole(role))
				return false;
        }

		return true;

	}
	
	/**
	 * Parses the actions' block
	 * @param description STON description of actions
	 * @return True if there is no error
	 */
	private boolean parseActions (String description){
		
		int idx;

		while ((idx = description.indexOf("act:}")) >= 0) {
			String action =  description.substring(5, idx);
			description = description.substring(idx+5);
			//System.out.println(role);
			if (! parseAction(action))
				return false;
			
        }
		
		return true;
	}
	
	
	/**
	 * Parses one action block
	 * @param description STON description of one action
	 * @return True if there is no error
	 */
	private boolean parseAction(String description){
		
		String description2 = description;
		
		String id = "";
		String synSetStr = "";
		String tense = "";
		boolean progressive = false;
		boolean negated = false;
		String modality = "NONE";
		String subjects = "";
		String objects = "";
		
		String times = "";
		String places = "";
		
		if (description2.contains("@times")){
			
			Pattern timesPattern = 
				Pattern.compile("(.*)" + TIMESblock + "(.*)");
			Matcher m = timesPattern.matcher(description2);
			if (m.find()){
				times = m.group(2);
				//System.out.println("time found");
				description2 = m.group(1) + m.group(3);		
			}
		}
		
		if (description2.contains("@places")){
			
			Pattern timesPattern = 
				Pattern.compile("(.*)" + PLACESblock + "(.*)");
			Matcher m = timesPattern.matcher(description2);
			if (m.find()){
				places = m.group(2);
				//System.out.println("time found");
				description2 = m.group(1) + m.group(3);		
			}
		}

		String[] descs = description2.split(";");
		
		for (String desc : descs){
			
			desc = desc.trim();
			
			if(desc.startsWith("id:")){
				id = desc.split(":")[1];
				continue;
			}
			
			if(desc.startsWith("synSet:")){
				synSetStr = desc.split(":")[1];
				continue;
			}
			
			if(desc.startsWith("tense:")){
				tense = desc.split(":")[1];
				continue;
			}
			
			if(desc.startsWith("progressive:")){
				String aspect = desc.split(":")[1];
				aspect = aspect.trim().toUpperCase();
				
				if(aspect.matches("YES")){
					progressive = true;
				}
				continue;
			}
			
			if(desc.startsWith("negated:")){
				String negate = desc.split(":")[1];
				negate = negate.trim().toUpperCase();

				if(negate.matches("YES")){
					negated = true;
				}
				continue;
			}
			
			if(desc.startsWith("modality:")){
				modality = desc.split(":")[1];
				continue;
			}
			
			if(desc.startsWith("subjects:")){
				subjects = desc.split(":")[1];
				continue;
			}
			
			if(desc.startsWith("objects:")){
				objects = desc.split(":")[1];
				continue;
			}
		}
		
		if(id.length() < 1){
			actionFail();
			success = false;
			return false;
		}
		
		int synSet = Integer.parseInt(synSetStr);
		
		addAction(id, synSet);
		
		//TODO add other components of the action
		if(! tense.matches("PAST|PRESENT|FUTURE")){
			tense = "PRESENT";
		}
		
		modality = modality.trim().toUpperCase();
		
		if(! modality.matches("CAN|MAY|MUST")){
			modality = "NONE";
		}
		
		
		addVerbSpecif(tense, modality, progressive, negated);
		
		if (times.length()>0){
			if (! parseTimes(times)) return false;
		}
		
		if (places.length()>0){
			if (! parsePlaces(places)) return false;
		}
		
		
		if(subjects.length() > 2){
			if (!(subjects.startsWith("[") && subjects.endsWith("]"))){
				System.out.println("subjects=" + subjects);
				return false;
			}
				
			subjects = subjects.substring(1, subjects.length()-1);
			addSubjects();
			parseComponents(subjects);

		}
		
		if(objects.length() > 2){
			if (!(objects.startsWith("[") && objects.endsWith("]")))
				return false;
			objects = objects.substring(1, objects.length()-1);
			addObjects();
			parseComponents(objects);

		}
		
		
		return true;
	}

	/**
	 * Subjects and objects are disjunctions of conjunctions, they are 
	 * represented like [id11, ...|id21, ... | ...] <br/>
	 * For example: [mother, son|father] means: mother and son or father
	 * @param description STON description of IDs
	 * @return True if there is no error
	 */
	private boolean parseComponents(String description){
		String[] disjunctions = description.split("\\|");
		
		for (String disjunction: disjunctions){
			Set<String> conjunctions = new HashSet<String>();
			for (String conjunction: disjunction.split(",")){
				conjunctions.add(conjunction);
				
			}
			addConjunctions(conjunctions);
			
		}
		return true;
	}
	

	/**
	 * Parse the 
	 * @param description
	 * @return
	 */
	private boolean parseAdjectives(String description){
		
		int idx;
		while ((idx = description.indexOf("adj:}")) >= 0) {
			String adjective =  description.substring(5, idx);
			description = description.substring(idx+5);
			if (description.startsWith(","))
				description = description.substring(1);
			
			String[] descs = adjective.split(";");
			
			int synSet = 0;
			HashSet<Integer> advSynSets = new HashSet<Integer>();
			for (String desc: descs){
				if(desc.startsWith("synSet:")){
					String synSetStr = desc.split(":")[1];
					synSet = Integer.parseInt(synSetStr);
					continue;
				}
				
				if(desc.startsWith("adverbs:")){
					String synSetStrs = desc.split(":")[1];
					synSetStrs = synSetStrs.substring(1, synSetStrs.length()-1);
					
					for (String AdvsynSetStr: synSetStrs.split(",")){
						
						int AdvsynSet = Integer.parseInt(AdvsynSetStr);
						advSynSets.add(AdvsynSet);
					}
				}
				
			}
			
			if (synSet < 1){
				adjectiveFail();
				success = false;
				return false;
			}
			
			addAdjective(synSet, advSynSets);
			
        }
		
		return true;
	}
	


	private boolean parseRole(String description){
		
		Matcher m = Pattern.compile("id\\:([^;]+)(;|$)").matcher(description);
		
		if (! m.find()){
			roleFail();
			success = false;
			return false;
		}
		
		String id = m.group(1);
		
		m = Pattern.compile("synSet\\:([^;]+)(;|$)").matcher(description);
		
		if (! m.find()){
			roleFail();
			success = false;
			return false;
		}
		
		String synSetStr = m.group(1);
		
		int synSet = Integer.parseInt(synSetStr);
		
		addRole(id, synSet);
		
		m = Pattern.compile("adjectives\\:\\[(.+adj\\:\\})\\]").matcher(description);
		
		if (m.find()){
			String adjectives = m.group(1);
			if (! parseAdjectives(adjectives)) return false;
		}
		
		return true;
		
	}

	/**
	 * 
	 * @param description
	 * @return
	 */
	private boolean parseTimes(String description){
		
		int idx;
		while ((idx = description.indexOf("t:}")) >= 0) {
			String times =  description.substring(3, idx);
			description = description.substring(idx+3);
			
			Matcher m = Pattern.compile("synSet\\:([^;]+)(;|$)").matcher(times);
			if (! m.find()){
				success = false;
				return false;
			}
			
			String synSetStr = m.group(1);
			
			int synSet = Integer.parseInt(synSetStr);
			
			addTime(synSet);
			
			m = Pattern.compile("predicates\\:\\[(.+)\\]").matcher(times);
			if ( m.find()){
				String predicates = m.group(1);
				parseComponents(predicates);
			}
			
        }
		
		return true;
	}
	
	/**
	 * Parse the places in an action
	 * @param description STON description for places
	 * @return
	 */
	private boolean parsePlaces(String description){
		
		int idx;
		while ((idx = description.indexOf("p:}")) >= 0) {
			String times =  description.substring(3, idx);
			description = description.substring(idx+3);
			
			Matcher m = Pattern.compile("synSet\\:([^;]+)(;|$)").matcher(times);
			if (! m.find()){
				success = false;
				return false;
			}
			
			String synSetStr = m.group(1);
			
			int synSet = Integer.parseInt(synSetStr);
			
			addPlace(synSet);
			
			m = Pattern.compile("predicates\\:\\[(.+)\\]").matcher(times);
			if ( m.find()){
				String predicates = m.group(1);
				parseComponents(predicates);
			}
			
        }
		
		return true;
	}
	


	
	//Action
	/**
	 * It is called when the parser finds an action
	 * @param id each action has a unique ID
	 * @param synSet this is the synset
	 */
	protected abstract void addAction(String id, int synSet);
	
	/**
	 * It is called to define the specifications of a verb
	 * @param tense It is the tense of the verb: past, present or future
	 * @param modality It is the modal verb: can, must, may, none
	 * @param progressive the action is progressive or not
	 * @param negated the action is negated or not
	 */
	protected abstract void addVerbSpecif(String tense, String modality, boolean progressive, boolean negated);
	
	/**
	 * It is called when the action is failed; It means when the parser find something
	 * wrong in the action block
	 */
	protected abstract void actionFail();
	
	//Subjects and Objects in the Action
	/**
	 * It is called when the parser finds subjects
	 */
	protected abstract void addSubjects();
	
	/**
	 * It is called when the parser finds objects
	 */
	protected abstract void addObjects();
	
	//Role
	/**
	 * It is called when the parser finds a role player
	 * @param id each role has a unique ID
	 * @param synSet wordnet synset of the Noun 
	 */
	protected abstract void addRole(String id, int synSet);
	
	/**
	 * It is called when the role player has an adjective
	 * @param synSet wordnet synset of the adjective which modify the noun in the role
	 * @param advSynSets wordnet synsets (Set) of adverbs which modify the adjective
	 */
	protected abstract void addAdjective(int synSet, Set<Integer> advSynSets);
	
	/**
	 * It is called when the parsing of an adjective failed
	 */
	protected abstract void adjectiveFail();
	
	/**
	 * it is called when the parsing of a role failed
	 */
	protected abstract void roleFail();
	
	/**
	 * It is called when the parsing of times failed
	 */
	protected abstract void timesFail();
	
	/**
	 * It is called to add a time
	 * @param synSet the synset of a time like: yesterday
	 */
	protected abstract void addTime(int synSet);
	
	/**
	 * It is called to add a location
	 * @param synSetthe synset of a location like: outside
	 */
	protected abstract void addPlace(int synSet);

	//can be used for subjects, objects, places or times
	protected abstract void addConjunctions(Set<String> IDs);
	
	//Parse
	protected abstract void parseSuccess();

}
