package kariminf.sentrep.univ.types;

public class Pronoun {
	
	

	public static enum Head {
		PERSONNAL,
		DEMONSTRATIVE
	}
	
	public static enum Person {
		FIRST,
		SECOND,
		THIRD
	}
	
	
	public static enum Number {
		NONE,
		SINGLE,
		DUAL,
		PLURAL
	}
	
	public static enum Gender {
		NEUTRAL,
		MALE,
		FEMALE
	}
	
	public static enum Formality {
		RUDE,
		CASUAL,
		FORMAL,
		POLITE
	}
	
	public static enum Proximity {
		UNDEF,
		DISTAL,
		MEDIAL,
		PROXIMAL
	}
	
	//Default 
	private Head head = Head.PERSONNAL;
	private Number number = Number.SINGLE;
	private Gender gender = Gender.NEUTRAL;
	private Formality formality = Formality.FORMAL;
	private Proximity proximity = Proximity.UNDEF;
	private Person person = Person.FIRST;
	
	private boolean unlocked = true;

	/**
	 * Creates a pronoun with default properties:
	 * @Head : @Head.PERSONNAL
	 */
	public Pronoun() {
	}
	
	/**
	 * Locks the modification
	 */
	public void lockIt(){
		unlocked = false;
	}
	
	/**
	 * Changes the type of the head
	 * @param head 
	 * @return
	 */
	public Pronoun setProperty(Head head){
		if (unlocked){
			this.head = head;
		}
		return this;
	}
	
	public Pronoun setProperty(Number number){
		if (unlocked){
			this.number = number;
		}
		return this;
	}
	
	public Pronoun setProperty(Gender gender){
		if (unlocked){
			this.gender = gender;
		}
		return this;
	}
	
	public Pronoun setProperty(Formality formality){
		if (unlocked){
			this.formality = formality;
		}
		return this;
	}
	
	public Pronoun setProperty(Proximity proximity){
		if (unlocked){
			this.proximity = proximity;
		}
		return this;
	}
	
	public Pronoun setProperty(Person person){
		if (unlocked){
			this.person = person;
		}
		return this;
	}
	
	
	/**
	 * @return the head
	 */
	public Head getHead() {
		return head;
	}

	/**
	 * @return the number
	 */
	public Number getNumber() {
		return number;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @return the formality
	 */
	public Formality getFormality() {
		return formality;
	}

	/**
	 * @return the proximity
	 */
	public Proximity getProximity() {
		return proximity;
	}
	
	public Person getPerson() {
		return person;
	}

	/**
	 * @return the unlocked
	 */
	public boolean isUnlocked() {
		return unlocked;
	}

}
