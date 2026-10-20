{ pkgs, lib, config, inputs, ... }:

{
  # https://devenv.sh/packages/
  packages = [
    pkgs.cosign
    pkgs.gitsign
    pkgs.go-task
    pkgs.httpie
    pkgs.jq
    pkgs.pack
    pkgs.skopeo
    pkgs.slsa-verifier
  ];

  # https://devenv.sh/languages/
  languages.java = {
    enable = true;
    jdk.package = pkgs.graalvmPackages.graalvm-ce;
  };

  # See full reference at https://devenv.sh/reference/options/
}
