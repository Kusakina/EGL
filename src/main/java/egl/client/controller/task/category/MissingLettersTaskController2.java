package egl.client.controller.task.category;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import egl.client.view.pane.CustomBorderPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
@FxmlView
public class MissingLettersTaskController2 extends AbstractMissingStringsTaskController {

    private static final int DEFAULT_ADDITIONAL_LETTERS_COUNT = 3;

    @FXML
    public CustomBorderPane gameBorderPane;

    @FXML
    private Text taskText;

    @FXML
    private Text sourceWordText;

    @FXML
    private HBox selectedLettersHBox;

    @FXML
    private HBox candidateLettersHBox;

    @FXML
    private CustomBorderPane textsBorderPane;

    @FXML
    private VBox lettersVBox;

    private Random random;

    //public MissingLettersTaskController2() {
        //this.random = new Random();
    //}

    //private List<Translation> translations;
    //private int curMissingLetterIndex;

    public MissingLettersTaskController2() {
    }

    Question createQuestion(Translation translation) {
        String sourceText = translation.getSource().toString();
        List<String> expectedOrder = new ArrayList<>();
        String expectedString = translation.getTarget().toString();
        String target = translation.getTarget().toString();
        for (int index = 0; index < target.length(); ++index) {
            expectedOrder.add( Character.toString(target.charAt(index)));
        }



        List<Integer>removedPositions = new ArrayList<>();
        for (int index = 0; index < target.length(); ++index) {
            removedPositions.add(index);
        }
        Collections.shuffle(removedPositions, random);
        int needRemove = target.length() / 2;
        removedPositions = removedPositions.subList(0, needRemove);
        Collections.sort(removedPositions);



        List<String> buttonStrings = new ArrayList<>();
        for (int index : removedPositions) {
            buttonStrings.add(Character.toString(target.charAt(index)));
        }

        for (int j = 0; j < DEFAULT_ADDITIONAL_LETTERS_COUNT; ++j) {
            int letterIndex = random.nextInt(26);
            char letter = (char) ('a' + letterIndex);
            if (!buttonStrings.contains(letter)) {
                buttonStrings.add(Character.toString(letter));
            } else {
                --j;
            }
        }
        Collections.shuffle(buttonStrings, random);

        return new Question(expectedOrder,sourceText, expectedString, removedPositions, buttonStrings);
    }

    @Override
    void fillQuestions() {
        Category category = (Category) controllerTopic;
        for (Translation translation : category.getTranslations()) {
            questions.add(createQuestion(translation));
        }
    }
}

