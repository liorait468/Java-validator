package oop.ex6.main;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class ConditionLineAnalyzer {

    /* Checks if the current line is a condition line, if yes returns true, otherwise returns false */
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
     * returns the array of variable names in the condition */
    static ArrayList<String> getConditionLineVariables(String line){
        Matcher conditionLineMatcher = PatternClass.ifWhileLineRegex.matcher(line);
        Matcher variableValueMatcher;
        // Checks if the line matches the condition pattern
        if (conditionLineMatcher.matches()) {
            String values = conditionLineMatcher.group(2);
            values = values.replaceAll("\\s+","");
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
    }
}
