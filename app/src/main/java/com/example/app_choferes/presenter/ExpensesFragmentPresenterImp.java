package com.example.app_choferes.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.example.app_choferes.contracts.ExpensesFragmentContract;
import com.example.app_choferes.models.ExpenseType;
import com.example.app_choferes.models.QueryResponse;
import com.example.app_choferes.models.User;
import com.example.app_choferes.service.QueriesRestAPIService;
import com.example.app_choferes.service.RetrofitService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.app_choferes.constants.AppConstants.SELECT_PHOTO;
import static com.example.app_choferes.constants.AppConstants.TAKE_PHOTO;

public class ExpensesFragmentPresenterImp implements ExpensesFragmentContract.Presenter {

    private WeakReference<ExpensesFragmentContract.View> expensesView;
    private QueriesRestAPIService appService;
    private RetrofitService retrofitService;
    private String mCurrentPhotoPath;

    public ExpensesFragmentPresenterImp(ExpensesFragmentContract.View expensesFragment) {
        this.expensesView = new WeakReference<>(expensesFragment);
        this.retrofitService = new RetrofitService();
        this.appService = retrofitService.connectToHerokuApp();
    }

    private ExpensesFragmentContract.View getExpensesView() {
        return this.expensesView.get();
    }

    @Override
    public String getPathImage() {
        return mCurrentPhotoPath;
    }

    @Override
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getExpensesView().getMainActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Foto nueva imagen", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                getExpensesView().startActivityForResult(intent, TAKE_PHOTO);
            }
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        getExpensesView().startActivityForResult(gallery, SELECT_PHOTO);
    }

    @Override
    public void saveNewExpense(final String description, final int idTypeExpense, final Double amount, Bitmap capturedImage) {
        File fileImage = this.createFileToImage(capturedImage);

        if (fileImage != null) {
            RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), fileImage);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", fileImage.getName(), fileReqBody);
            RequestBody responseBody = RequestBody.create(MultipartBody.FORM, "image-type");
            Call<Map<String, String>> req = appService.saveImage(part, responseBody);

            getExpensesView().showProgressBar();
            req.enqueue(new Callback<Map<String, String>>() {
                @Override
                public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                    getExpensesView().hideProgressBar();
                    QueryResponse queryResponse = new QueryResponse(response.body());
                    String nameImage = queryResponse.getValue("name");

                    saveExpense(description, idTypeExpense, amount, nameImage);
                }

                @Override
                public void onFailure(Call<Map<String, String>> call, Throwable t) {
                    getExpensesView().hideProgressBar();
                    getExpensesView().showTemporalMsg(t.getMessage());
                }
            });
        } else {
            getExpensesView().showTemporalMsg("Surgio un error al guardar la imagen. Intentelo nuevamente");
        }

    }

    private void saveExpense(String description, int idTypeExpense, Double amount, String nameImage) {
        getExpensesView().showProgressBar();
        User currentUser = getExpensesView().getCurrentUser();
        Call<Map<String, String>> users = appService.saveExpense(nameImage, idTypeExpense, description,
                amount.toString(), currentUser.getIdTravel());
        users.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                getExpensesView().hideProgressBar();
                QueryResponse queryResponse = new QueryResponse(response.body());

                if (queryResponse.isSuccess()) {
                    getExpensesView().showTemporalMsg("Nuevo gasto guardado exitosamente");
                    getExpensesView().switchFragmentBack();
                } else {
                    getExpensesView().showTemporalMsg("Ocurrió un error al obtener los tipos de gastos.");
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                getExpensesView().hideProgressBar();
                getExpensesView().showTemporalMsg("Ocurrió un error al obtener los tipos de gastos.");
            }
        });
    }

    public File createFileToImage(Bitmap capturedImage) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
            //create a file to write bitmap data
            File file = new File(getExpensesView().getMainActivity().getCacheDir(), timeStamp + ".jpg");
            file.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            capturedImage.compress(Bitmap.CompressFormat.JPEG, 20, bos);
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
