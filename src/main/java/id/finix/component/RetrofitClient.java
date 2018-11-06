package id.finix.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;


public class RetrofitClient<T> {

    protected Retrofit.Builder builder;

    protected Retrofit retrofit;

    protected OkHttpClient.Builder httpClient;

    protected HttpLoggingInterceptor logging;

    protected T service;

    public RetrofitClient(){

    }

    public RetrofitClient(String baseUrl) {
        init(baseUrl);

    }
    public RetrofitClient(String baseUrl, Class<T> clazz) {
        init(baseUrl);
        service = createService(clazz, null);
    }

    public RetrofitClient(String baseUrl, Class<T> tClass, Map<String, String> header) {
        init(baseUrl);
        service = createService(tClass, header);
    }

    protected void init(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        httpClient = new OkHttpClient.Builder();
        logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    protected <T> T createService(Class<T> serviceClass, Map<String, String> headers) {
        httpClient.interceptors().clear();
        httpClient.addInterceptor(logging);
        if (headers != null) {
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder builder1 = original.newBuilder();
                for (String key : headers.keySet()) {
                    builder1 = builder1.header(key, headers.get(key));
                }
                Request request = builder1.build();
                return chain.proceed(request);
            });
        }
        OkHttpClient client = httpClient
                .connectTimeout(180, TimeUnit.SECONDS)
                .readTimeout(180, TimeUnit.SECONDS)
                .build();
        builder.client(client);
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }

    protected void setHeader(Class<T> clazz, Map<String, String> header) {
        service = createService(clazz, header);
    }

}
