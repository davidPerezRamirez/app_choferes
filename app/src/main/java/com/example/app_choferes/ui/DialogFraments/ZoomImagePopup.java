package com.example.app_choferes.ui.DialogFraments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.app_choferes.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ZoomImagePopup extends DialogFragment {

    private Unbinder unbinder;
    private String urlPhoto;

    @OnClick(R.id.btn_close)
    public void onClickBtnClose() {
        dismiss();
    }
    @BindView(R.id.iv_zoom)
    ImageView ivZoom;

    public static ZoomImagePopup getInstance(String urlPhoto) {
        ZoomImagePopup popup = new ZoomImagePopup();

        popup.setUrlPhoto(urlPhoto);

        return popup;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.zoom_image_popup, null);
        unbinder = ButterKnife.bind(this, view);

        this.initialize();
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);

        Log.i("ZoomImage", "Mostrando vista de zoom de imagen");
        return dialog;
    }

    private void initialize() {
        Picasso.with(this.getActivity())
                .load(urlPhoto)
                .error(R.drawable.no_imagen)
                .rotate(90)
                .resize(800, 800)
                .into(ivZoom);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        Log.i("ZoomImage", "Cierra vista de zoom de imagen");
        super.onDestroyView();
    }
}
