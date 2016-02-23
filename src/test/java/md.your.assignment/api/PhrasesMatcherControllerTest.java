package md.your.assignment.api;

import md.your.assignment.App;
import md.your.assignment.bl.PhrasesMatcherService;
import md.your.assignment.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Martin on 2/22/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
public class PhrasesMatcherControllerTest {

    @Mock
    private PhrasesMatcherService phrasesMatcherService;

    @InjectMocks
    private PhrasesMatcherController phrasesMatcherController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(phrasesMatcherController).build();
    }


    @Test
    public void when_SentenceWithExistingPhrases_Then_ReturnMatchingPhrases() throws Exception {
        final String input = "I have a sore throat and headache.";

        Set<String> expectedPhrases = new HashSet<String>();
        expectedPhrases.add("sore throat");
        expectedPhrases.add("headache");

        when(phrasesMatcherService.getMatchedPhrases(input)).thenReturn(expectedPhrases);

        mockMvc.perform(get("/api/phrases/match?input=" + input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", is("sore throat")))
                .andExpect(jsonPath("$[1]", is("headache")));

        verify(phrasesMatcherService, times(1)).getMatchedPhrases(input);
        verifyNoMoreInteractions(phrasesMatcherService);
    }

    @Test
    public void when_SentenceWithNoneFoundedPhrasesIsPassed_Then_ReturnNoMatches() throws Exception {
        final String input = "I have a new car.";

        when(phrasesMatcherService.getMatchedPhrases(input)).thenReturn(new HashSet<String>());

        mockMvc.perform(get("/api/phrases/match?input=" + input))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(phrasesMatcherService, times(1)).getMatchedPhrases(input);
        verifyNoMoreInteractions(phrasesMatcherService);
    }

    @Test
    public void when_NoInputPassed_Then_ReturnBadRequest() throws Exception {
        final String input = "";

        doThrow(InvalidInputException.class).when(phrasesMatcherService).getMatchedPhrases(input);

        mockMvc.perform(get("/api/phrases/match?input=" + input))
                .andExpect(status().isBadRequest());

        verify(phrasesMatcherService, times(1)).getMatchedPhrases(input);
        verifyNoMoreInteractions(phrasesMatcherService);
    }

}



