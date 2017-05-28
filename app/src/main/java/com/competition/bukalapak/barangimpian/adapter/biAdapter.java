package com.competition.bukalapak.barangimpian.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.competition.bukalapak.barangimpian.R;
import com.competition.bukalapak.barangimpian.controller.data_impian;

import java.util.List;

/**
 * Created by macintosh on 5/28/17.
 */

public class biAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    List<data_impian> data;
    data_impian current;
    int currentPos=0;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public biAdapter(Context context, List<data_impian> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_barang_impian, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            default:
                MyHolder myHolder= (MyHolder) holder;
                int posisi=position+1;
                final data_impian current=data.get(position);
                myHolder.urutan.setText(""+posisi+"");
                myHolder.nama_barang.setText(current.nama_barang);
                myHolder.relatifl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });

        }
    }
    class MyHolder extends RecyclerView.ViewHolder{

        TextView nama_barang;
        TextView urutan;
        CardView cView;
        RelativeLayout relatifl;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            nama_barang= (TextView) itemView.findViewById(R.id.nama_barang);
            urutan= (TextView) itemView.findViewById(R.id.urutan);
            cView = (CardView) itemView.findViewById(R.id.card_view);
            relatifl = (RelativeLayout) itemView.findViewById(R.id.relative_timeline);
//            String nama=textKosName.toString();
        }

    }
}