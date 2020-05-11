package oop.ex6.main;

import java.util.regex.Matcher;

public class FunctionLineAnalyzer {

    static Matcher methodDeclarationMatcher;
    /* returns an array of the values in the calling to function line
     * some of them may be variables, and some strings or constants */
    static String[] getFunctionCallParams(String line){
        Matcher functionCallMatcher = PatternClass.callToFunctionWithParams.matcher(line);
        // Checks if the line is a function calling line - with or without params
        if (functionCallMatcher.matches()) {
            String strGroup = functionCallMatcher.group(2);
            if(strGroup == null){
                return new String[0]; //return empty array
            }
            String[] params = strGroup.split(" *, *");
            return params;
        }
        return null;
    }

    /* returns the name of the function in calling to function line*/
    static String getFunctionCallName(String line){
        String funcName = "";
        Matcher functionCallMatcher = PatternClass.callToFunctionWithParams.matcher(line);
        // Checks if the line is a function calling line - with or without params
        if (functionCallMatcher.matches()) {
            funcName = functionCallMatcher.group(1);
        }
        return funcName;
    }

    /* returns whether the given line is a function calling line */
    static boolean isFunctionCallLine(String line){
        Matcher functionCallMatcher = PatternClass.callToFunctionWithParams.matcher(line);
        // Checks if the line is a function calling line - with or without params
        if (functionCallMatcher.matches()){
            return true;
        }
        return false;
    }

    /* checks if the string in 'line' is a method declaration line */
    static boolean isFunctionDeclarationLine(String line){
        methodDeclarationMatcher = PatternClass.methodDeclaration.matcher(line);
        // Checks if the string line matches the regex
        if (methodDeclarationMatcher.matches()){
            return true;
        }
        return false;
    }

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
    }

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
    }

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
                varType = LineAnalyzeOld.castStringToType(paramsMatcher.group(2)); // casting the String to a Type
                paramsArray[i] = new Variable(varName, varType, isFinal); // TODO the other params are correct?  mistake in false!
            }
            else{
                return null; // or exception
            }
            i++;
            isFinal = false;
        }
        return paramsArray;
    }
}
