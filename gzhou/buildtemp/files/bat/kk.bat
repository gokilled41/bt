@echo off
call afu %* ar "no path" "multiple lines"
call afu %* ar NoPath MultipleLines
call afu %* ar "No Path" "Multiple Lines"
call afu %* ar noPath multipleLines
call afu %* ar npr mlr
