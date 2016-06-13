package com.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DAO.model.NoteListViewModel;
import com.Utilities.SecurityManager;
import com.Utilities.Utility;
import com.activites.InfoActivity;
import com.navigation.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by AMEN on 02.05.2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<NoteListViewModel> notes;
    private View view;
    private Context context;
    private NoteListViewModel item;
    private int color;

    public RecyclerViewAdapter(List<NoteListViewModel> notes,Context context) {
        this.notes = notes;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView day,mount,year,txtTitle;
        private ImageView lockImage,moodsImage;
        private LinearLayout monthLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            day = (TextView) itemView.findViewById(R.id.day);
            mount = (TextView) itemView.findViewById(R.id.mount);
            year = (TextView) itemView.findViewById(R.id.year);
            txtTitle = (TextView) itemView.findViewById(R.id.title_cardview);
            lockImage = (ImageView)itemView.findViewById(R.id.lockImage);
            moodsImage = (ImageView)itemView.findViewById(R.id.moodsImage);
            monthLayout = (LinearLayout)itemView.findViewById(R.id.month_layout);
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                Log.e("+++++++-------",getPosition() + "");
                    final Activity activity = (Activity) context;

                    item = notes.get(getAdapterPosition());
                    if(item.isLocked()){
                        final EditText editText = new EditText(context);
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                        alertDialogBuilder.setView(editText);
                        alertDialogBuilder.setTitle("Enter you password");
                        alertDialogBuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(SecurityManager.isPasswordCorrect(editText.getText().toString(),context)){
                                    Intent intent = new Intent(context, InfoActivity.class);
                                    intent.putExtra("listItem", item);
                                    context.startActivity(intent);
                                    activity.overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
                                    dialog.dismiss();
                                }else{
                                    editText.setError("Incorrect password");

//                                    dialog.dismiss();
                                }
                            }
                        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        alertDialogBuilder.show();
                    }
                    else {
                        Intent intent = new Intent(context, InfoActivity.class);
                        intent.putExtra("listItem", item);
                        context.startActivity(intent);
                        activity.overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);

                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_custom_adapter, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteListViewModel note = notes.get(position);
//        String[] date = notes.get(position).getDate().split("/");
        long date = note.getDate();
        Log.e("from database-----",date + "");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);

        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        holder.txtTitle.setText(note.getTitle());
        holder.day.setText(mDay + "");
        holder.mount.setText(Utility.getMonthName(mMonth+1));
        setMonthColor(Utility.getMonthName(mMonth+1));
        holder.monthLayout.setBackgroundColor(color);
        holder.year.setText(mYear + "");
        holder.moodsImage.setImageResource(note.getMood());

        if(notes.get(position).isLocked()) {
                    holder.lockImage.setImageResource(R.drawable.lock_icon);

        }else {
                    holder.lockImage.setImageResource(R.drawable.unlock_icon);

        }



    }

    private void setMonthColor(String monthName) {
        switch(monthName)
        {
            case "JAN":
                color = context.getResources().getColor(R.color.january);
                break;
            case "FEB":
                color = context.getResources().getColor(R.color.february);
                break;
            case "MAR":
                color = context.getResources().getColor(R.color.march);
                break;
            case "APR":
                color = context.getResources().getColor(R.color.april);
                break;
            case "MAY":
                color = context.getResources().getColor(R.color.may);
                break;
            case "JUN":
                color = context.getResources().getColor(R.color.june);
                break;
            case "JUL":
                color = context.getResources().getColor(R.color.july);
                break;
            case "AUG":
                color = context.getResources().getColor(R.color.august);
                break;
            case "SEP":
                color = context.getResources().getColor(R.color.september);
                break;
            case "OCT":
                color = context.getResources().getColor(R.color.october);
                break;
            case "NOV":
                color = context.getResources().getColor(R.color.november);
                break;
            case "DEC":
                color = context.getResources().getColor(R.color.december);
                break;

        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

}
