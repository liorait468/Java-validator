package oop.ex6.main;

import java.util.regex.*;

/* This class saves the patterns for the line regex */
class PatternClass {

    static Pattern pattEmptyLine = Pattern.compile(Regex.LineRegex.emptyLine);
    static Pattern pattCommentLine = Pattern.compile(Regex.LineRegex.commentLine);
    //static Pattern varDeclarationNoInit = Pattern.compile(Regex.VariableAndMethod.varDeclarationNoInit);
    static Pattern methodDeclaration = Pattern.compile(Regex.VariableAndMethod.methodDeclaration);
    static Pattern paramOfFunction = Pattern.compile(Regex.VariableAndMethod.paramOfFunction);
    static Pattern finalType = Pattern.compile(Regex.VariableAndMethod.finalType);
    static Pattern finalVarInitialization = Pattern.compile(Regex.VariableAndMethod.finalVarInitialization);
    static Pattern multipleVarDecOrInit = Pattern.compile(Regex.VariableAndMethod.multipleVarDecOrInit);
  //  static Pattern singleVarDeclarationOrInit = Pattern.compile(Regex.VariableAndMethod.singleVarDeclarationOrInit);
    static Pattern variableAssignmentRegex = Pattern.compile(Regex.VariableAndMethod.variableAssignmentRegex);
    static Pattern intAssignmentRegex = Pattern.compile(Regex.VariableAndMethod.intAssignmentRegex);
    static Pattern doubleAssignmentRegex = Pattern.compile(Regex.VariableAndMethod.doubleAssignmentRegex);
    static Pattern stringAssignmentRegex = Pattern.compile(Regex.VariableAndMethod.stringAssignmentRegex);
    static Pattern charAssignmentRegex = Pattern.compile(Regex.VariableAndMethod.charAssignmentRegex);
    static Pattern booleanAssignmentRegex = Pattern.compile(Regex.VariableAndMethod.booleanAssignmentRegex);
    static Pattern ifWhileLineRegex = Pattern.compile(Regex.VariableAndMethod.ifWhileLine);
    static Pattern closingScope = Pattern.compile(Regex.VariableAndMethod.closingScope);
    static Pattern returnLine = Pattern.compile(Regex.VariableAndMethod.returnLine);
    static Pattern assignemntLineWithEnding = Pattern.compile(Regex.VariableAndMethod.assignemntLineWithEnding);
    static Pattern assignemntVarLine = Pattern.compile(Regex.VariableAndMethod.assignmentVarLine);
    static Pattern callToFunctionWithParams = Pattern.compile(Regex.VariableAndMethod.callToFunctionWithParams);
}


