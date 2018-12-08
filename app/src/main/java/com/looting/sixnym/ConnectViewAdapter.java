package com.looting.sixnym;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConnectViewAdapter extends RecyclerView.Adapter<ConnectViewAdapter.ViewHolder>{

    private static final String TAG = "ConnectViewAdapter";
    private ArrayList<String> mNames = new ArrayList<>();
    private TextView tv;
    private Context mContext;

    public ConnectViewAdapter(Context context, ArrayList<String> names){
        mNames = names;
        mContext = context;
    }
    @NonNull
    @Override
    public ConnectViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: called.");

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_connect, viewGroup, false);
        return new ConnectViewAdapter.ViewHolder(view);
    }

    public void onBindViewHolder(@NonNull ConnectViewAdapter.ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");
        viewHolder.tv.setText(mNames.get(i));
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.connection);
        }
    }
}
