set ROOT_FILE=%CD%

set FILE_LIST=(API Engine Lib-Access ModCompat Modflags Prefabs)

for %%i in %FILE_LIST% do (
	cd %ROOT_FILE%\%%i
	git pull
	xcopy src ..\src\ /e /y /d
)




