package android.example.braintrain;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    //set up the buttons
    Button startButton, buttons0, buttons1, buttons2, buttons3, reset;

    //set up the textViews
    TextView countdown;
    TextView score;
    TextView question;
    TextView answerMessage;

    //Count down textview
    TextView cdTextView;
    TextView scTextView;

    ArrayList<Integer> answers = new ArrayList<Integer>();

    //these are the random values that make up the question i.e. 5 + 3
   // int q1, q2;
    int gamescore;
    int numberOfQuestions = 0;
    int qAns;
    int q1, q2;

    int scoreAmount;
    Random rand = new Random();

    //this store an integer of the correct answers location
    int locationOfCorrectAns;

   // Boolean gameState = true;
   // Boolean gameState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      //  scoreAmount = 0;
        }


    public void resetViews() {

        //set up the textviews here
        //set up the score and countdown
        cdTextView = findViewById(R.id.cdTextView); cdTextView.setVisibility(View.VISIBLE);
        countdown = findViewById(R.id.countDown); countdown.setVisibility(View.VISIBLE);
        //score view
        scTextView = findViewById(R.id.scTextView); scTextView.setVisibility(View.VISIBLE);

        scoreAmount = 0;
        score = findViewById(R.id.score); score.setVisibility(View.VISIBLE);
        score.setText(scoreAmount + "");
        //question view
        question = findViewById(R.id.question); question.setVisibility(View.VISIBLE);
        //set up the Game Button variables
        startButton = findViewById(R.id.gamebutton); startButton.setVisibility(View.INVISIBLE);

        buttons0 = findViewById(R.id.btn1); buttons0.setVisibility(View.VISIBLE);
        buttons1 = findViewById(R.id.btn2); buttons1.setVisibility(View.VISIBLE);
        buttons2 = findViewById(R.id.btn3); buttons2.setVisibility(View.VISIBLE);
        buttons3 = findViewById(R.id.btn4); buttons3.setVisibility(View.VISIBLE);

        answerMessage = findViewById(R.id.ansView);


        gameEngine();
        countDown();

    }


    //game specific method
    public void gameStart(View view) {

        resetViews();
    }


    //create a function for the pressed answers buttons.
    //use the selected view to access the Tag of the selected location

        public void checkAnswer(View view) {
       // Log.i("Tag location", " " + view.getTag().toString());

            if (Integer.toString(locationOfCorrectAns).equals(view.getTag().toString())) {

                answerMessage.setText(getString(R.string.correctansmessage));
                scoreAmount++;
                numberOfQuestions++;
                gamescore = scoreAmount;

                Log.i("GAME SCORE", gamescore + "");

                score.setText((scoreAmount + "/" + numberOfQuestions));
                //also increase the score

                //Toast message for correct answers.
                android.widget.Toast.makeText(this, getString(R.string.correcttoast), Toast.LENGTH_SHORT).show();
                gameEngine();
            }
            else
                {

                numberOfQuestions++;
                answerMessage.setText(getString(R.string.incorrectansmessage));
                android.widget.Toast.makeText(this, getString(R.string.incorrecttoast), Toast.LENGTH_SHORT).show();

               Log.i("THIS IS CORRECT", "" + locationOfCorrectAns);

               view.getTag();

                    score.setText((Integer.toString(scoreAmount) + "/" + Integer.toString(numberOfQuestions)));

               gameEngine();
            }
        }


    public void gameEngine() {
            //generate 2no random numbers, which are shown in the question TextView
             q1 = rand.nextInt(21);
             q2 = rand.nextInt(21);

            //create an integer to store the answer from the random numbers
               qAns = q1 + q2;
             question.setText(q1 + " + " + q2);

            //first we create a random number for the location of the correct answer.
            locationOfCorrectAns = rand.nextInt(4);
            Log.i("VALUE OF RANDOM ITEM", locationOfCorrectAns + "");

            answers.clear();
            //Log.i("The random number is", "" + locationOfCorrectAns);

            for (int i = 0; i < 4; i++) {

                //now we use an if statement to check if the position i is the same as the location of ans
                if (i == locationOfCorrectAns) {

                    answers.add(i, qAns); // we need to find the position of the random number, then store our qAns there.
                } else {

                    //since the position i is not the same as the random locationAns do this
                    int wrongAnswer = rand.nextInt(41);

                    //this checks, while the answers are the same as the displayed answer we need something different.
                    while (wrongAnswer == qAns) {
                        wrongAnswer = rand.nextInt(41);
                    }
                    answers.add(wrongAnswer);
                }
            }

            buttons0.setText(String.valueOf(answers.get(0)));
            buttons1.setText(String.valueOf(answers.get(1)));
            buttons2.setText(String.valueOf(answers.get(2)));
            buttons3.setText(String.valueOf(answers.get(3)));

            qAns = 0;  //this is to reset the main question on each try

        }


        public void countDown() {
            new CountDownTimer(15000, 1000) { //each question is given 5secs.
                @Override
                public void onTick(long l) {

                    countdown.setText(String.valueOf(l / 1000) + " s");
                    Log.i("Seconds Left", String.valueOf(l));
                }
                @Override
                public void onFinish() {

                    buttons0.setEnabled(false);
                    buttons1.setEnabled(false);
                    buttons2.setEnabled(false);
                    buttons3.setEnabled(false);

                    answerMessage.setText(R.string.gameend);

                    reset = findViewById(R.id.reset);
                    reset.setVisibility(View.VISIBLE);
                }
            }.start();
        }

        public void resetGame(View view) {


           reset.setVisibility(View.INVISIBLE);

            scoreAmount = 0; //reset score to zero for the game restart
            numberOfQuestions = 0; // we also need to set number of questions back to zero

            buttons0.setEnabled(true);
            buttons1.setEnabled(true);
            buttons2.setEnabled(true);
            buttons3.setEnabled(true);

            answerMessage.setVisibility(View.INVISIBLE);
            resetViews();
        }
}