package md.your.assignment.bl;

import md.your.assignment.persistence.PhrasesRepository;
import md.your.assignment.stubs.PhrasesFileRepositoryStub;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

/**
 * Created by Martin on 2/21/2016.
 */
public class PhrasesMatcherServiceTest {

    private PhrasesRepository mockPhrasesRepository;

    private PhrasesMatcherService phrasesMatcherService;

    @Before
    public void setUp() {
        mockPhrasesRepository = new PhrasesFileRepositoryStub();
        phrasesMatcherService = new PhrasesMatcherServiceImpl(mockPhrasesRepository);
    }

    //------------------------

    @Test
    public void when_SentenceWithFoundedPhrasesIsPassed_Then_ReturnMatchingPhrases() {
        Set<String> matchedPhrases = phrasesMatcherService.getMatchedPhrases("I have a sore throat and headache, but nothing serious.");
        assertNotNull(matchedPhrases);
        assertTrue(matchedPhrases.size() == 2);
        assertThat(matchedPhrases, hasItems("sore throat", "headache"));
    }

    @Test
    public void when_SentenceWithNoneFoundedPhrasesIsPassed_Then_ReturnNoMatches() {
        Set<String> matchedPhrases = phrasesMatcherService.getMatchedPhrases("I have new car.");
        assertNotNull(matchedPhrases);
        assertTrue(matchedPhrases.size() == 0);
    }

    @Test
    public void when_JustAnExistingPhraseIsPassed_Then_ReturnIt() {
        Set<String> matchedPhrases = phrasesMatcherService.getMatchedPhrases("sore throat");
        assertNotNull(matchedPhrases);
        assertTrue(matchedPhrases.size() == 1);
        assertThat(matchedPhrases, hasItems("sore throat"));
    }

    @Test
    public void when_SentenceWithFoundedPhrasesWhichContainsSymbols_Then_ReturnMatchingPhrases() {
        Set<String> matchedPhrases = phrasesMatcherService.getMatchedPhrases("My h/o: tracheostomy and I have a headache so far.");
        assertNotNull(matchedPhrases);
        assertTrue(matchedPhrases.size() == 2);
        assertThat(matchedPhrases, hasItems("headache", "h/o: tracheostomy"));
    }

    @Test
    public void when_OnlySymbolsArePassed_Then_ReturnNoMatches() {
        Set<String> matchedPhrases = phrasesMatcherService.getMatchedPhrases("---,,,???");
        assertNotNull(matchedPhrases);
        assertTrue(matchedPhrases.size() == 0);
    }

    @Test
    public void when_MatchingPhraseIsAtTheBeginning_Then_ReturnsItProperly() {
        Set<String> matchedPhrases = phrasesMatcherService.getMatchedPhrases("headache is killing me.");
        assertNotNull(matchedPhrases);
        assertTrue(matchedPhrases.size() == 1);
        assertThat(matchedPhrases, hasItems("headache"));
    }

    @Test
    public void when_MatchingPhraseIsAtTheEnd_Then_ReturnsItProperly() {
        Set<String> matchedPhrases = phrasesMatcherService.getMatchedPhrases("I am not feeling very well, because of my hypertensive left ventricular hypertrophy.");
        assertNotNull(matchedPhrases);
        assertTrue(matchedPhrases.size() == 1);
        assertThat(matchedPhrases, hasItems("hypertensive left ventricular hypertrophy"));
    }

}
