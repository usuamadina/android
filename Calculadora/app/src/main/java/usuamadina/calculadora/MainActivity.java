package usuamadina.calculadora;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


import usuamadina.calculadora.Logic.CalcLogic;

public class MainActivity extends ActionBarActivity implements  {

    private String [] op;
    private Button btn1, btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    private Button btnMul,btnDiv,btnAdd,btnSub,btnResult,btnDec;
    private TextView result;
    private int i = 0;
    CalcLogic c = new CalcLogic();

    private final String CALC = "CALC";
    private ArrayList<Button> numberButtons;
    private ArrayList<String> operationButtons;

   /* private View.OnClickListener numberOnClickListener = new View.OnClickListener(){
        @Override
        private void onClick(View v){
            setNumber(((Button) v).getText().toString());
        }

    };

    private View.OnClickListener numberOnClickListener = new View.OnClickListener(){
        @Override
        private void onClick(View v){
            setOperations(((Button) v).getText().toString());
        }

    }*/





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_main);
       /* btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        btn0 = (Button)findViewById(R.id.btn0);
        btnMul = (Button)findViewById(R.id.btnMul);
        btnDiv = (Button)findViewById(R.id.btnDiv);
        btnSub = (Button)findViewById(R.id.btnSub);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnDec = (Button)findViewById(R.id.btnDec);
        btnResult = (Button)findViewById(R.id.btnResult);
        result = (TextView)findViewById(R.id.result);
        this.addEventListeners(btn1);
        this.addEventListeners(btn2);*/



    }



   /* private void addEventListeners(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int j;
                String buttonPressed = ((Button) v).getText().toString();
                op[i] = new String[buttonPressed];
                i++;
                for (j = 0; j < op.length; j++) {
                    result.setText(op[i].toString());
                }

               /* switch (buttonPressed) {
                    case "1":
                            op[i] = new String[buttonPressed];
                            i++;
                            for (i=0;i<op.length;i++){

                            }



                        break;
                    case "2":
                            result.setText(buttonPressed.toString());

                        break;

                    case "3":
                            result.setText(buttonPressed.toString());

                        break;




                }

            }

            }




        });
    }*/


    //Automatizar el proceso de los botones
    private void getButtonsFromLayout(){
        String[] numbers = {"1","2","3","4","5","6","7","8","9","0"};

        String id;
        Button btn;
        for (int i = 0; i < numbers.length; i++){
            id = "btn".concat(numbers[i]);
            btn = (Button)findViewById(getResources().getIdentifier(id,"id", getPackageName()));
            numberButtons.add(btn);
        }

        String[] operations = {"Add","Sub","Mul","Div","Dec","Result"};
        for (int i = 0; i < numbers.length; i++){
            id = "btn".concat(numbers[i]);
            btn = (Button)findViewById(getResources().getIdentifier(id,"id", getPackageName()));
            operationButtons.add(btn);
        }

    }

    private void addEventListeners(){
        View.OnClickListener numberOnClickListener =
        //addListener
        for (int i = 0; i < numberButtons.size();i++){
            numberButtons.get(i).setOnClickListener();
        }

        for (int i = 0;i < operationButtons.size(); i++){

        }
    }

    public void setNumber (String number){
        Log.d(CALC, number);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
