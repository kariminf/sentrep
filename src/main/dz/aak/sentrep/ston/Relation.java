package dz.aak.sentrep.ston;

public enum Relation {
	SUBJ, //The man who ate
	OBJ, //The class which I teach
	POSS, //The man whose car is so expensive
	REAS, //The reason why he did this is unclear
	
	OF, //The mother of the boy
	
	//Time
	T_AT, 
	T_SNC,
	T_FOR,
	T_AGO, 
	T_BEF,
	T_AFT,
	T_TILL,
	T_BY,
	
	//Place
	P_IN,
	P_OUT,
	P_AT,
	P_ON,
	P_LOW,
	P_UP,
	P_BY,
	P_BET,
	P_BEH,
	P_FRN,
	P_THR,
	
	//Others
	ABOUT,
	FROM,
	WITH,
	TO
}
