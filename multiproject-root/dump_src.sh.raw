#!/bin/bash

# version: 2.5.0 (colored output + full summary + help)

set -euo pipefail

# --- Color setup ---
if [[ -t 1 ]]; then
  RED="\033[0;31m"
  GREEN="\033[0;32m"
  YELLOW="\033[0;33m"
  BLUE="\033[0;34m"
  CYAN="\033[0;36m"
  BOLD="\033[1m"
  RESET="\033[0m"
else
  RED=""; GREEN=""; YELLOW=""; BLUE=""; CYAN=""; BOLD=""; RESET=""
fi

info()    { printf "${CYAN}%s${RESET}\n" "$*"; }
ok()      { printf "${GREEN}%s${RESET}\n" "$*"; }
warn()    { printf "${YELLOW}%s${RESET}\n" "$*"; }
error()   { printf "${RED}%s${RESET}\n" "$*"; }
section() { printf "\n${BOLD}${BLUE}==> %s${RESET}\n" "$*"; }

# --- HELP / USAGE ---
show_help() {
  cat <<EOF
dump_src.sh – Create a clean source archive using git ls-files + ignore list

USAGE:
  ./dump_src.sh
  ./dump_src.sh --dry-run
  ./dump_src.sh --help
  ./dump_src.sh --help_git_rev

OPTIONS:
  --help, -h
        Shows this help text.

  ./dump_src.sh
        Only git-tracked files are included by default (without dump_src.cfg)

  --dry-run
        Shows what would be archived, but does NOT create an archive.

  --help_git_rev
        Shows documentation for archiving a historical git revision.
        No repository state is modified.

CONFIG FILE (dump_src.cfg):
  This file is optional and may contain three variables:

  1) basename="project_name"
     Overrides the default archive base directory (project folder name).

  2) file_list=()
     List of files NOT tracked in git that should still be included.
     Paths are relative to the project root.

     Example:
       file_list=(
         local_config.yaml
         scripts/generate_report.py
       )

  3) ignore_list=()
     Regex patterns (bash [[ =~ ]] syntax) for files to exclude.
     Patterns match against git paths (relative paths).

     Example:
       ignore_list=(
         '^build/'
         '^\\.idea/'
         '^out/'
         '.*\\.iml$'
       )

FULL EXAMPLE (dump_src.cfg):

  basename="project_name"

  # additional
  file_list=(
    docs/TODO.md
  )

  ignore_list=(
    '^dump_src\\.(sh|cfg)$'
    '^build/'
    '^\\.idea/'
    '\\.iml$'
  )

NOTES:
  • git ls-files determines which files are included by default.
  • ignore_list patterns use Bash regex matching.
  • All archived paths start with "\$basename/..."
  • Extra files in file_list are added even if they are not in git.

EOF
}

help_git_rev() {
  cat <<EOF
dump_src.sh always works on the currently checked-out repository state.
It never modifies the repository (no checkout, no reset, no worktree).

If you need an archive of a historical git revision, prepare that revision
outside of your working repository and run dump_src.sh there.

Recommended procedure:

1. Create a temporary directory
2. Check out the desired commit, tag, or branch in that directory
3. Run dump_src.sh in that directory

Example:

mkdir tmp_git_rev
cd tmp_git_rev

git clone <repository-url> .
git checkout <commit|tag|branch>

./dump_src.sh

This keeps the working repository untouched and avoids any risk of
accidentally modifying your current work state.
EOF
}

# Handle command-line options
dry_run=false
if [[ $# -gt 0 ]]; then
  case "$1" in
    --help|-h|help)
      show_help
      exit 0
      ;;
    --help_git_rev)
      help_git_rev
      exit 0
      ;;
    --dry-run)
      dry_run=true
      ;;
    *)
      error "Unknown argument: $1"
      echo "Use --help for usage."
      exit 1
      ;;
  esac
fi

# --- Resolve script directory ---
SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do
  DIR="$( cd -P "$( dirname "$SOURCE" )" >/dev/null 2>&1 && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ "$SOURCE" != /* ]] && SOURCE="$DIR/$SOURCE"
done
SCRIPT_DIR="$( cd -P "$( dirname "$SOURCE" )" >/dev/null 2>&1 && pwd )"
cd "$SCRIPT_DIR"

project="$(basename "$SCRIPT_DIR")"
basename="$project"

file_list=()
ignore_list=()

# --- Load optional config ---
if [[ -f dump_src.cfg ]]; then
  section "Loading configuration"
  # shellcheck disable=SC1091
  source dump_src.cfg
fi

basename="${basename:-$project}"

# --- Ensure git repo ---
section "Checking repository"
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
  error "Not inside a git repository: $SCRIPT_DIR"
  exit 1
fi
ok "OK – inside git repository."

# --- ignore helper ---
is_ignored() {
  local fname="$1"
  for pat in "${ignore_list[@]:-}"; do
    if [[ "$fname" =~ $pat ]]; then
      return 0
    fi
  done
  return 1
}

# Stats
git_files_count=0
extra_requested_count=0
extra_found_count=0
extra_missing_count=0
ignored_count=0
archived_count=0

ignored_files=()
extra_files_found=()
extra_files_missing=()

# ------------------------------------------------------------------------------
# 1. Collect git files
# ------------------------------------------------------------------------------
section "Collecting git files"
mapfile -t git_files < <(git ls-files)
git_files_count="${#git_files[@]}"
info "Found ${git_files_count} tracked git files."

# ------------------------------------------------------------------------------
# 2. Collect extra files
# ------------------------------------------------------------------------------
section "Collecting extra files"
extra_files=()
for f in "${file_list[@]:-}"; do
  [[ -z "$f" ]] && continue
  extra_requested_count=$((extra_requested_count + 1))

  if [[ -f "$SCRIPT_DIR/$f" ]]; then
    extra_files+=("$f")
    extra_files_found+=("$f")
    extra_found_count=$((extra_found_count + 1))
  else
    extra_files_missing+=("$f")
    extra_missing_count=$((extra_missing_count + 1))
  fi
done

# ------------------------------------------------------------------------------
# 3. Filter + deduplicate
# ------------------------------------------------------------------------------
section "Filtering files"
declare -A seen
all_files=()

# git files
for f in "${git_files[@]}"; do
  [[ -z "$f" ]] && continue
  [[ ! -f "$f" ]] && continue

  if is_ignored "$f"; then
    ignored_files+=("$f")
    ignored_count=$((ignored_count + 1))
    continue
  fi

  all_files+=("$f")
  seen["$f"]=1
done

# extra files
for f in "${extra_files[@]}"; do
  [[ -z "$f" ]] && continue

  if is_ignored "$f"; then
    ignored_files+=("$f")
    ignored_count=$((ignored_count + 1))
    continue
  fi

  if [[ -z "${seen[$f]+x}" ]]; then
    all_files+=("$f")
    seen["$f"]=1
  fi
done

if [[ "${#all_files[@]}" -eq 0 ]]; then
  error "No files left to archive after filtering!"
  exit 1
fi

mapfile -t all_files_sorted < <(printf '%s\n' "${all_files[@]}" | sort)
archived_count="${#all_files_sorted[@]}"
ok "Using ${archived_count} files."

# ------------------------------------------------------------------------------
# 4. Build file list for tar
# ------------------------------------------------------------------------------
section "Preparing file list"

tmpfile="$(mktemp "${TMPDIR:-/tmp}/dump_src.XXXXXX")"

for f in "${all_files_sorted[@]}"; do
  echo "$basename/$f" >> "$tmpfile"
done

info "Temporary file list: $tmpfile"

# ------------------------------------------------------------------------------
# 5. DRY-RUN MODE
# ------------------------------------------------------------------------------
if [[ "$dry_run" == true ]]; then
  warn "DRY RUN – NO ARCHIVE CREATED!"
  section "Files that *would* be archived:"
  for f in "${all_files_sorted[@]}"; do
    echo "  $f"
  done

  section "Summary"
  echo "Archived files total: $archived_count"
  ok "Done (dry-run)."
  exit 0
fi

# ------------------------------------------------------------------------------
# 6. Create archive
# ------------------------------------------------------------------------------
section "Creating archive"

(
  cd "$SCRIPT_DIR/.."
  tar cjpSvf "$basename/$basename.tar.bz2" --files-from "$tmpfile"
)

archive_path="$SCRIPT_DIR/$basename.tar.bz2"
ok "Archive created: $archive_path"

rm -f "$tmpfile"

# ------------------------------------------------------------------------------
# 7. Summary
# ------------------------------------------------------------------------------
section "Summary"
sha256=$(sha256sum "$basename.tar.bz2" | awk '{print $1}')
printf "%s%s\n" "project: " "$project"
printf "%s%s\n" "archive: " "$basename.tar.bz2"
printf "%s%s\n" "sha256:  " "$sha256"
echo

printf "Tracked git files:       %d\n" "$git_files_count"
printf "Extra files requested:   %d\n" "$extra_requested_count"
printf "  -> found:              %d\n" "$extra_found_count"
printf "  -> missing:            %d\n" "$extra_missing_count"
printf "Ignored by patterns:     %d\n" "$ignored_count"
printf "Archived files total:    %d\n" "$archived_count"
echo

if (( extra_found_count > 0 )); then
  info "Extra files included from file_list:"
  for f in "${extra_files_found[@]}"; do
    printf "  - %s\n" "$f"
  done
  echo
fi

if (( extra_missing_count > 0 )); then
  warn "Missing extra files:"
  for f in "${extra_files_missing[@]}"; do
    printf "  - %s\n" "$f"
  done
  echo
fi

if (( ignored_count > 0 )); then
  info "Ignored files (by ignore_list):"
  for f in "${ignored_files[@]}"; do
    printf "  - %s\n" "$f"
  done
  echo
fi

ok "Done."
