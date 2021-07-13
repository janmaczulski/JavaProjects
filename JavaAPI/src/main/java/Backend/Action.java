package Backend;

import Forms.FirstQuestion;
import Forms.MainForm;
import Forms.SecondQuestion;
import Forms.ThirdQuestion;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.java.Log;
import org.json.JSONObject;

import java.util.Locale;
import java.util.ResourceBundle;

@Log
public class Action {
    private boolean isPolish = true;
    private MainForm mainForm;
    private FirstQuestion firstQuestion = new FirstQuestion();
    private SecondQuestion secondQuestion =  new SecondQuestion();
    private ThirdQuestion thirdQuestion = new ThirdQuestion();
    ResourceBundle bundleDefault;
//   static ResourceBundle bundleEn = ResourceBundle.getBundle("Bundle", new Locale("en"));


    public void control ( ) {
        openMainForm();
        mainForm.getQuestion1Button().addActionListener(e -> onClickButtonQuestionFirstButton());
        mainForm.getQuestion2Button().addActionListener(e -> onClickButtonQuestionSecondButton());
        mainForm.getQuestion3Button().addActionListener(e -> onClickButtonQuestionThirdButton());
        mainForm.getLanguageButton().addActionListener(e -> onClickButtonLanguageButton());
        firstQuestion.getCheckAnswerButton().addActionListener(e -> onClickButtonFirstQuestionCheckAnswerButton());
        secondQuestion.getCheckAnswerButton().addActionListener(e -> onClickButtonSecondQuestionCheckAnswerButton());
        thirdQuestion.getCheckAnswerButton().addActionListener(e -> onClickButtonThirdQuestionCheckAnswerButton());
    }



    private void openMainForm(){
        if (mainForm == null || (mainForm != null && !mainForm.getFrameMainForm().isDisplayable())) {
            mainForm = new MainForm();
            mainForm.getFrameMainForm().setVisible(true);
        } else
            mainForm.getFrameMainForm().toFront();
    }

    private void openFirstQuestion() {
        if ((this.firstQuestion != null && !this.firstQuestion.getFrameFirstQuestion().isVisible())) {
            //this.firstQuestion = new FirstQuestion();
            mainForm.getFrameMainForm().setVisible(false);
            if (isPolish == false){
                bundleDefault = ResourceBundle.getBundle("resources",new Locale("en"));
                //isPolish = true;
            } else {
                bundleDefault = ResourceBundle.getBundle("resources");
                //isPolish = false;
            }
            this.firstQuestion.getLabel().setText(bundleDefault.getString("question1question"));
            this.firstQuestion.getComboBox1().addItem(bundleDefault.getString("question1country1"));
            this.firstQuestion.getComboBox1().addItem(bundleDefault.getString("question1country2"));
            this.firstQuestion.getComboBox1().addItem(bundleDefault.getString("question1country3"));
            this.firstQuestion.getUserAnswerLabel().setText(bundleDefault.getString("answer"));
            this.firstQuestion.getAnswerLabel().setText(bundleDefault.getString("rightAnswer"));
            this.firstQuestion.getCheckAnswerButton().setText(bundleDefault.getString("checkAnswerButton"));
            this.firstQuestion.getFrameFirstQuestion().setVisible(true);
        } else
            this.firstQuestion.getFrameFirstQuestion().toFront();
    }

    private void openSecondQuestion() {
        if (this.secondQuestion != null && !this.secondQuestion.getFrame().isVisible()) {
            // this.secondQuestion = new SecondQuestion();
            mainForm.getFrameMainForm().setVisible(false);
            if (isPolish == false){
                bundleDefault = ResourceBundle.getBundle("resources",new Locale("en"));
                //isPolish = true;
            } else {
                bundleDefault = ResourceBundle.getBundle("resources");
                //isPolish = false;
            }
            this.secondQuestion.getLabel().setText(bundleDefault.getString("question2question"));
            this.secondQuestion.getComboBox1().addItem(bundleDefault.getString("question2country1"));
            this.secondQuestion.getComboBox1().addItem(bundleDefault.getString("question2country2"));
            this.secondQuestion.getComboBox1().addItem(bundleDefault.getString("question2country3"));
            this.secondQuestion.getUserAnswerLabel().setText(bundleDefault.getString("answer"));
            this.secondQuestion.getAnswerLabel().setText(bundleDefault.getString("rightAnswer"));
            this.secondQuestion.getCheckAnswerButton().setText(bundleDefault.getString("checkAnswerButton"));
            this.secondQuestion.getFrame().setVisible(true);
        } else
            this.secondQuestion.getFrame().toFront();
    }

    private void openThirdQuestion() {
        if (this.thirdQuestion != null && !this.thirdQuestion.getFrame().isVisible()) {
            // this.thirdQuestion = new ThirdQuestion();
            mainForm.getFrameMainForm().setVisible(false);
            if (isPolish == false){
                bundleDefault = ResourceBundle.getBundle("resources",new Locale("en"));
                //isPolish = true;
            } else {
                bundleDefault = ResourceBundle.getBundle("resources");
                //isPolish = false;
            }
            this.thirdQuestion.getQuestionLabel().setText(bundleDefault.getString("question3question"));
            //this.thirdQuestion.getAnswerLabel().setText(bundleDefault.getString("question3question"));
            this.thirdQuestion.getComboBox1().addItem(bundleDefault.getString("question2country1"));
            this.thirdQuestion.getComboBox1().addItem(bundleDefault.getString("question2country2"));
            this.thirdQuestion.getComboBox1().addItem(bundleDefault.getString("question2country3"));
            this.thirdQuestion.getUserAnswerLabel().setText(bundleDefault.getString("answer"));
            this.thirdQuestion.getAnswerLabel().setText(bundleDefault.getString("rightAnswer"));
            this.thirdQuestion.getCheckAnswerButton().setText(bundleDefault.getString("checkAnswerButton"));
            this.thirdQuestion.getFrame().setVisible(true);
        } else
            this.thirdQuestion.getFrame().toFront();
    }
    private void executeRestCallFirstQuestion() {
        if (isPolish == false){
            bundleDefault = ResourceBundle.getBundle("resources",new Locale("en"));
            //isPolish = true;
        } else {
            bundleDefault = ResourceBundle.getBundle("resources");
            //isPolish = false;
        }
        try {
            String countryID = retrieveCountryID((String) firstQuestion.getComboBox1().getSelectedItem(), "Q36");
            HttpResponse<JsonNode> responseCity = Unirest.get("https://wft-geo-db.p.rapidapi.com/v1/geo/cities")
                    .header("x-rapidapi-key", "c94b07d5fcmsh192324421c65896p1d93d1jsna064f463a452")
                    .header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
                    .queryString("countryIds", countryID)
                    .queryString("minPopulation", Integer.valueOf(firstQuestion.getTextField1().getText()))
                    .asJson();
            String countOfCities = String.valueOf((((JSONObject)responseCity.getBody().getArray().get(0)).getJSONObject("metadata")).get("totalCount"));
            if (countOfCities.equals(firstQuestion.getUserAnswerTextField().getText()))
                firstQuestion.getAnswerLabel().setText(countOfCities + bundleDefault.getString("answerCorrect"));
             else
                 firstQuestion.getAnswerLabel().setText(countOfCities + bundleDefault.getString("answerIncorrect"));
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    private void executeRestCallSeconQuestion() {
        if (isPolish == false){
            bundleDefault = ResourceBundle.getBundle("resources",new Locale("en"));
            //isPolish = true;
        } else {
            bundleDefault = ResourceBundle.getBundle("resources");
            //isPolish = false;
        }
        try {
            String countryID = retrieveCountryID((String) secondQuestion.getComboBox1().getSelectedItem(), "Q36");
            HttpResponse<JsonNode> responseCity = Unirest.get("https://wft-geo-db.p.rapidapi.com/v1/geo/cities")
                    .header("x-rapidapi-key", "c94b07d5fcmsh192324421c65896p1d93d1jsna064f463a452")
                    .header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
                    .queryString("countryIds", countryID)
                    .queryString("minPopulation", Integer.valueOf(secondQuestion.getTextField1().getText()))
                    .asJson();
            Thread.sleep(2000L);
            HttpResponse<JsonNode> responseCitySum = Unirest.get("https://wft-geo-db.p.rapidapi.com/v1/geo/cities")
                    .header("x-rapidapi-key", "c94b07d5fcmsh192324421c65896p1d93d1jsna064f463a452")
                    .header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
                    .queryString("countryIds", countryID)
                    .asJson();
            Integer countOfCities = (Integer)((JSONObject)responseCity.getBody().getArray().get(0)).getJSONObject("metadata").get("totalCount");
            Integer sumOfCities = (Integer) ((JSONObject)responseCitySum.getBody().getArray().get(0)).getJSONObject("metadata").get("totalCount");


            String result = String.valueOf(sumOfCities - countOfCities);
            if (result.equals(secondQuestion.getUserAnswerTextField().getText()))
                secondQuestion.getAnswerLabel1().setText(result + bundleDefault.getString("answerCorrect"));
            else
                secondQuestion.getAnswerLabel1().setText(result + bundleDefault.getString("answerIncorrect"));
        } catch (UnirestException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void executeRestCallThirdQuestion() {
        if (isPolish == false){
            bundleDefault = ResourceBundle.getBundle("resources",new Locale("en"));
            //isPolish = true;
        } else {
            bundleDefault = ResourceBundle.getBundle("resources");
            //isPolish = false;
        }
        try {
            String countryID = retrieveCountryID((String) thirdQuestion.getComboBox1().getSelectedItem(), "Q36");
            HttpResponse<JsonNode> responseCityLess = Unirest.get("https://wft-geo-db.p.rapidapi.com/v1/geo/cities")
                    .header("x-rapidapi-key", "c94b07d5fcmsh192324421c65896p1d93d1jsna064f463a452")
                    .header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
                    .queryString("countryIds", countryID)
                    .queryString("minPopulation", Integer.valueOf(thirdQuestion.getTextFieldLess().getText()))
                    .asJson();
            Thread.sleep(2000L);
            HttpResponse<JsonNode> responseCityMore = Unirest.get("https://wft-geo-db.p.rapidapi.com/v1/geo/cities")
                    .header("x-rapidapi-key", "c94b07d5fcmsh192324421c65896p1d93d1jsna064f463a452")
                    .header("x-rapidapi-host", "wft-geo-db.p.rapidapi.com")
                    .queryString("countryIds", countryID)
                    .queryString("minPopulation", Integer.valueOf(thirdQuestion.getTextFieldMore().getText()))
                    .asJson();
            Integer less = (Integer)((JSONObject)responseCityLess.getBody().getArray().get(0)).getJSONObject("metadata").get("totalCount");
            Integer more = (Integer) ((JSONObject)responseCityMore.getBody().getArray().get(0)).getJSONObject("metadata").get("totalCount");


            String result = String.valueOf((less - more) * -1);
            if (result.equals(thirdQuestion.getUserAnswerTextField().getText()))
                thirdQuestion.getAnswerLabel1().setText(result + bundleDefault.getString("answerCorrect"));
            else
                thirdQuestion.getAnswerLabel1().setText(result + bundleDefault.getString("answerIncorrect"));
        } catch (UnirestException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String retrieveCountryID(String country, String defaultValue) {
        String countryID = defaultValue;
        ResourceBundle bundleDefault = isPolish ? ResourceBundle.getBundle("resources")
                : ResourceBundle.getBundle("resources",new Locale("en"));
        if (bundleDefault.getString("question1country1").equals(country)){
            countryID = "Q36";
        } else if (bundleDefault.getString("question1country2").equals(country)) {
            countryID = "Q38";
        } else if (bundleDefault.getString("question1country3").equals(country)) {
            countryID = "Q184";
        } else if (bundleDefault.getString("question2country1").equals(country)){
            countryID = "Q155";
        } else if (bundleDefault.getString("question2country2").equals(country)) {
            countryID = "Q142";
        } else if (bundleDefault.getString("question2country3").equals(country)) {
            countryID = "Q145";
        }  else if (bundleDefault.getString("question3country1").equals(country)){
            countryID = "Q155";
        } else if (bundleDefault.getString("question3country2").equals(country)) {
            countryID = "Q142";
        } else if (bundleDefault.getString("question3country3").equals(country)) {
            countryID = "Q145";
        }
        return countryID;
    }

    private void onClickButtonQuestionFirstButton() {
        openFirstQuestion();
    }

    private void onClickButtonQuestionSecondButton() {
        openSecondQuestion();
    }

    private void onClickButtonQuestionThirdButton() {
        openThirdQuestion();
    }

    private void onClickButtonFirstQuestionCheckAnswerButton() {
        executeRestCallFirstQuestion();
    }

    private void onClickButtonSecondQuestionCheckAnswerButton() {
        executeRestCallSeconQuestion();
    }

    private void onClickButtonThirdQuestionCheckAnswerButton() {
        executeRestCallThirdQuestion();
    }


    public void onClickButtonLanguageButton() {
        if (isPolish == true){
            bundleDefault = ResourceBundle.getBundle("resources",new Locale("en"));
            isPolish = false;
        } else {
            bundleDefault = ResourceBundle.getBundle("resources");
            isPolish = true;
        }
        mainForm.getQuestion1Button().setText(bundleDefault.getString("q1"));
        mainForm.getQuestion2Button().setText(bundleDefault.getString("q2"));
        mainForm.getQuestion3Button().setText(bundleDefault.getString("q3"));
        mainForm.getLanguageButton().setText(bundleDefault.getString("l"));
    }
}
