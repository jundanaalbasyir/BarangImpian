package com.competition.bukalapak.barangimpian.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.bukalapak.barangimpian.R;
import com.competition.bukalapak.barangimpian.controller.data_timeline;

import java.util.List;

/**
 * Created by macintosh on 5/28/17.
 */


public class cobaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    List<data_timeline> data;
    data_timeline current;
    int currentPos=0;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public cobaAdapter(Context context, List<data_timeline> data){
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

        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_big, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            default:
                MyHolder myHolder= (MyHolder) holder;
                final data_timeline current=data.get(position);
                myHolder.nama_barang.setText(current.ti_name+" ditukar dengan "+current.td_name);
                myHolder.relatifl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });


//         load image into imageview using glide
                Glide.with(context).load(current.ti_smallimage)
                        .placeholder(R.drawable.dummy)
                        .error(R.drawable.dummy)
                        .into(myHolder.img_barangimpian);

                Glide.with(context).load(current.td_smallimage)
                        .placeholder(R.drawable.dummy)
                        .error(R.drawable.dummy)
                        .into(myHolder.img_barangdimiliki);
                break;
        }
    }
    class MyHolder extends RecyclerView.ViewHolder{

        //        TextView textKosName;
        ImageView img_barangimpian;
        ImageView img_barangdimiliki;
        TextView nama_barang;
        //        TextView textKosAlamat;
        //        TextView textPrice;
        CardView cView;
        RelativeLayout relatifl;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
//            textKosName= (TextView) itemView.findViewById(R.id.textKosName);
            img_barangimpian = (ImageView) itemView.findViewById(R.id.image_impian);
            img_barangdimiliki= (ImageView) itemView.findViewById(R.id.image_dimiliki);
            nama_barang= (TextView) itemView.findViewById(R.id.nama_barang_1);
//            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            cView = (CardView) itemView.findViewById(R.id.card_view);
            relatifl = (RelativeLayout) itemView.findViewById(R.id.relative_timeline);
//            String nama=textKosName.toString();
        }

    }
}