package fr.machada.gathabaandroid;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by macha on 08/12/15.
 */
public class Utils {
    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }
}
