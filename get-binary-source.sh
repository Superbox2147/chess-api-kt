#!/bin/bash

mkdir -p ./build/binary
cd ./build/binary
git init >> /dev/null
# Define the repository URL
REPO_URL="https://github.com/shiro-nya/2025-chess-bot-tournament.git"

# Check if the remote origin already exists
if git remote get-url origin &> /dev/null; then
    echo "Remote 'origin' already exists. Skipping addition."
else
    # Add the remote origin
    git remote add origin "$REPO_URL"
    echo "Remote 'origin' added."
fi

git checkout main


