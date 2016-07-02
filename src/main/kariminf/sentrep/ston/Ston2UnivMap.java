package kariminf.sentrep.ston;

import kariminf.sentrep.UnivMap;
import kariminf.sentrep.univ.types.*;

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
	public Relation mapAdposition(String langAdpos) {
		
		Relation adpos = Relation.valueOf(langAdpos);
		
		return adpos;
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
}
