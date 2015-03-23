package usuamadina.todolistfragment;

import android.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import usuamadina.todolistfragment.fragment.InputFragment;
import usuamadina.todolistfragment.fragment.TodoListFragment;


public class MainActivity extends ActionBarActivity implements InputFragment.TODOItemListener {
    private final String TODO = "TODO";
    private InputFragment.TODOItemListener listfragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            listfragment = (InputFragment.TODOItemListener) getFragmentManager().findFragmentById(R.id.Listfragment); //de los fragmentos que están incrustado buscame el que tenga id ListFragment
        } catch (ClassCastException ex){
            throw new ClassCastException(this.toString());
        }
    }

    @Override
    public void addTodo(ToDo todo) {

       listfragment.addTodo(todo);

    }
}
