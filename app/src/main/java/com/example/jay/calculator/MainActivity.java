package com.example.jay.calculator;

import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView txtShow;
    private Button b1,b2,b3,b4,b5,b6,b7,b8,b9,b0;
    private Button add,sub,mult,div,eq,c;
    private String s = null;
    public boolean signal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtShow = (TextView)findViewById(R.id.textView);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        b0 = (Button) findViewById(R.id.button0);
        c = (Button) findViewById(R.id.buttonc);
        eq = (Button) findViewById(R.id.buttoneq);
        add = (Button) findViewById(R.id.buttonadd);
        sub = (Button) findViewById(R.id.buttonsub);
        mult = (Button) findViewById(R.id.buttonmult);
        div = (Button) findViewById(R.id.buttondiv);

        b0.setOnClickListener(myListener);
        b1.setOnClickListener(myListener);
        b2.setOnClickListener(myListener);
        b3.setOnClickListener(myListener);
        b4.setOnClickListener(myListener);
        b5.setOnClickListener(myListener);
        b6.setOnClickListener(myListener);
        b7.setOnClickListener(myListener);
        b8.setOnClickListener(myListener);
        b9.setOnClickListener(myListener);
        c.setOnClickListener(cListener);
        eq.setOnClickListener(eqListener);
        add.setOnClickListener(myListener);
        sub.setOnClickListener(myListener);
        mult.setOnClickListener(myListener);
        div.setOnClickListener(myListener);
    }
    private Button.OnClickListener myListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            String s = txtShow.getText().toString();
            Button btn = (Button)findViewById(v.getId());
            txtShow.setText(s+btn.getText());
            signal = true;
        }
    };
    private Button.OnClickListener cListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            txtShow.setText("");
            signal = false;
        }
    };
    private Button.OnClickListener eqListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v){
            AlertDialog.Builder obj= new AlertDialog.Builder(MainActivity.this);
            ArrayList<Double> num = new ArrayList<Double>();
            ArrayList<Character> op = new ArrayList<Character>();
            if(!signal){
                obj.setTitle("警告");
                obj.setMessage("要輸入運算式");
                obj.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i){}
                });
                obj.show();
                return;
            }
            s = txtShow.getText().toString();
            String[] tokens =s.split("\\+|-|\\*|/");

            if (s.charAt(0) == '+'||s.charAt(0) == '-'||s.charAt(0) == '*'||s.charAt(0) == '/'){
                obj.setTitle("警告");
                obj.setMessage("輸入格式錯誤");
                obj.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i){}
                });
                obj.show();
                return;
            }
            if(error(s)){
                obj.setTitle("警告");
                obj.setMessage("輸入格式錯誤");
                obj.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i){}
                });
                obj.show();
                return;
            }

            for (int i = 0;i < tokens.length;i++){
                num.add(Double.parseDouble(tokens[i]));
            }
            for (int i = 0;i < s.length();i++){
                if(s.charAt(i)=='+'||s.charAt(i)=='-'||s.charAt(i)=='*'||s.charAt(i)=='/'){
                    op.add(s.charAt(i));
                }
            }
            if (op.size() >= num.size()){
                obj.setTitle("警告");
                obj.setMessage("輸入格式錯誤");
                obj.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i){}
                });
                obj.show();
                return;
            }
            for (int i = 0; i < op.size(); i++){
                if(op.get(i) == '*'){
                    num.set(i, num.get(i)*num.get(i+1));
                    num.remove(i+1);
                    op.remove(i);
                    i--;
                }
                else if(op.get(i) == '/'){
                    num.set(i, num.get(i)/num.get(i+1));
                    num.remove(i+1);
                    op.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < op.size(); i++){
                if(op.get(i) == '+'){
                    num.set(i, num.get(i)+num.get(i+1));
                    num.remove(i+1);
                    op.remove(i);
                    i--;
                }
                else if(op.get(i) == '-'){
                    num.set(i, num.get(i)-num.get(i+1));
                    num.remove(i+1);
                    op.remove(i);
                    i--;
                }
            }
            String ans = String.valueOf(num.get(0));
            txtShow.setText(ans);
        }
        private boolean error(String str){
            for(int i = 0;i < str.length()-1; i++){
                if((str.charAt(i) == '+'||str.charAt(i)=='-'||str.charAt(i)=='*'||str.charAt(i)=='/')
                        &&(str.charAt(i+1) == '+'||str.charAt(i+1)=='-'||str.charAt(i+1)=='*'||str.charAt(i+1)=='/'))
                {
                    return true;
                }
            }
            return false;
        }
    };
}
