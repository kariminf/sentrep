package kariminf.sentrep.univ;

import kariminf.sentrep.univ.types.*;


public interface LangMap {
	public abstract <T extends Enum> T getTense(VerbTense tense);
	public abstract String getModal(Modality modal);
	public abstract String getAdposition(Relation adpos, String name);
	public abstract String getDeterminer(Determiner det);
	public abstract String getCoordination(Coordination coord);
	public abstract String getPreComparison(Comparison comp, boolean hasAdj);
	public abstract String getPostComparison(Comparison comp, boolean hasAdj);
}
