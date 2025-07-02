import subprocess

IGNORED_PATTERNS = [
    ".github/",
    ".yml",
    ".yaml",
    "LICENSE",
    "CHANGELOG.md",
    "README.md",
    "version.txt",
]

def is_meaningful_file(file):
    return not any(pattern in file for pattern in IGNORED_PATTERNS)

def get_changed_files_and_messages():
    try:
        result = subprocess.run(
            ["git", "diff", "--name-only", "--diff-filter=ACMRTUXB", "HEAD~3"],
            capture_output=True,
            text=True,
            check=True
        )
        raw_files = result.stdout.strip().splitlines()
        print("[DEBUG] Raw diff files:", raw_files)
    except subprocess.CalledProcessError:
        raw_files = []
        print("[DEBUG] Git diff failed.")

    changed_files = []
    for f in raw_files:
        if is_meaningful_file(f):
            changed_files.append(f)
        else:
            print(f"[DEBUG] Filtered out: {f}")

    print("[DEBUG] Final files after filtering:", changed_files)

    file_to_message = {}
    for file in changed_files:
        try:
            message_result = subprocess.run(
                ["git", "log", "-1", "--pretty=%s", file],
                capture_output=True,
                text=True,
                check=True
            )
            message = message_result.stdout.strip()
        except subprocess.CalledProcessError:
            message = "Updated"
        file_to_message[file] = message

    return file_to_message

