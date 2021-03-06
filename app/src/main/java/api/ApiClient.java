package api;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tapandtype.rutvik.ems.App;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rutvik on 12/3/2016 at 12:02 PM.
 */

public class ApiClient
{
    //private static final String API_BASE_URL = "http://saisamarthholidays.in/webservice/"; //"http://192.168.0.7/ems/webservice/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(final Context context)
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setLenient()
                .create();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(App.dir, 10 * 1024 * 1024)) // 10 MB
                .addInterceptor(provideOfflineCacheInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        if (retrofit == null)
        {
            final String baseUrl = PreferenceManager.getDefaultSharedPreferences(context)
                    .getString("host", "http://127.0.0.1/");

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Interceptor provideOfflineCacheInterceptor()
    {

        return new Interceptor()
        {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request request = chain.request();

                //int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build();

                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();

                return chain.proceed(request);

            }
        };

    }

}
