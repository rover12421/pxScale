package com.rover12421.pxscale.myapp;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * Created by rover12421 on 12/17/14.
 */
public class ScaleView {

    private static int width = 0;
    private static int height = 0;

    private static void init(int width, int height) {
        ScaleView.width = width;
        ScaleView.height = height;
    }

    public static void init(Context context) {
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getHeightPixels(context);
        if(widthPixels > heightPixels) {
            init(widthPixels, heightPixels);
        } else {
            init(heightPixels, widthPixels);
        }
    }

    private static int getHeightPixels(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        Display display = windowManager.getDefaultDisplay();

        int rawHeight;
        try {
            rawHeight = (Integer) Display.class.getDeclaredMethod("getRawHeight", (Class[]) null).invoke(display);
        } catch (Exception e) {
            int realHeight;
            try {
                realHeight = (Integer) Display.class.getDeclaredMethod("getRealHeight", (Class[]) null).invoke(display);
            } catch (Exception e1) {
                try {
                    Method method = Display.class.getDeclaredMethod("getRealSize", Point.class);
                    Point point = new Point();
                    method.invoke(display, point);
                    if(point.y > heightPixels) {
                        return point.y;
                    }

                    return heightPixels;
                } catch (Exception e2) {
                    return heightPixels;
                }
            }

            if(realHeight > heightPixels) {
                return realHeight;
            }

            return heightPixels;
        }

        int height = rawHeight;
        if(rawHeight <= heightPixels) {
            height = heightPixels;
        }

        return height;
    }

    public static void scaleView(View view) {
        if(view != null) {
            resetViewSize(view);
            if(view instanceof ViewGroup) {
                scaleView((ViewGroup) view);
            }
        }
    }

    public static void scaleView(ViewGroup viewGroup) {
        for(int childId = -1 + viewGroup.getChildCount(); childId >= 0; --childId) {
            scaleView(viewGroup.getChildAt(childId));
        }

    }

    private static void resetViewSize(View view) {
        if(view == null) {
            Log.e("ScaleView", "child is null");
        } else {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if(layoutParams != null && layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams)layoutParams).leftMargin = resetWidth(((ViewGroup.MarginLayoutParams) layoutParams).leftMargin);
                ((ViewGroup.MarginLayoutParams)layoutParams).rightMargin = resetWidth(((ViewGroup.MarginLayoutParams) layoutParams).rightMargin);
                ((ViewGroup.MarginLayoutParams)layoutParams).topMargin = resetHeight(((ViewGroup.MarginLayoutParams) layoutParams).topMargin);
                ((ViewGroup.MarginLayoutParams)layoutParams).bottomMargin = resetHeight(((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
            }

            if(layoutParams != null) {
                if(layoutParams.height != -1 && layoutParams.height != -2) {
                    layoutParams.height = resetHeight(layoutParams.height);
                }

                if(layoutParams.width != -1 && layoutParams.width != -2) {
                    layoutParams.width = resetWidth(layoutParams.width);
                }
            }

            if(layoutParams != null) {
                view.setLayoutParams(layoutParams);
            }

            view.setPadding(resetWidth(view.getPaddingLeft()), resetHeight(view.getPaddingTop()), resetWidth(view.getPaddingRight()), resetHeight(view.getPaddingBottom()));
            if(view instanceof TextView) {
                ((TextView)view).setTextSize(0, resetTextSize(((TextView) view).getTextSize()));
            }
        }

    }

    public static float resetTextSize(float textSize) {
        return resetHeight(textSize);
    }

    private static float resetWidth(float w) {
        return w * (float) width / 1920.0F;
    }

    public static int resetWidth(int width) {
        return (int)resetWidth((float)width);
    }

    public static int resetHeight(int height) {
        return (int)resetHeight((float)height);
    }

    private static float resetHeight(float h) {
        return h * (float) height / 1080.0F;
    }
}
