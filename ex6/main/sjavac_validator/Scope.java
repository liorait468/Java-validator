package oop.ex6.main.sjavac_validator;

import oop.ex6.main.Variable;

abstract class Scope {
	/**
	 * Search a variable by name in all the upper scopes of the current open inner scope in this Scope.
	 * @param varName name of the Variable to search
	 * @return return the Variable if found, else null.
	 */
	abstract public Variable getVariable(String varName);

	/**
	 * Check if a variable is already declared in the current open scope.
	 * @param varName Variable name to search.
	 * @return true if is declared.
	 */
	abstract public boolean isAlreadyDeclared(String varName);

	/**
	 * Declare a new Variable in the current open scope.
	 * @param variable - the Variable to declare
	 * @throws InterruptedException //TODO change exception
	 */
	abstract public void declareNewVar(Variable variable) throws InterruptedException;
}
