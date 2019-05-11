package com.example.app_choferes.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.app_choferes.R;
import com.example.app_choferes.ui.activities.BasicActivity;

public class FeedbackMessageDisplay {

    private Snackbar display;
    BasicActivity context;

    public FeedbackMessageDisplay(BasicActivity activity) {
        this.context = activity;
        this.display = null;
    }

    public void setContext(BasicActivity context) {
        this.context = context;
    }

    private Resources getResources() {
        return this.display.getView().getResources();
    }

    private Snackbar createSnackBar(String message, int duration) {
        int backgroundColor = Color.DKGRAY;
        View coordinatorLayout = this.context.getCoordinatorLayoutView();
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, "", duration);
        final View snackBarView = snackbar.getView();

        snackBarView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dimissDisplay();
                return false;
            }
        });
        snackBarView.setBackgroundColor(backgroundColor);
        TextView tv = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(ResourcesCompat.getColor(snackBarView.getResources(), R.color.inserted_item_order, null));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, snackBarView.getResources().getDimension(R.dimen.snackbar_font_size));
        tv.setText(message);
        tv.setMaxLines(5);

        return snackbar;
    }

    public void showTemporalMessage(String message) {
        this.display = createSnackBar(message, Snackbar.LENGTH_LONG);

        this.display.show();
    }

    public void dimissDisplay() {
        if (display != null) {
            this.display.dismiss();
        }
    }
}
