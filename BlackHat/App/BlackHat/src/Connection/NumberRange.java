/*
 * GNU Library or Lesser Public License (LGPL)
 */

package Connection;


/**
 * @author Geoffrey
 */
public class NumberRange {

    private int firstNumber, lastNumber, currentNumber;

    public NumberRange(String rangeString) throws IllegalArgumentException {
        int delimiter = rangeString.indexOf("-");
        if (delimiter < 0) {
            firstNumber = Integer.parseInt(rangeString.trim());
            lastNumber = firstNumber + 1;
        } else {
            firstNumber = Integer.parseInt(rangeString.substring(0, delimiter).trim());
            lastNumber = Integer.parseInt(rangeString.substring(delimiter + 1).trim()) + 1;
        }
        currentNumber = firstNumber;
    }

    public boolean hasNext() {
        return currentNumber < lastNumber;
    }

    public int next() {
        currentNumber++;
        return currentNumber - 1;
    }

    public void reset() {
        currentNumber = firstNumber;
    }
}
