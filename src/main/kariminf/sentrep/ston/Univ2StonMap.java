package kariminf.sentrep.ston;

import kariminf.sentrep.LangMap;
import kariminf.sentrep.ston.types.SPronoun;
import kariminf.sentrep.ston.types.SPronoun.SFormality;
import kariminf.sentrep.ston.types.SPronoun.SGender;
import kariminf.sentrep.ston.types.SPronoun.SHead;
import kariminf.sentrep.ston.types.SPronoun.SNumber;
import kariminf.sentrep.ston.types.SPronoun.SPProperty;
import kariminf.sentrep.ston.types.SPronoun.SPerson;
import kariminf.sentrep.ston.types.SPronoun.SProximity;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Pronoun.Formality;
import kariminf.sentrep.univ.types.Pronoun.Gender;
import kariminf.sentrep.univ.types.Pronoun.Head;
import kariminf.sentrep.univ.types.Pronoun.Person;
import kariminf.sentrep.univ.types.Pronoun.Proximity;
import kariminf.sentrep.univ.types.Relation.Adpositional;
import kariminf.sentrep.univ.types.Relation.Adverbial;
import kariminf.sentrep.univ.types.Relation.Relative;

public class Univ2StonMap implements LangMap {

	@Override
	public String getTense(VerbTense tense) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModal(Modality modal) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAdposition(Adpositional adpos, String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAdverbial(Adverbial adv, String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRelative(Relative rel, String param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeterminer(Determiner det) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCoordination(Coordination coord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPreComparison(Comparison comp, boolean hasAdj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPostComparison(Comparison comp, boolean hasAdj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPronoun(Pronoun pronoun) {
		SPronoun spronoun = new SPronoun();
		spronoun.setProperty(mapHead(pronoun.getHead()))
		.setProperty(mapNumber(pronoun.getNumber()))
		.setProperty(mapGender(pronoun.getGender()))
		.setProperty(mapFormality(pronoun.getFormality()))
		.setProperty(mapProximity(pronoun.getProximity()))
		.setProperty(mapPerson(pronoun.getPerson()));
		return spronoun.toString();
	}
	
	private SHead mapHead(Head head){
		return SHead.values()[head.ordinal()];
	}
	
	private SNumber mapNumber(Pronoun.Number number){
		return SNumber.values()[number.ordinal()];
	}
	
	private SGender mapGender(Gender gender){
		return SGender.values()[gender.ordinal()];
	}
	
	private SFormality mapFormality(Formality formality){
		return SFormality.values()[formality.ordinal()];
	}
	
	private SProximity mapProximity(Proximity proximity){
		return SProximity.values()[proximity.ordinal()];
	}
	
	private SPerson mapPerson(Person person){
		return SPerson.values()[person.ordinal()];
	}

	
}
