//package egl.client.controller.task.category;
//
//import egl.client.controller.task.AbstractTaskController;
//import egl.client.model.topic.category.Category;
//import egl.client.model.topic.category.Translation;
//import egl.client.model.topic.category.Word;
//import egl.core.model.task.Result;
//import egl.core.model.task.Task;
//import egl.core.model.topic.Topic;
//import javafx.application.Platform;
//import javafx.beans.value.ObservableValue;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.input.KeyCode;
//import javafx.scene.layout.ColumnConstraints;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//import javafx.util.Callback;
//import javafx.util.StringConverter;
//import net.rgielen.fxweaver.core.FxmlView;
//import org.springframework.stereotype.Component;
//
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.function.Consumer;
//@Component
//@FxmlView
//public class TestControllerTask4 extends AbstractTaskController {
//    int numTasks = 7;
//    String[] ansTrue = new String[numTasks];
//    String[] personAns = new String[numTasks];
//    String[] coutWord = new String[numTasks];
//    int[] editable = new int[numTasks];
//    TableView<Translation> table = new TableView<Translation>();
//    TableColumn<Translation, Word> WordCol = new TableColumn<>("Original");
//    TableColumn<Translation, Word> TranslationCol = new TableColumn<>("Translation");
//     @FXML
//    private VBox tasksVBox;
//    @Override
//    public void rescaleViews(double parentWidth, double parentHeight) {
//
//    }
//
//    @Override
//    protected void prepareToStart() {
//        table.setEditable(true);
//        WordCol.setEditable(true);
//        TranslationCol.setEditable(true);
//        Category category = (Category) controllerTopic;
//        List<Translation> tasks = category.getTranslations();
//        Collections.shuffle(tasks);
//        tasks.subList(0, numTasks - 1);
//        var vBoxChildren = tasksVBox.getChildren();
//        GridPane questionPane = new GridPane();
//        table.getColumns().addAll(WordCol, TranslationCol);
//        WordCol.setCellValueFactory(new PropertyValueFactory<Translation, Word>("original"));
//        TranslationCol.setCellValueFactory(new PropertyValueFactory<Translation, Word>("translation"));
//        table.getSelectionModel().setCellSelectionEnabled(true);
//        WordCol.setPrefWidth(300);
//        TranslationCol.setPrefWidth(300);
//        table.setMinHeight(500);
//        table.setFixedCellSize(60.5);
//
//        table.setStyle(
//                "-fx-font-weight:bold;" +
//                        " -fx-text-background-color: blue;" +
//                        "-fx-font-size:17px;"
//        );
//        //WordCol.setCellFactory(TextFieldTableCell.forTableColumn());
//        //TranslationCol.setCellFactory(TextFieldTableCell.forTableColumn());
//
//        table.getSelectionModel().selectedItemProperty().addListener( //чтобы редактировались только избранные ячейки
//                (observable, oldValue, newValue) -> checkEditable(table.getSelectionModel().getSelectedCells().get(0).getRow()));
//        for (int i = 0; i < numTasks; i++) {
//            int bool = (int) (Math.random() * 1.7);
//            editable[i] = bool;
//            if (bool == 0) {
//                ansTrue[i] = tasks.get(i).getSource().getText();
//                tasks.get(i).getSource().setText("");
//            }
//            else {
//                ansTrue[i] = tasks.get(i).getTarget().getText();
//                tasks.get(i).getTarget().setText("");
//            }
//            table.getItems().add(tasks.get(i));
//        }
//        questionPane.add(table, 0, 0);
//        //questionPane.getColumnConstraints().add(new ColumnConstraints(450));
//        vBoxChildren.add(questionPane);
//    }
//
//    @Override
//    protected void prepareToFinish() {
//
//    }
//    private void checkEditable(int i) {
//        if (table.isEditable()) { WordCol.setEditable(false);  TranslationCol.setEditable(false);}
//        if (editable[i] == 0) { WordCol.setEditable(true); }
//        if (editable[i] == 1) { TranslationCol.setEditable(true); }
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        WordCol.setOnEditCommit(event -> {
//
//        });
//    }
//}
