REM Define your favorate editor.
REM SET DEFAULT_EDITOR=notepad.exe
SET DEFAULT_EDITOR=C:\Program Files\Hidemaru\Hidemaru.exe

REM Define your favorate search engine.
REM caret (^) character is an escape character for things like <, >, (, ), &, = etc.
SET SEARCH_ENGINE=http://www.google.co.jp/search?hl^=ja^&q^=

REM Define your own maven repo (Needed in x-java).
REM SET M2_REPO=%USERPROFILE%\.m2\repository\
SET M2_REPO=%~dp0x-java\java\target\dependency

REM Define your own groovy env (Needed in x-java).
SET GROOVY_ALL=%M2_REPO%\groovy-all-2.0.0.jar
SET CODEC_JAR=%M2_REPO%\commons-codec-1.5.jar

REM Define your derby path.
SET DERBY_INSTALL=C:\jdk1.7.0-b66\db

REM Use memory
SET JAVA_OPTS=-Xms128m -Xmx512m
