# ![alt text](https://github.com/[username]/[reponame]/blob/[branch]/image.jpg?raw=true) Nexia Tutor
## A better way to learn for dyslexia

# What is Nexia Tutor?
Nexia tutor is ... ... ... ... ... .. .. .. .. .. .. .. .. .. .. 

- Teach Childeren
- Help them
- ✨Help them with magic✨


## Features

- Import a HTML file and watch it magically convert to Markdown
- Drag and drop images (requires your Dropbox account be linked)
- Import and save files from GitHub, Dropbox, Google Drive and One Drive
- Drag and drop markdown and HTML files into Dillinger
- Export documents as Markdown, HTML and PDF

Markdown is a lightweight markup language based on the formatting conventions
that people naturally use in email.
As [John Gruber] writes on the [Markdown site][df1]

> The overriding design goal for Markdown's
> formatting syntax is to make it as readable
> as possible. The idea is that a
> Markdown-formatted document should be
> publishable as-is, as plain text, without
> looking like it's been marked up with tags
> or formatting instructions.

This text you see here is *actually- written in Markdown! To get a feel
for Markdown's syntax, type some text into the left window and
watch the results in the right.

## Tech

Dillinger uses a number of open source projects to work properly:

- [Next JS] - Enables to create high-quality web applications
- [Spring boot] - Create stand-alone Spring applications.
- [KeyBert] - Keyword extraction technique that leverages BERT embeddings
- [Node.js] - cross-platform JavaScript runtime environment
- [Material UI] - great UI boilerplate for modern web apps

## Installation
##### Pre requriments
Before running our application some packages and modules needs to be installed:
- [Java] Please follow this video to download JDK 17 for java [Java 17 JDK] on windows 10
- Java Extensions are needed when developing using vs code please follow this [video](https://www.youtube.com/watch?v=dq1z9t03mXI&ab_channel=CodeWithArjun) and JUST apply their extensions Java Extensions
- [Python](https://www.python.org/) Follow this [video](https://www.youtube.com/watch?v=0QibxSdnWW4&ab_channel=AmitThinks) and download python v3.11.0
- [Git](https://git-scm.com/) Follow this [video](https://www.youtube.com/watch?v=cJTXh7g-uCM&ab_channel=AmitThinks) to download GIT
#### Git Clone
Navigate to the D:/ local drive by openning the terminal and writing this command
(You can use any directory you want but make sure you change it in upcoming commands)
While cloning the file please try to avoid long directories like (D:\folder1\folder2\folder3\Nexia-Tutor Clone) as it may cause some problems with running the kafka files
```sh
cd D:\
```
Then clone our file
```sh
git clone https://github.com/amr2006223/Nexia-Tutor.git
```

#### Backend Source Building 
Before running our backend servers we need to start kafka
To start Kafka First we need to open the terminal and navigate to the kafka folder
```sh
cd "D:\Nexia-Tutor\Kafka"
```
Then run this command
```sh
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
```
First wait and make sure the zookeeper service is running then open another terminal navigate to Kafka folder 
```sh
cd "D:\Nexia-Tutor\Kafka"
```
then run this command
```sh
.\bin\windows\kafka-server-start.bat .\config\server.properties
```
Do not close any of the terminals as they are important for running the application
. . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .

To run the backend servers we must first navigate to the main application and run it through vscode

First we will run eurekaserver so open vs code and locate the cloned folder then navigate to eurekaserver file then go to src->main->java->com->example->eurekaserver->eurekaserver and open the ConfigserverApplication.java and run it through visual studio code, wait for it to complete running before running another microservice

Same steps will be applies for eurekaserver, gateway, Nexia-Tutor and reportGeneration files

You'll have to wait for eureka server to run completely then u can run the rest of the files with any order

Now to run the other microservices open a new terminal in vscode and navigate to WebScraping folder

```sh
cd D:\Nexia-Tutor\WebScraping
```
install these dependecies
```sh
pip install flask flask_cors openai threading requests bs4 eureka
```
then run the application
```sh
python games_controller.py
```
same steps for ScreeningServiceAPI
```sh
cd D:\Nexia-Tutor\ScreeningServiceAPI
```
install these dependecies
```sh
pip install flask flask_cors pandas aiohttp scikit-learn
```
and run the app
```sh
python app.py
```
and lastly for KeywordExtractionAPI
```sh
cd D:\Nexia-Tutor\KeywordExtractionAPI
```
install these dependecies
```sh
pip install flask flask_cors keybert numpy PyPDF2 nltk scikit-learn networkx
```
and run the app
```sh
python app.py
```
#### FrontEnd Source Building
Nexia-Tutor Frontend requires [Node.js] v20.11.0+, [Material UI] v5.15.4+, [React JS] React 18 and [Next Js] v14.0.4+ to run.
Install the dependencies and start the server.
```sh
cd nexia-ai-tutor-frontend
npm i
npm run dev
```
Open any browser and go to this URL ["localhost:3000"](http://localhost:3000/)

## Docker
Not Yet

## License
MIU
Not Yet

[//]: # (These are reference links used in the body of this note and get stripped out when the markdown processor does its job. There is no need to format nicely because it shouldn't be seen. Thanks SO - http://stackoverflow.com/questions/4823468/store-comments-in-markdown-syntax)
   [Java 17 JDK]: <https://www.youtube.com/watch?v=cL4GcZ6GJV8&ab_channel=AmitThinks>
   [Java]: <https://www.java.com/en/>
   [Next JS]: <https://nextjs.org/>
   [React JS]: <https://react.dev/>
   [git-repo-url]: <https://github.com/amr2006223/Nexia-Tutor>
   [Spring boot]: <https://spring.io/>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [KeyBert]: <https://maartengr.github.io/KeyBERT/>
   [Material UI]: <https://mui.com/material-ui/>
   [Ace Editor]: <http://ace.ajax.org>
   [Node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]: <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
   [PlMe]: <https://github.com/joemccann/dillinger/tree/master/plugins/medium/README.md>
   [PlGa]: <https://github.com/RahulHP/dillinger/blob/master/plugins/googleanalytics/README.md>
