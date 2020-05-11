package oop.ex6.main;
import java.util.ArrayList;

public class LineAnalyze {
	public static final int MATCHED_TYPE = 1;
	public static final int UNKNOWN_TYPE = 0;
	public static final int BAD_TYPE     = -1;

	/* Conditions */

	/**
	 * Checks if the current line is a condition line, if yes returns true, otherwise returns false
	 * @param line - the line to check
	 * @return true if the current line is a condition line, false otherwise
	 */
	public static boolean isConditionLine(String line) {
		return ConditionLineAnalyzer.isConditionLine(line);
	}

	/**
	 * For each value in the condition line that is a variable, inserts it in
	 * an array of variables names, and ignores each value that is not a variable name.
	 * returns the array of variable names in the condition
	 * @param line - the condition line to analyze
	 * @return an arraylist of the variable names
	 */
	public static ArrayList<String> getConditionLineVariables(String line) {
		return ConditionLineAnalyzer.getConditionLineVariables(line);
	}

	/* Variables */

	/**
	 * Checks if the current line is var assignment line
	 * @param line - the line to check
	 * @return true if the line is an assignment variable line, false otherwise
	 */
	public static boolean isVarAssignmentLine(String line) {
		return VariableLineAnalyzer.isVarAssignmentLine(line);
	}

	public static String getAssignedVariableName(String line) {
		return VariableLineAnalyzer.getAssignedVariableName(line);
	}

	public static boolean isVarDeclarationLine(String line) {
		return VariableLineAnalyzer.isVarDeclarationLine(line);
	}

	public static String getAssignedValue(String line) {
		return VariableLineAnalyzer.getAssignedValue(line);
	}

	public static ArrayList<Variable> getDeclaredVariables(String line) throws InterruptedException {
		return VariableLineAnalyzer.getDeclaredVariables(line);
	}

	/* method lines */
	public static boolean isClosingScopeLine(String line) {
		return MethodLinesAnalyzer.isClosingScopeLine(line);
	}

	public static boolean isReturnLine(String line) {
		return MethodLinesAnalyzer.isReturnLine(line);
	}

	/* Function*/
	public static boolean isFunctionDeclarationLine(String line) {
		return FunctionLineAnalyzer.isFunctionDeclarationLine(line);
	}

	public static String getFunctionName(String line) {
		return FunctionLineAnalyzer.getFunctionName(line);
	}

	public static Variable[] getFunctionParams(String line) {
		return FunctionLineAnalyzer.getFunctionParams(line);
	}

	public static boolean isFunctionCallLine(String line) {
		return FunctionLineAnalyzer.isFunctionCallLine(line);
	}

	public static  String getFunctionCallName(String line){
		return FunctionLineAnalyzer.getFunctionCallName(line);
	}

	public static  String[] getFunctionCallParams(String line){
		return FunctionLineAnalyzer.getFunctionCallParams(line);
	}

	public static int isTypeMatch(Type type, String value) {
		return LineAnalyzeOld.isTypeMatch(type,value);
		//MATCHED_TYPE = 1;
		//UNKNOWN_TYPE = 0;(IS VARIABLE)
		//BAD_TYPE = -1;
	}

	public static boolean isLineEmptyOrComment(String line){
		return MethodLinesAnalyzer.isLineEmptyOrComment(line);
	}

}