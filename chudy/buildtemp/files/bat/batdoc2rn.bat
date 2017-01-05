@echo off

call btenv

call al %btdirfrom%\buildtemp\files\bat\doc .txt np acp rn ##{n}-2.txt

call al bt\files\bat\doc .txt np acp rn
