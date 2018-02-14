package com.experiments.restaurantapp.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 19-Mar-17.
 * <p>
 * holds api client and accessor for {@link WebServices} for retrofit
 */
public class RestClient {

    /**
     * singleton is useful as need to build client at once
     */
    private static final RestClient instance = new RestClient();

    /**
     * for all web services defined in {@link WebServices} interface
     */
    private final WebServices webServices;


    /**
     * building everything in constructor as using singleton pattern
     */
    private RestClient() {
        //adding logging and key interceptors to have http logs in console and
        //sending key as query parameter in every request
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(ServerConfig.TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ServerConfig.TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(ServerConfig.TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging);

        //using gson as converter
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(ServerConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = builder.client(httpClient.build())
                .build();
        webServices = retrofit.create(WebServices.class);
    }

    /**
     * Gets instance of this class.
     *
     * @return the instance
     */
    public static RestClient getInstance() {
        return instance;
    }


    /**
     * Gets web services.
     *
     * @return the web services
     */
    public WebServices getWebServices() {
        return webServices;
    }
}
