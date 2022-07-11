# openapi-android-client

## Requirements

Building the API client library requires [Maven](https://maven.apache.org/) to be installed.

## Installation

To install the API client library to your local Maven repository, simply execute:

```shell
mvn install
```

To deploy it to a remote Maven repository instead, configure the settings of the repository and execute:

```shell
mvn deploy
```

Refer to the [official documentation](https://maven.apache.org/plugins/maven-deploy-plugin/usage.html) for more information.

### Maven users

Add this dependency to your project's POM:

```xml
<dependency>
    <groupId>org.openapitools</groupId>
    <artifactId>openapi-android-client</artifactId>
    <version>1.0.0</version>
    <scope>compile</scope>
</dependency>
```

### Gradle users

Add this dependency to your project's build file:

```groovy
compile "org.openapitools:openapi-android-client:1.0.0"
```

### Others

At first generate the JAR by executing:

    mvn package

Then manually install the following JARs:

- target/openapi-android-client-1.0.0.jar
- target/lib/*.jar

## Getting Started

Please follow the [installation](#installation) instruction and execute the following Java code:

```java

import org.openapitools.client.api.CocktailApi;

public class CocktailApiExample {

    public static void main(String[] args) {
        CocktailApi apiInstance = new CocktailApi();
        Integer id = null; // Integer | 
        try {
            CocktailDTO result = apiInstance.getCocktail(id);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling CocktailApi#getCocktail");
            e.printStackTrace();
        }
    }
}

```

## Documentation for API Endpoints

All URIs are relative to *http://51.250.75.34:8080*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*CocktailApi* | [**getCocktail**](docs/CocktailApi.md#getCocktail) | **GET** /cocktails/{id} | 
*CocktailApi* | [**getCocktailNames**](docs/CocktailApi.md#getCocktailNames) | **GET** /cocktails/names/{name} | 
*CocktailApi* | [**getCocktails**](docs/CocktailApi.md#getCocktails) | **GET** /cocktails | 
*CocktailApi* | [**getCocktailsByIngredient**](docs/CocktailApi.md#getCocktailsByIngredient) | **GET** /cocktails/ingredients/{id} | 
*CocktailApi* | [**getCocktailsByIngredients**](docs/CocktailApi.md#getCocktailsByIngredients) | **GET** /cocktails/ingredients | 
*DefaultApi* | [**ping**](docs/DefaultApi.md#ping) | **GET** /ping | 
*IngredientApi* | [**getIngredient**](docs/IngredientApi.md#getIngredient) | **GET** /ingredients/{id} | 
*IngredientApi* | [**getIngredientNames**](docs/IngredientApi.md#getIngredientNames) | **GET** /ingredients/names/{name} | 
*IngredientApi* | [**getIngredients**](docs/IngredientApi.md#getIngredients) | **GET** /ingredients | 
*IngredientApi* | [**getIngredientsByIds**](docs/IngredientApi.md#getIngredientsByIds) | **GET** /ingredients-by-id | 


## Documentation for Models

 - [CocktailDTO](docs/CocktailDTO.md)
 - [CocktailIngredientDTO](docs/CocktailIngredientDTO.md)
 - [CocktailLightDTO](docs/CocktailLightDTO.md)
 - [CocktailNameDTO](docs/CocktailNameDTO.md)
 - [CocktailToolDTO](docs/CocktailToolDTO.md)
 - [IngredientDTO](docs/IngredientDTO.md)
 - [IngredientLightDTO](docs/IngredientLightDTO.md)
 - [IngredientNameDTO](docs/IngredientNameDTO.md)
 - [PingResponse](docs/PingResponse.md)


## Documentation for Authorization

All endpoints do not require authorization.
Authentication schemes defined for the API:

## Recommendation

It's recommended to create an instance of `ApiClient` per thread in a multithreaded environment to avoid any potential issues.

## Author



