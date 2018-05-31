package com.example.viralprajapati.coen268_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Button zero,one,two,three,four,five,six,seven,eight,nine,equals,dot,minus,plus,mul,divide,del;
    EditText text;
    TextView result;
    Double num1 = Double.NaN;
    Double num2;
    boolean add, sub, multiply, div;

    private static final ArrayList<Character> DIVIDERS = new ArrayList<Character>
            (Arrays.asList('*', '/', '-', '+'));

    private static final int RIGHT_DIRECTION = 1;
    private static final int LEFT_DIRECTION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        zero = (Button) findViewById(R.id.zero);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        four = (Button) findViewById(R.id.four);
        five = (Button) findViewById(R.id.five);
        six = (Button) findViewById(R.id.six);
        seven = (Button) findViewById(R.id.seven);
        eight = (Button) findViewById(R.id.eight);
        nine = (Button) findViewById(R.id.nine);
        plus = (Button) findViewById(R.id.plus);
        minus = (Button) findViewById(R.id.minus);
        mul = (Button) findViewById(R.id.mul);
        divide = (Button) findViewById(R.id.divide);
        equals = (Button) findViewById(R.id.equals);
        dot = (Button) findViewById(R.id.dot);
        del = (Button) findViewById(R.id.del);

        text = (EditText) findViewById(R.id.text);
        result = (TextView) findViewById(R.id.result);

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "0");
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "1");
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "2");
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "3");
            }
        });
        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "4");
            }
        });
        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "5");
            }
        });
        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "6");
            }
        });
        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "7");
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "8");
            }
        });
        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ "9");
            }
        });

        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText()+ ".");
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText() + "+");
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText() + "-");
            }
        });

        mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText() + "*");
            }
        });

        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(text.getText() + "/");
            }
        });

        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(result.getText() != null){
                    result.setText(null);
                }
                computeCalculation();
                result.setText(result.getText().toString() + num1);
                num1 = Double.NaN;
            }


        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(text.getText().length() > 0){
                    CharSequence current = text.getText();
                    text.setText(current.subSequence(0,current.length()-1));
                }else{
                    num1=Double.NaN;
                    num2 = Double.NaN;
                    text.setText("");
                    result.setText("");
                }
            }
        });
        del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                text.setText("");
                result.setText("");
                return true;
            }
        });



    }

    private void computeCalculation() {
        if(Double.isNaN(num1)) {
            try {
                num1=Double.parseDouble(calc(text.getText().toString()));
                text.setText(null);
            }
            catch(Exception e)
            { }
        }
        else {
            try {
                num1 = Double.valueOf(text.getText().toString());

            }
            catch (Exception e){}
        }
    }



    public static String calc (String expression)
    {
        int pos = 0;
        System.out.println("Solving expression: "+expression);
        /*
        Extracting expression from braces, doing recursive call
        replace braced expression on result of it solving
        */
        if (-1 != (pos = expression.indexOf("("))) {

            String subexp = extractExpressionFromBraces(expression,pos);
            expression = expression.replace("("+subexp+")", calc(subexp));

            return calc(expression);


        }  else if (-1 != (pos = expression.indexOf("exp"))) {

            pos += 2;

            String number = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace("exp" + number,
                    Double.toString(Math.exp(Double.parseDouble(number))));

            return calc(expression);


        } else if (expression.indexOf("*") > 0 | expression.indexOf("/") > 0) {

            int multPos = expression.indexOf("*");
            int divPos = expression.indexOf("/");

            pos = Math.min(multPos, divPos);
            if (multPos < 0) pos = divPos; else if (divPos < 0) pos = multPos;

            /*
             *If one value of
             *Pos will be -1 result of min will be incorrect.
             */

            char divider = expression.charAt(pos);

            String leftNum = extractNumber(expression, pos, LEFT_DIRECTION);
            String rightNum = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace(leftNum + divider + rightNum,
                    calcShortExpr(leftNum, rightNum, divider));

            return calc(expression);


        } else if (expression.indexOf("+") > 0 | expression.indexOf("-") > 0) {

            int summPos = expression.indexOf("+");
            int minusPos = expression.indexOf("-");

            pos = Math.min(summPos, minusPos);

            if (summPos < 0) pos = minusPos; else if (minusPos < 0) pos = summPos;

            char divider = expression.charAt(pos);

            String leftNum = extractNumber(expression, pos, LEFT_DIRECTION);
            String rightNum = extractNumber(expression, pos, RIGHT_DIRECTION);

            expression = expression.replace(leftNum + divider + rightNum,
                    calcShortExpr(leftNum, rightNum, divider));

            return calc(expression);

        } else return expression;
    }

    public static String extractExpressionFromBraces(String expression, int pos) {
        int braceDepth = 1;
        String subexp="";

        for (int i = pos+1; i < expression.length(); i++) {
            switch (expression.charAt(i)) {
                case '(':
                    braceDepth++;
                    subexp += "(";
                    break;
                case ')':
                    braceDepth--;
                    if (braceDepth != 0) subexp += ")";
                    break;
                default:
                    if (braceDepth > 0) subexp += expression.charAt(i);

            }
            if (braceDepth == 0 && !subexp.equals("")) return subexp;
        }
        return "Failure!";
    }

    public static String extractNumber(String expression, int pos, int direction) {

        String resultNumber = "";
        int currPos = pos + direction;//shift pos on next symbol from divider

        //For negative numbers
        if (expression.charAt(currPos) == '-') {
            resultNumber+=expression.charAt(currPos);
            currPos+=direction;
        }

        for (; currPos >= 0 &&
                currPos < expression.length() &&
                !DIVIDERS.contains(expression.charAt(currPos));
             currPos += direction) {
            resultNumber += expression.charAt(currPos);
        }

        if (direction==LEFT_DIRECTION) resultNumber = new
                StringBuilder(resultNumber).reverse().toString();

        return resultNumber;
    }

    public static String calcShortExpr(String leftNum, String rightNum, char divider) {
        switch (divider) {
            case '*':
                return Double.toString(Double.parseDouble(leftNum) *
                        Double.parseDouble(rightNum));
            case '/':
                return Double.toString(Double.parseDouble(leftNum) /
                        Double.parseDouble(rightNum));
            case '+':
                return Double.toString(Double.parseDouble(leftNum) +
                        Double.parseDouble(rightNum));
            case '-':
                return Double.toString(Double.parseDouble(leftNum) -
                        Double.parseDouble(rightNum));
            default:
                return "0";
        }

    }



}
