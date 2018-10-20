package hangman;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author HLC
 */

public class HangmanUIController implements Initializable {
    
    private final ObservableList<String> categories = FXCollections.observableArrayList(
        "Animals", "Sports"
    );
    
    private final String[][] categoriesData = {
        {"elephant", "bear"},
        {"baseball", "basketball", "football"}
    };
    
    String randomWord;
    
    int wins = 0;
    
    int losses = 0;

    @FXML
    private ChoiceBox category;
    
    @FXML
    private TextArea incorrectGuesses;
    
    @FXML
    private TextArea correctGuesses;
    
    @FXML
    private Label text;
    
    @FXML
    private TextField guess;
    
    public void categoryPicked() {
        int selectedItem = category.getSelectionModel().getSelectedIndex();
        int randomItem = (int) (Math.random() * categoriesData[selectedItem].length);
        randomWord = categoriesData[selectedItem][randomItem];
        String wordText = "";
        
        for (int i = 0; i < randomWord.length(); i++) {
            wordText += "_ ";
        }
        
        text.setText(wordText);
        incorrectGuesses.clear();
        correctGuesses.clear();
        guess.clear();
    }
    
    public void guessEntered() {
        if (randomWord.equals(guess.getText().toLowerCase())) {
            wins++;
        } else {
            if (randomWord.contains(guess.getText().toLowerCase())) {
                correctGuesses.appendText(guess.getText().toLowerCase() + ", ");
                String wordText = "";

                for (int i = 0; i < randomWord.length(); i++) {
                    if (correctGuesses.getText().contains(randomWord.substring(i, i + 1))) {
                        wordText += randomWord.charAt(i) + " ";
                    } else {
                        wordText += "_ ";
                    }
                }

                text.setText(wordText);
            } else {
                incorrectGuesses.appendText(guess.getText().toLowerCase() + ", ");
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        category.setItems(categories);
        category.getSelectionModel().select(0);
    }    
    
}
