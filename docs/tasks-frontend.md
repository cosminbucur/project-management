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
    <dependency>
      <groupId>org.webjars</groupId>
      <artifactId>font-awesome</artifactId>
      <version>${font-awesome.version}</version>
    </dependency>
</dependencies>
```

- add resources

      WebConfig

- allow in spring security

      SecurityConfig - ignore resources, static and webjars folder

### add boostrap with webjars
- add in header
```html
<link th:rel="stylesheet" th:href="@{/webjars/bootstrap/5.1.0/css/bootstrap.min.css}"/>
```

- add in footer
```html
<script th:src="@{/webjars/popper.js/2.9.3/umd/popper.min.js}"></script>
<script th:src="@{/webjars/bootstrap/5.1.0/js/bootstrap.min.js}"></script>
```

### add boostrap with cdn
- add in header
```html
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-wEmeIV1mKuiNpC+IOBjI7aAzPcEZeedi5yW5f2yOq55WWLwNGmvvx4Um1vskeMj0" crossorigin="anonymous">
```

- add in footer
```html
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"
        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"
        crossorigin="anonymous"></script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.min.js"
        integrity="sha384-lpyLfhYuitXl2zRZ5Bn2fqnhNAKOAaM/0Kr9laMspuaMiZfGmfwRNFh8HlMy49eQ"
        crossorigin="anonymous"></script>
```

### add jquery with cdn

- add in header before bootstrap

```html
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
```

### add jquery with webjars

- add in header before bootstrap

```html

<script th:src="@{/webjars/jquery/3.6.0/jquery.min.js}"></script>
```

### add custom css

- create src/main/resources/static/css/main.css
- add in header

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
<img th:src="@{/images/users.png}"/>
```

### add remote images
```html
<img th:src="@{https://www.site.com/image.png}">
```

### add background image
```html
<body th:style="'background: url(https://www.site.com/image.png) no-repeat center center fixed;'">
```

### add font-awesome icons with webjars
- add in header
```html
<link th:rel="stylesheet" th:href="@{/webjars/font-awesome/5.15.4/css/all.min.css}"/>
```

### add font-awesome icons with cdn

- add in header

```html

<script src="https://kit.fontawesome.com/7c632ec9b0.js" crossorigin="anonymous"></script>
```

- use in html

```html
<i class="fas fa-address-book"></i>
```

### add google fonts

```html
<!-- google fonts -->
<link th:rel="stylesheet" th:href="@{https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap}"/>
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

### add bootstrap toasts


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
