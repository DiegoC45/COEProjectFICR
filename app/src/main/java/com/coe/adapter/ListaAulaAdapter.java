package com.coe.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;

import com.coe.R;
import com.coe.bean.Aula;
import com.coe.bean.ListaAula;

import java.util.List;

public class ListaAulaAdapter extends ArrayAdapter<Aula> {

    private ImageView image;
    public ListaAulaAdapter(@NonNull Context context, List<Aula> list) {
        super(context, 0, list);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.adapter_lista_aula, parent, false);
        Aula aula = getItem(position);

         image = root.findViewById(R.id.image_lista_aula_adapter);

        TextView text = root.findViewById(R.id.text_aula_adapter);
        text.setText(aula.getNome());

        if(aula.getVisualizado()){
            image.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_youtube_true));


        }

        return root;
    }
}
