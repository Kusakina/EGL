package egl.client.controller.task.category;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

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

@Component
@FxmlView
public class MissingLettersTaskController extends AbstractTaskController {

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

    private final Random random;

    public MissingLettersTaskController() {
        this.random = new Random();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    private double fontSize;

    private void setRegionPrefSize(Region region) {
        region.setPrefSize(fontSize * 2, fontSize * 2);
    }

    private TextField createLetterTextField(String letter) {
        TextField letterField = new TextField(letter);
        letterField.setEditable(false);
        // настройку шрифта и других штук логичнее всего делать тут, если она нужна
        setPrefSize(letterField);
        return letterField;
    }

    private void setPrefSize(TextField letterField) {
        letterField.setFont(Font.font(fontSize));
        setRegionPrefSize(letterField);
    }

    private Button createLetterButton(String letter) {
        Button letterButton = new Button(letter);
        setPrefSize(letterButton);
        return letterButton;
    }

    private void setPrefSize(Button letterButton) {
        letterButton.setFont(Font.font(fontSize));
        setRegionPrefSize(letterButton);
    }

    private List<Translation> translations;
    private int curMissingLetterIndex;

    void initializeCurrentWord(int curIndex) {
        if (translations.size() == curIndex) {
            taskText.setText("Все слова закончились. Можете завершать игру");
            sourceWordText.setVisible(false);
            lettersVBox.setVisible(false);
            return;
        }

        //получение текущего слова
        String curSource = translations.get(curIndex).getSource().toString();
        String curTarget = translations.get(curIndex).getTarget().toString();
        sourceWordText.setText(curSource);

        // запись слова по буквам в список
        List<Integer> removedPositions = new ArrayList<>();
        for (int index = 0; index < curTarget.length(); ++index) {
            removedPositions.add(index);
        }
        Collections.shuffle(removedPositions, random);

        //выбор букв для исключения
        int needRemove = curTarget.length() / 2;
        removedPositions = removedPositions.subList(0, needRemove);
        Collections.sort(removedPositions);

        //добавление текстфилдов
        TextField[] letterFields = new TextField[curTarget.length()];

        for (int index = 0; index < curTarget.length(); ++index) {
            letterFields[index] = createLetterTextField("" + curTarget.charAt(index));
        }

        //исключение выбранных букв
        for (int index : removedPositions) {
            letterFields[index].setText("");
        }

        //настройки HBox
        selectedLettersHBox.getChildren().setAll(letterFields);

        //генерация букв для кнопок
        List<Character> buttonLetters = new ArrayList<>();
        for (int index : removedPositions) {
            buttonLetters.add(curTarget.charAt(index));
        }

        for (int j = 0; j < DEFAULT_ADDITIONAL_LETTERS_COUNT; ++j) {
            int letterIndex = random.nextInt(26);
            char letter = (char) ('a' + letterIndex);
            if (!buttonLetters.contains(letter)) {
                buttonLetters.add(letter);
            } else {
                --j;
            }
        }
        Collections.shuffle(buttonLetters, random);

        //генерация кнопок с буквами
        Button[] letterButtons = new Button[buttonLetters.size()];
        for (int j = 0; j < buttonLetters.size(); ++j) {
            letterButtons[j] = createLetterButton("" + buttonLetters.get(j));
        }

        //настройки HBox
        candidateLettersHBox.getChildren().setAll(letterButtons);

        List<Integer> finalRemovedPositions = removedPositions;

        this.curMissingLetterIndex = 0;
        for (Button button : letterButtons) {
            button.setOnAction(event -> {
                if (curMissingLetterIndex == finalRemovedPositions.size()) {
                    return;
                }

                int curRemoved = finalRemovedPositions.get(curMissingLetterIndex);
                letterFields[curRemoved].setText("" + button.getText());
                curMissingLetterIndex++;

                // в кнопку
            });
        }

        Button check = createLetterButton(":)");

        candidateLettersHBox.getChildren().add(check);
        check.setOnAction(event -> {
            if (curMissingLetterIndex != finalRemovedPositions.size()) {
                return;
            }

            StringBuilder ans = new StringBuilder();
            for (TextField letter : letterFields) {
                ans.append(letter.getText());
            }

            boolean isCorrect = curTarget.equals(ans.toString());
            result.registerAnswer(isCorrect);

            if (isCorrect) {
                initializeCurrentWord(curIndex + 1);
            } else {
                curMissingLetterIndex = 0;
                for (int index : finalRemovedPositions) {
                    letterFields[index].setText("");
                }
            }
        });
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        gameBorderPane.setPrefSize(parentWidth, parentHeight);

        double spacing = parentHeight / 10;
        double margin = parentHeight / 20;

        gameBorderPane.setSpacing(spacing);
        gameBorderPane.setMargin(margin);

        textsBorderPane.setMargin(margin / 2);
        lettersVBox.setSpacing(spacing);

        this.fontSize = parentHeight / 20;

        taskText.setFont(Font.font(fontSize));
        sourceWordText.setFont(Font.font(fontSize * 1.2));

        initializeCurrentWord(0);
    }

    @Override
    protected void prepareToStart() {
        initializeTask();
        initializeTranslations();
    }

    private void initializeTranslations() {
        Category category = (Category) controllerTopic;

        this.translations = category.getTranslations();
        Collections.shuffle(translations);
    }

    private void initializeTask() {
        taskText.setText("Заполните пропуски подходящими буквами");
    }

    @Override
    protected void prepareToFinish() {

    }
}

