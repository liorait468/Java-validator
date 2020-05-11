package oop.ex6.main;

public enum Type {

    INT("int"),
    DOUBLE("double"),
    STRING("String"),
    BOOLEAN("boolean"),
    CHAR("char");

    private final String typeName;

    Type(String strName) {
        this.typeName = strName;
    }

    String getTypeName(){
        return typeName;
    }


}