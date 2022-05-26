@echo off
set A_COMP_PATH=\\FPMI-serv604\commons_stud\Fokin\A

if not exist %A_COMP_PATH%\k1\d goto bad_fin1
if exist G: (net use G: /delete /y) > nul
net use G: %A_COMP_PATH%\k1\d > nul /y
goto good_fin1

:bad_fin1
echo %A_COMP_PATH%\k1\d not found
goto fin1

:good_fin1
echo %A_COMP_PATH%\k1\d is correct

:fin1
if not exist %A_COMP_PATH%\k1\e goto bad_fin
if exist H: (net use H: /delete /y) > nul
net use H: %A_COMP_PATH%\k1\e > nul /y
goto good_fin

:bad_fin
echo %A_COMP_PATH%\k1\e not found
goto fin

:good_fin
echo %A_COMP_PATH%\k1\e is correct

:fin
pause
exit