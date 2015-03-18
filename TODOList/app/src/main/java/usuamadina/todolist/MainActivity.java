package usuamadina.todolist;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    private final String TODOS_KEY = "TODOS_ARRAY";


    private Button btnAdd;
    private TextView todoText;
    private ListView todoListView;

    private ArrayList<String> todos;
    private ArrayAdapter<String> aa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        todos = new ArrayList<String>(); //todos apunta a la posición de memoria de ArrayList

        aa = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,todos);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        todoText = (TextView)findViewById(R.id.todoText);
        todoListView = (ListView)findViewById(R.id.todoListView);

        todoListView.setAdapter(aa);

        this.addEventListeners();
    }

    private void addEventListeners() {
        btnAdd.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String todo = todoText.getText().toString();
                todos.add(0,todo);
                aa.notifyDataSetChanged(); // avisamos que los datos han cambiado y que tiene que volver a pintar la vista
            }


        });
    }

   @Override
   public void onRestoreInstanceState(Bundle savedInstanceState){
       super.onRestoreInstanceState(savedInstanceState);
      todos.addAll(savedInstanceState.getStringArrayList(TODOS_KEY));

   }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //El orden es muy importante, primero guardamos las cosas
        // y luego llamamos al padre con el Bundle ya creado

        Log.d("todoList", "Change view onSaveInstanceState()");

        outState.putStringArrayList(TODOS_KEY,todos); // parámetros clave + valor, valor = todos,

        super.onSaveInstanceState(outState);



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
