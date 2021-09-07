# Orange: scrum management tool

## Backend

### create initial data
DbInit
- persist an admin user
- add initial data 

### error handler

### use webjars for bootstrap5
WebConfig

### security config
- allow /login and /register
- secure api
- use password encoder

### security
- use DaoAuthenticationProvider

### bypass login form for development
- use a filter to set authentication
- add in memory authentication to security config
- remove login form from security config

## Frontend

### add custom css
- create src/main/resources/static/css/main.css
- import in head of html:
```html
<link href="../../css/main.css" rel="stylesheet" th:href="@{/css/main.css}"/>
```

### add custom js
- create src/main/resources/static/js/main.js
- import in footer of html:
```html
<script src="../../js/main.js" type="text/javascript"></script>
```
### add images

### add icons


# Features



## Fun

### add favicon
- generate [favicon](https://favicon.io/favicon-generator/)
- add icon [example](https://www.baeldung.com/spring-boot-favicon)
  src/main/resources/static/favicon.ico
- allow icon in spring config

### add custom banner
- generate [banner](https://manytools.org/hacker-tools/ascii-banner/)
- add banner [example](https://www.baeldung.com/spring-boot-custom-banners)

### enable live reload
- add dependency
- install live reload extension
[live reload](https://chrome.google.com/webstore/detail/livereload/jnihajbhpnppcggbcgedagnkighmdlei?hl=en)
- configure Extensions > Manage extensions > LiveReload > Details 
  
      Allow access to file URLs
      Allow access on all sites
      Click icon to enable
  
- make change and recompile page
  
      Build > Recompile (CTRL+SHIFT+F9)
