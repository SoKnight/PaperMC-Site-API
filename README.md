[latestReleaseImg]: https://img.shields.io/maven-central/v/me.soknight/papermc-site-api?color=%23BA4CFF&label=Latest%20release&style=for-the-badge
[latestRelease]: https://search.maven.org/search?q=g:me.soknight%20a:papermc-site-api

[githubIssuesImg]: https://img.shields.io/github/issues-raw/SoKnight/PaperMC-Site-API?color=%23BA4CFF&logo=github&style=for-the-badge
[githubIssues]: https://github.com/SoKnight/PaperMC-Site-API/issues

[licenseImg]: https://img.shields.io/github/license/SoKnight/PaperMC-Site-API?color=%23BA4CFF&style=for-the-badge
[license]: https://github.com/SoKnight/PaperMC-Site-API/blob/main/LICENSE

# PaperMC Site API
The unofficial SDK for the PaperMC Site API written on Java.

[![latestReleaseImg]][latestRelease]<br>
[![githubIssuesImg]][githubIssues] [![licenseImg]][license]

## Dependency
This library is published to the central Maven repository.<br>
There are no third-party repository connecting required.

**NOTE:** Don't forget replace the `LATEST` stub to an actual latest release.
### Maven
An example how to use that as Maven dependency:
```xml
<dependencies>
    <!-- PaperMC Site API -->
    <dependency>
        <groupId>me.soknight</groupId>
        <artifactId>papermc-site-api</artifactId>
        <version>LATEST</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

### Gradle
An example how to use that as Gradle dependency:
```gradle
compile 'me.soknight:papermc-site-api:LATEST'
```

## Getting started
Before all you should make that a dependency for your project.<br>
You can see some examples for Maven and Gradle projects above it.

### Creating the API client instance
When your project will be ready for further actions, you should
create the API client instance firstly.<br>
You can use a client instance builder to configure the **user-agent** 
and any **timeouts** for further HTTP requests.

```java
PaperSiteApiClient client = PaperSiteApiClient.builder()
        .withUserAgent("MyApplication/1.0.0")
        .create();
```

### Examples of API methods usage
All PaperMC Site API methods are implemented by this SDK, for example:
```java
// get the project related data container
// for example, request info about the Paper project
Project project = client.getProjectInfo("paper");

// after that print the available versions list
System.out.printf("Available versions of the Paper project: %s%n", project.getVersions());
```

### Downloading a build artifact (JAR)
For example, lets download JAR artifact from the latest build of Paper 1.17.1.<br>
And save that to anyone file (e.g. `core.jar` in the current directory).
```java
// request a version info
Version version = client.getVersionInfo("paper", "1.17.1");

// find the latest build
OptionalInt lastBuild = version.getLastBuild();
if(lastBuild.isPresent()) {
    // request a build info
    int lastBuildNumber = lastBuild.getAsInt();
    Build buildInfo = client.getBuildInfo("paper", paperVersion, lastBuildNumber);

    // find the application download in the build instance
    Optional<BuildDownload> applicationDownload = buildInfo.getApplicationDownload();
    if(applicationDownload.isPresent()) {
        // extract the download file name and SHA256 hash
        String fileName = applicationDownload.get().getFileName();
        String fileHash = applicationDownload.get().getHashSha256();

        // get download callback from the download agent
        // and save download source as the local 'core.jar' file
        DownloadCallback callback = client.getDownloadAgent().download("paper", "1.17.1", lastBuildNumber, fileName);
        callback.saveTo(Paths.get("core.jar"));
        
        // TODO: compare hash of the downloaded file with a valid
    }
}
```

## License
This project is licensed under the [MIT License](https://github.com/SoKnight/PaperMC-Site-API/blob/main/LICENSE).
