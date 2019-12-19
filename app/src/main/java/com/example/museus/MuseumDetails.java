package com.example.museus;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.museus.models.Element;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MuseumDetails extends AppCompatActivity {
    private TextView adreça;
    private TextView descripcio;
    private TextView adreça2;
    private TextView nomICP;
    private TextView email;
    private TextView telefon;
    private ImageView escut;
    private ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.museu_details);

        adreça=findViewById(R.id.adreça);
        descripcio=findViewById(R.id.descripcio);
        adreça2=findViewById(R.id.adreça2);
        nomICP=findViewById(R.id.nomICP);
        email=findViewById(R.id.email);
        telefon=findViewById(R.id.telefon);
        escut=findViewById(R.id.escut);
        image=findViewById(R.id.image);

        Gson gson = new Gson();
        Element element = gson.fromJson(getIntent().getStringExtra("ObjectesMuseu"), Element.class);

        adreça.setText(element.getAdrecaNom());
        adreça2.setText(element.getGrupAdreca().getAdreca());
        descripcio.setText(element.getDescripcio());
        nomICP.setText(element.getGrupAdreca().getMunicipiNom()+""+element.getGrupAdreca().getCodiPostal());
        email.setText(element.getEmail().get(0));
        telefon.setText(element.getTelefonContacte().get(0));

        Picasso.with(this).load(element.getImatge().get(0)).into(image);
        Picasso.with(this).load(element.getRelMunicipis().getMunicipiEscut()).into(escut);

    }
}
