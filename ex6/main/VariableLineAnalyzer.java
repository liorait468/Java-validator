package oop.ex6.main;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class VariableLineAnalyzer {

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
    }

    /* Checks if the current line is var assignment line */
    static boolean isVarAssignmentLine(String line) {
        Matcher singleVarAssignmentMatcher = PatternClass.assignemntLineWithEnding.matcher(line);
        if (singleVarAssignmentMatcher.matches()){
            return true;
        }
        else{
            return false;
        }
    }

    /* returns whether the word final appears in the declaration line */
    static boolean isVariableFinal(String line){
        Matcher finalVarMatcher = PatternClass.finalType.matcher(line);
        if (finalVarMatcher.lookingAt()){
            return true;
        }
        return false;
    }

    static String getAssignedVariableName(String line){
        Matcher singleVarAssignmentMatcher = PatternClass.assignemntLineWithEnding.matcher(line);
        if (singleVarAssignmentMatcher.matches()){
            String varName = singleVarAssignmentMatcher.group(1);
            return varName;
        }
        return null;
    }

    static String getAssignedValue(String line){
        Matcher singleVarAssignmentMatcher = PatternClass.assignemntLineWithEnding.matcher(line);
        if (singleVarAssignmentMatcher.matches()){
            String varValue = singleVarAssignmentMatcher.group(3);
            return varValue;
        }
        return null;
    }

    static ArrayList<Variable> getDeclaredVariables(String line) throws InterruptedException {
        // Checks if there is final in the beginning of the line, which means there is one final variable
        if (isVariableFinal(line)) {
            Matcher finalVarDeclarationMatcher = PatternClass.finalVarInitialization.matcher(line);
            // If the line doesn't match the pattern returns null
            if (finalVarDeclarationMatcher.matches()) {
                String strType = finalVarDeclarationMatcher.group(2);
                Type type = LineAnalyzeOld.castStringToType(strType);
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
                Type type = LineAnalyzeOld.castStringToType(strType);

                String variablesGroup = multipleVarMatcher.group(2);
                String[] varsAssign = variablesGroup.split(" *, *");
                vars = getArray(type, varsAssign);
                return vars;
            }
        }
        return null;
    }

    private static ArrayList<Variable> getArray(Type type, String[] varsAssign) throws InterruptedException {
        ArrayList<Variable> vars = new ArrayList<Variable>();
        for (String s : varsAssign) {
            Matcher match = PatternClass.assignemntVarLine.matcher(s);
            if (match.matches()) {
                String name = match.group(1);
                String value = match.group(3);
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
}
