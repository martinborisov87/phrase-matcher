package md.your.assignment.bl;

import md.your.assignment.exception.InvalidInputException;
import md.your.assignment.persistence.PhrasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Martin on 2/21/2016.
 */
@Service
public class PhrasesMatcherServiceImpl implements PhrasesMatcherService {

    private static final char wordSeparator = ' ';

    private PhrasesRepository phrasesRepository;

    /**
     * Instantiates a new Phrases matcher service.
     *
     * @param phrasesRepository the phrases repository
     */
    @Autowired
    public PhrasesMatcherServiceImpl(PhrasesRepository phrasesRepository) {
        this.phrasesRepository = phrasesRepository;
    }

    //---------------------------

    @Override
    public Set<String> getMatchedPhrases(String input) {
        if (input == null || input.isEmpty()) {
            throw new InvalidInputException();
        }
        if (!isActualPhrase(input)) {
            return new HashSet<String>();
        }

        input = getProperlyFormattedPhraseToTest(input);
        Set<String> matchedPhrases = collectMatchedPhrases(input);

        return matchedPhrases;
    }

    //---------------------------

    /**
     * Collect matched phrases, based on a dictionary. <br/>
     * Every possible phrase in text is getting tested, starting from the longest ones.
     *
     * @param text the phrase to check
     * @return the set
     */
    private Set<String> collectMatchedPhrases(String text) {
        Set<String> dictionary = phrasesRepository.getDictionary();

        Integer[] wordsStartingIdx = getWordsStartingIndexes(text);

        Set<String> matchedPhrases = new HashSet<>();
        for (int i = 0; i < wordsStartingIdx.length; i++) {
            int startingIndex = wordsStartingIdx[i];

            for (int j = wordsStartingIdx.length; j > i; j--) {
                int endIndex = j == wordsStartingIdx.length ? -1 : (wordsStartingIdx[j] - 1);

                String phraseToTest;
                if (endIndex == -1) { // first test
                    phraseToTest = text.substring(startingIndex);
                } else {
                    phraseToTest = text.substring(startingIndex, endIndex);
                }

                String formattedPhraseToTest = getProperlyFormattedPhraseToTest(phraseToTest);
                if (dictionary.contains(formattedPhraseToTest)) {
                    matchedPhrases.add(formattedPhraseToTest);
                }
            }
        }

        return matchedPhrases;
    }

    /**
     * Get words starting indexes.
     * Constructs an integer array (of type resultedArray[numberOfWordInText] = startingIndex), which points to the starting index of every word in the text.
     * Example: resultedArray[2] = 15, means that the starting index of the second word in text is 15.
     *
     * @param text the text
     * @return the integer [ ]
     */
    private Integer[] getWordsStartingIndexes(String text) {
        List<Integer> wordsStartingIdx = new ArrayList<>();
        wordsStartingIdx.add(0);

        for (int i = 0; i < text.length() - 1; i++) {
            char currChar = text.charAt(i);
            char nextChar = text.charAt(i + 1);

            if (currChar == wordSeparator && nextChar != wordSeparator) {
                wordsStartingIdx.add(i + 1);
            }
        }

        return wordsStartingIdx.toArray(new Integer[wordsStartingIdx.size()]);
    }

    //---------------------------

    /**
     * Removes non-alphanumerics from the beginning and the end of phrase
     *
     * @param phrase the phrase
     * @return the properly formatted phrase to test
     */
    private String getProperlyFormattedPhraseToTest(String phrase) {
        return phrase.replaceAll("^[\\W]+|[\\W]+$", "");
    }

    /**
     * Check if a phrase contains at least one alphanumeric char.
     *
     * @param phrase the phrase
     * @return the boolean
     */
    private boolean isActualPhrase(String phrase) {
        boolean hasOnlyNonAlphanumeric = phrase.replaceAll("[\\W]+", "").isEmpty();
        return !hasOnlyNonAlphanumeric;
    }

}
