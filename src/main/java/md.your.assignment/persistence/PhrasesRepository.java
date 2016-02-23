package md.your.assignment.persistence;

import java.util.Set;

/**
 * Created by Martin on 2/21/2016.
 */
public interface PhrasesRepository {

    /**
     * Returns a dictionary, which contains all existing phrases in some data storage.
     *
     * @return the dictionary
     */
    Set<String> getDictionary();

}
