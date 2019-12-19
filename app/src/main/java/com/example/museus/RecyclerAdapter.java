package com.example.museus;


import android.content.Context;
import android.content.Intent;
import android.telephony.mbms.MbmsErrors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Element> mElements;

    public void addElements(List<Element> elementList) {
        mElements.addAll(elementList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txt;
        public ImageView img;
        public Button btn;

        public ViewHolder(View itemView) {
            super(itemView);

            txt = (TextView) itemView.findViewById(R.id.titolMuseu);
            img = (ImageView) itemView.findViewById(R.id.imatgeMuseu);
            btn = (Button) itemView.findViewById(R.id.btnMuseu);
        }
    }

    public RecyclerAdapter(Context context) {
        this.mElements=new ArrayList<>();
        this.context = context;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Element element = mElements.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.txt;
        textView.setText(element.getAdrecaNom());

        Picasso.with(context).load(element.getImatge().get(0)).into(viewHolder.img);


        //Assignem el event al botó per tal de veure la info
        Button boto = viewHolder.btn;
        boto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Obrir la nova activity amb tota la infod el museu seleccionat
                Intent intent = new Intent(context, MuseumDetails.class);

                //Serialitzo per passar el Museu
                Gson gson = new Gson();
                String eSerialized = gson.toJson(element);

                intent.putExtra("ObjectesMuseu", eSerialized);
                context.startActivity(intent);
            }
        });


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mElements.size();
    }
}
