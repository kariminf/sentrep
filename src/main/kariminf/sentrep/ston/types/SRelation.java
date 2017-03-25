package kariminf.sentrep.ston.types;

import java.util.HashMap;

import kariminf.sentrep.types.Relation.*;

public class SRelation {
	
	/**
	 * it is a relation between a role and an action.
	 * An adjectival relation which describes the role
	 * @author Abdelkrime Aries
	 *
	 */
	public static enum SRelative {
		SBJ (Relative.SUBJECT), //subject
		OBJ (Relative.OBJECT), //object
		POS (Relative.POSSESSIVE), //possessive
		RSN (Relative.REASON), //reason
		IO_ (Relative.IO_EXIST);//indirect object
		
		private Relative relative;
		
		private SRelative (Relative r){
			relative = r;
		}
		
		public Relative getRelation(){
			return relative;
		}
		
		public static SRelative fromRelation(Relative rel){
			for (SRelative srel: SRelative.values())
				if (srel.relative == rel)
					return srel;
			return null;
		}
	}
	
	/**
	 * It is a relation between a role and a role;
	 * or between an action and a role
	 * @author Abdelkrime Aries
	 *
	 */
	public static enum SAdpositional {
		
		IN (Adpositional.EXIST), // particular time or location in, at (time, place, situation)
		AGO (Adpositional.PAST), //ago (time)
		SNC (Adpositional.SINCE), //means since (time)
		FRM (Adpositional.SOURCE), //from
		TO (Adpositional.DESTINATION), // till, to (time, place)
		FOR (Adpositional.INTENTION), // for (time, reason)
		BEF (Adpositional.BEFORE), //before, in front (time, place)
		AFT (Adpositional.AFTER), //after, behind (time, place)
		BY (Adpositional.PROXIMITY), //by (time, place)

		INS (Adpositional.INSIDE), // (place)
		OUT (Adpositional.OUTSIDE), // (place)
		BLW (Adpositional.BELOW), // (place)
		ABV (Adpositional.ABOVE), // (place)
		BTW (Adpositional.BETWEEN), 
		THR (Adpositional.THROUGH),

		ON (Adpositional.SUBJECT), //About, on "consultant on IT"
		WTH (Adpositional.ACCOMPANY), //With
		OF (Adpositional.POSSESSION), // OF
		AS (Adpositional.ROLE), //AS
		UND (Adpositional.SITUATION); //under some situation
		
		private Adpositional adposition;
		
		private SAdpositional (Adpositional r){
			adposition = r;
		}
		
		public Adpositional getRelation(){
			return adposition;
		}
		
		
		public static SAdpositional fromAdpositional(Adpositional adp){
			for (SAdpositional sadp: SAdpositional.values())
				if(sadp.adposition == adp) return sadp;
			return null;
		}
	}
	
	/**
	 * Adverbial phrases that modifies the verb
	 * @author Abdelkrime Aries
	 *
	 */
	public static enum SAdverbial {
		WHN (Adverbial.TIME), //when
		WHL (Adverbial.CONTINUUM), //while
		WHR (Adverbial.PLACE), //where
		IF (Adverbial.CONDITION), //if, unless, lest
		SO (Adverbial.PURPOSE), //in order to, so that, in order that
		BCS (Adverbial.REASON), //because, since, as, given
		THG (Adverbial.CONSESSION), //although, though, while
		LIK (Adverbial.MANNER), //as, like, the way
		FTR (Adverbial.AFTER),
		BFR (Adverbial.BEFORE)
		;
		
		Adverbial adverbial;
		
		private SAdverbial(Adverbial r){
			adverbial = r;
		}
		
		public Adverbial getRelation(){
			return adverbial;
		}
		
		public static SAdverbial fromRelation(Adverbial adv){
			for (SAdverbial sadv: SAdverbial.values())
				if (sadv.adverbial == adv) return sadv;
			return null;
		}
	}
	
	
}
