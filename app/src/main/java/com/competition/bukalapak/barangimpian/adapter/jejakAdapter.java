package com.competition.bukalapak.barangimpian.adapter;

/**
 * Created by macintosh on 5/28/17.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.competition.bukalapak.barangimpian.R;
import com.competition.bukalapak.barangimpian.controller.data_jejak;

import java.util.List;

public class jejakAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;

    List<data_jejak> data;
    data_jejak current;
    int currentPos=0;

    static final int TYPE_HEADER = 0;
    static final int TYPE_CELL = 1;

    public jejakAdapter(Context context, List<data_jejak> data){
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

        switch (viewType) {
            case TYPE_HEADER: {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jejak_big, parent,false);
                MyHolder holder=new MyHolder(view);
                return holder;
            }
            case TYPE_CELL: {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_jejak_small, parent,false);
                MyHolder holder=new MyHolder(view);
                return holder;
            }
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder= (MyHolder) holder;
        final data_jejak current=data.get(position);
        int posisi=position+1;
        myHolder.urutan.setText(""+posisi+"");
        myHolder.nama_barang_1.setText(current.nama_barang_1+" ditukar dengan "+current.nama_barang_2);
        myHolder.tanggal.setText(current.tanggal);
        myHolder.berhasil.setText(current.berhasil);
        myHolder.linear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
//         load image into imageview using glide
                Glide.with(context).load(current.image_2)
                        .placeholder(R.drawable.dummy)
                        .error(R.drawable.dummy)
                        .into(myHolder.img_barangimpian);

                Glide.with(context).load(current.image_1)
                        .placeholder(R.drawable.dummy)
                        .error(R.drawable.dummy)
                        .into(myHolder.img_barangdimiliki);
                break;

            case TYPE_CELL:
                break;

            default:
                break;
        }
    }
    class MyHolder extends RecyclerView.ViewHolder{

        //        TextView textKosName;
        TextView urutan;
        ImageView img_barangimpian;
        ImageView img_barangdimiliki;
        TextView nama_barang_1;
        TextView tanggal;
        TextView berhasil;
        //        TextView textKosAlamat;
        //        TextView textPrice;
        CardView cView;
        LinearLayout linear1;
        LinearLayout linear21;
        LinearLayout linear22;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
//            textKosName= (TextView) itemView.findViewById(R.id.textKosName);
            urutan=(TextView) itemView.findViewById(R.id.urutan);
            nama_barang_1= (TextView) itemView.findViewById(R.id.barang1);
            img_barangimpian = (ImageView) itemView.findViewById(R.id.image_impian);
            img_barangdimiliki= (ImageView) itemView.findViewById(R.id.image_dimiliki);
            tanggal= (TextView) itemView.findViewById(R.id.tanggal);
            berhasil= (TextView) itemView.findViewById(R.id.berhasil);
//            textKosAlamat= (TextView) itemView.findViewById(R.id.textKosAlamat);
//            textKosHarga = (TextView) itemView.findViewById(R.id.textHargaKos);
//            textPrice = (TextView) itemView.findViewById(R.id.textPrice);
            cView = (CardView) itemView.findViewById(R.id.card_view);
            linear1= (LinearLayout) itemView.findViewById(R.id.liniear1);
            linear21= (LinearLayout) itemView.findViewById(R.id.liniear21);
            linear22= (LinearLayout) itemView.findViewById(R.id.liniear22);
//            String nama=textKosName.toString();
        }

    }
}