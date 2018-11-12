@echo off
for /l %%a in (10001,1,10010) do (
  echo java Program %%a
  start /b java Program %%a
)