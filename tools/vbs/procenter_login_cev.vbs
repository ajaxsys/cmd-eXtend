'Set objArgs = WScript.Arguments
'Select Case objArgs.Count
USAGE = "USAGE: this.vba pathOfProCenter password.(Manually login before first run)"
Set objShell = WScript.CreateObject("WScript.Shell")

if WScript.Arguments.Count < 2 then
    WScript.Echo USAGE
    WScript.Quit
end if

path = Wscript.Arguments(0)
psw  = Wscript.Arguments(1)

'PROCENTER Start
Set objExec = objShell.Exec(path)

'Use commonEvents from vbs
'Use commonEvents from cmd . Reference to : x-work/procenter_cev.cmd
objShell.Run "commonEvents.vbs    ""PROCENTER LITE Version 3""    %{P}"+psw+"{ENTER}    0    " 
objShell.Run "commonEvents.vbs    Topics                          {ENTER}               2000              {ENTER}               2000      " 


Set objShell=Nothing
