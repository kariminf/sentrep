package kariminf.sentrep;

import kariminf.sentrep.univ.types.*;
import kariminf.sentrep.univ.types.Relation.Adpositional;
import kariminf.sentrep.univ.types.Relation.Adverbial;
import kariminf.sentrep.univ.types.Relation.Relative;


public interface LangMap {
	public abstract String getTense(VerbTense tense);
	public abstract String getModal(Modality modal);
	public abstract String getAdposition(Adpositional adpos, String param);
	public abstract String getAdverbial(Adverbial adv, String param);
	public abstract String getRelative(Relative rel, String param);
	public abstract String getDeterminer(Determiner det);
	public abstract String getCoordination(Coordination coord);
	public abstract String getPreComparison(Comparison comp, boolean hasAdj);
	public abstract String getPostComparison(Comparison comp, boolean hasAdj);
	public abstract String getPronoun(Pronoun pronoun);
}
