package edu.ucsb.cs.cs190i.deannapham.imagetagexplorer;

import android.app.DialogFragment;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Deanna on 5/15/17.
 */

public class TagEntryFragment extends android.support.v4.app.DialogFragment {

    private AutoCompleteTextView tag_text;
    private ImageView tag_image;

    public static TagEntryFragment newInstance(Uri uri) {
        TagEntryFragment frag = new TagEntryFragment();
        Bundle args = new Bundle();
        args.putString("title", uri.toString());
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tag, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tag_text = (AutoCompleteTextView) view.findViewById(R.id.tag_text);
        tag_image = (ImageView) view.findViewById(R.id.tag_image);
        String title = getArguments().getString("title", "Enter Name");
        Uri uri = Uri.parse((String)getArguments().get("title"));
        Picasso.with(getActivity()).load(uri).resize(500,500).centerCrop().into(tag_image);
        getDialog().setTitle(title);
        tag_text.requestFocus();

    }




}
