package oop.ex6.main;

import java.util.regex.Matcher;

public class MethodLinesAnalyzer {

    /* returns whether current line is a closing scope line */
    static boolean isClosingScopeLine(String line){
        Matcher closingScopeMatcher = PatternClass.closingScope.matcher(line);
        if (closingScopeMatcher.matches()){
            return true;
        }
        return false;
    }

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

    /* Checks if the given line is an empty line or a line starting with a comment,
    if yes, returns true, otherwise, returns false */
    static boolean isLineEmptyOrComment(String line) {
        Matcher emptyMatcher   = PatternClass.pattEmptyLine.matcher(line);
        Matcher commentMatcher = PatternClass.pattCommentLine.matcher(line);

        // Checks if the line matches an emptyline or commentline or both,
        // if so, returns true
        if ((emptyMatcher.matches()) || (commentMatcher.matches())) {
            return true;
        }
        return false;
    }
}
