package com.example.calculator;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    Button[] Numbers;
    Button[] Operator;
    Button dot,allClear, equal,bracket;
    ImageButton back;
    View divider;
    LinearLayout masterLayout;
    TextView Solution,resultTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        divider = findViewById(R.id.divider);

        masterLayout = findViewById(R.id.masterLayout);
        Solution = findViewById(R.id.Solution);
        resultTv = findViewById(R.id.resultTv);
        allClear = findViewById(R.id.allClear);
        back = findViewById(R.id.back);
        bracket = findViewById(R.id.bracket);
        dot = findViewById(R.id.dot);
        equal = findViewById(R.id.equal);

        bracket.setBackgroundResource(R.drawable.ripple_effect);
        dot.setBackgroundResource(R.drawable.ripple_effect);
        allClear.setBackgroundResource(R.drawable.ripple_effect);
        back.setBackgroundResource(R.drawable.ripple_effect);


        Numbers = new Button[10];
        for (int i = 0; i <= 9; i++) {
            @SuppressLint("DiscouragedApi") int buttonId = getResources().getIdentifier("button" + i, "id", getPackageName());
            Numbers[i] = findViewById(buttonId);
            Numbers[i].setBackgroundColor(Color.BLACK);
            Numbers[i].setBackgroundResource(R.drawable.ripple_effect);
            Numbers[i].setOnClickListener(v -> Solution.append(((Button) v).getText()));
        }
        Operator = new Button[5];
        for (int i = 0; i <= 4; i++) {
            @SuppressLint("DiscouragedApi") int operatorId = getResources().getIdentifier("operator" + i, "id", getPackageName());
            Operator[i] = findViewById(operatorId);
            Operator[i].setBackgroundResource(R.drawable.ripple_effect);

            Operator[i].setOnClickListener(v -> Solution.append(((Button) v).getText()));
        }
        back.setOnClickListener(v -> {
            String text = Solution.getText().toString();
            if (text.length() > 0) {
                int positionToDelete = text.length() - 1;
                String updatedText = text.substring(0, positionToDelete);
                Solution.setText(updatedText);
            }
        });

        allClear.setOnClickListener(v -> {
            Solution.setText("");
            resultTv.setText("");
        });

        equal.setOnClickListener(v -> {
            //Get the solution text
            try{
                String text = Solution.getText().toString();
                String [] array = {"s","i","n","c","o","t","a","l","^","g"};
                boolean geoExp = false;
                for(String element: array){
                    if(text.contains(element)){
                        geoExp = true;
                        break;
                    }
                }
                if(geoExp){
                    geoMetric(text);
                }
                else {
                    displayResult(text);
                }
            }
            catch (Exception e){
                e.getStackTrace();
            }
        });


    }


    public void geoMetric(String text){
        String first = "";
        StringBuilder second = new StringBuilder();
        String [] data = text.split("");
        int check = 0;
        String [] array = {"s","i","n","c","o","t","a","l","^","g"};
        for(int i =0; i<data.length; i++){
            if(Arrays.asList(array).contains(data[i])){
                first +=data[i];
            }
            else {
                check = i;
                break;
            }
        }
        for(int i = check+1; i<data.length; i++){
            second.append(data[i]);
        }
        double val1 = Double.parseDouble(second.toString());
        double result = 0.0;
        switch (first){
            case "sin":
                result =   Math.sin(val1);
                break;
            case "cos":
                result = Math.cos(val1);
                break;
            case "tan":
                result = Math.tan(val1);
                break;
            case "log":
                result = Math.log(val1);
                break;
            case "ln":
                result = Math.log10(val1);
                break;

            case "/":
                result = Math.sqrt(val1);
                break;
            default:
                break;


        }
        String finalResult = String.valueOf(result);
        resultTv.setText(finalResult);

    }
    public void displayResult(String text){
        String first = "";
        StringBuilder second = new StringBuilder();
        String oper = "";

        String []array = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String []data =text.split("");
        int check = 0;
        for(int i =0; i<data.length; i++){
            if (Arrays.asList(array).contains(data[i])) {
                first += data[i];
            } else {
                oper = data[i];
                check = i;
                break;
            }
        }
        for(int i =check+1; i<data.length; i++){
            second.append(data[i]);
        }


        boolean isInputDouble = first.contains(".");
        boolean isInput2Double = second.toString().contains(".");

        double val1 = Double.parseDouble(first);
        double val2 = Double.parseDouble(second.toString());

        double result;
        switch (oper) {
            case "+":
                result = val1 + val2;
                break;
            case "-":
                result = val1 - val2;
                break;
            case "×":
                result = val1 * val2;
                break;
            case "%":
                result = val1 % val2;
                break;
            case "÷":
                result = val1 / val2;
                break;
            default:
                System.out.println("Invalid operation");
                return;
        }
        if(isInputDouble || isInput2Double){
            String fiRes = String.valueOf(result);
            resultTv.setText(fiRes);
        }
        else {
            int res = (int) result;
            String fiRes = String.valueOf(res);
            resultTv.setText(fiRes);
        }
    }
}