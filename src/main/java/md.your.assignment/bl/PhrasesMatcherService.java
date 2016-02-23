package md.your.assignment.bl;

import java.util.List;
import java.util.Set;

/**
 * Created by Martin on 2/21/2016.
 */
public interface PhrasesMatcherService {

    /**
     * Service, which collects matched phrases, based on user's input.
     *
     * @param input the input
     * @return the matched phrases
     */
    Set<String> getMatchedPhrases(String input);

}
