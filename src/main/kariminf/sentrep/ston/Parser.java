package kariminf.sentrep.ston;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kariminf.sentrep.ston.types.SPronoun;
import kariminf.sentrep.ston.types.SRelation.SAdpositional;

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
		//description = description.toLowerCase();
		description = description.replaceAll(StonBlocks.IGNOREblock, "");

		//System.out.println(description);
		Matcher m = StonBlocks.CONT.matcher(description);
		if (! m.find()) {
			parseFailure();
			return;
		}

		String roles =  m.group(1);
		String actions =  m.group(2);
		String sentences = m.group(3);
		//System.out.println(actions);
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

		for (String desc : description.split(";")){

			//desc = desc.trim();

			String key = StonKeys.TYPE + ":";
			if(desc.startsWith(key)){
				type = desc.split(":")[1];
				continue;
			}

			key = StonKeys.ACT + ":";
			if(desc.startsWith(key)){
				actions = desc.split(":")[1];
				continue;
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
			endActions(true);
		}

		endSentence(type);

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
		boolean perfect = false;
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
			
			key = StonKeys.PERFECT + ":";
			if(desc.startsWith(key)){
				String aspect = desc.split(":")[1];

				if(aspect.matches("y")){
					perfect = true;
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
			success = false;
			if (actionFailure()) return false;
		}

		// An action must have a synset which is a number
		if (! synSetStr.matches("\\d+")){
			success = false;
			if (roleFailure()) return false;
		}

		int synSet = Integer.parseInt(synSetStr);

		beginAction(id, synSet);

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
		addVerbSpecif(tense, modality, progressive, perfect, negated);

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
			if (! parseRelatives(relatives, false)) return false;
		}

		endAction(id, synSet);

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
			ArrayList<String> conjunctions = new ArrayList<String>();
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
			ArrayList<Integer> advSynSets = new ArrayList<Integer>();
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
				success = false;
				if (adjectiveFailure()) return false;
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
			ArrayList<Integer> advSynSets = new ArrayList<Integer>();

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
				success = false;
				if (adverbFailure()) return false;
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
		boolean pronounFound = false;
		type = type.trim();
		int synSet = 0;
		if (synSetStr.matches("\\d+")) synSet = Integer.parseInt(synSetStr);
		
		if (type.length() == SPronoun.PropertiesNumber){
			beginRole(id, synSet, type);
			ref = ref.trim();
			if (ref.length() > 2){
				ref = ref.substring(1, ref.length()-1);
				beginPRelatives();
				parseComponents(ref);
				endPRelatives();
			}

			pronounFound = true;
		} else{
			if (synSet > 0) beginRole(id, synSet);
		}

		// A role must have a synset which is a number
		if (synSet < 1 && ! pronounFound){
			success = false;
			if (roleFailure()) return false;
		}

		addRoleSpecif(name, def, quantity);

		//Process adjectives
		if (adjectives.length() > 0){
			if (! parseAdjectives(adjectives)) return false;
		}

		//Process relatives
		if (relatives.length() > 0){
			if (! parseRelatives(relatives, true)) return false;
		}

		//End of the role
		endRole(id, synSet);

		return true;

	}

	/**
	 * 
	 * @param description
	 * @return
	 */
	private boolean parseRelatives(String description, boolean role){
		int idx;
		String key = StonKeys.RELBL + ":}";
		while ((idx = description.indexOf(key)) >= 0) {
			String rel =  description.substring(5, idx);
			description = description.substring(idx+5);
			if (! parseRelative(rel, role))
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

	private boolean parseRelative(String description, boolean role){
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

		type = type.toUpperCase();

		// If there is no type
		boolean refuse = (type.length() < 1);
		//If the relation is not allowed in the role
		refuse = refuse || (role && !StonLex.isRoleRelation(type));

		//If the relation is not allowed in the action
		refuse = refuse || (!role && !StonLex.isActionRelation(type));

		if (refuse){
			success = false;
			if (relativeFailure()) return false;
		}

		beginRelative(type);

		//Process objects
		if(refs.length() > 2){
			if (!(refs.startsWith("[") && refs.endsWith("]")))
				return false;
			refs = refs.substring(1, refs.length()-1);
			parseComponents(refs);

		}
		
		endRelative(type);

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
			success = false;
			if (relativeFailure()) return false;
		}

		ArrayList<Integer> adjSynSets = new ArrayList<Integer>();
		if(adjs.length() > 2){
			if (!(adjs.startsWith("[") && adjs.endsWith("]")))
				return false;
			adjs = adjs.substring(1, adjs.length()-1);
			for (String AdvsynSetStr: adjs.split(",")){

				int AdjsynSet = Integer.parseInt(AdvsynSetStr);
				adjSynSets.add(AdjsynSet);
			}
		}

		beginComparison(type.toUpperCase(), adjSynSets);

		//Process objects
		if(refs.length() > 2){
			if (!(refs.startsWith("[") && refs.endsWith("]")))
				return false;
			refs = refs.substring(1, refs.length()-1);
			parseComponents(refs);

		}
		
		endComparison(type.toUpperCase(), adjSynSets);

		return true;
	}

	
	//=====================================================================
	//==== Protected methods to be implemented to process how every element 
	//==== can be handled when it has been found ==========================
	//=====================================================================
	
	
	
	//=====================================================================
	//======================== ACTION METHODS =============================
	//=====================================================================
	
	/**
	 * When the parser finds an Action, this method marks the beginning of 
	 * its process
	 * @param id Each action has a unique ID in STON
	 * @param synSet the verb's ID in the lexicon (Wordnet, or else)
	 */
	protected abstract void beginAction(String id, int synSet);
	
	/**
	 * When the parser finds an Action, this method marks the ending of 
	 * its process
	 * @param id Each action has a unique ID in STON
	 * @param synSet the verb's ID in the lexicon (Wordnet, or else)
	 */
	protected abstract void endAction(String id, int synSet);
	
	/**
	 * It is called when the action is failed; It means when the parser find something
	 * wrong in the action block
	 * @return false if we want the parser to continue nevertheless
	 */
	protected abstract boolean actionFailure();

	/**
	 * It is called inside the Action block to define the specifications of a verb
	 * @param tense It is the tense of the verb: past, present or future
	 * @param modality It is the modal verb: can, must, may, none
	 * @param progressive the action is progressive or not
	 * @param perfect the action has perfect aspect or not
	 * @param negated the action is negated or not
	 */
	protected abstract void addVerbSpecif(String tense, String modality, boolean progressive, boolean perfect, boolean negated);

	
	/**
	 * When the parser starts processing the agents of an action, this
	 * method marks the beginning of it.
	 */
	protected abstract void beginAgents();

	/**
	 * When the parser starts processing the agents of an action, this
	 * method marks the ending of it.
	 */
	protected abstract void endAgents();
	
	/**
	 * When the parser starts processing the themes of an action, this
	 * method marks the beginning of it.
	 */
	protected abstract void beginThemes();

	/**
	 * When the parser starts processing the themes of an action, this
	 * method marks the beginning of it.
	 */
	protected abstract void endThemes();

	/**
	 * When the action has adverbs, this method is called.
	 * @param advSynSet The Adverb's ID in the lexicon (Wordnet, or else)
	 * @param advSynSets A list of adverbs that modify the first adverb (can be null)
	 */
	protected abstract void addActionAdverb(int advSynSet, List<Integer> advSynSets);

	/**
	 * This is called when the processing of the adverb fails
	 * @return false if we want the parser to continue nevertheless
	 */
	protected abstract boolean adverbFailure();
	

	/**
	 * When the parser finds comparison inside Action block, this methods marks the beginning 
	 * of comparison processing
	 * @param type The type of comparison (See {@link kariminf.sentrep.ston.types.SComparison}): 
	 * @param adjSynSets The IDs of the comparison adjectives in the lexicon (Wordnet, or other)
	 */
	protected abstract void beginComparison(String type, List<Integer> adjSynSets);
	
	/**
	 * When the parser finds comparison inside Action block, this methods marks the beginning 
	 * of comparison processing
	 * @param type The type of comparison (See {@link kariminf.sentrep.ston.types.SComparison}): 
	 * @param adjSynSets The IDs of the comparison adjectives in the lexicon (Wordnet, or other)
	 */
	protected abstract void endComparison(String type, List<Integer> adjSynSets);

	
	//=====================================================================
	//========================= ROLE METHODS ==============================
	//=====================================================================
	
	/**
	 * It is called when the parser finds a role player to indicate the beginning of its process
	 * @param id each role has a unique ID
	 * @param synSet The ID the Noun in a lexicon (Wordnet, or else)
	 */
	protected abstract void beginRole(String id, int synSet);
	
	/**
	 * It is called when the parser finds a role player (pronoun), 
	 * to indicate the beginning of its process
	 * @param id each role has a unique ID
	 * @param synSet wordnet synset of the Noun 
	 * @param type the type of the pronoun See @StonLex
	 */
	protected abstract void beginRole(String id, int synSet, String pronoun);
	
	/**
	 * It is called when the parser finds a role player to indicate the ending of its process
	 * @param id each role has a unique ID
	 * @param synSet The ID the Noun in a lexicon (Wordnet, or else)
	 */
	protected abstract void endRole(String id, int synSet);


	/**
	 * it is called when the parsing of a role failed
	 * @return false if we want the parser to continue nevertheless
	 */
	protected abstract boolean roleFailure();
	
	/**
	 * It is called after {@link beginRole} when some further specifications are afforded
	 * @param name proper names if existed, if not the string is empty
	 * @param def defined or not  
	 * @param quantity the quantity of the role. Eg. 4 apples, it can take the term pl
	 * for plural, the ordinal quantity is preceded by the letter "o"
	 */
	protected abstract void addRoleSpecif(String name, String def, String quantity);

	/**
	 * It is called when the role player has an adjective
	 * @param synSet Wordnet synset of the adjective which modify the noun in the role
	 * @param advSynSets Wordnet synsets (Set) of adverbs which modify the adjective
	 */
	protected abstract void addAdjective(int synSet, List<Integer> advSynSets);

	/**
	 * It is called when the parsing of an adjective failed
	 * @return false if we want the parser to continue nevertheless
	 */
	protected abstract boolean adjectiveFailure();
	
	/**
	 * It is called when the parsing of current relative failed
	 * @return false if we want the parser to continue nevertheless
	 */
	protected abstract boolean relativeFailure();

	/**
	 * Called to mark the beginning of pronoun relatives
	 */
	protected abstract void beginPRelatives();
	
	/**
	 * Called to mark the ending of pronoun relatives
	 */
	protected abstract void endPRelatives();

	
	
	//=====================================================================
	//======================= SENTENCE METHODS ============================
	//=====================================================================
	
	/**
	 * It is called when a sentence is detected; It marks the beginning
	 * of sentence processing
	 * @param type the type of sentence, See {@link kariminf.sentrep.ston.types.SSentType}
	 */
	protected abstract void beginSentence(String type);
	
	/**
	 * It is called when a sentence is detected; It marks the ending
	 * of sentence processing
	 * @param type the type of sentence, See {@link kariminf.sentrep.ston.types.SSentType}
	 */
	protected abstract void endSentence(String type);

	/**
	 * It is called to mark the beginning of actions in a sentence.
	 * @param mainClause is it a main clause or secondary
	 */
	protected abstract void beginActions(boolean mainClause);
	
	/**
	 * It is called to mark the ending of actions in a sentence.
	 * @param mainClause is it a main clause or secondary
	 */
	protected abstract void endActions(boolean mainClause);
	
	
	//=====================================================================
	//======================== SHARED METHODS =============================
	//=====================================================================
	
	//can be used for subjects, objects, places or times
	/**
	 * This is called to add conjunctions. 
	 * It can be called in blocks such as: agents, themes, relatives, pronoun relatives, 
	 * actions of sentences, comparison, etc.
	 * @param IDs The IDs of either Actions or Roles, depending on the type of block
	 */
	protected abstract void addConjunctions(List<String> IDs);
	
	
	/**
	 * It is called to add a relative: time or location or else; It marks the beginning 
	 * of relative block
	 * @param type the type of the relatives See 
	 * {@link kariminf.sentrep.ston.types.SRelation.SRelative}
	 */
	protected abstract void beginRelative(String type);
	
	/**
	 * It is called to add a relative: time or location or else; It marks the ending 
	 * of relative block. <br>
	 * It depends on which block it is called: <br>
	 * If the block is a Role, there are two types of relatives: 
	 * {@link SRelative}
	 * which is is a relation between a role and an action; Or
	 * {@link SAdpositional} 
	 * which is a relation between a role and a role.
	 * <br>
	 * If the block is an Action, there are two types of relatives: 
	 * {@link SAdpositional}
	 * which is a relation between an action and a role; Or
	 * {@link SAdverbial}
	 * Which is a relation between an action and another.
	 * @param type the type of the relatives
	 */
	protected abstract void endRelative(String type);
	
	//=====================================================================
	//========================= PARSE METHODS =============================
	//=====================================================================
	
	/**
	 * Called when the parse ends with a success
	 */
	protected abstract void parseSuccess();
	
	/**
	 * it is called when the parsing failed (general structure)
	 */
	protected abstract void parseFailure();


}
