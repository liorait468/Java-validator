package oop.ex6.main.sjavac_validator;

import oop.ex6.main.LineAnalyze;

import java.util.*;

import oop.ex6.main.Variable;
import oop.ex6.main.Type;

/**
 * A class used to validate if a sjavac code file is legal or not
 */
public class SjavacValidator {

	/* validate if a sjavac code file is legal or not */
	static void validateSjavaCode(ArrayList<String> fileArray) throws InterruptedException {

		GlobalScope globalScope = new GlobalScope();

		// =========================// Global scope check //=========================//
		int scopeDepth = 0;
		FunctionScope currentFunction = null;

		//todo~~~~~~~~~~~~~~~~~~~~~~~ Testing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
		OfekTester.greenTestPrint("\nChecking Global Scope", OfekTester.isTest);
		//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		int lineNum = 0;
		for (String line : fileArray) {
			lineNum++;

			//todo~~~~~~~~~~~~~~~~~~~~~~~ Testing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			OfekTester.testPrint("\n<G> Checking Global line " + lineNum + ": " + line, OfekTester.isTest);
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			if (scopeDepth == 0) {

				// --- Var Declare Line --- //
				if (LineAnalyze.isVarDeclarationLine(line)) {
					VarDeclarationCheck(globalScope, line, lineNum);

					// --- Var Assign Line --- //
				} else if (LineAnalyze.isVarAssignmentLine(line)) {
					VarAssignmentCheck(globalScope, line, lineNum);

					// --- Function Declare Line --- //
				} else if (LineAnalyze.isFunctionDeclarationLine(line)) {
					functionDeclareCheck(globalScope, line, lineNum);
					currentFunction = globalScope.getFunction(LineAnalyze.getFunctionName(line));
					scopeDepth++;
					// --- Bad Line --- //
				} else {
					OfekTester.redTestError("ERROR: Syntax error, line: " + lineNum, OfekTester.isTest);
					//TODO throw exception here, delete print
				}

			} else { // Check inner scopes (scope depth > 0)

				// --- Bad Function Declare Line --- //
				if (LineAnalyze.isFunctionDeclarationLine(line)) {
					OfekTester.redTestError("ERROR: Function declaration not in global scope, line: " +
							lineNum, OfekTester.isTest);
					//TODO throw exception here, delete print
				}
				// --- Skip Condition Line --- //
				else if (LineAnalyze.isConditionLine(line)) {
					scopeDepth++;
				}
				// --- Closing Scope Line --- //
				else if (LineAnalyze.isClosingScopeLine(line)) {
					scopeDepth--;
					if (scopeDepth == 0) {
						currentFunction.setEndLine(lineNum);
						if (!currentFunction.checkReturn()) {
							OfekTester.redTestError("ERROR: Missing return statement for function '" +
									currentFunction.getFuncName() + "', line: " + lineNum, OfekTester.isTest);
							//TODO throw exception here, delete print
						}
					}
					// --- Return Line --- //
				} else if (LineAnalyze.isReturnLine(line)) {
					currentFunction.setReturnLine(lineNum);
				}

				//TODO delete this check?
				// --- Bad Line --- //
				else if (!LineAnalyze.isVarDeclarationLine(line) && !LineAnalyze.isVarAssignmentLine(line)
						&& !LineAnalyze.isFunctionCallLine(line)) {
					OfekTester.redTestError("ERROR: Syntax error in line: " + lineNum, OfekTester.isTest);
					//TODO throw exception here, delete print
				}
			}
		}
		//End of file, check if brackets are balanced
		if (scopeDepth != 0) {
			OfekTester.redTestError("ERROR: Unbalanced number of brackets in file", OfekTester.isTest);
			//TODO throw exception here, delete print
		}

		// =========================// Functions and Conditions scopes check //=========================-//
		HashMap<String, FunctionScope> allFunctions = globalScope.getAllFunctions();

		for (FunctionScope function : allFunctions.values()) {

			//todo~~~~~~~~~~~~~~~~~~~~~~~ Testing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			OfekTester.greenTestPrint("\nChecking Function Scope: " + function.getFuncName() + " Lines:" + String.valueOf
					(function.getStartLine()) + "-" + String.valueOf(function.getEndLine()), OfekTester.isTest);
			for (Variable v : function.getAllLocalVars()) {
				OfekTester.greenTestPrint("LocalVar: " + v.getVarType() + " " + v.getVarName(), OfekTester.isTest);
			}
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			for (int j = function.getStartLine(); j < function.getEndLine(); j++) {

				String line = fileArray.get(j);
				int lineNumber = j + 1;

				//todo~~~~~~~~~~~~~~~~~~~~~~~ Testing ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				OfekTester.testPrint("\n<f> Checking Function line " + lineNumber + ": " + line, OfekTester.isTest);
				//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

				// --- Condition Line --- //
				if (LineAnalyze.isConditionLine(line)) {
					function.openInnerScope();
					conditionCheck(function, line, lineNumber);
				}
				// --- Function Call Line --- //
				else if (LineAnalyze.isFunctionCallLine(line)) {
					functionCallCheck(globalScope, function, line, lineNumber);
				}
				// --- Var Assign Line --- //
				else if (LineAnalyze.isVarAssignmentLine(line)) {
					VarAssignmentCheck(function, line, lineNumber);
				}

				// --- Var Declare Line --- //
				else if (LineAnalyze.isVarDeclarationLine(line)) {
					VarDeclarationCheck(function, line, lineNumber);

					// --- Closing Scope Bracket Line --- //
				} else if (LineAnalyze.isClosingScopeLine(line)) {
					function.closeInnerScope();
				}

				// --- Bad Line --- //
				else if (!LineAnalyze.isReturnLine(line)) {
					OfekTester.redTestError("ERROR: Syntax error, line: " + lineNumber, OfekTester.isTest);
					//TODO throw exception here, delete print
				}
			}
		}
	}


	//=================================// METHODS //===========================================//

	// -----  Variable Assign Check ----- //
	private static void VarAssignmentCheck(Scope scope, String line, int lineNumber) throws
			InterruptedException {
		String varName = LineAnalyze.getAssignedVariableName(line);
		String varValue = LineAnalyze.getAssignedValue(line);

		Variable var = scope.getVariable(varName);

		if (var == null)

		{
			OfekTester.redTestError("ERROR: Var is not declared, line: " + lineNumber, OfekTester.isTest);
			//TODO throw exception here, delete print
		} else

		{
			if (var.isFinal()) {
				OfekTester.redTestError("ERROR: Assigning final var, line: " + lineNumber, OfekTester.isTest);
				//TODO throw exception here, delete print
			} else {
				var.setVarValue(varValue);
				if (!var.isAssigned()) {
					Variable assignedVar = scope.getVariable(varValue);

					if (assignedVar == null) {
						OfekTester.redTestError("ERROR: Var is not declared, line: " + lineNumber,
								OfekTester.isTest);
						//TODO throw exception here, delete print
					} else if (var.getVarType() != assignedVar.getVarType()) {
						OfekTester.redTestError("ERROR: Assigned var types do not match, line: " + lineNumber,
								OfekTester.isTest);
						//TODO throw exception here, delete print
					} else {
						var.setVarValue(assignedVar.getVarValue());

					}
				}
			}
		}
	}

	// -----  Variable Declare Check ----- //
	private static void VarDeclarationCheck(Scope scope, String line, int lineNumber) throws
			InterruptedException {
		ArrayList<Variable> declaredVars = LineAnalyze.getDeclaredVariables(line);
		if (declaredVars != null) {
			for (Variable var : declaredVars) {
				String varName = var.getVarName();

				if (scope.isAlreadyDeclared(varName)) {
					OfekTester.redTestError("ERROR: var is already declared in scope, line: " +
							lineNumber, OfekTester.isTest);
					//TODO throw exception here, delete print
				} else {
					String varValue = var.getVarValue();
					//check if var is assigned with another variable
					if (varValue != null && !var.isAssigned()) {
						Variable assignedVar = scope.getVariable(varValue);
						if (assignedVar == null) {
							OfekTester.redTestError("ERROR: Var is not declared, line: " + lineNumber, OfekTester.isTest);
							//TODO throw exception here, delete print
						} else if (assignedVar.getVarValue() == null) {
							OfekTester.redTestError("ERROR: Var is not assigned, line: " + lineNumber, OfekTester.isTest);
							//TODO throw exception here, delete print
						} else if (!typeCheck (var.getVarType(),assignedVar.getVarType()) ) {
							OfekTester.redTestError("ERROR: Assigned var types do not match, line: " + lineNumber,
									OfekTester.isTest);
							//TODO throw exception here, delete print
						} else {
							var.setVarValue(assignedVar.getVarValue());
						}
					}
					//declare new var in this scope
					scope.declareNewVar(var);
				}
			}
		} else {
			OfekTester.redTestError("ERROR: Empty Declaration, line: " + lineNumber, OfekTester.isTest);
			//TODO throw exception here, delete print
		}
	}

	// -----  Function Call Check ----- //
	private static void functionCallCheck(GlobalScope globalScope, FunctionScope function, String line, int
			lineNumber) throws
			InterruptedException {
		String functionName = LineAnalyze.getFunctionCallName(line);
		String[] calledParams = LineAnalyze.getFunctionCallParams(line);
		FunctionScope calledFunction = globalScope.getFunction(functionName);
		if (calledFunction == null) {
			OfekTester.redTestError("ERROR: Called function is not declared, line: " + lineNumber, OfekTester.isTest);
			//TODO throw exception here, delete print
		} else {
			Variable[] functionParams = calledFunction.getFunctionParameters();
			if (functionParams.length == calledParams.length) {
				for (int i = 0; i < functionParams.length; i++) {
					String CalledParam = calledParams[i];
					Variable functionParam = functionParams[i];
					int typeMatch = LineAnalyze.isTypeMatch(functionParam.getVarType(),
							CalledParam);
					if (typeMatch == LineAnalyze.UNKNOWN_TYPE) {
						Variable assignedParam = function.getVariable(CalledParam);
						if (assignedParam == null) {
							OfekTester.redTestError("ERROR: Called param in function is not " +
									"declared, line: " + lineNumber, OfekTester.isTest);
							//TODO throw exception here, delete print
						} else {
							if (functionParam.getVarType() != assignedParam.getVarType()) {
								OfekTester.redTestError("ERROR: Param Type does't match, line " +
										lineNumber, OfekTester.isTest);
								//TODO throw exception here, delete print
							} else {
								if (!assignedParam.isAssigned()) {
									OfekTester.redTestError("ERROR: Function Param is not assigned, line"
											+ " " + lineNumber, OfekTester.isTest);
									//TODO throw exception here, delete print
								}
							}
						}
					} else if (typeMatch == LineAnalyze.BAD_TYPE) {
						OfekTester.redTestError("ERROR: Param Type does't match, line " +
								lineNumber, OfekTester.isTest);
						//TODO throw exception here, delete print
					}
				}
			} else {
				OfekTester.redTestError("ERROR: The number of variables in function call does not match "
						+ "declaration, line " + lineNumber, OfekTester.isTest);
				//TODO throw exception here, delete print
			}
		}
	}

	// ----- Conditions Check ----- //
	private static void conditionCheck(FunctionScope function, String line, int lineNumber) throws
			InterruptedException {
		ArrayList<String> params = LineAnalyze.getConditionLineVariables(line);
		if (params == null) {
			OfekTester.redTestError("ERROR: Condition is empty, line: " + lineNumber, OfekTester.isTest);
			//TODO throw exception here, delete print
		} else {
			for (String param : params) {
				int typeMatch = LineAnalyze.isTypeMatch(Type.BOOLEAN, param);
				if (typeMatch == LineAnalyze.UNKNOWN_TYPE) {
					Variable var = function.getVariable(param);
					if (var == null) {
						OfekTester.redTestError("ERROR: Var is not declared, line: " + lineNumber, OfekTester.isTest);
						//TODO throw exception here, delete print
					} else if (!var.isAssigned()) {
						OfekTester.redTestError("ERROR: var is not assigned, line: " + lineNumber, OfekTester.isTest);
						//TODO throw exception here, delete print
					} else if (LineAnalyze.isTypeMatch(Type.BOOLEAN, var.getVarValue()) !=
							LineAnalyze.MATCHED_TYPE) {
						OfekTester.redTestError("ERROR: Bad var type in condition,  line: " + lineNumber,
								OfekTester.isTest);
						//TODO throw exception here, delete print
					}
				} else if (typeMatch == LineAnalyze.BAD_TYPE) {
					OfekTester.redTestError("ERROR: Bad var type in condition,  line: " + lineNumber,
							OfekTester.isTest);
					//TODO throw exception here, delete print
				}
			}
		}
	}

	// -----  Function Declare Check ----- //
	private static void functionDeclareCheck(GlobalScope globalScope, String line, int lineNum) throws
			InterruptedException {
		Variable[] params = LineAnalyze.getFunctionParams(line);
		String funcName = LineAnalyze.getFunctionName(line);
		FunctionScope function = new FunctionScope(globalScope, funcName, params, lineNum);
		globalScope.declareNewFunction(function);
	}

	private static boolean typeCheck(Type varType, Type assignedType) {
		if(varType == Type.DOUBLE && assignedType == Type.INT){
			return true;
		}
		if (varType == Type.BOOLEAN && (assignedType == Type.INT || assignedType == Type.DOUBLE)) {
			return true;
		}
		return varType == assignedType;
	}
}
