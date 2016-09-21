package kariminf.sentrep.ston;

import kariminf.sentrep.UnivMap;
import kariminf.sentrep.ston.types.SPronoun;
import kariminf.sentrep.ston.types.SPronoun.SFormality;
import kariminf.sentrep.ston.types.SPronoun.SGender;
import kariminf.sentrep.ston.types.SPronoun.SHead;
import kariminf.sentrep.ston.types.SPronoun.SNumber;
import kariminf.sentrep.ston.types.SPronoun.SPProperty;
import kariminf.sentrep.ston.types.SPronoun.SPerson;
import kariminf.sentrep.ston.types.SPronoun.SProximity;
import kariminf.sentrep.ston.types.SRelation.*;
import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Relation.*;
import kariminf.sentrep.univ.types.Pronoun.*;


public class Ston2UnivMap implements UnivMap {

	@Override
	public VerbTense mapTense(String langTense) {
		int idx = StonLex.getTenseIndex(langTense);
		
		switch (idx){
		case 1: return VerbTense.PAST;
		case 2: return VerbTense.FUTURE;
		
		}
		
		return VerbTense.PRESENT;
	}

	@Override
	public Modality mapModal(String langModal) {
		
		int idx = StonLex.getModalIndex(langModal);
		
		switch (idx){
		case 1: return Modality.CAN;
		case 2: return Modality.MAY;
		case 3: return Modality.MUST;
		
		}
		return Modality.NONE;
	}

	@Override
	public Adpositional mapAdposition(String langAdpos) {
		SAdpositional adp = SAdpositional.valueOf(langAdpos);	
		return adp.getRelation();
	}
	
	@Override
	public Relative mapRelative(String langRel) {
		
		if (! langRel.contains("_")){
			SRelative sr = SRelative.valueOf(langRel);
			return sr.getRelation();
		}
		
		String[] split = langRel.split("_");
		String result = split[0] + "_";
		
		SAdpositional adp = SAdpositional.valueOf(split[1]);
		result += adp.getRelation().name();
		
		return Relative.valueOf(result);
	}

	@Override
	public Adverbial mapAdverbial(String langAdv) {
		SAdverbial sadv = SAdverbial.valueOf(langAdv);
		return sadv.getRelation();
	}

	@Override
	public Determiner mapDeterminer(String langDet) {
		int idx = StonLex.getDetIndex(langDet);
		switch (idx){
		case 1: return Determiner.YES;
		case 2: return Determiner.NO;
		default: return Determiner.NONE;
		}
	}

	@Override
	public Comparison mapComparison(String comp) {
		int idx = StonLex.getCompIndex(comp);
		
		Comparison[] compValues = Comparison.values();
		
		if (idx < compValues.length && idx >=0)
			return compValues[idx];
		
		return Comparison.EQUAL;
	}

	@Override
	public Pronoun mapPronoun(String pronoun) {
		SPronoun spronoun = SPronoun.create(pronoun);
		Pronoun result = new Pronoun();
		result.setProperty(mapHead((SHead) spronoun.getProperty(SPProperty.Head)))
			.setProperty(mapNumber((SNumber) spronoun.getProperty(SPProperty.Number)))
			.setProperty(mapGender((SGender) spronoun.getProperty(SPProperty.Gender)))
			.setProperty(mapFormality((SFormality) spronoun.getProperty(SPProperty.Formality)))
			.setProperty(mapProximity((SProximity) spronoun.getProperty(SPProperty.Proximity)))
			.setProperty(mapPerson((SPerson) spronoun.getProperty(SPProperty.Person)));
		result.lockIt();
		return result;
	}
	
	private Head mapHead(SHead head){
		return Head.values()[head.ordinal()];
	}
	
	private Pronoun.Number mapNumber(SNumber number){
		return Pronoun.Number.values()[number.ordinal()];
	}
	
	private Gender mapGender(SGender gender){
		return Gender.values()[gender.ordinal()];
	}
	
	private Formality mapFormality(SFormality formality){
		return Formality.values()[formality.ordinal()];
	}
	
	private Proximity mapProximity(SProximity proximity){
		return Proximity.values()[proximity.ordinal()];
	}
	
	private Person mapPerson(SPerson person){
		return Person.values()[person.ordinal()];
	}

	@Override
	public SentMood mapMood(String mood) {
		// TODO Auto-generated method stub
		return SentMood.AFFIRMATIVE;
	}
	
public static void main(String[] args) {
		
		SPronoun p = new SPronoun();
		SPronoun p2 = SPronoun.create("DSXXXM");
		SPronoun p3 = SPronoun.create("DTDFPP");
		
		Ston2UnivMap m = new Ston2UnivMap();
		System.out.println(m.mapPronoun(p.toString()));
		System.out.println(m.mapPronoun(p2.toString()));
		System.out.println(m.mapPronoun(p3.toString()));
			
	}

}
