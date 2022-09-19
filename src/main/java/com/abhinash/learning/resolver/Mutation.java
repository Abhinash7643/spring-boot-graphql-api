package com.abhinash.learning.resolver;

import com.abhinash.learning.model.Author;
import com.abhinash.learning.model.Tutorial;
import com.abhinash.learning.repository.AuthorRepository;
import com.abhinash.learning.repository.TutorialRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {

    private final AuthorRepository authorRepository;
    private final TutorialRepository tutorialRepository;

    @Autowired
    public Mutation(AuthorRepository authorRepository, TutorialRepository tutorialRepository) {
        this.authorRepository = authorRepository;
        this.tutorialRepository = tutorialRepository;
    }

    public Author createAuthor(String name, Integer age) {
        Author author = new Author();
        author.setName(name);
        author.setAge(age);

        return authorRepository.save(author);

    }

    public Tutorial createTutorial(String title, String description, Long authorId) {
        Tutorial book = new Tutorial();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setDescription(description);

        tutorialRepository.save(book);

        return book;
    }

    public boolean deleteTutorial(Long id) {
        tutorialRepository.deleteById(id);
        return true;
    }

    public Tutorial updateTutorial(Long id, String title, String description) throws ClassNotFoundException {
        Optional<Tutorial> optTutorial = tutorialRepository.findById(id);

        if (optTutorial.isPresent()) {
            Tutorial tutorial = optTutorial.get();

            if (title != null)
                tutorial.setTitle(title);
            if (description != null)
                tutorial.setDescription(description);

            tutorialRepository.save(tutorial);
            return tutorial;
        }

        throw new ClassNotFoundException("Not found Tutorial to update!");
    }
}
