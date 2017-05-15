package edu.ucsb.cs.cs190i.deannapham.imagetagexplorer;

import android.app.DialogFragment;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Deanna on 5/15/17.
 */

public class TagEntryFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tag, container, false);
        View tv = v.findViewById(R.id.tag_text);
        ImageView image = (ImageView)v.findViewById(R.id.tag_image);
        Toast.makeText(getActivity(), "HELLO", Toast.LENGTH_SHORT).show();
        return v;
    }



}
