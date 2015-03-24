package usuamadina.intents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by cursomovil on 24/03/15.
 */
public class Detail_Activity extends ActionBarActivity {

    private Button btnSend;
    private Button btnCamera;
    private EditText txtMessage;
    private TextView lblMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        btnSend = (Button)findViewById(R.id.btnSend);
        btnCamera = (Button)findViewById(R.id.btnCamera);
        txtMessage = (EditText)findViewById(R.id.txtMessage);
        lblMessage = (TextView)findViewById(R.id.lblMessage);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityTarget);


     processIntent();
    }

    private void addEventListeners(){
        btnSend.setOnClickListener(new View.OnClickListener()){
            @Override
            public void onClick(View v){
                String message = txtMessage.getText().toString();
                if(message.length()>0){
                    Intent data = new Intent(MainActivity.this, Detail_Activity.class);
                    data.putExtra(MESSAGE, txtMessage.getText().toString());
                    setResult(RESULT_OK,data);
                    finish();
                }
                /*else {
                    Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.),)
                }*/
            }



    }

    private void processIntent(){
        Intent data = getIntent();
        String message = data.getStringExtra(MainActivity.MESSAGE);
        lblMessage.setText(MESSAGE);
    }
}
