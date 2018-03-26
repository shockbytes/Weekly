package at.shockbytes.weekly.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import at.shockbytes.weekly.R;

/**
 * @author Martin Macheiner
 *         Date: 26.06.2016.
 */
public class InformationChipView extends LinearLayout{

    private ImageView iconView;
    private TextView textView;

    public InformationChipView(Context context) {
        super(context);
        initialize();
    }

    public InformationChipView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    public InformationChipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        inflate(getContext(), R.layout.information_chip, this);
        this.iconView = (ImageView) findViewById(R.id.information_chip_image);
        this.textView = (TextView) findViewById(R.id.information_chip_text);
    }

    public void setTextAndImage(String text, @DrawableRes int image) {

        textView.setText(text);

        Bitmap bm = BitmapFactory.decodeResource(getResources(), image);
        if (bm != null) {
            RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), bm);
            dr.setCornerRadius(Math.max(bm.getWidth(), bm.getHeight()) / 2.0f);
            iconView.setImageDrawable(dr);
        }
    }


}
