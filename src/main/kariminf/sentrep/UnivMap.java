package kariminf.sentrep;

import kariminf.sentrep.univ.types.*;


public interface UnivMap {
	
	public abstract VerbTense mapTense(String langTense);
	public abstract Modality mapModal(String langModal);
	public abstract Relation mapAdposition(String langAdpos);
	public abstract Determiner mapDeterminer(String langDet);
	public abstract Comparison mapComparison(String comp);
	public abstract Pronoun mapPronoun(String pronoun);
}
