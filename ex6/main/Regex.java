package oop.ex6.main;
import java.util.regex.*;

/* This class saves all the possible regex:
 * Variable regex, method regex and condition regex */
class Regex {

    /* This class saves the regex of lines that needs to be ignored - empty lines and comment lines */
    static class LineRegex {

        /* represents an empty line (containing only tabs, spaces etc.) */
         static String emptyLine = "\\h*";

         /* represents a line starting with '//' and followed by any character */
         static String commentLine = "^\\/\\/.*";
    }

    /* This class saves the regex that are used for var declaration, var assignment, method call, method declaration
    * and conditions */
    static class VariableAndMethod {

        /* Assignment regex */

        static String variableAssignmentRegex = "\\h*([a-z]+\\w*|_+\\w+)";
        static String intAssignmentRegex      = "\\-*\\d+";
        static String doubleAssignmentRegex   = "\\-*\\d+(\\.\\d+)*";
        static String stringAssignmentRegex   = "\\\".*\\\"";
        static String charAssignmentRegex     = "\\'.\\'";
        static String booleanAssignmentRegex  = "true|false|\\-*\\d+(\\.\\d*)*";

        /* Variables regex */

        /* a representation for valid types of variables */
        static String varTypesList = "(int|double|String|char|boolean)";

        /* final type regex*/
        static String finalType = "\\h*(final)\\h*";

        /* a representation for a valid variable's name */
        static String varName = "([a-z]+\\w*|_+\\w+)";

        /* saves the regex for variable declaration */
        static String varDeclarationNoInit = "\\h*" + varTypesList + " *" + varName + ";"; // not final

        static String singleVarDeclarationOrInit = " *(int|double|String|char|boolean) *([a-z]+\\w*|_+\\w+) *(= *\\w*)*;";

        static String assignmentVarLine = "\\h*([a-z]+\\w*|_+\\w+)\\h*(=\\h*([a-z]+\\w*|_+\\w+|\\'.\\'|\\\".+\\\"|(\\-*\\d*(\\.\\d+)*))){0,1}";

        static String assignemntLineWithEnding = "\\h*([a-z]+\\w*|_+\\w+)\\h*=(\\h*([a-z]+\\w*|_+\\w+|\\'.\\'|\\\".+\\\"|(\\-*\\d*(\\.\\d+)*))){0,1}\\h*\\;";

        static String finalVarInitialization = "\\h*(final)+\\h*(int|double|String|char|boolean)\\h*([a-z]+\\w*|_+\\w+)\\h*=\\h*(\\h*[a-z]+\\w*|_+\\w+|\\'.\\'|\\\".+\\\"|(\\-*\\d+(\\.\\d+)*))\\h*;";

        /* final variable must be initialized with some value in declaration */
        static String paramOfFunction = "\\h*(final)*\\h*(int|double|String|char|boolean)\\h*([a-z]+\\w*|_+\\w+)";

        static String multipleVarDecOrInit = "\\h*(int|double|String|char|boolean)\\h*(([a-z]+\\w*|_+\\w+)\\h*(=\\h*([a-z]+\\w*|_+\\w+|\\'.\\'|\\\".+\\\"|(\\-{0,1}\\d*(\\.\\d+)*))){0,1}\\h*((,\\h*([a-z]+\\w*|_+\\w+)(\\h*=\\h*([a-z]+\\w*|_+\\w+|\\'.\\'|\\\".+\\\"|(\\-{0,1}\\d*(\\.\\d+)*))){0,1})*));";
        /* var declaration list - no init*/
        String multipleVarInit = "\\h*(int|double|String|char|boolean)\\h*((([a-z]+\\w*|_+\\w+)\\h*,)\\h*)*([a-z]+\\w*|_+\\w+);";

        static String returnLine ="\\h*return\\h*;\\h*";

        /* list of only var names, separated by commas */
        String listOfVarNames = "((([a-z]+\\w*|_+\\w+) *,) *)*([a-z]+\\w*|_+\\w+)";

        //----------------------------------------------------------------------------

        /* Method regex */

        /* a representation for a valid method's name, (must not start with a digit,_) */
        static String methodName = "([a-z]+\\w*)";

        static String callToFunctionNoParams = "([a-z]+\\w*)\\h*\\(\\);";

        static String callToFunctionWithParams = "\\h*([a-z]+\\w*)\\h*\\(\\h*(([a-z]+\\w*|_+\\w+|\\'.\\'|\\\".+\\\"|(\\-*\\d+(\\.\\d+)*))(\\h*,{1}\\h*(([a-z]+\\w*|_+\\w+)|\\'.\\'|\\\".+\\\"|(\\-*\\d+(\\.\\d+)*)){1})*)*\\h*\\);";

        /* saves the possible types of methods*/
        static String methodType = "void";

        /* list of parameters, excluding paratherss */
        static String listOfParameters = "(((final)* *(int|double|String|char|boolean) *([a-z]+\\w*|_+\\w+),*)* *((final)* *(int|double|String|char|boolean) *([a-z]+\\w*|_+\\w+^,)))";

        //static String methodDeclaration = "\\h*void *([a-z]+\\w*) *\\(((((final)* *" +
        //      "(int|double|String|char|boolean) *([a-z]+\\w*|_+\\w+) *,* *)* * *( *(final)* *" +
        //      "(int|double|String|char|boolean) *([a-z]+\\w*|_+\\w+^ *,))))*\\)\\h*\\{";
        static String methodDeclaration ="\\h*void *([a-z]+\\w*) *\\(((((final)* *(int|double|String|char|boolean) *([a-z]+\\w*|_+\\w+) *,* *)* * *( *(final)* *(int|double|String|char|boolean) *([a-z]+\\w*|_+\\w+^ *\\h*,))))*\\h*\\)\\h*\\{\\h*";

        /* condition and loop regex */

        // regex to check if the line is 'while' or 'if' line
        String ifWhileInBracketsValue = "([a-z]+\\w*|_+\\w+|\\-*\\d+(\\.\\d*)*) *( *(&&|\\|\\|) *([a-z]+\\w*|_+\\w+|\\-*\\d(\\.\\d*)*))*";

        //static String ifWhileLine = "\\h*(if|while)\\h*\\(\\h*(([a-z]+\\w*|_+\\w+|\\-*\\d+(\\.\\d+){0,1})\\h*((\\|\\||&&)\\h*([a-z]+\\w*|_+\\w+|\\-*\\d+(\\.\\d+){0,1})\\h*)*)\\h*\\)\\h*\\{";
        static String ifWhileLine = "\\h*(if|while)\\h*\\(\\h*(([a-z]+\\w*\\h*|_+\\w+\\h*|\\-*\\d+(\\.\\d+){0,1})\\h*((\\|\\||&&)\\h*([a-z]+\\w*|_+\\w+|\\-*\\d+(\\.\\d+){0,1})\\h*)*)\\h*\\)\\h*\\{";
        static String closingScope = "\\h*}\\h*";
        static String conditionCorrectBrackets = "([a-z]+\\w*|_+\\w+|\\-*\\d+(\\.\\d+){0,1})\\h*((\\|\\||&&)\\h*([a-z]+\\w*|_+\\w+|\\-*\\d+(\\.\\d+){0,1})\\h*)*";
    }
}
