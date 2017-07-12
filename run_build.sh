echo ----------------------Building and testing backend----------------------
java -jar sl-build-scanner-1.9.487.jar -config -tokenfile sl_token.txt -appname "VisualCaptchaSelenium_PTC" -branchname origin/master -buildname "$1" -pi "com.kuhniverse.*"
mvn clean install  -Psealights_build

echo ----------------------Building, deploying webapp----------------------
npm i slnodejs
./node_modules/.bin/slnodejs config --tokenfile sl_token.txt --appname "VisualCaptchaSeleniumWeb_PTC" --branch "origin/master" --build "$1"
./node_modules/.bin/slnodejs build --tokenfile sl_token.txt --buildsessionidfile buildSessionId --resolveWithoutHash --instrumentForBrowsers --outputpath js_browser --workspacepath src/main/webapp_bak --scm git
rm -rf src/main/webapp
mkdir src/main/webapp
cp -r js_browser/* src/main/webapp

echo ----------------------Creating integration build----------------------
#---------------------------------------update deps.json before running this---------------------------------------
./node_modules/.bin/slnodejs config --tokenfile sl_token.txt --appname "VisualCaptcha" --branch "origin/master" --build "$1"
./node_modules/.bin/slnodejs build --tokenfile sl_token.txt --buildsessionidfile buildSessionId --resolveWithoutHash --dependenciesFile "deps.json" --failbuild true --workspacepath dummy --scm none

echo ----------------------Running backend server----------------------
mvn spring-boot:run
