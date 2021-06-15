package egl.client.controller.task.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import egl.client.controller.task.LocalTaskController;
import egl.client.model.local.topic.category.Category;
import egl.client.model.local.topic.category.Translation;
import egl.client.service.model.local.CategoryService;
import egl.client.service.model.local.LocalTopicInfoService;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class MatchWordsController extends LocalTaskController<Category> {

    private static final int MAX_WORDS_COUNT = 8;

    @FXML private GridPane mainGridPane;

    private final Random random;

    public MatchWordsController(LocalTopicInfoService localTopicInfoService,
                                CategoryService categoryService) {
        super(localTopicInfoService, categoryService);
        this.random = new Random();
    }

    private Label[] answers;

    private void initializeWords(Category category) {
        mainGridPane.getChildren().clear();

        var translations = category.getTranslations();

        int wordsCount = Math.min(MAX_WORDS_COUNT, translations.size());
        List<Translation> leftList = random.ints(0, translations.size())
                .distinct()
                .limit(wordsCount)
                .boxed()
                .map(translations::get)
                .collect(Collectors.toList());

        List<Translation> rightList = new ArrayList<>(leftList);
        Collections.shuffle(rightList, random);

        ToggleGroup originGroup = new ToggleGroup();
        ToggleGroup transGroup = new ToggleGroup();

        this.answers = new Label[leftList.size()];

        List<ToggleButton> leftButtons = new ArrayList<>();
        List<ToggleButton> rightButtons = new ArrayList<>();

        for (int i = 0; i < leftList.size(); ++i) {
            var leftButton = new ToggleButton(leftList.get(i).getSource().getText());
            leftButton.setMaxHeight(Double.MAX_VALUE);
            leftButton.setMaxWidth(Double.MAX_VALUE);
            leftButton.setToggleGroup(originGroup);
            GridPane.setHgrow(leftButton, Priority.ALWAYS);
            GridPane.setVgrow(leftButton, Priority.ALWAYS);
            GridPane.setMargin(leftButton, new Insets(10));
            leftButtons.add(leftButton);
            mainGridPane.add(leftButton, 0, i);

            Label ans = answers[i] = new Label("");
            ans.setMaxHeight(Double.MAX_VALUE);
            ans.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(ans, Priority.ALWAYS);
            GridPane.setVgrow(ans, Priority.ALWAYS);
            GridPane.setMargin(ans, new Insets(10));
            mainGridPane.add(ans, 1, i);

            var rightButton = new ToggleButton(rightList.get(i).getTarget().getText());
            rightButton.setMaxHeight(Double.MAX_VALUE);
            rightButton.setMaxWidth(Double.MAX_VALUE);
            rightButton.setToggleGroup(transGroup);
            GridPane.setHgrow(rightButton, Priority.ALWAYS);
            GridPane.setVgrow(rightButton, Priority.ALWAYS);
            GridPane.setMargin(rightButton, new Insets(10));
            rightButtons.add(rightButton);
            mainGridPane.add(rightButton, 2, i);
        }

        for (int i = 0; i < leftList.size(); ++i) {
            for (int j = 0; j < rightList.size(); ++j) {
                if (leftList.get(i).equals(rightList.get(j))) {
                    int finalI = i;
                    int finalJ = j;
                    ToggleButton sourceButton = leftButtons.get(i), targetButton = rightButtons.get(j);

                    Function<ToggleButton, ChangeListener<? super Boolean>> listenerGetter = (otherButton) -> (observableValue, oldValue, newValue) -> {
                        if (newValue && otherButton.isSelected()) {
                            answers[finalI].setText(leftList.get(finalI).getSource().getText() + " - " + rightList.get(finalJ).getTarget().getText());
                            rightButtons.get(finalJ).setText("");
                            leftButtons.get(finalI).setText("");
                        } else {
                            if (!leftButtons.get(finalI).getText().isBlank() &&
                                    !rightButtons.get(finalJ).getText().isBlank()) {
                                result.registerAnswer(false);
                            }
                        }
                    };


                    sourceButton.selectedProperty().addListener(listenerGetter.apply(targetButton));
                    targetButton.selectedProperty().addListener(listenerGetter.apply(sourceButton));
                }
            }
        }
    }

    @Override
    protected void prepareToStart() {
        initializeWords(specificLocalTopic);
    }

    @Override
    protected void prepareToFinish() {
        for (Label answer : answers) {
            result.registerAnswer(!answer.getText().isBlank());
        }
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
        mainGridPane.setPrefSize(parentWidth, parentHeight);
    }
}
