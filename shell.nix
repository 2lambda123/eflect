{pkgs ? import <nixpkgs> {}}:

pkgs.mkShell {
  nativeBuildInputs = with pkgs; [jdk gradle gcc];
}

# vim:expandtab ts=2 sw=2
