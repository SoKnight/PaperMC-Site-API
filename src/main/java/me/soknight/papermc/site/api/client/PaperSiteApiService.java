package me.soknight.papermc.site.api.client;

import me.soknight.papermc.site.api.data.model.*;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PaperSiteApiService {

    @GET("projects")
    @NotNull Call<Projects> getProjects();

    @GET("projects/{project}")
    @NotNull Call<Project> getProjectInfo(
            @Path("project") String projectId
    );

    @GET("projects/{project}/versions/{version}")
    @NotNull Call<Version> getVersionInfo(
            @Path("project") String projectId,
            @Path("version") String version
    );

    @GET("projects/{project}/versions/{version}/builds/{build}")
    @NotNull Call<Build> getBuildInfo(
            @Path("project") String projectId,
            @Path("version") String version,
            @Path("build") int buildNumber
    );

    @GET("projects/{project}/version_group/{family}")
    @NotNull Call<VersionGroup> getVersionGroupInfo(
            @Path("project") String projectId,
            @Path("family") String family
    );

    @GET("projects/{project}/version_group/{family}/builds")
    @NotNull Call<VersionGroupBuilds> getVersionGroupBuilds(
            @Path("project") String projectId,
            @Path("family") String family
    );

}
