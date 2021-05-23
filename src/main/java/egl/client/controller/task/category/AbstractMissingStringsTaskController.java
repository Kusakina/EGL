package egl.client.controller.task.category;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;

import egl.client.view.pane.CustomBorderPane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;

@Component
@FxmlView
public abstract class AbstractMissingStringsTaskController extends AbstractTaskController {

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

    public AbstractMissingStringsTaskController() {
        this.random = new Random();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
    }

    private double fontSize;

    private void setRegionPrefSize(Region region) {
        region.setPrefSize(fontSize * 2.5, fontSize * 2);
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



    @RequiredArgsConstructor
    @Getter
    protected static class Question{
       private final List<String> expectedOrder;
        private final String sourceText;
        private final List<Integer>removedPositions;
        private final List<String> buttonStrings;
    }
    protected List<Question> questions;
    private int curMissingQuestionIndex;

    void showQuestion(int curIndex) {
        if (questions.size() == curIndex) {
            taskText.setText("Все задания закончились. Можете завершать игру");
            sourceWordText.setVisible(false);
            lettersVBox.setVisible(false);
            return;
        }

        //добавление текстфилдов в каждый класс
        sourceWordText.setText(questions.get(curIndex).sourceText);//тут либо слово, либо предложение
        TextField[] letterFields = new TextField[questions.get(curIndex).expectedOrder.size()];

        for (int index = 0; index < questions.get(curIndex).expectedOrder.size(); ++index) {
            letterFields[index] = createLetterTextField(questions.get(curIndex).expectedOrder.get(index));
        }

        var removedPositions = questions.get(curIndex).removedPositions;

        //исключение выбранных букв
        for (int index : removedPositions) {
            letterFields[index].setText("");
        }

        //настройки HBox
        selectedLettersHBox.getChildren().setAll(letterFields);


        var buttonString = questions.get(curIndex).buttonStrings;
        //генерация кнопок с буквами
        Button[] buttonStrings = new Button[buttonString.size()];
        for (int j = 0; j < buttonString.size(); ++j) {
            buttonStrings[j] = createLetterButton(buttonString.get(j));
        }

        //настройки HBox
        candidateLettersHBox.getChildren().setAll(buttonStrings);

        List<Integer> finalRemovedPositions = removedPositions;

        this.curMissingQuestionIndex = 0;
        for (Button button : buttonStrings) {
            button.setOnAction(event -> {
                if (curMissingQuestionIndex == finalRemovedPositions.size()) {
                    return;
                }

                int curRemoved = finalRemovedPositions.get(curMissingQuestionIndex);
                letterFields[curRemoved].setText("" + button.getText());
                curMissingQuestionIndex++;

                // в кнопку
            });
        }

        Button check = createLetterButton(":)");

        candidateLettersHBox.getChildren().add(check);
        check.setOnAction(event -> {
            if (curMissingQuestionIndex != finalRemovedPositions.size()) {
                return;
            }

            StringBuilder ans = new StringBuilder();

            for (TextField letter : letterFields) {
                ans.append(letter.getText());
            }

            //тут тоже непонятно до массива преобразовать или вынести в класссы
            boolean isCorrect = questions.get(curIndex).expectedOrder.equals(ans.toString());
            result.registerAnswer(isCorrect);

            if (isCorrect) {
                showQuestion(curIndex + 1);
            } else {
                curMissingQuestionIndex = 0;
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

        showQuestion(0);
    }

    abstract List<Question> fillQuestions();
    @Override
    protected void prepareToStart() {
        initializeTask();
        questions.clear();
        fillQuestions();
        //initializeTranslations();
    }

    private void initializeTask() {
        taskText.setText("Заполните пропуски подходящими буквами");
    }

    @Override
    protected void prepareToFinish() {

    }
}

