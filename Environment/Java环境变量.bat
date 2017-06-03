@echo off

wmic ENVIRONMENT create name="JAVA_HOME",username="<system>",VariableValue="C:\Program Files\Java\jdk1.8.0_112"

wmic ENVIRONMENT create name="CLASSPATH",username="<system>",VariableValue=".;%%JAVA_HOME%%\lib;%%JAVA_HOME%%\lib\tools.jar"

wmic ENVIRONMENT where "name='Path' and username='<system>'" set VariableValue="%%JAVA_HOME%%\bin;%%JAVA_HOME%%\jre\bin;%path%"

pause