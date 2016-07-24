# Finicity Client

This is a Java Finicity Client library for communicating with the [Finicity API](https://developer.finicity.com/admin/docs).

This client is currently usable, but the first fully-functional, stable release is still in development. You can,
however, clone the source and build it yourself. The `master` branch is pseudo-stable, and when an official release is
made available on Maven, we'll update this `README`.

## Building

Building with Gradle is easy. Simply clone the repository, then run:

```
./gradlew buildAll
```

This will generate both useful Javadoc for you as well as a fat JAR in build/libs, which you can include as a local
dependency in your own project.