package com.looting.sixnym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecycleViewAdapter";
    private int cardPlayed;
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mValues = new ArrayList<>();
    TextView tv;
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> values, TextView tv){
        mNames = names;
        mValues = values;
        this.tv = tv;
        mContext = context;
        cardPlayed = -1;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called.");

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

            Glide.with(mContext)
                    .asBitmap()
                    .load(R.drawable.back)
                    .into(viewHolder.img);
            viewHolder.cv.setText(mValues.get(i));
            viewHolder.tv.setText(mNames.get(i));

            viewHolder.img.setOnClickListener(new View.OnClickListener(){
               @Override
                public void onClick(View view){
                   Log.d(TAG, "onClick: clicked on an image: " + mNames.get(i));
                   String name = tv.getText().toString();
                   String[] info = name.split("turn");
                   cardPlayed = i+1;
                   tv.setText(info[0] + "turn \nSelected Card " + Integer.toString(i+1) + ":\n" + mValues.get(i) + "\n");
               }
            });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public int getCardPlayed() {
        int card = cardPlayed;
        cardPlayed = -1;
        return card;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView cv;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            cv = itemView.findViewById(R.id.cardValue);
            tv = itemView.findViewById(R.id.pointText);
        }
    }
}
