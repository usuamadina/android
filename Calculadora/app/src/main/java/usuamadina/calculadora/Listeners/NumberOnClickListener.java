package usuamadina.calculadora.Listeners;

import android.view.View;
import android.widget.Button;

import usuamadina.calculadora.MainActivity;

/**
 * Created by cursomovil on 20/03/15.
 */
public class NumberOnClickListener implements View.OnClickListener{

    public interface NumberListenerInterface{
        public void setNumber(String number);
    }

    private NumberListenerInterface target;

    public NumberOnClickListener(MainActivity target){
        this.target = target;
    }

    public void onClick(View v){
        Button btn = (Button) v;
        setNumber(btn.getText().toString());
    }

}
