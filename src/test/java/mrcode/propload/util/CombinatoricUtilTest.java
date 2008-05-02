package mrcode.propload.util;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

public class CombinatoricUtilTest extends TestCase {

    public void testCombinationFromRightToLeft() {
        String[] groupA = new String[] { "ab", "cd", "ef" };
        String[] groupB = new String[] { "01", "2", "34" };
        String[] groupC = new String[] { "", "A", "AB"};
        String[] groupD = new String[] { "", "23", "4"} ;
        String[] groupE = new String[] { "", "34", "4"} ;
        String[] groupF = new String[] { "01", "2", "23" };
        
        String[] expectedAB = new String[] { "ab01", "ab2", "ab34", "cd01", "cd2", "cd34", "ef01", "ef2", "ef34" };
        String[] expectedCAB = new String[] {
                "ab01", "ab2", "ab34", "cd01", "cd2", "cd34", "ef01", "ef2", "ef34",
                "Aab01", "Aab2", "Aab34", "Acd01", "Acd2", "Acd34", "Aef01", "Aef2", "Aef34",
                "ABab01", "ABab2", "ABab34", "ABcd01", "ABcd2", "ABcd34", "ABef01", "ABef2", "ABef34"};
        String[] expectedCD = new String[] { "", "23", "4", "A", "A23", "A4", "AB", "AB23", "AB4" };
        String[] expectedFE = new String[] { "01", "0134", "014", "2", "234", "24", "23", "2334" };
        
        assertSameOrder(expectedAB, CombinatoricUtil.combineRightToLeft(groupA, groupB));
        assertSameOrder(expectedCAB, CombinatoricUtil.combineRightToLeft(groupC, groupA, groupB));
        assertSameOrder(expectedCD, CombinatoricUtil.combineRightToLeft(groupC, groupD));
        assertSameOrder(expectedFE, CombinatoricUtil.combineRightToLeft(groupF, groupE));
    }

    private void assertSameOrder(String[] expected, Collection<String> actual) {
        if (expected == null || expected.length == 0) {
            assertNull(actual);
        } else {
            assertEquals(expected.length, actual.size());
            Iterator<String> it = actual.iterator();
            for (int i=0; i<expected.length; i++) {
                assertEquals(expected[i], it.next());
            }
        }
    }
}
