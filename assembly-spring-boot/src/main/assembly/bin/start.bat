echo off

set APP_NAME=${project.build.finalName}.jar
set LOG_IMPL_FILE=logback-spring.xml
set LOGGING_CONFIG=
if exist ../config/%LOG_IMPL_FILE% (
    set LOGGING_CONFIG=-Dlogging.config=../config/%LOG_IMPL_FILE%
)
set CONFIG= -Dlogging.path=../logs %LOGGING_CONFIG% -Dspring.config.location=../config/

echo "Starting the %APP_NAME%"
java -Xms512m -Xmx512m %CONFIG% -jar ../lib/%APP_NAME%
echo "java -Xms512m -Xmx512m %CONFIG% -jar ../lib/%APP_NAME%"
goto end

:end
pause