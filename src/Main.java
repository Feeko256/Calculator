import java.util.Scanner;

import static java.lang.System.*;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in);
            String data = in.nextLine();
            String[] dataArray = data.split(" ");
            if(dataArray.length != 3)
                throw new IllegalArgumentException("ошибка ввода");

            try {
                int a = Integer.parseInt(dataArray[0]);
                int b = Integer.parseInt(dataArray[2]);
                char operator = dataArray[1].charAt(0);

                if (a <= 10 && b <= 10)
                    out.println(calc(a, b, operator));
                else
                    throw new IllegalArgumentException("операнды должны быть в пределах 1..10");
            }
            catch (NumberFormatException ex){

                RomanNumeralFormat RNF = new RomanNumeralFormat();
                int a =  RNF.parse(dataArray[0]).intValue();
                int b =  RNF.parse(dataArray[2]).intValue();
                char operator = dataArray[1].charAt(0);
                int output;
                if (a <= 10 && b <= 10)
                    output = calc(a, b, operator);
                else
                    throw new IllegalArgumentException("операнды должны быть в пределах I..X");
                out.println(RNF.format(output));
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
           //ex.printStackTrace();
        }
    }
    public static int calc(int a, int b, char operator) {
        switch (operator) {
            case '+':
                return a+b;
            case '-':
                return a-b;
            case '*':
                return a*b;
            case '/':
                if(b==0)
                    throw new ArithmeticException("деление на 0");
                return a/b;
            default:
                throw new IllegalArgumentException("Некорректный ввод");
        }

    }
}