package com.example.app_choferes.presenter;

import com.example.app_choferes.contracts.LoginFragmentContract;
import com.example.app_choferes.models.QueryResponse;
import com.example.app_choferes.models.User;
import com.example.app_choferes.service.QueriesRestAPIService;
import com.example.app_choferes.service.RetrofitService;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragmentPresenterImp implements LoginFragmentContract.Presenter {

    private QueriesRestAPIService appService;
    private RetrofitService retrofitService;
    private WeakReference<LoginFragmentContract.View> loginView;

    public LoginFragmentPresenterImp(LoginFragmentContract.View loginFragment) {
        this.loginView = new WeakReference<>(loginFragment);
        this.retrofitService = new RetrofitService();
        this.appService = retrofitService.connectToHerokuApp();
    }

    private LoginFragmentContract.View getLoginFragment() {
        return this.loginView.get();
    }

    @Override
    public void doLogin(String waiterCode) {

    }

    @Override
    public void doLogout() {

    }

    @Override
    public void validateUserLogin(String password) {
        getLoginFragment().showProgressBar();
        User selectedUser = getLoginFragment().getCurrentUser();
        Call<Map<String, String>> users = appService.validateUser(selectedUser.getId(), password);
        users.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                QueryResponse queryResponse = new QueryResponse(response.body());

                getLoginFragment().hideProgressBar();
                if (queryResponse.isSuccess()) {
                    getLoginFragment().navigateToListExpenseFragment();
                } else {
                    getLoginFragment().showFailMsg("No fue posible verificar la identidad del usuario. Intentelo nuevamente");
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                getLoginFragment().hideProgressBar();
                getLoginFragment().showFailMsg("Ocurrió un error validar el usuario. Intentelo nuevamente");
            }
        });
    }

    @Override
    public void doUnsubscribe() {

    }

    @Override
    public void getUsers() {
        /*TODO: El usuario que llega por parametro debe venir con el viaje actual que esta realizando*/
        getLoginFragment().showProgressBar();
        Call<List<User>> users = appService.userList();
        users.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                getLoginFragment().loadUsers(response.body());
                getLoginFragment().hideProgressBar();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                getLoginFragment().showFailMsg("No pudieron cargarse los usuarios");
            }
        });
    }
}
