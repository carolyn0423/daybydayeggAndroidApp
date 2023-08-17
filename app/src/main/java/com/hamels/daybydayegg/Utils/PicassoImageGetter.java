package com.hamels.daybydayegg.Utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hamels.daybydayegg.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class PicassoImageGetter implements Html.ImageGetter {
    private final Context context;
    private TextView textView;
    private Picasso picasso;

    public PicassoImageGetter(Context context,@NonNull Picasso picasso, @NonNull TextView textView) {
        this.context = context;
        this.picasso = picasso;
        this.textView = textView;
    }
    @Override
    public Drawable getDrawable(String source) {
        Log.d(PicassoImageGetter.class.getName(), "Start loading url " + source);

        BitmapDrawablePlaceHolder drawable = new BitmapDrawablePlaceHolder();

        picasso
                .load(source)
                //.error(R.drawable.connection_error)
                .into(drawable);

        return drawable;
    }

    private class BitmapDrawablePlaceHolder extends BitmapDrawable implements Target {

        protected Drawable drawable;

        @Override
        public void draw(final Canvas canvas) {
            if (drawable != null) {
                checkBounds();
                drawable.draw(canvas);
            }
        }

        public void setDrawable(@Nullable Drawable drawable) {
            if (drawable != null) {
                this.drawable = drawable;
                checkBounds();
            }
        }

        private void checkBounds() {
            float defaultProportion = (float) drawable.getIntrinsicWidth() / (float) drawable.getIntrinsicHeight();
            int width = Math.min(textView.getWidth(), drawable.getIntrinsicWidth());
            int height = (int) ((float) width / defaultProportion);

            if (getBounds().right != textView.getWidth() || getBounds().bottom != height) {

                setBounds(0, 0, textView.getWidth(), height); //set to full width

                int halfOfPlaceHolderWidth = (int) ((float) getBounds().right / 2f);
                int halfOfImageWidth = (int) ((float) width / 2f);

                drawable.setBounds(
                        halfOfPlaceHolderWidth - halfOfImageWidth, //centering an image
                        0,
                        halfOfPlaceHolderWidth + halfOfImageWidth,
                        height);

                textView.setText(textView.getText()); //refresh text
            }
        }

        //------------------------------------------------------------------//

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

            //setDrawable(new BitmapDrawable(Application.getContext().getResources(), bitmap));
            setDrawable(new BitmapDrawable(context.getResources(), bitmap));
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            setDrawable(errorDrawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            setDrawable(placeHolderDrawable);
        }

        //------------------------------------------------------------------//

    }
}
