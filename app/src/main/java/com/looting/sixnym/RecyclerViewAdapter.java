package com.looting.sixnym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecycleViewAdapter";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImg = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context context, ArrayList<String> names){
        mNames = names;
        mContext = context;
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
                    .load(R.drawable.ic_launcher_background)
                    .into(viewHolder.img);
            viewHolder.tv.setText(mNames.get(i));

            viewHolder.img.setOnClickListener(new View.OnClickListener(){
               @Override
                public void onClick(View view){
                   Log.d(TAG, "onClick: clicked on an image: " + mNames.get(i));
                   Toast.makeText(mContext, mNames.get(i), Toast.LENGTH_SHORT).show();
               }
            });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.image);
            tv = itemView.findViewById(R.id.pointText);
        }
    }
}
