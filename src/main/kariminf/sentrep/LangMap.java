package kariminf.sentrep;

import kariminf.sentrep.univ.types.*;


public interface LangMap {
	public abstract String getTense(VerbTense tense);
	public abstract String getModal(Modality modal);
	public abstract String getAdposition(Relation adpos, String param);
	public abstract String getDeterminer(Determiner det);
	public abstract String getCoordination(Coordination coord);
	public abstract String getPreComparison(Comparison comp, boolean hasAdj);
	public abstract String getPostComparison(Comparison comp, boolean hasAdj);
	public abstract String getPronoun(Pronoun pronoun);
}
