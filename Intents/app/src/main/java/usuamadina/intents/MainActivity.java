package usuamadina.intents;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String MESSAGE = "MESSAGE";
    private static final int REQUEST_TARGET = 1;
    private final int REQUEST_IMAGE_CAPTURE = 2;

    private Button btnSend;
    private Button btnCamera;
    private EditText txtMessage;
    private TextView lblMessage;
    private ImageView imgCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = (Button) findViewById(R.id.btnSend);
        btnCamera = (Button) findViewById(R.id.btnCamera);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        lblMessage = (TextView) findViewById(R.id.lblMessage);
        imgCamera = (ImageView) findViewById(R.id.imgCamera);


        addEventsListeners();

    }

    private void addEventsListeners() {

        btnSend.setOnClickListener(new View.OnClickListener()) {
            @Override
            public void onClick (View v){
                String message = txtMessage.getText().toString();
                if (message.length() > 0) {
                    Intent sendRequest = new Intent(MainActivity.this, Detail_Activity.class);
                    sendRequest.putExtra(MESSAGE, txtMessage.getText().toString());
                    startActivityForResult(sendRequest, REQUEST_TARGET);
                }
                /*else {
                    Toast toast = Toast.makeText(MainActivity.this, getResources().getString(R.),)
                }*/
            }


        }

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(sendRequest, REQUEST_IMAGE_CAPTURE));
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_TARGET:
                    String message = data.getStringExtra(MESSAGE);
                    lblMessage.setText(message);
                    break;
                case REQUEST_IMAGE_CAPTURE:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap)extras.get("data");
                    imgCamera.setImageBitmap(imageBitmap);
                    break;
            }
        }
    }
}
