package usuamadina.todolistfragment;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by cursomovil on 23/03/15.
 */
public class ToDo implements Parcelable {

    private String task;

      private Date created;

    public ToDo(String task, Date created) {
        this.task = task;
        this.created = created;
    }

    public ToDo(String task) {
        this.task = task;
        this.created = new Date();
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setCreated(Date created) {
        this.created = created;
    }



    public String getTask() {
        return task;
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
