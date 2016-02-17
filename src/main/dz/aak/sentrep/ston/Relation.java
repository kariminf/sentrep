package dz.aak.sentrep.ston;

public enum Relation {
	SUBJ, //The man who ate
	OBJ, //The class which I teach
	POSS, //The man whose car is so expensive
	REAS, //The reason why he did this is unclear
	
	OF, //The mother of the boy
	
	//Time
	T_TIME, 
	T_PERIOD,
	T_AMOUNT,
	T_AGO, 
	T_BEFORE,
	T_AFTER,
	T_TILL,
	T_BY,
	
	//Place
	P_INSIDE,
	P_OUTSIDE,
	P_PLACE,
	P_ON,
	P_BELOW,
	P_ABOVE,
	P_BY,
	P_BETWEEN,
	P_BEHIND,
	P_FRONT,
	P_THROUGH,
	
	//Others
	ABOUT,
	FROM,
	WITH,
	TO
}
