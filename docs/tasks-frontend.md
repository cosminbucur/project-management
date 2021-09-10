## Frontend tasks

### add boostrap with webjars
- add dependencies
```xml
<dependencies>
  <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>webjars-locator</artifactId>
      <version>${webjars-locator.version}</version>
  </dependency>
  <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>bootstrap</artifactId>
      <version>${bootstrap.version}</version>
  </dependency>
</dependencies>
```

- add resources

      WebConfig

- allow in spring security

      SecurityConfig - ignore resources, static and webjars folder

### add boostrap with webjars
- add this in header
```html
<link th:rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.0/css/bootstrap.min.css}"/>
```

- add this in footer
```html
<script th:src="@{/webjars/popper.js/2.9.3/umd/popper.min.js}"></script>
<script th:src="@{/webjars/bootstrap/5.1.0/js/bootstrap.min.js}"></script>
```

### add boostrap manually
- add this in header
```html
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
```

- add this in footer
```html
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.min.js"
        integrity="sha384-lpyLfhYuitXl2zRZ5Bn2fqnhNAKOAaM/0Kr9laMspuaMiZfGmfwRNFh8HlMy49eQ"
        crossorigin="anonymous"></script>
```

### add custom css
- create src/main/resources/static/css/main.css
- import in head of html:
```html
<link th:rel="stylesheet" th:href="@{/css/main.css}"/>
```

### add custom js
- create src/main/resources/static/js/main.js
- import in footer of html:
```html
<script th:src="@{/js/main.js}"></script>
```

### add local images
- create src/main/resources/static/img/image.png
```html
<img th:src="@{/img/users.png}"/>
```

### add remote images
```html
<img th:src="@{https://www.site.com/image.png}">
```

### add background image
```html
<body th:style="'background: url(https://www.site.com/image.png) no-repeat center center fixed;'">
```

### add icons
- add in header
```html
<!-- font awesome -->
<script src="https://kit.fontawesome.com/7c632ec9b0.js" crossorigin="anonymous"></script>
or
<link th:rel="stylesheet" th:href="@{https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.2/css/all.css}"/>
```

- use in html
```html
<i class="fas fa-address-book"></i>
```

### add dates
```html
<p th:text="${#temporals.format(localDate, 'MM-yyyy')}"></p>
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
