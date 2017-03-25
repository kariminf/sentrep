package kariminf.sentrep.types;

public class Relation {
	
	public static enum Relative {
		SUBJECT, //The man who ate
		OBJECT, //The class which I teach
		POSSESSIVE, //The man whose car is so expensive
		REASON, //The reason why he did this is unclear
		IO_EXIST, // in which, at which
		IO_PAST, //which ... ago (time)
		IO_SINCE, //since which
		IO_SOURCE, //from which
		IO_DESTINATION, // till, to which
		IO_INTENTION, // for which
		IO_BEFORE, //before, in front which
		IO_AFTER, //after, behind which
		IO_PROXIMITY, //by which
		
		IO_INSIDE, // inside which
		IO_OUTSIDE, // outside which
		IO_BELOW, // below which
		IO_ABOVE, // above which
		IO_BETWEEN, //between which
		IO_THROUGH, // thought which
		
		IO_SUBJECT, //About, on which
		IO_ACCOMPANY, //With which
		IO_POSSESSION, // OF which
		IO_ROLE, //AS which
		IO_SITUATION //under which
		
		;
		
		public static Relative fromAdpositional(Adpositional adp){
			return Relative.valueOf("IO_" + adp.name());
		}
	}
	
	public static enum Adpositional {
		EXIST, // particular time or location in, at (time, place, situation)
		PAST, //ago (time)
		SINCE, //means since (time)
		SOURCE, //from (time, place, other)
		DESTINATION, // till, to (time, place, intention)
		INTENTION, // for (time, intention)
		BEFORE, //before, in front (time, place)
		AFTER, //after, behind (time, place)
		PROXIMITY, //by (time, place)
		
		INSIDE, // (place)
		OUTSIDE, // (place)
		BELOW, // (place)
		ABOVE, // (place)
		BETWEEN, // place, time
		THROUGH,//place, time
		
		SUBJECT, //About, on "consultant on IT"
		ACCOMPANY, //With
		POSSESSION, // OF
		ROLE, //AS
		SITUATION //under some situation
	}
	
	public static enum Adverbial {
		TIME, //when
		CONTINUUM, //while
		PLACE, //where
		CONDITION, //if, unless, lest
		PURPOSE, //in order to, so that, in order that, to
		REASON, //because, since, as, given
		CONSESSION, //although, though, while
		MANNER, //as, like, the way
		AFTER, // after I ...
		BEFORE // before I
	}
	
}
