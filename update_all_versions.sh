#!/bin/bash
# update_all_versions.sh
# Updates all template libs.versions.toml using gradleInit
#
# Process:
# 1. Replace @pin with @* to allow updates (only non-comment lines)
# 2. Run gradleInit versions --update --yes
# 3. Restore @pin constraints (only non-comment lines)

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"

for toml in "$SCRIPT_DIR"/*/gradle/libs.versions.toml; do
    if [[ -f "$toml" ]]; then
        template_dir=$(dirname "$(dirname "$toml")")
        template_name=$(basename "$template_dir")
        
        echo "==> Updating $template_name"
        
        # 1. Replace @pin with @* (only on non-comment lines)
        sed -i '/^[^#]/ s/@pin/@*/g' "$toml"
        
        # 2. Run gradleInit versions --update
        (cd "$template_dir" && gradleInit versions --update --yes --include-recent)
        
        # 3. Restore @pin (only on non-comment lines)
        sed -i '/^[^#]/ s/@\*/@pin/g' "$toml"
        
        echo
    fi
done

echo "Done!"
