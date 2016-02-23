package md.your.assignment.api;

/**
 * Created by Martin on 2/21/2016.
 */

import md.your.assignment.bl.PhrasesMatcherService;
import md.your.assignment.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * The type Phrases matcher controller.
 */
@RestController
@RequestMapping("/api/phrases/")
public class PhrasesMatcherController {

    private PhrasesMatcherService phraseMatcherService;

    /**
     * Instantiates a new Phrases matcher controller, which depends on a {@link PhrasesMatcherService} to collect matched phrases
     *
     * @param phraseMatcherService the phrase matcher service
     */
    @Autowired
    public PhrasesMatcherController(PhrasesMatcherService phraseMatcherService) {
        this.phraseMatcherService = phraseMatcherService;
    }

    //---------------------------

    /**
     * Collects matched phrases set.
     *
     * @param input the input
     * @return the set
     */
    @RequestMapping("match")
    public Set<String> getMatchedPhrases(@RequestParam(value = "input") String input) {
        Set<String> matchedPhrases = phraseMatcherService.getMatchedPhrases(input);
        return matchedPhrases;
    }
}
