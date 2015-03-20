package usuamadina.todolistfragment.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import usuamadina.todolistfragment.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InputFragment extends Fragment {

    public interface TODOItemListener{
        public void addTodo(String todo);
    }

    private Button btnAdd;
    private EditText todoText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            this.target=(TODOItemListener)activity;
        }catch(ClassCastException ex){
            throw new ClassCastException(activity.toString() + "must implement TODOItemListener Interface");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_input, container, false); // por defecto viene sin View Layout = quitamos el return, así accedemos al layout del fragmento
        btnAdd = layout.findViewById(R.id.btnAdd);
        todoText = layout.findViewById(R.id.inputText);

      private void addEventListener(){
         btnAdd.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {

                 String todo = todoText.getText().toString();

             }

         });
        }

        return layout;
    }


}
