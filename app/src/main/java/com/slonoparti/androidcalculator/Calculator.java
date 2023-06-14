package com.slonoparti.androidcalculator;

import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.lang.Math;

public class Calculator {
    public static double calc(String sequence){
        Stack<Double> stack = new Stack<>();
        sequence=toReversePolishNotation(sequence);

        for (String token : sequence.split("\\s+")) {
            switch (token) {
                case "^":
                    double temp=stack.pop();
                    stack.push(Math.pow(stack.pop(),temp));
                    break;
                case "+":
                    stack.push(stack.pop() + stack.pop());
                    break;
                case "-":
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case "*":
                    stack.push(stack.pop() * stack.pop());
                    break;
                case "/":
                    double divisor = stack.pop();
                    stack.push(stack.pop() / divisor);
                    break;
                default:
                    stack.push(Double.parseDouble(token));
                    break;
            }

        }

        return stack.pop();
    }
    public static String toReversePolishNotation(String sequence){
        StringBuffer buffer=new StringBuffer();
        ConcurrentLinkedDeque<Character> operandDeque=new ConcurrentLinkedDeque<>();
        for(int i=0;i<sequence.length();i++){
            if(sequence.charAt(i)=='('){
                operandDeque.addFirst(sequence.charAt(i));
            }
            else if(sequence.charAt(i)==')'){
                while (!operandDeque.isEmpty()&&operandDeque.peek()!='('){
                    buffer.append(operandDeque.pop());
                    buffer.append(" ");
                }
                operandDeque.pop();
            }
            else if(Character.isDigit(sequence.charAt(i))){
                int j=i;
                while (j<sequence.length()&&Character.isDigit(sequence.charAt(j))){
                    buffer.append(sequence.charAt(j));
                    j++;
                }
                buffer.append(" ");
                i+=j-i-1;
            }
            else{
                if(!operandDeque.isEmpty()&&checkPriority(sequence.charAt(i))<checkPriority(operandDeque.peek())){
                    while (!operandDeque.isEmpty()&&checkPriority(sequence.charAt(i))<checkPriority(operandDeque.peek())){
                        buffer.append(operandDeque.pop());
                        buffer.append(" ");
                    }
                }
                operandDeque.addFirst(sequence.charAt(i));
            }
        }
        while (operandDeque.peek()!=null){
            buffer.append(operandDeque.pop());
            buffer.append(" ");
        }
        System.err.println(buffer);
        return buffer.toString();
    }
    private static int checkPriority(char operand){
        if(operand=='+'||operand=='-')return 1;
        else if(operand=='*'||operand=='/')return 2;
        else if(operand=='^')return 3;
        return 0;
    }
}
