@echo off

echo Activating virtual environment...
echo ====================================================
call .\venv\Scripts\activate || exit /b 1

echo:
echo Running pip from location:
pip -V || exit /b 1

echo:
echo Installing dependencies...
echo ====================================================
pip install -r requirements.txt || exit /b 1

echo:
echo Running tests with coverage...
echo ====================================================
coverage run -m unittest discover || exit /b 1
coverage report

echo:
echo Running linter for source code
echo ====================================================
pylint leetcode
