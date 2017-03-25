package kariminf.sentrep.ston.types;

import java.util.TreeMap;

public class SPronoun {

	public static interface SProProperty {}
	
	public static enum SPProperty{
		Head(SHead.S),
		Person(SPerson.F),
		Number(SNumber.S),
		Gender(SGender.N),
		Formality(SFormality.F),
		Proximity(SProximity.N);
		
		private SProProperty property;
		
		private SPProperty(SProProperty property){
			this.property = property;
		}
		
		public String getDefault(){
			return property.toString();
		}
		
		public SProProperty getProperty(){
			return property;
		}
		
		
	}
	
	public static enum SHead implements SProProperty {
		S,
		O,
		P,
		D
	}
	
	public static enum SPerson implements SProProperty {
		F,
		S,
		T
	}
	
	
	public static enum SNumber implements SProProperty {
		N,
		S,
		D,
		P
	}
	
	public static enum SGender implements SProProperty{
		N,
		M,
		F
	}
	
	
	public static enum SFormality implements SProProperty {
		R,
		C,
		F,
		P
	}
	
	public static enum SProximity implements SProProperty{
		N,
		D,
		M,
		P
	}
	
	public static final int PropertiesNumber = SPProperty.values().length;
	
	private TreeMap<SPProperty, SProProperty> properties = 
			new TreeMap<SPProperty, SProProperty>();
	
	
	
	public SPronoun(){
		for(SPProperty p: SPProperty.values())
			properties.put(p, p.getProperty());
	}
	
	/*
	private static TreeMap<SPProperty, SProProperty> initProperties(){
		TreeMap<SPProperty, SProProperty> result = new TreeMap<SPProperty, SProProperty>();
		for(SPProperty p: SPProperty.values())
			result.put(p, p.getProperty());
		return result;
	}
	*/
	
	public static SPronoun create(String specif){
		SPronoun result = new SPronoun();
		int specifLen = specif.length();
		int i = 0;
		specif = specif.toUpperCase();
		for(SPProperty p: SPProperty.values()){
			if (i >= specifLen)
				break;
			result.setProperty(p, "" + specif.charAt(i));
			i++;
		}
		
		return result;
	}

	
	public SPronoun setProperty(SFormality formality){
		properties.put(SPProperty.Formality, formality);
		return this;
	}
	
	public SPronoun setProperty(SGender gender){
		properties.put(SPProperty.Gender, gender);
		return this;
	}
	
	public SPronoun setProperty(SHead head){
		properties.put(SPProperty.Head, head);
		return this;
	}
	
	public SPronoun setProperty(SNumber number){
		properties.put(SPProperty.Number, number);
		return this;
	}
	
	public SPronoun setProperty(SProximity proximity){
		properties.put(SPProperty.Proximity, proximity);
		return this;
	}
	
	public SPronoun setProperty(SPerson person){
		properties.put(SPProperty.Person, person);
		return this;
	}
	
	private void setProperty(SPProperty property, String value){
		
		switch(property){
		
		case Formality:
			
			SFormality formality;
			try {
				formality = SFormality.valueOf(value);
			}
			catch (IllegalArgumentException e){
				formality = (SFormality) property.getProperty();
			}
			properties.put(property, formality);
			break;
			
		case Gender:
			SGender gender;
			try{
				gender = SGender.valueOf(value);
			}
			catch (IllegalArgumentException e){
				gender = (SGender) property.getProperty();
			}
			properties.put(property, gender);
			break;
			
		case Head:
			SHead head;
			try{
				head = SHead.valueOf(value);
			}
			catch (IllegalArgumentException e){
				head = (SHead) property.getProperty();
			}
			properties.put(property, head);
			break;
			
		case Number:
			SNumber number;
			try {
				number = SNumber.valueOf(value);
			}
			catch (IllegalArgumentException e){
				number = (SNumber) property.getProperty();
			}
			properties.put(property, number);
			break;
			
		case Proximity:
			SProximity proximity;
			try{
				proximity = SProximity.valueOf(value);
			}
			catch (IllegalArgumentException e){
				proximity = (SProximity) property.getProperty();
			}
			properties.put(property, proximity);
			break;
			
		case Person:
			
			SPerson person;
			try {
				person = SPerson.valueOf(value);
			}
			catch (IllegalArgumentException e){
				person = (SPerson) property.getProperty();
			}
			properties.put(property, person);
			break;

		default:
			break;
		
		}
	}
	
	
	public SProProperty getProperty(SPProperty property){
		return properties.get(property);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return properties.values().toString()
				.replaceAll("[\\[\\], ]", "");
	}
	
	
	public static void main(String[] args) {
		
		SPronoun p = new SPronoun();
		SPronoun p2 = SPronoun.create("DSXXXM");
		SPronoun p3 = SPronoun.create("DTDFPP");
		System.out.println(p);
		System.out.println(p2);
		System.out.println(p3);
			
	}

}
