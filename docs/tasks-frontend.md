## Frontend tasks

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
```html
<!-- font awesome -->
<script src="https://kit.fontawesome.com/7c632ec9b0.js" crossorigin="anonymous"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css" rel="stylesheet" type="text/css" />

<i class="fas fa-address-book"></i>
```

### add favicon
- generate [favicon](https://favicon.io/favicon-generator/)
- add icon [example](https://www.baeldung.com/spring-boot-favicon)
  src/main/resources/static/favicon.ico
- allow icon in spring config

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
