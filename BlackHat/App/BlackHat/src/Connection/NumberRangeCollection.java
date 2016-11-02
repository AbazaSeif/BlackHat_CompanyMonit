/*
 * GNU Library or Lesser Public License (LGPL)
 */

package Connection;

import java.util.StringTokenizer;

/**
 * @author Geoffrey
 */
public class NumberRangeCollection {

    private final NumberRange[] rangeList;
    private int rangeListIndex = 0;

    public NumberRangeCollection(String rangeString) throws IllegalArgumentException {
        StringTokenizer rangeTokenizer = new StringTokenizer(rangeString, ",");
        rangeList = new NumberRange[rangeTokenizer.countTokens()];
        for (int i = 0; i < rangeList.length; i++) {
            rangeList[i] = new NumberRange(rangeTokenizer.nextToken());
        }
        while (rangeListIndex < rangeList.length && !rangeList[rangeListIndex].hasNext()) {
            rangeListIndex++;
        }
    }

    public boolean hasNext() {
        return rangeListIndex < rangeList.length;
    }

    public int next() {
        int returnNumber = rangeList[rangeListIndex].next();
        while (rangeListIndex < rangeList.length && !rangeList[rangeListIndex].hasNext()) {
            rangeListIndex++;
        }
        return returnNumber;
    }

    public void reset() {
        rangeListIndex = 0;
        for (NumberRange rangeList1 : rangeList) {
            rangeList1.reset();
        }
        while (rangeListIndex < rangeList.length && !rangeList[rangeListIndex].hasNext()) {
            rangeListIndex++;
        }
    }

}
