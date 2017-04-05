set ROOT_FILE=%CD%

set FILE_LIST=(API Engine Lib-Access ModCompat Modflags Prefabs)

RMDIR /S /Q src

git submodule update --recursive --remote

for %%i in %FILE_LIST% do (
	cd %ROOT_FILE%\%%i
	xcopy src ..\src\ /e /y /d
)



PAUSE


