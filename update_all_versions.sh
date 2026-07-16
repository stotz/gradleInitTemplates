#!/bin/bash
# update_all_versions.sh
# Update all template version catalogs to the newest library versions.
#
# This is a thin wrapper around gradleInit's version SSoT tool
# (gradleInit/tools/version_sync.py). The SSoT is
#   gradleInit/versions/gradle/libs.versions.toml
# and it is the only place a version is decided. Never edit a template catalog
# by hand and never point 'gradleInit versions --update' at a template: that
# bypasses the SSoT and creates drift.
#
# Sequence:
#   1. version_sync --update   raise the SSoT from Maven Central / services.gradle.org,
#                              within each entry's maintenance constraint
#   2. version_sync --apply    write the SSoT into the derived locations
#                              (template catalogs, tool defaults, READMEs)
#   3. version_sync --check    verify nothing drifted
#
# Requires the gradleInit repository as a sibling of this repository.
#
# Usage:
#   ./update_all_versions.sh                 update SSoT (with prompt), apply, check
#   ./update_all_versions.sh --yes           same, without the confirmation prompt
#   ./update_all_versions.sh --include-recent also raise very fresh releases
#   ./update_all_versions.sh --check         read-only: report drift, change nothing

set -o pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"
VERSION_SYNC="$ROOT_DIR/gradleInit/tools/version_sync.py"

if command -v python3 >/dev/null 2>&1; then
    PYTHON=python3
elif command -v python >/dev/null 2>&1; then
    PYTHON=python
else
    echo "ERROR: no python interpreter found on PATH." >&2
    exit 1
fi

if [ ! -f "$VERSION_SYNC" ]; then
    echo "ERROR: version_sync.py not found at: $VERSION_SYNC" >&2
    echo "The gradleInit repository must be a sibling of this repository." >&2
    exit 1
fi

CHECK_ONLY=0
UPDATE_ARGS=()
for arg in "$@"; do
    case "$arg" in
        --check)          CHECK_ONLY=1 ;;
        --yes|-y)         UPDATE_ARGS+=("--yes") ;;
        --include-recent) UPDATE_ARGS+=("--include-recent") ;;
        *) echo "Unknown option: $arg" >&2
           echo "Use: --check | --yes | --include-recent" >&2
           exit 2 ;;
    esac
done

sync() {
    "$PYTHON" "$VERSION_SYNC" --root "$ROOT_DIR" "$@"
}

if [ "$CHECK_ONLY" -eq 1 ]; then
    echo "==> Checking SSoT consistency (read-only)"
    sync --check
    exit $?
fi

echo "==> Raising the SSoT to the newest allowed versions"
if ! sync --update "${UPDATE_ARGS[@]}"; then
    echo "ERROR: version_sync --update failed; nothing was applied." >&2
    exit 1
fi

echo
echo "==> Applying the SSoT to templates, tool defaults and READMEs"
if ! sync --apply; then
    echo "ERROR: version_sync --apply reported unresolved issues." >&2
    exit 1
fi

echo
echo "==> Verifying"
if ! sync --check; then
    echo "ERROR: drift remains after apply." >&2
    exit 1
fi

echo
echo "Done. Review the changes in both repositories, then commit."
