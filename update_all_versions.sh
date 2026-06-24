#!/bin/bash
# update_all_versions.sh
# Update every template's gradle/libs.versions.toml to the newest library
# versions on Maven Central, using 'gradleInit versions --latest'.
#
# Only LITERAL library versions are touched (shadow, clikt, ktor, logback,
# spring-boot, junit, assertj, mockk, javafx, ikonli, ...). Templated
# placeholders (kotlin, jdk) stay placeholders: those are gradleInit tool
# defaults and are maintained via the gradleInit constants plus
# tools/version_sync.py, not here.
#
# Requires gradleInit with the 'versions --latest' flag (>= 1.12.4).
#
# Usage:
#   ./update_all_versions.sh            apply updates (writes the catalogs)
#   ./update_all_versions.sh --dry-run  preview only, change nothing

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

if ! command -v gradleInit >/dev/null 2>&1; then
    echo "ERROR: 'gradleInit' not found on PATH." >&2
    exit 1
fi

if ! gradleInit versions --help 2>&1 | grep -q -- '--latest'; then
    echo "ERROR: installed gradleInit has no 'versions --latest' (need >= 1.12.4)." >&2
    exit 1
fi

DRY_RUN=0
case "${1:-}" in
    --dry-run) DRY_RUN=1 ;;
    "")        ;;
    *) echo "Unknown option: $1 (use --dry-run or no argument)" >&2; exit 2 ;;
esac

processed=0
failed=0
for toml in "$SCRIPT_DIR"/*/gradle/libs.versions.toml; do
    [ -f "$toml" ] || continue
    template_dir=$(dirname "$(dirname "$toml")")
    template_name=$(basename "$template_dir")

    echo "==> $template_name"
    if [ "$DRY_RUN" -eq 1 ]; then
        ( cd "$template_dir" && gradleInit versions --latest --include-recent )
    else
        ( cd "$template_dir" && gradleInit versions --update --latest --include-recent --yes )
    fi
    if [ $? -ne 0 ]; then
        echo "    [WARN] gradleInit returned non-zero for $template_name"
        failed=$((failed + 1))
    fi
    processed=$((processed + 1))
    echo
done

echo "Done. Processed $processed template catalog(s), $failed with errors."
if [ "$DRY_RUN" -eq 1 ]; then
    echo "(dry-run: no files changed)"
fi
[ "$failed" -eq 0 ]
