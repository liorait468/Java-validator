package oop.ex6.main;

import java.util.ArrayList;
import java.util.regex.Matcher;
/* This class is used to analyze different kinds of lines.
 * It gets a line  */
class LineAnalyzeOld {

    static Matcher methodDeclarationMatcher;
    static Matcher conditionLineMatcher;
    static final int MATCHED_TYPE = 1;  //TODO implement static magic number for typeMatch
    static final int UNKNOWN_TYPE = 0;  //TODO implement static magic number for typeMatch
    static final int BAD_TYPE     = -1; //TODO implement static magic number for typeMatch


    /* Checks if the current line is a condition line, if yes returns true, otherwise returns false */ //pass
    static boolean isConditionLine(String line){
        Matcher conditionLineMatcher = PatternClass.ifWhileLineRegex.matcher(line);
        // Checks if the given line matches the regex
        if (conditionLineMatcher.matches()){
            return true;
        }
        return false;
    }

    /* Checks the condition - for each value in the condition that is a variable, inserts it in
     * an array of variables names, and ignores each value that is not a variable name.
     * returns the array of variable names in the condition*/
    static ArrayList<String> getConditionLineVariables(String line){
        Matcher conditionLineMatcher = PatternClass.ifWhileLineRegex.matcher(line);
        Matcher variableValueMatcher;
        // Checks if the line matches the condition pattern
        if (conditionLineMatcher.matches()) {
            String values = conditionLineMatcher.group(2);
            String[] valuesArray = values.split(" *(&&|\\|\\|) *");
            ArrayList<String> variableNames = new ArrayList<String>();
            int i = 0;
            // for each value in the condition, checks if it matches the variable name regex.
            // if yes, adds it to the array of variable names, if not, continues
            for (String s: valuesArray) {
                variableValueMatcher = PatternClass.variableAssignmentRegex.matcher(s);
                // Checks if the value in the condition is a variable name, if yes, adds it to the array
                if (variableValueMatcher.matches()){
                    if (!(s.equals("true") || (s.equals("false")))) {
                        variableNames.add(i, s);
                        i++;
                    }
                }
                else{
                    continue;
                }
            }
            return variableNames;
        }
        return null; // Shouldn't reach this line because when we call that method we already have checked that the line is a condition line
    } //pass

    /* returns whether current line is a closing scope line */
    static boolean isClosingScopeLine(String line){
        Matcher closingScopeMatcher = PatternClass.closingScope.matcher(line);
        if (closingScopeMatcher.matches()){
            return true;
        }
        return false;
    }

    /* returns true if the given line is variable declaration line */
    static boolean isVarDeclarationLine(String line){
        if (line != null) {
            // Checks if the variable is final, (only one), in this case, the variable must be initialized with a value
            if (isVariableFinal(line)) {
                Matcher finalVarDeclarationMatcher = PatternClass.finalVarInitialization.matcher(line);
                // If the line doesn't match the pattern returns false
                if (!finalVarDeclarationMatcher.matches()){
                    return false;
                }
                else{
                    //String strType = finalVarDeclarationMatcher.group(2); //TODO MAGIC TODO SOMEWHERE ELSE
                    //Type type = castStringToType(strType);
                    //String assignmentValue = finalVarDeclarationMatcher.group(4); // TODO MAGIC
                    //if (checkAssignmentType(type, assignmentValue)){
                    //    return true;
                    //}
                    return true;
                }
            }
            // If there is no final, means there can be one or more variables declaration in the line, will check each case
            // if matches one of the cases and the assignment types are correct, returns true
            else{
                //Matcher multipleVarDecMatcher      = PatternClass.multipleVarDecOrInit.matcher(line);
                //Matcher singleVarDecOrInitMatcher  = PatternClass.singleVarDeclarationOrInit.matcher(line);
                //if (multipleVarDecMatcher.matches() || singleVarDecOrInitMatcher.matches()){
                //    return true;
               // }
                Matcher varDecOrInitMatcher = PatternClass.multipleVarDecOrInit.matcher(line);
                if (varDecOrInitMatcher.matches()){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;
    } //pass

    /* Checks if the current line is var assignment line */
    static boolean isVarAssignmentLine(String line) {
        Matcher singleVarAssignmentMatcher = PatternClass.assignemntLineWithEnding.matcher(line);
        if (singleVarAssignmentMatcher.matches()){
            return true;
        }
        else{
            return false;
        }
    } //pass

    /* returns whether the word final appears in the declaration line */
    static boolean isVariableFinal(String line){
        Matcher finalVarMatcher = PatternClass.finalType.matcher(line);
        if (finalVarMatcher.lookingAt()){
            return true;
        }
        return false;
    } //pass

    /* checks if the string in 'line' is a method declaration line */
   static boolean isFunctionDeclarationLine(String line){
        methodDeclarationMatcher = PatternClass.methodDeclaration.matcher(line);
        // Checks if the string line matches the regex
        if (methodDeclarationMatcher.matches()){
            return true;
        }
        return false;
    } //TODO pass

    /* returns the name of the declared method */
    static String getFunctionName(String line){
        String methodName = "";

        methodDeclarationMatcher = PatternClass.methodDeclaration.matcher(line);
        if (methodDeclarationMatcher.matches()) {
            if (methodDeclarationMatcher.group(1) != null) {
                methodName = methodDeclarationMatcher.group(1); //TODO MAGIC number
            }
        }
        return methodName;
    } //TODO pass

    /* returns an array that represents the parameters of the function declared in 'line', if there are no parameters, returns null */
    static Variable[] getFunctionParams(String line){
        // Checks if the current line is function declaration line - TODO to keep?
        if (isFunctionDeclarationLine(line)) {
            Variable[] paramsArray = new Variable[0];
            //methodDeclarationMatcher = PatternClass.methodDeclaration.matcher(line); //TODO no need
            if (methodDeclarationMatcher.group(2) != null) {
                String params = methodDeclarationMatcher.group(2); // Gets the list of the params as String list TODO magic
                //Matcher paramOfFunctionMatcher = PatternClass.paramOfFunction.matcher(params);
                String[] paramsStringArray = params.split(" *, *");
                paramsArray = changeIntoVariableArray(paramsStringArray);
                return paramsArray;
            }
            return paramsArray;
        }
        return null;
    } //TODO pass

    static Variable[] changeIntoVariableArray(String[] strParams){
        boolean isFinal = false;
        String varName = "";
        Type varType;
        Variable[] paramsArray = new Variable[strParams.length]; // New Variable Array with the size - number of parameters
        int i = 0;

        for (String s: strParams){
            Matcher paramsMatcher = PatternClass.paramOfFunction.matcher(s);
            if(paramsMatcher.matches()) {
                if (paramsMatcher.group(1) != null){
                    if (paramsMatcher.group(1).equals("final")){
                        isFinal = true;
                    }
                }
                varName = paramsMatcher.group(3);
                varType = castStringToType(paramsMatcher.group(2)); // casting the String to a Type
                paramsArray[i] = new Variable(varName, varType, isFinal); // TODO the other params are correct?  mistake in false!
            }
            else{
                return null; // or exception
            }
            i++;
            isFinal = false;
        }
        return paramsArray;
    } //TODO pass

    /* returns the enum Type of the string */
    static Type castStringToType(String strToCast) { // TODO - not working
        if (strToCast != null) {
            try {
                for (Type t : Type.values()){
                    if (t.getTypeName().equals(strToCast)){
                        return t;
                    }
                }
            } catch (IllegalArgumentException e) {
                System.err.println("wrong string type");
            }
        }
        return null;
    } // no need to pass

    /* checks if the value of the assignment matches the variable type.
    * If unknown value */
    static int isTypeMatch(Type type, String value){
        Type tempType = getAssignmentValueType(type,value);
        if (tempType != null) {
            // Checks if the returned type matches the given type
            if (type == tempType) {
                return MATCHED_TYPE;
            }
        }
        else{ // if the returned value is null
            Matcher variableMatcher = PatternClass.variableAssignmentRegex.matcher(value);
            if (variableMatcher.matches()){
                return UNKNOWN_TYPE; // if the value is a variable name
            }
        }
        return BAD_TYPE; // invalid value or isn't matched
    }

    /* Returns the assignment value type, if the value isn't valid or is a variable declaration returns null */
    private static Type getAssignmentValueType(Type type, String value){
        Matcher valueMatcher;
        switch (type){
            case INT:{
                valueMatcher = PatternClass.intAssignmentRegex.matcher(value);
                if (valueMatcher.matches()){
                    return Type.INT;
                }break;
            }
            case DOUBLE:{
                valueMatcher = PatternClass.doubleAssignmentRegex.matcher(value);
                if (valueMatcher.matches()){
                    return Type.DOUBLE;
                }break;
            }
            case BOOLEAN:{
                valueMatcher = PatternClass.booleanAssignmentRegex.matcher(value);
                if (valueMatcher.matches()){
                    return Type.BOOLEAN;
                }break;
            }
            case CHAR:{
                valueMatcher = PatternClass.charAssignmentRegex.matcher(value);
                if (valueMatcher.matches()){
                    return Type.CHAR;
                }break;
            }
            case STRING:{
                valueMatcher = PatternClass.stringAssignmentRegex.matcher(value);
                if (valueMatcher.matches()){
                    return Type.STRING;
                }break;
            }
            default:{
                return null;// if the value is variable assignment value, or another invalid value, returns null
            }
        }
        return null;
    }

    static String getAssignedVariableName(String line){
        Matcher singleVarAssignmentMatcher = PatternClass.assignemntLineWithEnding.matcher(line);
        if (singleVarAssignmentMatcher.matches()){
            String varName = singleVarAssignmentMatcher.group(1);
            return varName;
        }
        return null;
    } //pass

    static String getAssignedValue(String line){
        Matcher singleVarAssignmentMatcher = PatternClass.assignemntLineWithEnding.matcher(line);
        if (singleVarAssignmentMatcher.matches()){
            String varValue = singleVarAssignmentMatcher.group(3);
            return varValue;
        }
        return null;
    } //pass

    // Checks if the given line is a return line
    static boolean isReturnLine(String line){
        Matcher returnLineMatcher = PatternClass.returnLine.matcher(line);
        if (returnLineMatcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }

    static ArrayList<Variable> getDeclaredVariables(String line) throws InterruptedException {
        // Checks if there is final in the beginning of the line, which means there is one final variable
        if (isVariableFinal(line)) {
            Matcher finalVarDeclarationMatcher = PatternClass.finalVarInitialization.matcher(line);
            // If the line doesn't match the pattern returns null
            if (finalVarDeclarationMatcher.matches()) {
                String strType = finalVarDeclarationMatcher.group(2);
                Type type = castStringToType(strType);
                String name = finalVarDeclarationMatcher.group(3);
                String value = finalVarDeclarationMatcher.group(4);
                Variable var = new Variable(name, type, true, value);
                ArrayList<Variable> vars = new ArrayList<Variable>();
                vars.add(var);
                return vars;
            } else {
                return null;// no var in line, shouldn't reach this code
            }
        } else { // there are some variable in the line, which are not final
            Matcher multipleVarMatcher = PatternClass.multipleVarDecOrInit.matcher(line);
            if (multipleVarMatcher.matches()) {
                ArrayList<Variable> vars = new ArrayList<Variable>();
                String strType = multipleVarMatcher.group(1);
                Type type = castStringToType(strType);

                String variablesGroup = multipleVarMatcher.group(2);
                String[] varsAssign = variablesGroup.split(" *, *");
                vars = getArray(type, varsAssign);
                return vars;
            }
        }
        return null;
    } //pass

    /* private function that receives an array of assignment var lines,
    * creates a Variable instance for each of the lines and returns an ArrayList
    * of the variables*/
    private static ArrayList<Variable> getArray(Type type, String[] varsAssign) throws InterruptedException {
        ArrayList<Variable> vars = new ArrayList<Variable>();
        // this loop goes on every line in varAssign array, checks if the line is an assignment line,
        // and creates a variable for each valid line
        for (String s : varsAssign) {
            Matcher match = PatternClass.assignemntVarLine.matcher(s);
            // Checks if the current line matches the pattern
            if (match.matches()) {
                String name = match.group(1); // gets the name of the line
                String value = match.group(3);// gets the value from thr line
                // Checks if there is assignment value
                if (value != null) {
                    Variable var = new Variable(name, type, false, value);
                    vars.add(var);
                } else {
                    Variable var = new Variable(name, type, false);
                    vars.add(var);
                }
            }
        }
        return vars;
    }

    /* returns an array of the values in the calling to function line
    * some of them may be variables, and some strings or constants */
    static String[] getFunctionCallParams(String line){
        Matcher functionCallMatcher = PatternClass.callToFunctionWithParams.matcher(line);
        // Checks if the line is a function calling line - with or without params
        if (functionCallMatcher.matches()) {
            String strGroup = functionCallMatcher.group(2);
            String[] params = strGroup.split(" *, *");
            return params;
        }
        return null;
    } //TODO pass

    /* returns the name of the function in calling to function line*/
    static String getFunctionCallName(String line){
        String funcName = "";
        Matcher functionCallMatcher = PatternClass.callToFunctionWithParams.matcher(line);
        // Checks if the line is a function calling line - with or without params
        if (functionCallMatcher.matches()) {
            funcName = functionCallMatcher.group(1);
        }
        return funcName;
    } //TODO pass

    /* returns whether the given line is a function calling line */
    static boolean isFunctionCallLine(String line){
        Matcher functionCallMatcher = PatternClass.callToFunctionWithParams.matcher(line);
        // Checks if the line is a function calling line - with or without params
        if (functionCallMatcher.matches()){
            return true;
        }
        return false;
    } //TODO pass
}
