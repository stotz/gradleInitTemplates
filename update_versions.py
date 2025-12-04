#!/usr/bin/env python3
"""
update_versions.py - Update all template libs.versions.toml from central versions.toml

Usage:
    ./update_versions.py [--dry-run] [--verbose]

This script reads versions.toml and updates all libs.versions.toml files
in the template directories.
"""

import re
import sys
from pathlib import Path

SCRIPT_DIR = Path(__file__).parent.resolve()
VERSIONS_FILE = SCRIPT_DIR / "versions.toml"


def parse_versions_toml(filepath: Path) -> dict[str, str]:
    """Parse versions.toml and extract version definitions."""
    versions = {}
    in_versions_section = False
    
    with open(filepath, "r", encoding="utf-8") as f:
        for line in f:
            line = line.strip()
            
            # Track sections
            if line == "[versions]":
                in_versions_section = True
                continue
            elif line.startswith("["):
                in_versions_section = False
                continue
            
            # Skip comments and empty lines
            if not line or line.startswith("#"):
                continue
            
            # Parse version entries in [versions] section
            if in_versions_section and "=" in line:
                match = re.match(r'^(\S+)\s*=\s*"([^"]+)"', line)
                if match:
                    key, value = match.groups()
                    versions[key] = value
    
    return versions


def update_libs_versions_toml(filepath: Path, versions: dict[str, str], 
                               dry_run: bool = False, verbose: bool = False) -> list[str]:
    """Update a libs.versions.toml file with versions from central catalog."""
    changes = []
    
    with open(filepath, "r", encoding="utf-8") as f:
        lines = f.readlines()
    
    new_lines = []
    in_versions_section = False
    
    for line in lines:
        stripped = line.strip()
        
        # Track sections
        if stripped == "[versions]":
            in_versions_section = True
            new_lines.append(line)
            continue
        elif stripped.startswith("["):
            in_versions_section = False
            new_lines.append(line)
            continue
        
        # Update version entries
        if in_versions_section and "=" in stripped and not stripped.startswith("#"):
            match = re.match(r'^(\S+)\s*=\s*"([^"]+)"(.*)$', stripped)
            if match:
                key, old_value, rest = match.groups()
                
                # Skip template variables like {{ kotlin_version }}
                if "{{" in old_value:
                    new_lines.append(line)
                    continue
                
                # Check if we have a new version for this key
                if key in versions and versions[key] != old_value:
                    new_value = versions[key]
                    # Preserve original indentation
                    indent = line[:len(line) - len(line.lstrip())]
                    new_line = f'{indent}{key} = "{new_value}"{rest}\n'
                    new_lines.append(new_line)
                    changes.append(f"  {key}: {old_value} -> {new_value}")
                    continue
        
        new_lines.append(line)
    
    # Write changes if not dry run
    if changes and not dry_run:
        with open(filepath, "w", encoding="utf-8") as f:
            f.writelines(new_lines)
    
    return changes


def main():
    dry_run = "--dry-run" in sys.argv
    verbose = "--verbose" in sys.argv or "-v" in sys.argv
    
    if not VERSIONS_FILE.exists():
        print(f"Error: {VERSIONS_FILE} not found")
        sys.exit(1)
    
    # Parse central versions
    versions = parse_versions_toml(VERSIONS_FILE)
    if verbose:
        print(f"Loaded {len(versions)} versions from {VERSIONS_FILE.name}")
        for k, v in sorted(versions.items()):
            print(f"  {k} = {v}")
        print()
    
    # Find all libs.versions.toml files
    template_dirs = [d for d in SCRIPT_DIR.iterdir() 
                     if d.is_dir() and not d.name.startswith(".")]
    
    total_changes = 0
    
    for template_dir in sorted(template_dirs):
        libs_file = template_dir / "gradle" / "libs.versions.toml"
        if not libs_file.exists():
            continue
        
        changes = update_libs_versions_toml(libs_file, versions, dry_run, verbose)
        
        if changes:
            rel_path = libs_file.relative_to(SCRIPT_DIR)
            action = "Would update" if dry_run else "Updated"
            print(f"{action} {rel_path}:")
            for change in changes:
                print(change)
            print()
            total_changes += len(changes)
    
    if total_changes == 0:
        print("All versions are up to date.")
    else:
        action = "would be made" if dry_run else "made"
        print(f"Total: {total_changes} changes {action}")


if __name__ == "__main__":
    main()
