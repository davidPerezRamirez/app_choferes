package com.example.app_choferes.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

import com.example.app_choferes.contracts.ExpensesFragmentContract;
import com.example.app_choferes.models.ExpenseType;
import com.example.app_choferes.service.QueriesRestAPIService;
import com.example.app_choferes.service.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.app_choferes.constants.AppConstants.TAKE_PHOTO;

public class ExpensesFragmentPresenterImp implements ExpensesFragmentContract.Presenter {

    private WeakReference<ExpensesFragmentContract.View> expensesView;
    private QueriesRestAPIService appService;
    private RetrofitService retrofitService;

    public ExpensesFragmentPresenterImp(ExpensesFragmentContract.View expensesFragment) {
        this.expensesView = new WeakReference<>(expensesFragment);
        this.retrofitService = new RetrofitService();
        this.appService = retrofitService.connectToHerokuApp();
    }

    private ExpensesFragmentContract.View getExpensesView() {
        return this.expensesView.get();
    }

    @Override
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        getExpensesView().startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    public void saveExpense(String description, int idTypeExpense, Double amount, Bitmap capturedImage) {
        File fileImage = this.createFileToImage(capturedImage);

        if (fileImage != null) {
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), fileImage);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", fileImage.getName(), fileReqBody);
            RequestBody responseBody = RequestBody.create(MultipartBody.FORM, "image-type");
            Call<ResponseBody> req = appService.saveImage(part, responseBody);

            getExpensesView().showProgressBar();
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    getExpensesView().hideProgressBar();
                    ResponseBody body = response.body();
                    //this.saveExpense({.....});
                    getExpensesView().switchFragmentBack();
                    getExpensesView().showTemporalMsg("Nuevo gasto guardado exitosamente");
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    getExpensesView().hideProgressBar();
                    getExpensesView().showTemporalMsg(t.getMessage());
                }
            });
        } else {
            getExpensesView().showTemporalMsg("Surgio un error al guardar la imagen. Intentelo nuevamente");
        }

    }

    public File createFileToImage(Bitmap capturedImage) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            //create a file to write bitmap data
            File file = new File(getExpensesView().getMainActivity().getCacheDir(), timeStamp + ".jpg");
            file.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            capturedImage.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

            return file;

        } catch (IOException ex) {
            getExpensesView().showTemporalMsg(ex.getMessage());
            return null;
        }
    }

    @Override
    public void loadExpenseTypes() {
        getExpensesView().showProgressBar();
        Call<List<ExpenseType>> users = appService.getExpenseTypes();
        users.enqueue(new Callback<List<ExpenseType>>() {
            @Override
            public void onResponse(Call<List<ExpenseType>> call, Response<List<ExpenseType>> response) {
                getExpensesView().hideProgressBar();
                getExpensesView().loadExpenseTypes(response.body());
            }

            @Override
            public void onFailure(Call<List<ExpenseType>> call, Throwable t) {
                getExpensesView().hideProgressBar();
                getExpensesView().showTemporalMsg("Ocurrió un error al obtener los tipos de gastos.");
            }
        });
    }

    @Override
    public void doUnsubscribe() {

    }
}
