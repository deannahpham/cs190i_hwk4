package edu.ucsb.cs.cs190i.deannapham.imagetagexplorer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Deanna on 5/15/17.
 */

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    private final Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public ImageAdapter(Context context){
        this.context = context;
    }

    public void addImage(Bitmap image) {
        BitmapManager.addBitmap(image);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main,parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Bitmap bitmap = BitmapManager.bitmapList.get(position);
        holder.image.setImageBitmap(bitmap);



        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(null, holder.toString(), Toast.LENGTH_SHORT).show();
//                Intent activityImg = new Intent (holder.image.getContext(), ImageActivity.class);
//                activityImg.putExtra(ImageActivity.keyVal, position);
//                context.startActivity(activityImg);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return BitmapManager.bitmapList.size();
    }
}
