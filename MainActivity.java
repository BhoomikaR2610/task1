package com.example.calcutator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    String currentInput = "";
    double firstNumber = 0;
    String operator = "";
    boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Number buttons
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (isNewOperation) {
                    currentInput = "";
                    isNewOperation = false;
                }
                currentInput += b.getText().toString();
                tvDisplay.setText(currentInput);
            }
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(numberListener);
        }

        // Operator buttons
        findViewById(R.id.btnAdd).setOnClickListener(opListener);
        findViewById(R.id.btnSub).setOnClickListener(opListener);
        findViewById(R.id.btnMul).setOnClickListener(opListener);
        findViewById(R.id.btnDiv).setOnClickListener(opListener);

        // Equals button
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentInput.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter a number", Toast.LENGTH_SHORT).show();
                    return;
                }

                double secondNumber = Double.parseDouble(currentInput);
                double result = 0;

                switch (operator) {

                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "−": // special minus symbol
                        result = firstNumber - secondNumber;
                        break;
                    case "×": // special multiplication symbol
                        result = firstNumber * secondNumber;
                        break;
                    case "÷": // special division symbol
                        if (secondNumber != 0) {
                            result = firstNumber / secondNumber;
                        } else {
                            tvDisplay.setText("Error");
                            return;
                        }
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "Select an operator", Toast.LENGTH_SHORT).show();
                        return;
                }

                tvDisplay.setText(String.valueOf(result));
                currentInput = String.valueOf(result);
                isNewOperation = true;
            }
        });

        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                firstNumber = 0;
                operator = "";
                tvDisplay.setText("0");
                isNewOperation = true;
            }
        });
    }

    // Operator button click listener
    View.OnClickListener opListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (currentInput.isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter a number first", Toast.LENGTH_SHORT).show();
                return;
            }

            Button b = (Button) v;
            firstNumber = Double.parseDouble(currentInput);
            operator = b.getText().toString(); // store the symbol exactly as in XML
            isNewOperation = true;
        }
    };
}
