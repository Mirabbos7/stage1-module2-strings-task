package com.epam.mjc;

import java.util.ArrayList;
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
        String accessModifier = null;
        String returnType;
        String methodName;
        List<MethodSignature.Argument> arguments = new ArrayList<>();

        String regex = "(\\w+ )?(\\w+ )?(\\w+)\\(([^)]*)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(signatureString);

        if (matcher.find()) {
            accessModifier = matcher.group(1) != null ? matcher.group(1).trim() : null;
            returnType = matcher.group(2) != null ? matcher.group(2).trim() : null;
            methodName = matcher.group(3);

            String args = matcher.group(4).trim();
            if (!args.isEmpty()) {
                String[] argsArray = args.split(",");
                for (String arg : argsArray) {
                    String[] argParts = arg.trim().split("\\s+");
                    if (argParts.length == 2) {
                        arguments.add(new MethodSignature.Argument(argParts[0], argParts[1]));
                    }
                }
            }

            MethodSignature methodSignature = new MethodSignature(methodName, arguments);
            methodSignature.setAccessModifier(accessModifier);
            methodSignature.setReturnType(returnType);
            return methodSignature;
        }
        throw new IllegalArgumentException("Invalid method signature: " + signatureString);
    }
}
