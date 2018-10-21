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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author HLC
 */

public class HangmanUIController implements Initializable {
    
    private final ObservableList<String> categories = FXCollections.observableArrayList(
        "Animals", "Sports", "Countries", "Languages"
    );
    
    private final String[][] categoriesData = {
        {"elephant", "bear", "tiger", "bass", "dolphin", "eagle", "shark", "snake", "giraffe", "llama", "leopard", "octopus", "pig"},
        {"baseball", "basketball", "football", "soccer", "lacrosse", "tennis", "golf", "volleyball", "boxing", "archery", "badminton"},
        {"america", "mexico", "canada", "brazil", "spain", "france", "china", "peru", "russia", "japan", "australia", "india", "chile"},
        {"english", "spanish", "swahili", "hindi", "french", "russian", "german", "chinese", "japanese", "arabic", "hebrew", "latin"}
    };
    
    String randomWord;
    
    int wins = 0;
    
    int losses = 0;
    
    int triesLeft = 6;

    @FXML
    private ChoiceBox category;
    
    @FXML
    private TextArea incorrectGuesses;
    
    @FXML
    private TextArea correctGuesses;
    
    @FXML
    private Label text;
    
    @FXML
    private Label winsLabel;
    
    @FXML
    private Label lossesLabel;
    
    @FXML
    private Label percentageLabel;
    
    @FXML
    private TextField guess;
    
    @FXML
    private ImageView hangman;
    
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
        triesLeft = 6;
        setImage();
    }
    
    public void guessEntered() {
        if (randomWord != null) {
            if (guess.getText().length() == 1 && isAlpha(guess.getText())) {
                if (incorrectGuesses.getText().contains(guess.getText()) || correctGuesses.getText().contains(guess.getText())) {
                    guess.setText("You've already guessed that");
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
                        int count = 0;

                        for (int i = 0; i < randomWord.length(); i++) {
                            if (correctGuesses.getText().contains(randomWord.substring(i, i + 1))) {
                                count++;
                            }
                        }

                        if (count == randomWord.length()) {
                            wins++;
                            updateStatus();
                        } 
                    } else {
                        triesLeft--;

                        if (triesLeft > 0) {
                            incorrectGuesses.appendText(guess.getText().toLowerCase() + ", ");
                        } else {
                            losses++;
                            String wordText = "";

                            for (int i = 0; i < randomWord.length(); i++) {
                                wordText += randomWord.charAt(i) + " ";
                            }
                            
                            updateStatus();
                            text.setText(wordText);
                        }
                        
                        setImage();
                    } 
                    
                    guess.clear();
                }
            } else {
                guess.setText("You must only enter a single a-z character");
            }
        } else {
            guess.setText("Select a category");
        }
    }
    
    public void setImage() {
        switch (triesLeft) {
            case 6:
                hangman.setImage(new Image (getClass().getResourceAsStream("Images/hangmanFull.png")));
                break;
            case 5:
                hangman.setImage(new Image (getClass().getResourceAsStream("Images/hangmanOneArm.png")));
                break;
            case 4:
                hangman.setImage(new Image (getClass().getResourceAsStream("Images/hangmanTwoLegs.png")));
                break;
            case 3:
                hangman.setImage(new Image (getClass().getResourceAsStream("Images/hangmanOneLeg.png")));
                break;
            case 2:
                hangman.setImage(new Image (getClass().getResourceAsStream("Images/hangmanTorso.png")));
                break;
            case 1:
                hangman.setImage(new Image (getClass().getResourceAsStream("Images/hangmanHead.png")));
                break;
            case 0:
                hangman.setImage(new Image (getClass().getResourceAsStream("Images/hangmanDead.png")));
                break;
        }
    }
    
    public void updateStatus() {
        winsLabel.setText("Wins: " + wins);
        lossesLabel.setText("Losses: " + losses);
        percentageLabel.setText("Win percentage: " + ((int)Math.round(wins/(wins + losses)) * 100));
    }
    
    public static boolean isAlpha(String str) {  
          try {  
            double d = Double.parseDouble(str);  
          }  
          catch(NumberFormatException nfe) {  
            return true;  
          }  
          
          return false;  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        category.setItems(categories);
        category.getSelectionModel().select(0);
    }    
}