# Contributing to Our Project

First off, thank you for considering contributing to our project! It's people like you that make the open-source community such a fantastic place. We welcome any form of contribution, from reporting a bug to submitting a feature request, and of course, writing code.

This document provides a set of guidelines for contributing to this project. These are mostly guidelines, not strict rules. Use your best judgment, and feel free to propose changes to this document in a pull request.

## Code of Conduct

This project and everyone participating in it is governed by our [Code of Conduct](CODE_OF_CONDUCT.md). By participating, you are expected to uphold this code. Please report unacceptable behavior.

## How Can I Contribute?

### Reporting Bugs

- **Ensure the bug was not already reported** by searching on GitHub under [Issues](https://github.com/vchhabra/a2a-sdk-jvm/issues).
- If you're unable to find an open issue addressing the problem, [open a new one](https://github.com/vchhabra/a2a-sdk-jvm/issues/new?assignees=&labels=bug%2C+needs-triage&template=bug_report.md&title=fix%3A+%5BBUG%5D+A+brief+and+clear+title+of+the+bug).
- Be sure to include a **title and clear description**, as much relevant information as possible, and a **code sample** or an **executable test case** demonstrating the expected behavior that is not occurring.

### Suggesting Enhancements

- **Ensure the enhancement was not already suggested** by searching on GitHub under [Issues](https://github.com/vchhabra/a2a-sdk-jvm/issues).
- If you're unable to find an open issue, [open a new one](https://github.com/vchhabra/a2a-sdk-jvm/issues/new?assignees=&labels=enhancement%2C+needs-triage&template=feature_request.md&title=feat%3A+A+brief+and+clear+title+of+the+feature).
- Provide a clear and detailed explanation of the proposed enhancement, including why it would be beneficial to the project.

### Your First Code Contribution

Unsure where to begin? You can start by looking through `good-first-issue` and `help-wanted` issues:

-   **Good First Issue** - issues which should only require a few lines of code, and a test or two.
-   **Help Wanted** - issues which should be a bit more involved than `good-first-issue` issues.

### Pull Requests

1.  **Fork the repository** and create your branch from `main`.
2.  **Set up your development environment.** You will need Java 17 and Gradle.
3.  **Make your changes.**
4.  **Add tests** for your changes. This is important so we don't break them in a future version.
5.  **Ensure the test suite passes** by running `./gradlew build`.
6.  **Format your commit messages** according to our [commit message guidelines](#commit-message-guidelines).
7.  **Create a pull request** to our `main` branch. Please follow the pull request template.

## Development Setup

1.  Fork the repository.
2.  Clone your fork: `git clone https://github.com/vchhabra/a2a-sdk-jvm.git`
3.  Navigate to the project directory: `cd a2a-sdk-jvm`
4.  Set up the commit message template: `git config commit.template .gitmessage`
5.  Install dependencies and build the project: `./gradlew build`

## Commit Message Guidelines

We use the [Conventional Commits](https://www.conventionalcommits.org/) specification for our commit messages. This allows for automated changelog generation and helps us keep the project history clean and easy to navigate.

The commit message should be structured as follows:

\`\`\`
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
\`\`\`

**Example:**

\`\`\`
feat: Add user authentication service

Implement a new service for handling user login and registration.
This includes password hashing and JWT generation.

Closes: #42
See-also: #35
\`\`\`

## Styleguides

Please follow the coding style of the existing codebase. We use Detekt for static analysis to enforce a consistent style. Your pull request will not be merged if `./gradlew build` fails.

---

Thank you again for your contribution!
