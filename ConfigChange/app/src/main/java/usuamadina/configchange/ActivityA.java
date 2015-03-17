package usuamadina.configchange;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityA extends ActionBarActivity {

    // Para sobreescribir los métodos onStart, onRestart, onResume etc..ALT+insert ->Override Methods
    //para solucionar el error del import del Log, posicionarse encima del Log y...
    private final String DATA = "data"; // final = constante

    private String op = null;
    private double result=;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);

        Log.d("CHANGE", "ActivityA onCreate()");

        if(savedInstanceState != null){
            String data = savedInstanceState.getString(DATA);
            Log.d("CHANGE", "ActivityA onCreate saved data: " + data);
        }


        Button btnOpenB = (Button)findViewById(R.id.btnOpenB);
        Button btnClose = (Button)findViewById(R.id.btnClose);

        btnOpenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CHANGE", "ActivityA onClick()");
                Intent openB = new Intent(ActivityA.this,ActivityB.class); // el this primer parámetro del intent, hace referencia al Listener
                startActivity(openB);

            }
        });

        btnClose.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.d("CHANGE", "ActivityA close");
                finish();

            }
        });

    }

   private void restoreState(Bundle saveInstanceState){
      this.op = saveInstanceState.getString(op);
      this.result = saveInstanceState.getDouble(result);

   }


    @Override
    protected void onStart() {
        super.onStart();

        Log.d("CHANGE", "ActivityA onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("CHANGE","ActivityA onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("CHANGE","ActivityA onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("CHANGE","ActivityA onPause()");
    }

    // onStop() y onDestroy() son principalmente para parar
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("CHANGE","ActivityA onStop()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //El orden es muy importante, primero guardamos las cosas
        // y luego llamamos al padre con el Bundle ya creado

        Log.d("CHANGE", "ActivityA onSaveInstanceState()");

        outState.putString(DATA,"datos");

        super.onSaveInstanceState(outState);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CHANGE","ActivityA onDestroy()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
