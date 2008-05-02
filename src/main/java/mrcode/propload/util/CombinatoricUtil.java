package mrcode.propload.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

public class CombinatoricUtil {
    
    public static Collection<String> combineRightToLeft(final String[] ... strings) {
        if (strings == null || strings.length == 0) {
            return null;
        }
        Collection<String>[] stringCollections = new Collection[strings.length];
        for (int i=0; i<strings.length; i++) {
            stringCollections[i] = Arrays.asList(strings[i]);
        }
        return new LinkedHashSet<String>(combineRightToLeft(stringCollections));
    }

    public static Collection<String> combineRightToLeft(Collection<String> ... strings) {
        if (strings == null || strings.length == 0) {
            return null;
        }
        return combine(0, strings);
    }

    protected static Collection<String> combine(final int index, final Collection<String>[] strings) {
        if (index == strings.length - 1) {
            return strings[index];
        }
        final Collection<String> combinations = combine(index + 1, strings);
        Collection<String> result = new ArrayList<String>(combinations.size() * strings[index].size());
        for (String string1 : strings[index]) {
            for (String string2 : combinations) {
                result.add(string1 + string2);
            }
        }
        return result;
    }
}
