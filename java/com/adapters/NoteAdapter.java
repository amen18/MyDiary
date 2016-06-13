package com.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.DAO.model.NoteListViewModel;
import com.navigation.R;

import java.util.List;
/**
 * Created by AMEN on 27.04.2016.
 */
public class NoteAdapter extends ArrayAdapter<NoteListViewModel> {
    private final Activity context;
    private final List<NoteListViewModel> notes;

    public NoteAdapter(Activity context, List<NoteListViewModel> notes) {
        super(context, R.layout.row, notes);
        // TODO Auto-generated constructor stub

        this.notes = notes;
        this.context=context;
    }
//
//    public View getView(int position,View view,ViewGroup parent) {
//        LayoutInflater inflater=context.getLayoutInflater();
//        View rowView=inflater.inflate(R.layout.row, null, true);
//
//        TextView txtDay = (TextView) rowView.findViewById(R.id.day);
//        TextView txtMonthYear = (TextView) rowView.findViewById(R.id.year);
//        TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
//        String[] date = notes.get(position).getDate().split("/");
//
//        txtDay.setText(date[0]);
//        txtMonthYear.setText(date[1]+ ":" + date[2]);
//        txtTitle.setText(notes.get(position).getTitle());
//        return rowView;
//    };
}
