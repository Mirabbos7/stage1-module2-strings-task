package com.epam.mjc;

import java.util.HashMap;
import java.util.Map;
public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     *      1. access modifier - optional, followed by space: ' '
     *      2. return type - followed by space: ' '
     *      3. method name
     *      4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     *      accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     *      private void log(String value)
     *      Vector3 distort(int x, int y, int z, float magnitude)
     *      public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */

    public static void main(String[] args) {
        MethodParser methodParser = new MethodParser();
        methodParser.parseFunction("public void mirabbos(string a, String b)");
    }
    public MethodSignature parseFunction(String signatureString) {
        Map<String, String> parsedMap = new HashMap<>();
        String[] parts = signatureString.split(" ");

        parsedMap.put("accessModifier", parts[0]);
        parsedMap.put("returnType", parts[1]);
        parsedMap.put("methodName", parts[2]);

        String arguments = signatureString.substring(signatureString.indexOf('(') + 1, signatureString.indexOf(')'));
        String[] argumentParts = arguments.split(", ");
        for (String argument : argumentParts) {
            String[] argumentDetails = argument.split(" ");
            parsedMap.put(argumentDetails[1], argumentDetails[0]);
        }

        return new MethodSignature("public void main(String a)");
    }
}
