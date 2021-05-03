package egl.client.controller.task.category;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import egl.client.controller.task.AbstractTaskController;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView
public class MissingLettersTaskController extends AbstractTaskController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text taskEn;

    @FXML
    private Text taskRu;

    @FXML
    private Text transword;

    @FXML
    private HBox word;

    @FXML
    private HBox Letters;

    @FXML
    private HBox CheckHbox;
    @FXML
   /* void initialize() {
        letter1.setOnAction(event ->{
          System.out.println("Жопа");
        } );
    }*/
    private void initializeButtons() {
        //пусть всегда будет букв = кол-во пропуск+2

    }



    private void updateWord() {


    }
    Random random = new Random();
    int cur;
    ArrayList<String> Enword;
    ArrayList<String> Ruword;
    void initalizeWords() {
        Enword = new ArrayList<>(
                Arrays.asList("RED", "YELLOW", "BLUE", "GREEN", "PINK", "WHITE", "BLACK")
        );

        Ruword = new ArrayList<>(
                Arrays.asList("КРАСНЫЙ", "ЖЕЛТЫЙ", "СИНИЙ","ЗЕЛЕНЫЙ","РОЗОВЫЙ","БЕЛЫЙ","ЧЕРНЫЙ")
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);
        initalizeWords();
        initalizeRandomWord();
    }

    void initalizeRandomWord(){
        //создание 2 списков англ + перевод имеют один индекс

        /*Enword.add("YELLOW");
        Ruword.add("ЖЕЛТЫЙ");
        Enword.add("RED");
        Ruword.add("КРАСНЫЙ");
        Enword.add("BLUE");
        Ruword.add("СИНИЙ");
        Enword.add("GREEN");
        Ruword.add("ЗЕЛЕНЫЙ");
        Enword.add("PINK");
        Ruword.add("РОЗОВЫЙ");*/

        if (Enword.isEmpty()){
            initalizeWords();
        }
        int random_number = random.nextInt(Enword.size());
        //получение слова с рандомным номером
        String randEnWord = Enword.remove(random_number);
        String randRuWord = Ruword.remove(random_number);
        transword.setText(randRuWord);

        //запись слова по буквам в список
        List<Integer> removedPositions = new ArrayList<>();
        for (int index = 0; index < randEnWord.length(); ++index)
            removedPositions.add(index);
        Collections.shuffle(removedPositions, random);

        //выбор букв для исключения
        int needRemove = randEnWord.length() / 2;
        removedPositions = removedPositions.subList(0, needRemove);
        Collections.sort(removedPositions);

        //добавление текстфилдов
        TextField[] letterFields = new TextField[randEnWord.length()];

        for (int index = 0; index < randEnWord.length(); ++index) {
            TextField letterField = new TextField("" + randEnWord.charAt(index));
            // настройку шрифта и других штук логичнее всего делать тут, если она нужна
            letterFields[index] = letterField;
            letterFields[index].setFont(Font.font(24));
            letterFields[index].setEditable(false);
            letterFields[index].setPrefSize(50, 50);
            //letterFields[index].setStyle("-fx-background-color: #8FC0C0;");
        }

        //исключение выбранных букв
        for (int index : removedPositions) {
            letterFields[index].setText("");
            //letterFields[index].setStyle("-fx-background-color: #3FC0C0;");

            // дополнительная настройка филда с пропуском, если нужно
        }
        //настройки HBox
        word.getChildren().clear();
        word.getChildren().addAll(letterFields);
        word.setSpacing(15);
        word.setAlignment(Pos.TOP_CENTER);

        //генерация букв для кнопок
        List<Character> buttonLetters = new ArrayList<>();
        for (int index : removedPositions) buttonLetters.add(randEnWord.charAt(index));

        int needRandom = 3;
        for (int i = 0; i < needRandom; ++i) {
            int letterIndex = random.nextInt(26);
            char letter = (char)('A' + letterIndex);
            if (!buttonLetters.contains(letter)) {
                buttonLetters.add(letter);
            } else {
                --i;
            }
        }
        Collections.shuffle(buttonLetters, random);

        //генерация кнопок с буквами
        Button[] letterButtons = new Button[buttonLetters.size()];
        for(int i = 0; i<buttonLetters.size(); ++i){
            Button let = new Button(""+buttonLetters.get(i));
            let.setFont(Font.font(24));
            let.setPrefSize(60, 50);
            letterButtons[i] = let;
        }

        //настройки HBox
        Letters.getChildren().clear();
        Letters.getChildren().addAll(letterButtons);
        Letters.setSpacing(20);
        Letters.setAlignment(Pos.BOTTOM_CENTER);


        List<Integer> finalRemovedPositions = removedPositions;

        cur=0;
        for (Button button:letterButtons) {
            button.setOnAction(event -> {
                //если кур = сайз ретерн
                if (cur == finalRemovedPositions.size()){
                    return;
                }
                int curRemoved = finalRemovedPositions.get(cur);
                letterFields[curRemoved].setText("" + button.getText());
                cur++;

                // в кнопку



            });
        }
        Button check = new Button(":)");
        check.setFont(Font.font(20));
        check.setPrefSize(60, 40);

        Letters.getChildren().add(check);
        check.setOnAction(event2 -> {
            if (cur != finalRemovedPositions.size()) {
                return;
            }
            StringBuilder ans = new StringBuilder();
            for (TextField letter : letterFields) {
                ans.append(letter.getText());
            }
            if (randEnWord.equals(ans.toString())) {
                initalizeRandomWord();
            } else {
                cur = 0;
                for (int index : finalRemovedPositions) {
                    letterFields[index].setText("");

                }

            }
        });





    }

    @Override
    public void rescaleViews(double parentWidth, double parentHeight) {

    }

    @Override
    protected void prepareToStart() {

    }

    @Override
    protected void prepareToFinish() {

    }
}

