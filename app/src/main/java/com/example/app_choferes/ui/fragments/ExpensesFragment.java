package com.example.app_choferes.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.app_choferes.R;
import com.example.app_choferes.adapters.ExpenseTypeListAdapter;
import com.example.app_choferes.contracts.ExpensesFragmentContract;
import com.example.app_choferes.listeners.OnBackPressedListener;
import com.example.app_choferes.models.ExpenseType;
import com.example.app_choferes.models.User;
import com.example.app_choferes.presenter.ExpensesFragmentPresenterImp;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.app_choferes.constants.AppConstants.SELECT_PHOTO;
import static com.example.app_choferes.constants.AppConstants.TAKE_PHOTO;

public class ExpensesFragment extends BaseFragment<ExpensesFragmentContract.Presenter> implements OnBackPressedListener, ExpensesFragmentContract.View {

    private static final int MAX_ATTEMPT = 3;
    private Bitmap capturedImage;
    private User currentUser;

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
        int idTypeExpense = ((ExpenseType) spTypeExpenseList.getSelectedItem()).getId();
        Double amount = Double.parseDouble(etAmount.getText().toString());

        getPresenter().saveNewExpense(description, idTypeExpense, amount, capturedImage);
    }

    @OnClick(R.id.btn_camera)
    public void onClickBtnCamera() {
        this.getPresenter().openCamera();
    }

    @OnClick(R.id.btn_open_gallery)
    public void onClickBtnOpenGallery() {
        this.getPresenter().openGallery();
    }

    public static ExpensesFragment newInstance(User currentUser) {
        ExpensesFragment fragment = new ExpensesFragment();

        fragment.setCurrentUser(currentUser);

        return fragment;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        clContainer = (CoordinatorLayout) inflater.inflate(R.layout.expenses_fragment, container, false);
        unbinder = ButterKnife.bind(this, clContainer);

        faActivity.setOnBackPressedListener(this);
        this.initializeSpinnerExpenseTypes();

        return clContainer;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = null;
        capturedImage = null;
        int currentAttempt = 0;

        if (data != null) {
            if (requestCode == TAKE_PHOTO) {
                imageUri = Uri.parse(presenter.getPathImage());
            } else if (requestCode == SELECT_PHOTO) {
                imageUri = data.getData();
            }
            try {
                while (capturedImage == null && currentAttempt < MAX_ATTEMPT) {
                    capturedImage = MediaStore.Images.Media.getBitmap(this.getMainActivity().getContentResolver(), imageUri);
                    currentAttempt++;
                }
                if (capturedImage == null) {
                    showTemporalMsg("No fue posible cargar la imagen. Intente nuevamente");
                } else {
                    image.setImageURI(imageUri);
                }
            } catch (IOException ex) {
                Log.e("ExpensesFragment", ex.getMessage());
            } catch (NullPointerException ex) {
                Log.e("ExpensesFragment", ex.getMessage());
            }
        }
    }

    @Override
    public boolean doBack() {
        this.switchFragmentBack();
        return false;
    }

    @Override
    protected ExpensesFragmentContract.Presenter createPresenter() {
        return new ExpensesFragmentPresenterImp(this);
    }

    private void initializeSpinnerExpenseTypes() {
        getPresenter().loadExpenseTypes();
    }

    @Override
    public void loadExpenseTypes(List<ExpenseType> expenseTypes) {
        spTypeExpenseList.setAdapter(new ExpenseTypeListAdapter(this.getMainActivity(), R.layout.spinner_item_list, expenseTypes));
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
