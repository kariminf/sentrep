package kariminf.sentrep;

import kariminf.sentrep.types.*;
import kariminf.sentrep.types.Relation.Adpositional;
import kariminf.sentrep.types.Relation.Adverbial;
import kariminf.sentrep.types.Relation.Relative;

/**
 * <p>This interface is used to transform lexical and morphological markers 
 * from SentRep Universal annotation to a defined language. </p>
 * 
 * <p>By implementing this interface, we define the mapping mechanisms between 
 * the universal annotation and a destination language</p>
 * <p>
 * For example, suppose our languages has as verb tenses: "P" for past, "R" for present 
 * and no mark for future, so it can be considered as present.
 * <br>
 * If we want to create an object with an anonymous class 
 * implementing this interface, the code would be:
 * <pre>
 * LangMap lm = new LangMap(){
 *   public String getTense(VerbTense tense){
 *     if (tense == VerbTense.PAST)
 *       return "P";
 *     return "R";
 *   }
 *   ...
 * }
 * </pre>
 * 
 * @author Abdelkrime Aries
 *
 */
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
