package egl.client.controller.task.category;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class TestControllerTask1 extends AbstractTaskController {

    @FXML
    private Text textWindow1;

    @FXML
    private RadioButton RadioButton11;

    @FXML
    private RadioButton RadioButton12;

    @FXML
    private RadioButton RadioButton13;

    @FXML
    private RadioButton RadioButton14;

    @FXML
    private Text textWindow2;

    @FXML
    private RadioButton RadioButton21;

    @FXML
    private RadioButton RadioButton22;

    @FXML
    private RadioButton RadioButton23;

    @FXML
    private RadioButton RadioButton24;

    @FXML
    private Text textWindow3;

    @FXML
    private RadioButton RadioButton31;

    @FXML
    private RadioButton RadioButton32;

    @FXML
    private RadioButton RadioButton33;

    @FXML
    private RadioButton RadioButton34;

    @FXML
    private Text textWindow4;

    @FXML
    private RadioButton RadioButton41;

    @FXML
    private RadioButton RadioButton42;

    @FXML
    private RadioButton RadioButton43;

    @FXML
    private RadioButton RadioButton44;

    @FXML
    private Text textWindow5;

    @FXML
    private RadioButton RadioButton51;

    @FXML
    private RadioButton RadioButton52;

    @FXML
    private RadioButton RadioButton53;

    @FXML
    private RadioButton RadioButton54;

    @FXML
    private Text textWindow6;

    @FXML
    private RadioButton RadioButton61;

    @FXML
    private RadioButton RadioButton62;

    @FXML
    private RadioButton RadioButton63;

    @FXML
    private RadioButton RadioButton64;

    @FXML
    private Text textWindow7;

    @FXML
    private RadioButton RadioButton71;

    @FXML
    private RadioButton RadioButton72;

    @FXML
    private RadioButton RadioButton73;

    @FXML
    private RadioButton RadioButton74;

    @FXML
    private Text textWindow8;

    @FXML
    private RadioButton RadioButton81;

    @FXML
    private RadioButton RadioButton82;

    @FXML
    private RadioButton RadioButton83;

    @FXML
    private RadioButton RadioButton84;

    @FXML
    private ProgressBar Progress;

    @FXML
    private Label ProgressLable;

    @FXML
    private ProgressIndicator ProgressCircle;

    @FXML
    private VBox tasksVBox;
/*
    void rand(ReadWord exercise, int[] index, ReadWord a, int p)
    {
        for (int i=0; i< index.length; i++)
        {
            int n = (int) (Math.random() * a.getWords().length);
            int j = i;
            while (i==0 && n==p)
                n = (int) (Math.random() * a.getWords().length);
            while (j>0)
            {
                --j;
                if (n == index[j] || n == p) {
                    n = (int) (Math.random() * a.getWords().length);
                    j = i;
                }
            }
            index[i] = n;
            exercise.createWord(i, a.getWords()[n]);
        }
    }

 */

    void setGroup(ToggleGroup group, RadioButton[] buttons) {
        for (RadioButton button : buttons) {
            button.setToggleGroup(group);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void prepareTasks() {
        ProgressLable.setVisible(false);
        Progress.setVisible(false);
        ProgressCircle.setVisible(false);
        Category category = (Category) controllerTopic;
        List<Translation> translations = category.getTranslations();
        Collections.shuffle(translations);
        //ReadWord a = new ReadWord();
        //a.setWords(a.reading("family.txt"));//все семейные слова теперь тут

//        ReadWord exercise = new ReadWord(8);
//        int[] index = new int[8];
//        rand (exercise, index, a, -1);
//        textWindow1.setText(translations);
//
//        ReadWord.Word[] words = exercise.getWords();

        Text[] textWindows = { textWindow1, textWindow2, textWindow3, textWindow4, textWindow5, textWindow6, textWindow7, textWindow8 };

        int size = Math.min(textWindows.length, translations.size());

        for (int i = 0; i < size; ++i) {
            textWindows[i].setText(translations.get(i).getSource().getText());
        }

        //ReadWord[] answers = new ReadWord[8];
        int[] trueAnswers1 = new int[size];//запоминаем правильные ответы
        /*for (int i=0; i<index.length; ++i)
        {
            ReadWord answer = new ReadWord(4);
            int[] indexAnswer = new int[4];
            rand(answer, indexAnswer, a, index[i]);
            int k = (int) (Math.random() * 4);
            indexAnswer[k] = index[i];
            answer.createWord(k, a.getWords()[index[i]]);
            trueAnswers1[i] = k;
            answers[i] = new ReadWord(answer);
        }*/
        RadioButton[] mas1 = new RadioButton[]{RadioButton11, RadioButton12, RadioButton13, RadioButton14};
        RadioButton[] mas2 = new RadioButton[]{RadioButton21, RadioButton22, RadioButton23, RadioButton24};
        RadioButton[] mas3 = new RadioButton[]{RadioButton31, RadioButton32, RadioButton33, RadioButton34};
        RadioButton[] mas4 = new RadioButton[]{RadioButton41, RadioButton42, RadioButton43, RadioButton44};
        RadioButton[] mas5 = new RadioButton[]{RadioButton51, RadioButton52, RadioButton53, RadioButton54};
        RadioButton[] mas6 = new RadioButton[]{RadioButton61, RadioButton62, RadioButton63, RadioButton64};
        RadioButton[] mas7 = new RadioButton[]{RadioButton71, RadioButton72, RadioButton73, RadioButton74};
        RadioButton[] mas8 = new RadioButton[]{RadioButton81, RadioButton82, RadioButton83, RadioButton84};


        RadioButton[][] firstTask = new RadioButton[][]{mas1, mas2, mas3, mas4, mas5, mas6, mas7, mas8};

        for (int i = 0; i<size; ++i)
        {
            for (int j = 0; j<4; ++j)
            {
                firstTask[i][j].setText(translations.get((i + j) % translations.size()).getTarget().getText());
            }
        }

        ToggleGroup testFirst = new ToggleGroup();
        ToggleGroup testSecond = new ToggleGroup();
        ToggleGroup testThird = new ToggleGroup();
        ToggleGroup testForth = new ToggleGroup();
        ToggleGroup testFifth = new ToggleGroup();
        ToggleGroup testSixth = new ToggleGroup();
        ToggleGroup testSeventh = new ToggleGroup();
        ToggleGroup testEighth = new ToggleGroup();


        ToggleGroup[] toggleGroups = new ToggleGroup[]{testFirst,testSecond, testThird,
                testForth, testFifth, testSixth, testSeventh, testEighth};

        for (int i = 0; i< toggleGroups.length; ++i)
        {
            setGroup(toggleGroups[i], firstTask[i]);
        }
    }


    @Override
    protected void prepareToStart() {
        prepareTasks();
    }

    @Override
    protected void prepareToFinish() {

    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {

    }
}
