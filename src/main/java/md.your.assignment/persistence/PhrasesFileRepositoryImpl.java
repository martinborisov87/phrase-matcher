package md.your.assignment.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Martin on 2/21/2016.
 */
public class PhrasesFileRepositoryImpl implements PhrasesRepository {

    private volatile Set<String> dictionary;

    private String storageFileName;

    private ResourceLoader resourceLoader;

    //-------------------------

    /**
     * Instantiates a new Phrases file repository.
     *
     * @param storageFileName the storage file name
     */
    public PhrasesFileRepositoryImpl(String storageFileName, ResourceLoader resourceLoader) {
        this.storageFileName = storageFileName;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Init.
     */
    @PostConstruct
    private void init() {
        if (dictionary == null) {
            synchronized (this) {
                if (dictionary == null) {
                    dictionary = new HashSet<>();

                    Scanner scanner = null;
                    try {
                        Resource resource = resourceLoader.getResource(storageFileName);
                        File dictionaryFile = resource.getFile();

                        scanner = new Scanner(dictionaryFile);

                        //now read the file line by line...
                        while (scanner.hasNextLine()) {
                            String phrase = scanner.nextLine();
                            dictionary.add(phrase);
                        }

                        System.out.println("==========================  Phrases count: " + dictionary.size());
                    } catch (IOException e) {
                        System.out.println("Some error occurs while reading dictionary file. " + e.getMessage());
                    } finally {
                        if (scanner != null) {
                            scanner.close();
                        }
                    }
                }
            }
        }
    }

    //-------------------------

    @Override
    public synchronized Set<String> getDictionary() {
        return dictionary;
    }
}