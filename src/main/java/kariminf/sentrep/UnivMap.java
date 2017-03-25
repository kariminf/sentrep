package kariminf.sentrep;

import kariminf.sentrep.types.*;
import kariminf.sentrep.types.Relation.*;

public interface UnivMap {
	
	public abstract VerbTense mapTense(String langTense);
	public abstract Modality mapModal(String langModal);
	public abstract Adpositional mapAdposition(String langAdpos);
	public abstract Relative mapRelative(String langRel);
	public abstract Adverbial mapAdverbial(String langAdv);
	public abstract Determiner mapDeterminer(String langDet);
	public abstract Comparison mapComparison(String comp);
	public abstract Pronoun mapPronoun(String pronoun);
	public abstract SentMood mapMood(String mood);
	
}
