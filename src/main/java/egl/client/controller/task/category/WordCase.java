package egl.client.controller.task.category;

import javafx.scene.control.ToggleButton;

public class WordCase {
    private String original;
    private String translation;
    private int id;
    public ToggleButton button;

    public WordCase(String original, String translation, int id) {
        this.original = original;
        this.translation = translation;
        this.id = id;
        this.button = new ToggleButton();
    }

    public String getOriginal() { return original; }
    public String getTranslation() { return translation; }

    public int getId() {
        return id;
    }
}
