# Band Service

## Container images with Cloud Native Buildpacks

"Cloud Native Buildpacks transform your application source code into images that can run on any cloud." ([buildpacks.io](https://buildpacks.io))

### Buildpacks with pack

The [pack CLI](https://buildpacks.io/docs/tools/pack/) is "a tool maintained by the Cloud Native Buildpacks project to support the use of buildpacks".

For AMD64 architectures:

```shell
pack build band-service \
    --builder paketobuildpacks/builder:base \
    --env BP_JVM_VERSION=17
```

For ARM64 architectures:

```shell
pack build band-service \
    --builder ghcr.io/thomasvitale/java-builder-arm64 \
    --env BP_JVM_VERSION=17 \
    --trust-builder
```

### Buildpacks with Spring Boot

[Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/container-images.html#container-images.buildpacks) includes support for Cloud Native Buildpacks directly for both Maven and Gradle.

```shell
./gradlew bootBuildImage
```

## Signing commits with Sigstore gitsign

Install [gitsgin](https://github.com/sigstore/gitsign):

```shell
brew install sigstore/tap/gitsign
```

Configure your application repository to enforce commit signing with gitsign:

```shell
cd <your_repository_path>
git config --local commit.gpgsign true  # Sign all commits
git config --local tag.gpgsign true  # Sign all tags
git config --local gpg.x509.program gitsign  # Use gitsign for signing
git config --local gpg.format x509  # gitsign expects x509 args
```

```shell
cd <your_repository_path>
# Sign all commits
git config --local commit.gpgsign true
# Sign all tags
git config --local tag.gpgsign true
# Use gitsign for signing
git config --local gpg.x509.program gitsign
# gitsign expects x509 args
git config --local gpg.format x509
```

The first time you commit code, a browser window will open and ask you to authenticate with Sigstore following the OIDC protocol. For example, you can authenticate via your GitHub account.

You can verify a commit as follows:

```shell
git verify-commit HEAD
```

## Software Bill of Materials (SBOMs) with Syft

You can generate a SBOM with [Syft](https://github.com/anchore/syft) as follows:

```shell
syft band-service
```

To include also the materials excluded from the final container representation, use this command:

```shell
syft band-service --scope all-layers
```

You can export SBOM in Syft, SPDX, and CycloneDX formats.

```shell
syft band-service -o cyclonedx-json
```

When using Paketo Buildpacks, Syft is already used as part of the build process and SBOMs are generated
for each layer. You can access the SBOMs per layer with the following command.

```shell
pack sbom download band-service --output-dir sboms
```

To learn more about Cloud Native Buildpacks and SBOMs:

* [Software Bill of Materials (SBOM)](https://paketo.io/docs/concepts/sbom/)
* [How to Access the Software Bill of Materials](https://paketo.io/docs/howto/sbom/)

## Vulnerability Scanning with Trivy

You can scan source code with [Trivy](https://trivy.dev) as follows:

```shell
trivy fs .
```

You can also scan a container image:

```shell
trivy image <image>
```

## Lock dependencies with Gradle

Gradle lets you lock all the dependencies in your project and fail a build is any of them is changed
outside the standard lifecycle.

You can generate/update the list of locked dependencies as follows:

```shell
./gradlew dependencies --write-locks
```

## Verify third-party artefacts with Gradle

You can use Gradle to verify checksums and signatures of all the dependencies used in your project.

First, run this command for Gradle to download checksums and signatures:

```shell
./gradlew --write-verification-metadata pgp,sha256
```

When building the project, Gradle will ensure the dependencies have not been tampered with:

```shell
./gradlew build
```
