package com.activites.filters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.DAL.manager.DB_Note;
import com.DAO.model.NoteListViewModel;
import com.Utilities.Filter;
import com.adapters.RecyclerViewAdapter;
import com.navigation.R;
import com.statics.Static_Arrays;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMEN on 08.05.2016.
 */
public class PageFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DB_Note db = DB_Note.getInstance(getContext());
    private TextView txt;
    private List<NoteListViewModel> model;
    public static final String ARG_PAGE = "ARG_PAGE";
    private boolean filtered;
    private long firstDate;
    private long lastDate;

    final ArrayList<String> listarray  = new ArrayList<String>(){{
        add(0,"1");
        add(1,"2");
        add(2,"3");
    }};


    private int mPage;

    public static PageFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main, container, false);
        txt = (TextView) view.findViewById(com.navigation.R.id.firstNoteTextView);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null){
            filtered = getActivity().getIntent().getExtras().getBoolean("filtered");
            firstDate = getActivity().getIntent().getExtras().getLong("firstDate");
            lastDate = getActivity().getIntent().getExtras().getLong("lastDate");

        }


        if (db.getAllNotes().isEmpty()) {
            txt.setText("Add your firts note!");
            txt.setTextColor(com.Utilities.SecurityManager.getToolbarColor(getContext()));
            recyclerView.setVisibility(View.GONE);
        } else if(filtered){

            txt.setVisibility(View.GONE);
            if(mPage == Filter.LOCK_PAGE.ordinal()){
                model = db.getDateFilteredNotes(firstDate,lastDate,Filter.LOCK.ordinal());
            }else
            if(mPage == Filter.UNLOCK_PAGE.ordinal()){
                model = db.getDateFilteredNotes(firstDate,lastDate,Filter.UNLOCK.ordinal());
            }else{
                model = db.getFilteredNotes(firstDate,lastDate);
            }
            mAdapter = new RecyclerViewAdapter(model, getContext());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
        else {
            txt.setVisibility(View.GONE);
            if(mPage == Filter.LOCK_PAGE.ordinal()){
                model = db.getNotes(Static_Arrays.LOCK_NOTES);

            }else
            if(mPage == Filter.UNLOCK_PAGE.ordinal()){
                model = db.getNotes(Static_Arrays.UNLOCK_NOTES);

            }else{
                model = db.getAllNotes();
            }
            mAdapter = new RecyclerViewAdapter(model, getContext());
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }

            return view;
    }
}
