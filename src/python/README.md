Welcome to the snek zone 🐍

# Prerequisites
Install Python 3 (required) and Pip (optional).

# Project Structure
All Python code in this repo lives within `src/python`. `src/python/tests` contains unit tests.

# Run unit tests without coverage
```bash
cd src/python
python3 -m unittest
```

# Installing dependencies for advanced build features
It is recommended to use [virtualenv](https://virtualenv.pypa.io/en/latest/) or a similar tool to isolate installed dependencies to this project. For simplicity's sake, you can use the virtual environment tool packaged with Python as below.
```bash
cd src/python
# create a virtual environment called venv
python3 -m venv venv
# activate the virtual environment
source venv/bin/activate
# install dependencies within the virtual environment
pip install -r requirements.txt
```

# Run a full build
The full build will install dependencies, run unit tests with coverage, and run a linter. It assumes you have created a virtual environment in a `src/python/venv` directory as above.
```bash
bash full_build
```

# Run unit tests with coverage
```bash
# collect coverage while running tests
coverage run -m unittest
# report the collected coverage stats
coverage report
```

# Run linter
```bash
# Scan all Python files under the current directory:
python3 -m pylint *
# Scan Python files in a specific module:
python3 -m pylint leetcode
```
