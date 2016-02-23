package md.your.assignment.stubs;

import md.your.assignment.persistence.PhrasesRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Martin on 2/22/2016.
 */
public class PhrasesFileRepositoryStub implements PhrasesRepository {

    private static Set<String> dictionary;

    static {
        dictionary = new HashSet<>();
        dictionary.add("sore throat");
        dictionary.add("headache");
        dictionary.add("head ache");
        dictionary.add("heart valve replacement - prosthesis");
        dictionary.add("bernhardt's paresthesia");
        dictionary.add("h/o: tracheostomy");
        dictionary.add("hypertensive left ventricular hypertrophy");
    }

    @Override
    public Set<String> getDictionary() {
        return dictionary;
    }
}
