package usuamadina.intents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by cursomovil on 24/03/15.
 */
public class Detail_Activity extends ActionBarActivity {

    public static final String MESSAGE = "response";

    private Button btnSend;
    private Button btnCancel;
    private EditText txtMessage;
    private TextView lblMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        btnSend = (Button) findViewById(R.id.btnSend);
        btnCancel = (Button) findViewById(R.id.btnCamera);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        lblMessage = (TextView) findViewById(R.id.lblMessage);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        processIntent();

    }

    private void addEventListeners() {
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = txtMessage.getText().toString();

                if (message.length() > 0) {
                    Intent data = new Intent();
                    data.putExtra(MESSAGE, message);
                    setResult(RESULT_OK, data);
                    finish();
                } else {
                    // Si no escribe nada, mostramos un mensaje Toast

                  /*  Toast toast = Toast.makeText(Detail_Activity.this, getResources().getString(R.string.no_text), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.show();*/
                }

            }
        });
    }

    private void processIntent() {
        Intent data = getIntent();
        String message = data.getStringExtra(MainActivity.MESSAGE);
        lblMessage.setText(message);
    }
}
