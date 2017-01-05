@echo off

call btenv

call al "%btdirfrom%\others\PA Operations.txt" acp rn "##PA Operations-2.txt"

call al "dd\PA Operations.txt" acp rn
