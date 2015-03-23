package usuamadina.todolistfragment.fragment;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import java.util.ArrayList;

import usuamadina.todolistfragment.R;
import usuamadina.todolistfragment.ToDo;
import usuamadina.todolistfragment.ToDoAdapter;

/**
 * A fragment representing a list of Items.
 */
public class TodoListFragment extends ListFragment implements InputFragment.TODOItemListener {
    private final String TODOS_KEY = "TODO_KEY";

    private ArrayList<ToDo> todos;
    private ArrayAdapter<ToDo> aa;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout= super.onCreateView(inflater, container, savedInstanceState);

        todos = new ArrayList<>();
        aa = new ToDoAdapter(getActivity(), R.layout.todo_list_item, todos);

        if (savedInstanceState != null){
            ArrayList<ToDo> tmp = savedInstanceState.getParcelableArrayList(TODOS_KEY);
            todos.addAll(tmp);

        }

        setListAdapter(aa);


        return layout;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(TODOS_KEY, todos);
        super.onSaveInstanceState(outState);
    }



    @Override
    public void addTodo(ToDo todo){
        todos.add(0, todo);
        aa.notifyDataSetChanged();
    }



  }
