package oop.ex6.main.sjavac_validator;
import oop.ex6.main.Variable;
import oop.ex6.main.sjavac_validator.OfekTester;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to store ScopeVariables object that represent all the declared variables in a
 * single function's inner scope.
 */
public class ScopeVariables {

	/* HashMap that saves all the variables in the current scope by name*/
	// won't allow name duplicates
	private HashMap<String, Variable> declaredVariables;

	/**
	 * Constructor for the ScopeVariables class
	 */
	public ScopeVariables() {
		declaredVariables = new HashMap<>();
	}
	/* declare a new scope variable */
	void declareNewVar(Variable variable) throws InterruptedException{
		String varName = variable.getVarName();
		if (!this.isVarDeclared(varName) && varName.length() >= 1) { //TODO check length somewhere else
			this.declaredVariables.put(variable.getVarName(), variable);
		} else {
			OfekTester.redTestError("ERROR: Bad Variable declaration (ScopeVariables) ",OfekTester.isTest);
			//TODO Throw exception bad var declaration in line...;
		}
	}

	/**
	 * check  if a variable is declared in the ScopeVariables object.
	 * @param varName name of the variable to check
	 * @return true if declared, false if not.
	 */
	public boolean isVarDeclared(String varName) {
		return declaredVariables.containsKey(varName);
	}

	/**
	 * search for a variable declared in the ScopeVariables, return null if not found.
	 * @param varName name of the Variable to search.
	 * @return Variable, null if not found.
	 */
	public Variable getVariable(String varName) {
		return declaredVariables.get(varName);//returns null if not found
	}

	/**
	 *get ArrayList of all the Variables declared in the ScopeVariables object.
	 * @return ArrayList of Variables.
	 */
	public ArrayList<Variable> getAllVariables() {
		ArrayList<Variable> variables = new ArrayList<>();
		variables.addAll(declaredVariables.values());
		return variables;
	}
}
