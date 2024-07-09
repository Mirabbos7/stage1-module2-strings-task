package com.epam.mjc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public MethodSignature parseMethodSignature(String signatureString) {
        StringSplitter splitter = new StringSplitter();
        List<String> tokens = splitter.splitByDelimiters(signatureString, Arrays.asList("(", ")", ",", " "));

        // Remove empty strings from the tokens list
        tokens.removeIf(String::isEmpty);

        String accessModifier = null;
        String returnType = null;
        String methodName;
        List<MethodSignature.Argument> arguments = new ArrayList<>();

        int currentIndex = 0;

        // Determine if the first token is an access modifier
        if (tokens.get(0).matches("public|private|protected")) {
            accessModifier = tokens.get(0);
            currentIndex++;
        }

        // The next token must be the return type
        returnType = tokens.get(currentIndex);
        currentIndex++;

        // The next token must be the method name
        methodName = tokens.get(currentIndex);
        currentIndex++;

        // The rest are arguments, pair them up
        while (currentIndex < tokens.size()) {
            String type = tokens.get(currentIndex);
            currentIndex++;
            String name = tokens.get(currentIndex);
            currentIndex++;
            arguments.add(new MethodSignature.Argument(type, name));
        }

        MethodSignature methodSignature = new MethodSignature(methodName, arguments);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);

        return methodSignature;
    }
}
