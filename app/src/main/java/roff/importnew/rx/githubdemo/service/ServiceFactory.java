package roff.importnew.rx.githubdemo.service;

import retrofit.RestAdapter;

/**
 * Created by wuyongbo on 16-3-10.
 */
public class ServiceFactory {
    public static <T> T createRetrofitService(final Class<T> clazz, final String endpoint) {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        T service = restAdapter.create(clazz);
        return service;
    }
}
