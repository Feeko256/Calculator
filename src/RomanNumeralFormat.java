import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.LinkedHashMap;
import java.util.Map;

public class RomanNumeralFormat
        extends NumberFormat {


    public final static String CHECK_REGEXP = "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$";


    protected static LinkedHashMap<String, Integer> m_RomanNumerals;
    static {
        m_RomanNumerals = new LinkedHashMap<>();
        m_RomanNumerals.put("M", 1000);
        m_RomanNumerals.put("CM", 900);
        m_RomanNumerals.put("D", 500);
        m_RomanNumerals.put("CD", 400);
        m_RomanNumerals.put("C", 100);
        m_RomanNumerals.put("XC", 90);
        m_RomanNumerals.put("L", 50);
        m_RomanNumerals.put("XL", 40);
        m_RomanNumerals.put("X", 10);
        m_RomanNumerals.put("IX", 9);
        m_RomanNumerals.put("V", 5);
        m_RomanNumerals.put("IV", 4);
        m_RomanNumerals.put("I", 1);
    }


    @Override
    public boolean isGroupingUsed() {
        return false;
    }


    @Override
    public boolean isParseIntegerOnly() {
        return true;
    }


    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        return format(new Double(number).longValue(), toAppendTo, pos);
    }


    protected String repeatFormat(String s, int n) {
        if (s == null)
            return null;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++)
            sb.append(s);

        return sb.toString();
    }


    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        if ((number < 1) || (number > 3999))
            throw new IllegalArgumentException("Римское число может быть от 1 до 3999, получено: " + number);

        StringBuffer result = new StringBuffer();
        int intNum = (int) number;
        for(Map.Entry<String, Integer> entry : m_RomanNumerals.entrySet()){
            int matches = intNum /entry.getValue();
            result.append(repeatFormat(entry.getKey(), matches));
            intNum = intNum % entry.getValue();
        }

        return result;
    }


    protected int processDecimal(int decimal, int lastNumber, int lastDecimal) {
        if (lastNumber > decimal)
            return lastDecimal - decimal;
        else
            return lastDecimal + decimal;
    }


    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        if (!source.matches(CHECK_REGEXP))
            return null;

        int decimal = 0;
        int lastNumber = 0;
        String romanNumeral = source.toUpperCase();
        for (int x = romanNumeral.length() - 1; x >= 0 ; x--) {
            char convertToDecimal = romanNumeral.charAt(x);

            switch (convertToDecimal) {
                case 'M':
                    decimal = processDecimal(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;

                case 'D':
                    decimal = processDecimal(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;

                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;

                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;

                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;

                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;

                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;

                default:
                    return null;
            }

            parsePosition.setIndex(parsePosition.getIndex() + 1);
        }

        return decimal;
    }
}