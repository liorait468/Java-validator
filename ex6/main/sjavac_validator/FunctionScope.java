package oop.ex6.main.sjavac_validator;

import oop.ex6.main.Variable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

/**
 * This class is used to store a function object that can check validation
 * for functions, and will store it's name and parameters, and all its inner scopes.
 */
class FunctionScope extends Scope{

	// -----------------------// constants //----------------------------------------//
	public static final int NO_RETURN_LINE = -1;
	public static final int RETURN_RANGE_FROM_END = 1;

	// -----------------------// Data members //----------------------------------------//
	private int returnLine;
	private String funcName;
	private int startLine; //start line number of the scope
	private int endLine; //end line number of the scope
	private GlobalScope globalScope;

	/* Array that saves all the parameters in the current function */
	private Variable[] parameters;

	/*
	 * LinkedList of all the Local Variables of this function's current open inner scopes, each node
	 * represents an open scope in the function. The first node represents the local vars of function itself.
	 */
	private LinkedList<ScopeVariables> innerScopesVars;

	/**
	 * A class that represents a scope of a function and it's inner scopes in the code.
	 * @param parentScope the Global scope parent of the function
	 * @param name - String, function name
	 * @param params - list of Variable parameters
	 * @param startline - the start line number of the function
	 * @throws InterruptedException
	 */
	public FunctionScope(GlobalScope parentScope, String name, Variable[] params, int startline) throws
			InterruptedException {
		this.innerScopesVars = new LinkedList<>();
		this.innerScopesVars.add(new ScopeVariables()); // function root scope
		this.returnLine = FunctionScope.NO_RETURN_LINE;
		this.startLine = startline;
		this.funcName = name;
		this.globalScope = parentScope;
		this.parameters = new Variable[params.length];
		for (int i = 0; i < params.length; i++) {
			this.parameters[i] = params[i];
			declareNewVar(params[i]);
		}
	}

	//----------------------------// Setters //---------------------------------------//
	/**
	 * Declare a new Variable in the current open scope.
	 * @param variable - the Variable to declare
	 * @throws InterruptedException //TODO change exception
	 */
	public void declareNewVar(Variable variable) throws InterruptedException {
		this.innerScopesVars.getLast().declareNewVar(variable);
	}

	/*set the end line number of the function*/
	void setEndLine(int line) {
		this.endLine = line;
	}

	/*set the last Return line number of the function*/
	void setReturnLine(int line) {
		this.returnLine = line;
	}

	/* Open a new inner scope in this function*/
	void openInnerScope() {
		this.innerScopesVars.add(new ScopeVariables());
	}

	/* Close the most recent opened inner scope in this function*/
	void closeInnerScope() {
		this.innerScopesVars.removeLast();
	}
	//----------------------------// Getters //---------------------------------------//

	/**
	 * Check if a variable is already declared in the current open scope.
	 * @param varName Variable name to search.
	 * @return true if is declared.
	 */
	public boolean isAlreadyDeclared(String varName) {
		if (this.innerScopesVars.getLast().getVariable(varName) != null){
			return true;
		}
		return false;
	}

	/**
	 * Search a variable by name in all the upper scopes of the current open inner scope in this Scope.
	 * @param varName name of the Variable to search
	 * @return return the Variable if found, else null.
	 */
	public Variable getVariable(String varName) {
		Iterator<ScopeVariables> scopesIterator = innerScopesVars.descendingIterator();

		while (scopesIterator.hasNext()) {
			Variable var = scopesIterator.next().getVariable(varName);
			if (var != null) {
				return var;
			}
		}
		return globalScope.getVariable(varName);
	}

	/* get the start line number of the function*/
	int getStartLine() {
		return startLine;
	}
	/* get the end line number of the function */
	int getEndLine() {
		return endLine;
	}

	/* returns true if the function has a valid return line*/
	boolean checkReturn() {
		if ((this.returnLine == NO_RETURN_LINE) || (this.endLine - this.returnLine != RETURN_RANGE_FROM_END)) {
			return false;
		}
		return true;
	}
	/* get the declared parameters of this function */
	Variable[] getFunctionParameters() {
		return parameters;
	}
	/* get all the local variables of this function higher scope (the function itself) */
	ArrayList<Variable> getAllLocalVars() {
		return innerScopesVars.getFirst().getAllVariables();
	}
	/* get the function's name */
	String getFuncName() {
		return this.funcName;
	}


}
