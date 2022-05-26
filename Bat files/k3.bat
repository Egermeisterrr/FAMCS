@echo off
set A_COMP_PATH=\\FPMI-serv604\commons_stud\Fokin\A

if not exist %A_COMP_PATH%\k1\d goto bad_fin
if exist G: (net use G: /delete /y) > nul
net use G: %A_COMP_PATH%\k1\d > nul /y
goto good_fin

:bad_fin
echo Network resource not found
goto fin

:good_fin
echo The settings are correct

:fin
pause
exit