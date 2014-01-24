@echo off

call gsh %~dp0\eclipse_callhierarchy_result_merge.groovy

n d:\uResult.txt

:END