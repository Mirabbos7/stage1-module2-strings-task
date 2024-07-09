package com.epam.mjc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MethodParser {

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseMethodSignature(String signatureString) {
        // Regular expression to match the method signature
        String regex = "(public|protected|private)?\\s*(\\w+|\\w+<\\w+>)\\s+(\\w+)\\s*\\(([^)]*)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(signatureString);

        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid method signature: " + signatureString);
        }

        String accessModifier = matcher.group(1);
        String returnType = matcher.group(2);
        String methodName = matcher.group(3);
        String argumentsString = matcher.group(4);

        List<MethodSignature.Argument> arguments = new ArrayList<>();
        if (!argumentsString.isEmpty()) {
            String[] argsArray = argumentsString.split(",\\s*");
            for (String arg : argsArray) {
                String[] argParts = arg.split("\\s+");
                if (argParts.length != 2) {
                    throw new IllegalArgumentException("Invalid argument: " + arg);
                }
                String argType = argParts[0];
                String argName = argParts[1];
                arguments.add(new MethodSignature.Argument(argType, argName));
            }
        }

        MethodSignature methodSignature = new MethodSignature(methodName, arguments);
        methodSignature.setAccessModifier(accessModifier);
        methodSignature.setReturnType(returnType);

        return methodSignature;
    }
}
