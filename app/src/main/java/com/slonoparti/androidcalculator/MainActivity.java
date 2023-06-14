package com.slonoparti.androidcalculator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String sequence="";
    private TextView editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.sequenceField);
        ImageButton button=findViewById(R.id.imageButton);
        button.setOnClickListener(v-> {
            if(sequence.equals(""))return;
            sequence=sequence.substring(0,sequence.length()-1);
            editText.setText(sequence);
        });
    }
    public void addChar(View v){
        Button button=findViewById(v.getId());
        if(button.getText().equals("=")){
            if(isValid(sequence)){
                TextView text=findViewById(R.id.textView);
                text.setText(String.valueOf(Calculator.calc(sequence)));
            }
            else{
                Toast.makeText(this,"Expression is not valid",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            sequence+=button.getText();
            editText.setText(sequence);
        }
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("str",sequence);
        TextView result=findViewById(R.id.textView);
        outState.putString("result", (String) result.getText());
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle inState){
        super.onRestoreInstanceState(inState);
        TextView text=findViewById(R.id.sequenceField);
        TextView result=findViewById(R.id.textView);
        sequence=inState.getString("str");
        text.setText(sequence);
        result.setText(inState.getString("result"));
    }
    private boolean isValid(String sequence){
        return checkBrackets(sequence)&&Pattern.matches("^\\d+(?:[+*/\\-^]\\d+)+$",sequence.replaceAll("[()]",""));
    }
    private boolean checkBrackets(String sequence){
        ConcurrentLinkedDeque<Character> deque=new ConcurrentLinkedDeque<>();
        for(int i=0;i<sequence.length();i++){
            if(sequence.charAt(i)=='(')deque.addFirst(sequence.charAt(i));
            else if(sequence.charAt(i)==')')deque.pop();
        }
        return deque.isEmpty();
    }
}