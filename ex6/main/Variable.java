package oop.ex6.main;

import oop.ex6.main.sjavac_validator.OfekTester;

/**
 *
 */
public class Variable {

	/*  data members */
	private String varName;
	private Type varType;
	private boolean isFinal;
	private boolean isAssigned;
	private String varValue;

	public Variable(String name, Type type, boolean isVarFinal) {
		varName = name;
		this.varType = type;
		this.isFinal = isVarFinal;
		this.varValue = null;
		this.isAssigned = false;
	}

	public Variable(String name, Type type, boolean isVarFinal, String value) throws InterruptedException {
		varName = name;
		this.varType = type;
		this.isFinal = isVarFinal;
		if (value != null) {
			this.setVarValue(value);
		}
	}

	/* Getters */

	public String getVarName() {
		return varName;
	}

	public String getVarValue() {
		return varValue;
	}

	public Type getVarType() {
		return varType;
	}

	public boolean isFinal() {
		return isFinal;
	}

	public boolean isAssigned() {
		return isAssigned;
	}

	/* Setters */
	public void setVarValue(String value) throws InterruptedException{
		int typeMatch = LineAnalyze.isTypeMatch(this.varType, value);
		if (typeMatch == LineAnalyze.MATCHED_TYPE) {
			this.isAssigned = true;
			this.varValue = value;
		} else if (typeMatch == LineAnalyze.UNKNOWN_TYPE) {
			this.isAssigned = false;
			this.varValue = value;
		} else if (typeMatch == LineAnalyze.BAD_TYPE) {
			OfekTester.redTestError("Bad assignment (Variable)",OfekTester.isTest); //TODO EXCEPTION
		}
	}

}