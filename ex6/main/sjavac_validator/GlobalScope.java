package oop.ex6.main.sjavac_validator;

import oop.ex6.main.Variable;

import java.util.HashMap;

/**
 * This class is used to store a Global Scope object that can check validation
 * and store it's name , Variables and Functions.
 */
public class GlobalScope extends Scope {

	//--------------// Data members //-------------------------//
	/* HashMap that saves all the variables by name for fast searching*/
	private HashMap<String, Variable> declaredVariables;

	/* HashMap that saves all the functions by name for fast searching*/
	private HashMap<String, FunctionScope> declaredFunctions;

	/**
	 * A class representing the Global Scope of the code
	 */
	public GlobalScope() {
		declaredFunctions = new HashMap<>();
		declaredVariables = new HashMap<>();
	}

	//--------------// Getters //-------------------------//

	/**
	 * get a variable declared in the global Scope by name, return null if there is no Variable declared
	 * with the given name in the Scope
	 * @param varName name of the Variable to search
	 * @return Variable, null if not found.
	 */
	public Variable getVariable(String varName) {
		return declaredVariables.get(varName); //returns null if not found
	}

	/**
	 * Check if a variable is already declared in the global scope.
	 * @param varName Variable name to search.
	 * @return true if is declared.
	 */
	public boolean isAlreadyDeclared(String varName) {

		if (declaredVariables.get(varName) != null) {
			return true;
		}
		return false;
	}

	/* get a function declared in the global scope by name, return null if not fount*/
	FunctionScope getFunction(String funcName) {
		return declaredFunctions.get(funcName);//returns null if not found
	}

	/*Get an HashMap<function name,FunctionScope> containing all the functions declared in the Global Scope*/
	HashMap<String,FunctionScope> getAllFunctions() {
		return this.declaredFunctions;
	}

	/*Get an HashMap<variable name,Variable> containing all the Variables declared in the Global Scope*/
	HashMap<String, Variable> getAllVariables() {
		return this.declaredVariables;
	}

	//--------------// Setters //-------------------------//

	/**
	 * declare a new Variable in the global scope.
	 * @param variable - the Variable to declare
	 * @throws InterruptedException
	 */
	public void declareNewVar(Variable variable) throws InterruptedException { //TODO כפל קוד?
		String varName = variable.getVarName();
		if (this.getVariable(varName) == null && varName.length() >= 1) {//TODO check length somewhere else
			this.declaredVariables.put(variable.getVarName(), variable);
		} else {
			OfekTester.redTestError("ERROR: Bad Variable declaration (GlobalScope) ", OfekTester.isTest);
			//TODO Throw exception bad var declaration in line...;
		}
	}

	/**
	 * declare a new function in the global scope.
	 * @param function functionScope to declare
	 * @throws InterruptedException
	 */
	public void declareNewFunction(FunctionScope function) throws InterruptedException {
		String funcName = function.getFuncName();
		if (this.getFunction(funcName) == null && funcName.length() >= 1) {//TODO check length somewhere else
			this.declaredFunctions.put(function.getFuncName(), function);
		} else {
			OfekTester.redTestError("ERROR: Bad Variable declaration (GlobalScope)", OfekTester.isTest);
			//TODO Throw exception bad func declaration in line...;
		}
	}
}
