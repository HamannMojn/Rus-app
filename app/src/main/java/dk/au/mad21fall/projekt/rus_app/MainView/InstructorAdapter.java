package dk.au.mad21fall.projekt.rus_app.MainView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.au.mad21fall.projekt.rus_app.Models.Instructor;
import dk.au.mad21fall.projekt.rus_app.R;


public class InstructorAdapter extends BaseAdapter {
    private List<Instructor> instructors = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public InstructorAdapter(ArrayList<Instructor> instructors, Context context){
        this.instructors = instructors;
        this.context = context;
    }

    @Override
    public int getCount() {
        return instructors.size();
    }

    @Override
    public Instructor getItem(int i) {
        return instructors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return instructors.get(i).getId();
    }

    public void updateInstructorList(List<Instructor> list){
        this.instructors = list;
        notifyDataSetChanged();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null){
           view = inflater.inflate(R.layout.list_item, null);
        }
        TextView name = view.findViewById(R.id.listItemName);
        Instructor instructor = instructors.get(i);
        name.setText(instructor.getName());

        return view;
    }
}
