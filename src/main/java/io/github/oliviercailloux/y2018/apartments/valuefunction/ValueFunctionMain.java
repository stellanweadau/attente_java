package io.github.oliviercailloux.y2018.apartments.valuefunction;

public class ValueFunctionMain {

	public static void main(String[] args) {
		
		LinearValueFunction f = new LinearValueFunction(10,50);
		System.out.println(f.getSubjectiveValue(20.0));
		
		Discrete3ValueFunction f1 = new Discrete3ValueFunction("Bad","Medium","Good");
		System.out.println(f1.getSubjectiveValue("Medium"));
		
		ValueFunction f2 = new ValueFunction(f1,f,"Medium",20.0);
		System.out.println(f2.getValue());

	}

}
