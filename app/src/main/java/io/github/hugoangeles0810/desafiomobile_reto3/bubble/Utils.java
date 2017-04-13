package io.github.hugoangeles0810.desafiomobile_reto3.bubble;

import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;

/**
 * Created by hugo on 11/04/17.
 */

public class Utils {

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static float dpToPxFloat(int dp) {
        return (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static ShapeDrawable getRoundedDrawable(float size, int backgroundColor) {
        int intSize = (int) size;
        OvalShape shape = new OvalShape();
        shape.resize(size, size);

        ShapeDrawable drawable = new ShapeDrawable(shape);
        drawable.setBounds(0, 0, intSize, intSize);
        drawable.getPaint().setColor(backgroundColor);

        return drawable;
    }

}
