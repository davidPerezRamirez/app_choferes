package com.example.app_choferes.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.app_choferes.R;
import com.example.app_choferes.contracts.ExpensesFragmentContract;
import com.example.app_choferes.listeners.OnBackPressedListener;
import com.example.app_choferes.presenter.ExpensesFragmentPresenterImp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.app_choferes.constants.AppConstants.TAKE_PHOTO;

public class ExpensesFragment extends BaseFragment<ExpensesFragmentContract.Presenter> implements OnBackPressedListener, ExpensesFragmentContract.View {

    private Bitmap capturedImage;

    @BindView(R.id.clContainer)
    CoordinatorLayout clContainer;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.spTypeExpenseList)
    Spinner spTypeExpenseList;
    @BindView(R.id.etAmount)
    EditText etAmount;
    @BindView(R.id.etDescription)
    EditText etDescrption;
    @OnClick(R.id.btn_accept)
    public void onClickBtnAccept() {
        String description = etDescrption.getText().toString();
        /*TODO: El spinner tiene que devolver un TypeExpense*/
        String typeExpense = (String)spTypeExpenseList.getSelectedItem();
        Double amount = Double.parseDouble(etAmount.getText().toString());

        getPresenter().saveExpense(description, typeExpense, amount, capturedImage);
    }
    @OnClick(R.id.image)
    public void onClickImage() {
        image.setScaleX(10.0f);
        image.setScaleY(10.0f);
    }

    @OnClick(R.id.btn_camera)
    public void onClickBtnCamera() {
        this.getPresenter().openCamera();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clContainer = (CoordinatorLayout) inflater.inflate(R.layout.expenses_fragment, container, false);
        unbinder = ButterKnife.bind(this, clContainer);
        faActivity.setOnBackPressedListener(this);

        return clContainer;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO) {
            if (data != null) {
                if (data.hasExtra("data")) {
                    Bitmap capturedImage = data.getParcelableExtra("data");
                    capturedImage = rotateImage(capturedImage, 90);
                    image.setImageBitmap(capturedImage);
                }
            }
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();

        matrix.postRotate(degree);
        Bitmap resizedImage = Bitmap.createScaledBitmap(img, 400, 400, true);
        Bitmap rotatedImg = Bitmap.createBitmap(resizedImage, 0, 0, resizedImage.getWidth(), resizedImage.getHeight(), matrix, true);
        img.recycle();

        return rotatedImg;
    }

    @Override
    public boolean doBack() {
        showExitDialog();
        return false;
    }

    @Override
    protected ExpensesFragmentContract.Presenter createPresenter() {
        return new ExpensesFragmentPresenterImp(this);
    }
}
