package roff.importnew.rx.githubdemo.service;

import retrofit.http.GET;
import retrofit.http.Path;
import roff.importnew.rx.githubdemo.model.Github;
import rx.Observable;

/**
 * Created by wuyongbo on 16-3-10.
 */
public interface GithubService {
    String SERVICE_ENDPOINT = "https://api.github.com";

    @GET("/users/{login}")
    Observable<Github> getUser(@Path("login") String login);
}
