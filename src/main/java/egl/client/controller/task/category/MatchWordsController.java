package egl.client.controller.task.category;

import egl.client.controller.task.AbstractTaskController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import net.rgielen.fxweaver.core.FxmlView;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@FxmlView
public class MatchWordsController extends AbstractTaskController {

    @FXML
    public void initialize(URL location, ResourceBundle resourceBundle) {
        super.initialize(location, resourceBundle);

        WordCase[] Colors = new WordCase[10];
        Colors[0] = new WordCase("Red", "Красный", 1);
        Colors[1] = new WordCase("Blue", "Синий", 2);
        Colors[2] = new WordCase("Green", "Зеленый", 3);
        Colors[3] = new WordCase("Grey", "Серый", 4);
        Colors[4] = new WordCase("Black", "Черный", 5);
        Colors[5] = new WordCase("White", "Белый", 6);
        Colors[6] = new WordCase("Yellow", "Желтый", 7);
        Colors[7] = new WordCase("Pink", "Розовый", 8);
        Colors[8] = new WordCase("Orange", "Оранжевый", 9);
        Colors[9] = new WordCase("Cyan", "Циан", 10);

        ArrayList<WordCase> leftList = new ArrayList<>();

        Random random = new Random();
        List<Integer> randomNumbers = random.ints(0, 10).distinct().limit(4).boxed().collect(Collectors.toList());
        for (int i = 0; i < randomNumbers.size(); ++i) {
            leftList.add(Colors[randomNumbers.get(i)]);
        }

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(33.3);
        //table.getColumnConstraints().add(column1);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(33.3);
        //table.getColumnConstraints().add(column2);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(33.3);
        //table.getColumnConstraints().add(column3);

        ArrayList<WordCase> rightList = new ArrayList<>();
        for (WordCase wordCase : leftList) {
            rightList.add(new WordCase(wordCase.getOriginal(), wordCase.getTranslation(), wordCase.getId()));
        }
        Collections.shuffle(rightList);

        ToggleGroup originGroup = new ToggleGroup();
        ToggleGroup transGroup = new ToggleGroup();

        Label[] answers = new Label[leftList.size()];

        for (int i = 0; i < leftList.size(); ++i) {
            leftList.get(i).button = new ToggleButton(leftList.get(i).getOriginal());
            leftList.get(i).button.setMaxHeight(Double.MAX_VALUE);
            leftList.get(i).button.setMaxWidth(Double.MAX_VALUE);
            leftList.get(i).button.setToggleGroup(originGroup);
            GridPane.setHgrow(leftList.get(i).button, Priority.ALWAYS);
            GridPane.setVgrow(leftList.get(i).button, Priority.ALWAYS);
            GridPane.setMargin(leftList.get(i).button, new Insets(10));

            Label ans = answers[i] = new Label("");
            ans.setMaxHeight(Double.MAX_VALUE);
            ans.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(ans, Priority.ALWAYS);
            GridPane.setVgrow(ans, Priority.ALWAYS);
            GridPane.setMargin(ans, new Insets(10));

            rightList.get(i).button = new ToggleButton(rightList.get(i).getTranslation()); //ToggleButton button =
            rightList.get(i).button.setMaxHeight(Double.MAX_VALUE);
            rightList.get(i).button.setMaxWidth(Double.MAX_VALUE);
            rightList.get(i).button.setToggleGroup(transGroup);
            GridPane.setHgrow(rightList.get(i).button, Priority.ALWAYS);
            GridPane.setVgrow(rightList.get(i).button, Priority.ALWAYS);
            GridPane.setMargin(rightList.get(i).button, new Insets(10));

//            table.add(leftList.get(i).button, 0, i);
//            table.add(ans, 1, i);
//            table.add(rightList.get(i).button, 2, i);
        }

        for (int i = 0; i < leftList.size(); ++i) {
            for (int j = 0; j < rightList.size(); ++j) {
                if (leftList.get(i).getId() == rightList.get(j).getId()) {
                    int finalI = i;
                    int finalJ = j;
                    ToggleButton origin = leftList.get(i).button, translation = rightList.get(j).button;
                    translation.selectedProperty().addListener((observableValue, oldValue, newValue) -> { // read documentation
                        if (newValue && origin.isSelected()) {
                            answers[finalI].setText(leftList.get(finalI).getOriginal() + " - " + rightList.get(finalJ).getTranslation());
                            rightList.get(finalJ).button.setText("");
                            leftList.get(finalI).button.setText("");
                        }
                    });
                    origin.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
                        if (newValue && translation.isSelected()) {
                            answers[finalI].setText(leftList.get(finalI).getOriginal() + " - " + rightList.get(finalJ).getTranslation());
                            rightList.get(finalJ).button.setText("");
                            leftList.get(finalI).button.setText("");
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void prepareToStart() {

    }

    @Override
    protected void prepareToFinish() {
    }

    @Override
    public void setPrefSize(double parentWidth, double parentHeight) {
    }
}
