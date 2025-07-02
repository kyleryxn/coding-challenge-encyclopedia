import os
import subprocess

def get_changed_files_and_messages():
    is_ci = os.getenv("GITHUB_ACTIONS") == "true"

    try:
        if is_ci:
            base_ref = os.getenv("GITHUB_BASE_REF") or "origin/main"
            diff_range = f"{base_ref}...HEAD"
            print(f"[DEBUG] CI mode: git diff {diff_range}")
            result = subprocess.run(
                ["git", "diff", "--name-only", "--diff-filter=ACMRTUXB", diff_range],
                capture_output=True,
                text=True,
                check=True
            )
        else:
            print("[DEBUG] Local mode: git diff HEAD~3")
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
