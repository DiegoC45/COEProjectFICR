package com.coe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.coe.R;
import com.coe.bean.Conquista;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ConquistaAdapter extends ArrayAdapter<Conquista> {

    public ConquistaAdapter(@NonNull Context context, List<Conquista> list) {
        super(context, 0, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.adapter_conquista, parent, false);
        Conquista conquista = getItem(position);

        CircleImageView image = root.findViewById(R.id.image);
        Picasso.get().load(conquista.getImgUrl()).into(image);
        TextView text = root.findViewById(R.id.text);
        text.setText(conquista.getNome());

        return root;
    }

}

