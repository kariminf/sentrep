package kariminf.sentrep.ston;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kariminf.sentrep.ston.types.SPronoun;

/**
 * 
 * @author Abdelkrime Aries
 *
 */
public abstract class Parser {

	
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
		
		description = description.replaceAll(StonBlocks.BL, "");
		description = description.toLowerCase();
		
		//System.out.println(description);
		Matcher m = StonBlocks.CONT.matcher(description);
		if (! m.find()) {
			parseFail();
			return;
        }
		
		String roles =  m.group(1);
		String actions =  m.group(2);
		String sentences = m.group(3);
		if (! parseRoles(roles)) return;
		if (! parseActions(actions)) return;
		if (! parseSentences(sentences)) return;
		
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
		String key = StonKeys.ROLEBL + ":}";
		while ((idx = description.indexOf(key)) >= 0) {
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
		String key = StonKeys.ACTBL + ":}";
		while ((idx = description.indexOf(key)) >= 0) {
			String action =  description.substring(5, idx);
			description = description.substring(idx+5);
			//System.out.println(role);
			if (! parseAction(action))
				return false;
			
        }
		
		return true;
	}
	
	/**
	 * 
	 * @param description
	 * @return
	 */
	private boolean parseSentences(String description){
		int idx;
		String key = StonKeys.SENTBL + ":}";
		while ((idx = description.indexOf(key)) >= 0) {
			String sentence =  description.substring(4, idx);
			description = description.substring(idx+4);
			if (! parseSentence(sentence))
				return false;
        }
		return true;
	}
	
	/**
	 * 
	 * @param description
	 * @return
	 */
	private boolean parseSentence(String description){
		
		String type = "";
		String actions = "";
		String actions2 = "";
		
		for (String desc : description.split(";")){
			
			//desc = desc.trim();
			
			String key = StonKeys.TYPE + ":";
			if(desc.startsWith(key)){
				type = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.ACT1 + ":";
			if(desc.startsWith(key)){
				actions = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.ACT2 + ":";
			if(desc.startsWith(key)){
				actions2 = desc.split(":")[1];
			}
		}
		
		beginSentence(type);
		
		
		if(actions.length() > 2){
			if (!(actions.startsWith("[") && actions.endsWith("]"))){
				return false;
			}
			
			actions = actions.substring(1, actions.length()-1);
			beginActions(true);
			parseComponents(actions);
			endActions();
		}
		
		if(type.matches("cond|cause")){
			if(actions2.length() > 2){
				if (!(actions2.startsWith("[") && actions2.endsWith("]"))){
					return false;
				}
				actions2 = actions2.substring(1, actions2.length()-1);
				beginActions(false);
				parseComponents(actions2);
				endActions();
			}
		}
		
		endSentence();
		
		return true;
	}
	
	
	/**
	 * Parses one action block
	 * @param description STON description of one action
	 * @return True if there is no error
	 */
	private boolean parseAction(String description){
		
		String id = "";
		String synSetStr = "";
		String tense = "";
		boolean progressive = false;
		boolean negated = false;
		String modality = StonLex.getDefaultModal();
		String agents = "";
		String themes = "";
		String adverbs = "";
		
		String relatives = "";
		String comparison = "";
		
		if (description.contains(StonBlocks.beginADV)){
			
			Pattern adpPattern = 
				Pattern.compile("(.*)" + StonBlocks.ADVblock + "(.*)");
			Matcher m = adpPattern.matcher(description);
			if (m.find()){
				adverbs = m.group(2);
				//System.out.println("time found");
				description = m.group(1) + m.group(3);		
			}
		}

		if (description.contains(StonBlocks.beginREL)){
			
			Pattern relPattern = 
				Pattern.compile("(.*)" + StonBlocks.RELblock + "(.*)");
			Matcher m = relPattern.matcher(description);
			if (m.find()){
				relatives = m.group(2);
				//System.out.println("time found");
				description = m.group(1) + m.group(3);		
			}
		}
		
		if (description.contains(StonBlocks.beginCMP)){
			
			Pattern cmpPattern = 
				Pattern.compile("(.*)" + StonBlocks.CMPblock + "(.*)");
			Matcher m = cmpPattern.matcher(description);
			if (m.find()){
				comparison = m.group(2);
				//System.out.println("time found");
				description = m.group(1) + m.group(3);		
			}
		}
		
		for (String desc : description.split(";")){
			
			desc = desc.toLowerCase();
			
			String key = StonKeys.ID + ":";
			if(desc.startsWith(key)){
				id = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.SYNSET + ":";
			if(desc.startsWith(key)){
				synSetStr = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.TENSE + ":";
			if(desc.startsWith(key)){
				tense = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.PROGRESSIVE + ":";
			if(desc.startsWith(key)){
				String aspect = desc.split(":")[1];
				
				if(aspect.matches("y")){
					progressive = true;
				}
				continue;
			}
			
			key = StonKeys.NEGATED + ":";
			if(desc.startsWith(key)){
				String negate = desc.split(":")[1];
				if(negate.matches("y")){
					negated = true;
				}
				continue;
			}
			
			key = StonKeys.MODAL + ":";
			if(desc.startsWith(key)){
				modality = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.AGENT + ":";
			if(desc.startsWith(key)){
				agents = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.THEME + ":";
			if(desc.startsWith(key)){
				themes = desc.split(":")[1];
				continue;
			}
		}
		
		//The action must have an ID
		if(id.length() < 1){
			actionFail();
			success = false;
			return false;
		}
		
		// An action must have a synset which is a number
		if (! synSetStr.matches("\\d+")){
			roleFail();
			success = false;
			return false;
		}
		
		int synSet = Integer.parseInt(synSetStr);
		
		addAction(id, synSet);
		
		// There are three tenses
		tense = tense.toUpperCase();
		if(! StonLex.isTense(tense)){
			tense = StonLex.getDefaultTense();
		}
		
		// There are three modalities: permissibility, possibility and obligation
		modality = modality.toUpperCase();
		if(! StonLex.isModal(modality)){
			modality = StonLex.getDefaultModal();
		}
		
		//Defines verb specifications
		addVerbSpecif(tense, modality, progressive, negated);

		// Process subjects
		if(agents.length() > 2){
			if (!(agents.startsWith("[") && agents.endsWith("]"))){
				//System.out.println("subjects=" + subjects);
				return false;
			}
			
			agents = agents.substring(1, agents.length()-1);
			beginAgents();
			parseComponents(agents);
			endAgents();
		}
		
		//Process objects
		if(themes.length() > 2){
			if (!(themes.startsWith("[") && themes.endsWith("]")))
				return false;
			themes = themes.substring(1, themes.length()-1);
			beginThemes();
			parseComponents(themes);
			endThemes();
		}
		
		//Process adverbs
		if(adverbs.length() > 2){
			if (! parseAdverbs(adverbs)) return false;
		}
		
		// Process the relative clause
		if (comparison.length()>0){
			if (! parseComparisons(comparison)) return false;
		}
		
		// Process the relative clause
		if (relatives.length()>0){
			if (! parseRelatives(relatives)) return false;
		}
		
		endAction();
		
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
		String key0 = StonKeys.ADJBL + ":}";
		while ((idx = description.indexOf(key0)) >= 0) {
			String adjective =  description.substring(5, idx);
			description = description.substring(idx+5);
			if (description.startsWith(","))
				description = description.substring(1);
			
			String[] descs = adjective.split(";");
			
			int synSet = 0;
			HashSet<Integer> advSynSets = new HashSet<Integer>();
			for (String desc: descs){
				
				String key = StonKeys.SYNSET + ":";
				if(desc.startsWith(key)){
					String synSetStr = desc.split(":")[1];
					synSet = Integer.parseInt(synSetStr);
					continue;
				}
				
				key = StonKeys.ADVERB + ":";
				if(desc.startsWith(key)){
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
	
	
	/**
	 * Parse the adverbs that modify the action (verb)
	 * @param description
	 * @return
	 */
	private boolean parseAdverbs(String description){
		
		int idx;
		String key0 = StonKeys.ADVBL + ":}";
		while ((idx = description.indexOf(key0)) >= 0) {
			String adverb =  description.substring(5, idx);
			description = description.substring(idx+5);
			if (description.startsWith(","))
				description = description.substring(1);
			
			String[] descs = adverb.split(";");
			
			int synSet = 0;
			HashSet<Integer> advSynSets = new HashSet<Integer>();
			
			for (String desc: descs){
				
				String key = StonKeys.SYNSET + ":";
				if(desc.startsWith(key)){
					String synSetStr = desc.split(":")[1];
					synSet = Integer.parseInt(synSetStr);
					continue;
				}
				
				key = StonKeys.ADVERB + ":";
				if(desc.startsWith(key)){
					String synSetStrs = desc.split(":")[1];
					synSetStrs = synSetStrs.substring(1, synSetStrs.length()-1);
					
					for (String AdvsynSetStr: synSetStrs.split(",")){
						
						int AdvsynSet = Integer.parseInt(AdvsynSetStr);
						advSynSets.add(AdvsynSet);
					}
				}
				
			}
			
			if (synSet < 1){
				adverbFail();
				success = false;
				return false;
			}
			
			addActionAdverb(synSet, advSynSets);
			
        }
		
		return true;
	}
	
	
	private boolean parseRole(String description){
		
		String id = "";
		String synSetStr = "";
		String name = "";
		String quantity = "";
		String def = StonLex.getDefaultDeterminer();
		String adjectives = "";
		String relatives = "";
		String type = "";
		String ref = "";
		
		String key = StonKeys.BLOCKSIGN + StonKeys.ADJBL;
		if (description.contains(key)){
			
			Pattern adpPattern = 
				Pattern.compile("(.*)" + StonBlocks.ADJblock + "(.*)");
			Matcher m = adpPattern.matcher(description);
			if (m.find()){
				adjectives = m.group(2);
				//System.out.println("time found");
				description = m.group(1) + m.group(3);		
			}
		}
		
		key = StonKeys.BLOCKSIGN + StonKeys.RELBL;
		if (description.contains(key)){
			
			Pattern adpPattern = 
				Pattern.compile("(.*)" + StonBlocks.RELblock + "(.*)");
			Matcher m = adpPattern.matcher(description);
			if (m.find()){
				relatives = m.group(2);
				//System.out.println("time found");
				description = m.group(1) + m.group(3);		
			}
		}

		for (String desc: description.split(";")){
			
			desc = desc.trim();
			
			key = StonKeys.ID + ":";
			if(desc.startsWith(key)){
				id = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.SYNSET + ":";
			if(desc.startsWith(key)){
				synSetStr = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.NAME + ":";
			if(desc.startsWith(key)){
				name = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.QUANTITY + ":";
			if(desc.startsWith(key)){
				quantity = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.DEFINED + ":";
			if(desc.startsWith(key)){
				String defStr = desc.split(":")[1].toUpperCase();
				if (StonLex.isDeterminer(defStr)){
					def = defStr;
				}
				continue;
			}
			
			key = StonKeys.TYPE + ":";
			if(desc.startsWith(key)){
				type = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.REFERENCE + ":";
			if(desc.startsWith(key)){
				ref = desc.split(":")[1];
				continue;
			}
		}
		
		// A role can be a pronoun which is encoded in a number of characters
		//See @SPronoun for description
		
		type = type.trim();
		if (type.length() == SPronoun.PropertiesNumber){
			addPRole(id, type);
			ref = ref.trim();
			ref = ref.substring(1, ref.length()-1);
			if (ref.length() > 2){
				beginPRelatives();
				parseComponents(ref);
				endPRelatives();
			}
				
			return true;
		}
		
		//A role must have an ID
		if (id.length() < 1){
			roleFail();
			success = false;
			return false;
		}
		
		// A role must have a synset which is a number
		if (! synSetStr.matches("\\d+")){
			roleFail();
			success = false;
			return false;
		}
		
		int synSet = Integer.parseInt(synSetStr);
		
		// Add the role 
		addRole(id, synSet);
		addRoleSpecif(name, def, quantity);

		//Process adjectives
		if (adjectives.length() > 0){
			if (! parseAdjectives(adjectives)) return false;
		}
		
		//Process relatives
		if (relatives.length() > 0){
			if (! parseRelatives(relatives)) return false;
		}
		
		//Process 
		
		return true;
		
	}

	/**
	 * 
	 * @param description
	 * @return
	 */
	private boolean parseRelatives(String description){
		int idx;
		String key = StonKeys.RELBL + ":}";
		while ((idx = description.indexOf(key)) >= 0) {
			String rel =  description.substring(5, idx);
			description = description.substring(idx+5);
			if (! parseRelative(rel))
				return false;
        }
		
		return true;
	}
	
	private boolean parseComparisons(String description){
		int idx;
		String key = StonKeys.COMPBL + ":}";
		while ((idx = description.indexOf(key)) >= 0) {
			String cmp =  description.substring(5, idx);
			description = description.substring(idx+5);
			if (! parseComparison(cmp))
				return false;
        }
		
		return true;
	}
	

	private boolean parseRelative(String description){
		String type= "";
		String refs = "";
		for (String desc : description.split(";")){
			
			String key = StonKeys.TYPE + ":";
			if(desc.startsWith(key)){
				type = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.REFERENCE + ":";
			if(desc.startsWith(key)){
				refs = desc.split(":")[1];
				continue;
			}
			
		}
		
		//If there is no type
		if (type.length() < 1){
			relativeFail();
			success = false;
			return false;
		}
		
		addRelative(type.toUpperCase());
		
		//Process objects
		if(refs.length() > 2){
			if (!(refs.startsWith("[") && refs.endsWith("]")))
				return false;
			refs = refs.substring(1, refs.length()-1);
			parseComponents(refs);

		}
		
		return true;
	}
	
	private boolean parseComparison(String description){
		
		String type= "";
		String refs = "";
		String adjs = "";
		
		for (String desc : description.split(";")){
			
			String key = StonKeys.TYPE + ":";
			if(desc.startsWith(key)){
				type = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.REFERENCE + ":";
			if(desc.startsWith(key)){
				refs = desc.split(":")[1];
				continue;
			}
			
			key = StonKeys.ADJECTIVE + ":";
			if(desc.startsWith(key)){
				adjs = desc.split(":")[1];
				continue;
			}
			
		}
		
		//If there is no type
		if (type.length() < 1){
			relativeFail();
			success = false;
			return false;
		}
		
		HashSet<Integer> adjSynSets = new HashSet<Integer>();
		if(adjs.length() > 2){
			if (!(adjs.startsWith("[") && adjs.endsWith("]")))
				return false;
			adjs = adjs.substring(1, adjs.length()-1);
			for (String AdvsynSetStr: adjs.split(",")){
				
				int AdjsynSet = Integer.parseInt(AdvsynSetStr);
				adjSynSets.add(AdjsynSet);
			}
		}
		
		addComparison(type.toUpperCase(), adjSynSets);
		
		//Process objects
		if(refs.length() > 2){
			if (!(refs.startsWith("[") && refs.endsWith("]")))
				return false;
			refs = refs.substring(1, refs.length()-1);
			parseComponents(refs);

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
	protected abstract void beginAgents();
	
	protected abstract void endAgents();
	
	protected abstract void addActionAdverb(int advSynSet, Set<Integer> advSynSets);
	
	protected abstract void adverbFail();
	/**
	 * It is called when the parser finds objects
	 */
	protected abstract void beginThemes();
	
	protected abstract void endThemes();
	
	protected abstract void endAction();
	
	//Role
	/**
	 * It is called when the parser finds a role player
	 * @param id each role has a unique ID
	 * @param synSet wordnet synset of the Noun 
	 */
	protected abstract void addRole(String id, int synSet);
	
	/**
	 * It is called when the parser finds a role player (pronoun)
	 * @param id each role has a unique ID
	 * @param type the type of the pronoun See @StonLex
	 */
	protected abstract void addPRole(String id, String pronoun);
	
	/**
	 * It is called after {@link addRole}
	 * @param name proper names if existed, if not the string is empty
	 * @param def defined or not  
	 * @param quantity the quantity of the role. Eg. 4 apples, it can take the term pl
	 * for plural
	 */
	protected abstract void addRoleSpecif(String name, String def, String quantity);
	
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
	 * it is called when the parsing failed (general structure)
	 */
	protected abstract void parseFail();
	
	/**
	 * It is called when the parsing of relatives failed
	 */
	protected abstract void relativeFail();
	
	/**
	 * It is called to add an relative: time or location
	 * @param type the type of the relatives
	 */
	protected abstract void addRelative(String SP);
	
	
	protected abstract void addComparison(String type, Set<Integer> adjSynSets);
	
	//can be used for subjects, objects, places or times
	protected abstract void addConjunctions(Set<String> IDs);
	
	
	//Parse
	protected abstract void parseSuccess();
	
	
	protected abstract void beginSentence(String type);
	protected abstract void endSentence();
	
	protected abstract void beginActions(boolean mainClause);
	protected abstract void endActions();
	
	protected abstract void beginPRelatives();
	protected abstract void endPRelatives();

}
