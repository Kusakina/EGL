package egl.client.controller.task.category;

import egl.client.controller.task.AbstractTaskController;
import egl.client.model.topic.LocalTopic;
import egl.client.model.topic.category.Category;
import egl.client.model.topic.category.Translation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

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
    private TextField TextField21;

    @FXML
    private Text textWindow2_11;

    @FXML
    private TextField TextField22;

    @FXML
    private Text textWindow2_12;

    @FXML
    private TextField TextField23;

    @FXML
    private Text textWindow2_13;

    @FXML
    private TextField TextField24;

    @FXML
    private Text textWindow2_14;

    @FXML
    private TextField TextField25;

    @FXML
    private Text textWindow2_15;

    @FXML
    private TextField TextField26;

    @FXML
    private Text textWindow2_16;

    @FXML
    private TextField TextField27;

    @FXML
    private Text textWindow2_17;

    @FXML
    private TextField TextField28;

    @FXML
    private Text textWindow2_18;

    @FXML
    private Text textWindow2_21;

    @FXML
    private Text textWindow2_22;

    @FXML
    private Text textWindow2_23;

    @FXML
    private Text textWindow2_24;

    @FXML
    private Text textWindow2_25;

    @FXML
    private Text textWindow2_26;

    @FXML
    private Text textWindow2_27;

    @FXML
    private Text textWindow2_28;

    @FXML
    private Button CheckButton;

    @FXML
    private Label TextCorrect1;

    @FXML
    private Label TextCorrect2;

    @FXML
    private Text Correct1;

    @FXML
    private Text Correct2;

    @FXML
    private Text Correct3;

    @FXML
    private Text Correct4;

    @FXML
    private Text Correct5;

    @FXML
    private Text Correct6;

    @FXML
    private Text Correct7;

    @FXML
    private Text Correct8;

    @FXML
    private ProgressBar Progress;

    @FXML
    private Label ProgressLable;

    @FXML
    private ProgressIndicator ProgressCircle;
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

    void setGroup(ToggleGroup group, RadioButton[] buttons)
    {
        for (RadioButton button : buttons) {
            button.setToggleGroup(group);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ProgressLable.setVisible(false);
        Progress.setVisible(false);
        ProgressCircle.setVisible(false);
        Category category = (Category) controllerTopic;
        //List<Translation> translations = category.getTranslations();
       //Collections.shuffle(translations);
        //ReadWord a = new ReadWord();
        //a.setWords(a.reading("family.txt"));//все семейные слова теперь тут
        /*
        ReadWord exercise = new ReadWord(8);
        int[] index = new int[8];
        rand (exercise, index, a, -1);
        textWindow1.setText(translations);
        //textWindow1.setText(exercise.getWords()[0].getTranslation());
        //textWindow2.setText(exercise.getWords()[1].getTranslation());
        //textWindow3.setText(exercise.getWords()[2].getTranslation());
        //textWindow4.setText(exercise.getWords()[3].getTranslation());
        //textWindow5.setText(exercise.getWords()[4].getTranslation());
        //textWindow6.setText(exercise.getWords()[5].getTranslation());
        //textWindow7.setText(exercise.getWords()[6].getTranslation());
        //textWindow8.setText(exercise.getWords()[7].getTranslation());
        ReadWord[] answers = new ReadWord[8];
        int[] trueAnswers1 = new int[8];//запоминаем правильные ответы
        for (int i=0; i<index.length; ++i)
        {
            ReadWord answer = new ReadWord(4);
            int[] indexAnswer = new int[4];
            rand(answer, indexAnswer, a, index[i]);
            int k = (int) (Math.random() * 4);
            indexAnswer[k] = index[i];
            answer.createWord(k, a.getWords()[index[i]]);
            trueAnswers1[i] = k;
            answers[i] = new ReadWord(answer);
        }
        RadioButton[] mas1 = new RadioButton[]{RadioButton11, RadioButton12, RadioButton13, RadioButton14};
        RadioButton[] mas2 = new RadioButton[]{RadioButton21, RadioButton22, RadioButton23, RadioButton24};
        RadioButton[] mas3 = new RadioButton[]{RadioButton31, RadioButton32, RadioButton33, RadioButton34};
        RadioButton[] mas4 = new RadioButton[]{RadioButton41, RadioButton42, RadioButton43, RadioButton44};
        RadioButton[] mas5 = new RadioButton[]{RadioButton51, RadioButton52, RadioButton53, RadioButton54};
        RadioButton[] mas6 = new RadioButton[]{RadioButton61, RadioButton62, RadioButton63, RadioButton64};
        RadioButton[] mas7 = new RadioButton[]{RadioButton71, RadioButton72, RadioButton73, RadioButton74};
        RadioButton[] mas8 = new RadioButton[]{RadioButton81, RadioButton82, RadioButton83, RadioButton84};

        RadioButton[][] firstTask = new RadioButton[][]{mas1, mas2, mas3, mas4, mas5, mas6, mas7, mas8};

        for (int i = 0; i<8; ++i)
        {
            for (int j = 0; j<4; ++j)
            {
                firstTask[i][j].setText(answers[i].getWords()[j].getOriginal());
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

        ReadWord exercise2 = new ReadWord(8);
        int[] index2 = new int[8];
        rand(exercise2, index2, a, -1);

        textWindow2_21.setText(exercise2.getWords()[0].getOriginal());
        textWindow2_22.setText(exercise2.getWords()[1].getOriginal());
        textWindow2_23.setText(exercise2.getWords()[2].getOriginal());
        textWindow2_24.setText(exercise2.getWords()[3].getOriginal());
        textWindow2_25.setText(exercise2.getWords()[4].getOriginal());
        textWindow2_26.setText(exercise2.getWords()[5].getOriginal());
        textWindow2_27.setText(exercise2.getWords()[6].getOriginal());
        textWindow2_28.setText(exercise2.getWords()[7].getOriginal());

        int[] positions = new int[8];
        for (int i = 0; i<8; ++i)
        {
            int n = (int) (Math.random() * 8);
            int j = i;
            while (j>0)
            {
                --j;
                if (n == positions[j]) {
                    n = (int) (Math.random() * 8);
                    j = i;
                }
            }
            positions[i] = n;
        }

        textWindow2_11.setText(exercise2.getWords()[positions[0]].getTranslation());
        textWindow2_12.setText(exercise2.getWords()[positions[1]].getTranslation());
        textWindow2_13.setText(exercise2.getWords()[positions[2]].getTranslation());
        textWindow2_14.setText(exercise2.getWords()[positions[3]].getTranslation());
        textWindow2_15.setText(exercise2.getWords()[positions[4]].getTranslation());
        textWindow2_16.setText(exercise2.getWords()[positions[5]].getTranslation());
        textWindow2_17.setText(exercise2.getWords()[positions[6]].getTranslation());
        textWindow2_18.setText(exercise2.getWords()[positions[7]].getTranslation());

        UnaryOperator<TextFormatter.Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[1-8]*") && change.getControlNewText().length()<2) {
                return change;
            }
            return null;
        };
        TextField21.setTextFormatter(new TextFormatter<String>(integerFilter));
        TextField22.setTextFormatter(new TextFormatter<String>(integerFilter));
        TextField23.setTextFormatter(new TextFormatter<String>(integerFilter));
        TextField24.setTextFormatter(new TextFormatter<String>(integerFilter));
        TextField25.setTextFormatter(new TextFormatter<String>(integerFilter));
        TextField26.setTextFormatter(new TextFormatter<String>(integerFilter));
        TextField27.setTextFormatter(new TextFormatter<String>(integerFilter));
        TextField28.setTextFormatter(new TextFormatter<String>(integerFilter));

        CheckButton.setOnAction(actionEvent -> {

            CheckButton.setVisible(false);

            TextField21.setEditable(false);
            TextField22.setEditable(false);
            TextField23.setEditable(false);
            TextField24.setEditable(false);
            TextField25.setEditable(false);
            TextField26.setEditable(false);
            TextField27.setEditable(false);
            TextField28.setEditable(false);

            int correct = 0;
            for (int i=0; i<8; ++i)
            {
                firstTask[i][trueAnswers1[i]].setStyle("-fx-background-color: lime");
            }
            for (int i=0; i<8; ++i)
            {
                RadioButton selectedRadioButton = (RadioButton) toggleGroups[i].getSelectedToggle();
                if (selectedRadioButton != null && selectedRadioButton.getText().equals(answers[i].getWords()[trueAnswers1[i]].getOriginal()))
                    ++correct;
            }
            TextCorrect1.setText("Correct answers: " + correct + "/8");


            TextField[] fields = new TextField[]{TextField21, TextField22, TextField23, TextField24,
                    TextField25, TextField26, TextField27, TextField28};
            int[] personAnswers2 = new int[8];
            int correct2 = 0;
            for (int i=0; i<8; ++i)
            {
                try {
                    personAnswers2[i] = Integer.parseInt(fields[i].getText());
                }
                catch(NumberFormatException e)
                {
                    personAnswers2[i] = 0;
                }
                if (personAnswers2[i] == positions[i]+1) {
                    ++correct2;
                    fields[i].setStyle("-fx-background-color: lime");
                }
                else
                    fields[i].setStyle("-fx-background-color: red");
            }
            TextCorrect2.setText("Correct answers: " + correct2 + "/8");
            Correct1.setText("A. " + ++positions[0]);
            Correct2.setText("B. " + ++positions[1]);
            Correct3.setText("C. " + ++positions[2]);
            Correct4.setText("D. " + ++positions[3]);
            Correct5.setText("E. " + ++positions[4]);
            Correct6.setText("F. " + ++positions[5]);
            Correct7.setText("G. " + ++positions[6]);
            Correct8.setText("H. " + ++positions[7]);

            Progress.setVisible(true);
            ProgressLable.setVisible(true);
            ProgressCircle.setVisible(true);
            Progress.setProgress((double)(correct+correct2)/16);
            ProgressCircle.setProgress((double)(correct+correct2)/16);


        });

         */
    }



    @Override
    protected void prepareToStart() {


    }

    @Override
    protected void prepareToFinish() {

    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {

    }
}
