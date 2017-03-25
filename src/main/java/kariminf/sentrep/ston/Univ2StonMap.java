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
import kariminf.sentrep.ston.types.SRelation.SAdpositional;
import kariminf.sentrep.ston.types.SRelation.SAdverbial;
import kariminf.sentrep.ston.types.SRelation.SRelative;
import kariminf.sentrep.types.*;
import kariminf.sentrep.types.Pronoun.Formality;
import kariminf.sentrep.types.Pronoun.Gender;
import kariminf.sentrep.types.Pronoun.Head;
import kariminf.sentrep.types.Pronoun.Person;
import kariminf.sentrep.types.Pronoun.Proximity;
import kariminf.sentrep.types.Relation.Adpositional;
import kariminf.sentrep.types.Relation.Adverbial;
import kariminf.sentrep.types.Relation.Relative;
import kariminf.sentrep.ston.types.SVerbModal;
import kariminf.sentrep.ston.types.SVerbTense;

public class Univ2StonMap implements LangMap {

	@Override
	public String getTense(VerbTense tense) {
		switch (tense) {
		case FUTURE:
			return SVerbTense.FU.name();
		case PAST:
			return SVerbTense.PA.name();
		case PRESENT:
			return SVerbTense.PR.name();
		default:
			break;
		}
		
		return "";
	}

	@Override
	public String getModal(Modality modal) {
		switch (modal) {
		case CAN:
			return SVerbModal.CAN.name();
		case MAY:
			return SVerbModal.MAY.name();
		case MUST:
			return SVerbModal.MUST.name();
		case NONE:
			return SVerbModal.NONE.name();
		default:
			break;
		}
		return "";
	}

	@Override
	public String getAdposition(Adpositional adpos, String param) {
		for (SAdpositional sadp: SAdpositional.values())
			if (sadp.getRelation() == adpos) 
				return sadp.name();
		return "";
	}

	@Override
	public String getAdverbial(Adverbial adv, String param) {
		SAdverbial sadv = SAdverbial.fromRelation(adv);
		return sadv.name();
	}

	@Override
	public String getRelative(Relative rel, String param) {
		if (! rel.name().contains("_")){
			return SRelative.fromRelation(rel).name();
		}
		
		String[] split = rel.name().split("_");
		String result = split[0] + "_";
		
		Adpositional adp = Adpositional.valueOf(split[1]);
		result += SAdpositional.fromAdpositional(adp).name();
		
		return result;
	}

	@Override
	public String getDeterminer(Determiner det) {
		switch (det) {
		case NO:
			return "N";
		case NONE:
			return "NONE";
		case YES:
			return "Y";
		default:
			break;
		}
		return "";
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
