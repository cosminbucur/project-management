# Project management
`scrum management tool`

# plan
task management - [jira board](https://freeminions.atlassian.net/jira/software/projects/DEV/boards/2)

documentation - [confluence](https://freeminions.atlassian.net/wiki/spaces/FM/pages/131171/Team+strategies)

- story mapping: /story-mapping.md
- backend tasks: /task-backend.md
- frontend tasks: /task-fronted.md
- class diagram: /project mgm.drawio

collaboration - [slack](freeminions.slack.com)

# design
design - [figma](https://www.figma.com/files/team/720697583435508813/free-minions?fuid=720697471832871306)

# write code
[git repository](https://github.com/cosminbucur/project-management)

# test
```mvn -T 4 clean test```

# run

- create mysql database: project-management
- use the run configuration: ProjectManagementApplication
- open http://localhost:8081/ in browser

# deploy

# monitor

## REST endpoints

Swagger 2 is used to document the endpoints. The swagger ui is located at:

http://localhost:8081/swagger-ui/#/

All rest controllers follow the API convention:

    POST    /{collection} (json body)
    GET     /{collection}
    GET     /{collection}/{id}
    PUT     /{collection}/{id} (json body)
    DELETE  /{collection}/{id}

## Logging

Logback is configured at:

    /src/main/resources/logback.xml

There are two appenders defined:

- console appender
- file appender

The log files are located at:

    /logs/app.log

## Custom banner

You can change the custom banner by editing the file:

    /src/main/resources/banner.txt
