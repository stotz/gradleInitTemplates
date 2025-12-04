#!/bin/bash
# dump_src.sh - Dump source files for AI context
# Usage: ./dump_src.sh [output_file]

set -e

OUTPUT_FILE="${1:-src_dump.txt}"
CONFIG_FILE="dump_src.cfg"

if [ ! -f "$CONFIG_FILE" ]; then
    echo "Error: $CONFIG_FILE not found"
    exit 1
fi

echo "Dumping source files to $OUTPUT_FILE"
echo ""

> "$OUTPUT_FILE"

while IFS= read -r pattern || [ -n "$pattern" ]; do
    # Skip empty lines and comments
    [[ -z "$pattern" || "$pattern" =~ ^[[:space:]]*# ]] && continue
    
    # Find and dump matching files
    for file in $pattern; do
        if [ -f "$file" ]; then
            echo "=== $file ===" >> "$OUTPUT_FILE"
            cat "$file" >> "$OUTPUT_FILE"
            echo "" >> "$OUTPUT_FILE"
            echo "  Added: $file"
        fi
    done
done < "$CONFIG_FILE"

echo ""
echo "Done. Output written to $OUTPUT_FILE"
