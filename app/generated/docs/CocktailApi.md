# CocktailApi

All URIs are relative to *http://51.250.75.34:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getAllCocktailsByIngredients**](CocktailApi.md#getAllCocktailsByIngredients) | **GET** /v2/cocktails/ingredients/search | 
[**getCocktail**](CocktailApi.md#getCocktail) | **GET** /cocktails/{id} | 
[**getCocktailNames**](CocktailApi.md#getCocktailNames) | **GET** /cocktails/names/{name} | 
[**getCocktails**](CocktailApi.md#getCocktails) | **GET** /cocktails | 
[**getCocktailsByIds**](CocktailApi.md#getCocktailsByIds) | **GET** /cocktails-by-id | 
[**getCocktailsByIngredient**](CocktailApi.md#getCocktailsByIngredient) | **GET** /cocktails/ingredients/{id} | 
[**getCocktailsByIngredients**](CocktailApi.md#getCocktailsByIngredients) | **GET** /cocktails/ingredients | 
[**getCocktailsByName**](CocktailApi.md#getCocktailsByName) | **GET** /v2/cocktails | 
[**saveCocktail**](CocktailApi.md#saveCocktail) | **POST** /cocktails | 



## getAllCocktailsByIngredients

> PagedCocktailLightResponse getAllCocktailsByIngredients(ingredientIds, start, limit)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
List<Integer> ingredientIds = null; // List<Integer> | 
Integer start = null; // Integer | 
Integer limit = null; // Integer | 
try {
    PagedCocktailLightResponse result = apiInstance.getAllCocktailsByIngredients(ingredientIds, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getAllCocktailsByIngredients");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ingredientIds** | [**List&lt;Integer&gt;**](Integer.md)|  | [default to null]
 **start** | **Integer**|  | [optional] [default to null]
 **limit** | **Integer**|  | [optional] [default to null]

### Return type

[**PagedCocktailLightResponse**](PagedCocktailLightResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getCocktail

> CocktailDTO getCocktail(id)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
Integer id = null; // Integer | 
try {
    CocktailDTO result = apiInstance.getCocktail(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getCocktail");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]

### Return type

[**CocktailDTO**](CocktailDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getCocktailNames

> List&lt;CocktailNameDTO&gt; getCocktailNames(name)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
String name = null; // String | 
try {
    List<CocktailNameDTO> result = apiInstance.getCocktailNames(name);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getCocktailNames");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [default to null]

### Return type

[**List&lt;CocktailNameDTO&gt;**](CocktailNameDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getCocktails

> List&lt;CocktailLightDTO&gt; getCocktails(name, start, limit)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
String name = null; // String | 
Integer start = null; // Integer | 
Integer limit = null; // Integer | 
try {
    List<CocktailLightDTO> result = apiInstance.getCocktails(name, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getCocktails");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [default to null]
 **start** | **Integer**|  | [optional] [default to null]
 **limit** | **Integer**|  | [optional] [default to null]

### Return type

[**List&lt;CocktailLightDTO&gt;**](CocktailLightDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getCocktailsByIds

> List&lt;CocktailLightDTO&gt; getCocktailsByIds(ids, start, limit)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
List<Integer> ids = null; // List<Integer> | 
Integer start = null; // Integer | 
Integer limit = null; // Integer | 
try {
    List<CocktailLightDTO> result = apiInstance.getCocktailsByIds(ids, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getCocktailsByIds");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | [**List&lt;Integer&gt;**](Integer.md)|  | [default to null]
 **start** | **Integer**|  | [optional] [default to null]
 **limit** | **Integer**|  | [optional] [default to null]

### Return type

[**List&lt;CocktailLightDTO&gt;**](CocktailLightDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getCocktailsByIngredient

> List&lt;CocktailLightDTO&gt; getCocktailsByIngredient(id, start, limit)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
Integer id = null; // Integer | 
Integer start = null; // Integer | 
Integer limit = null; // Integer | 
try {
    List<CocktailLightDTO> result = apiInstance.getCocktailsByIngredient(id, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getCocktailsByIngredient");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]
 **start** | **Integer**|  | [optional] [default to null]
 **limit** | **Integer**|  | [optional] [default to null]

### Return type

[**List&lt;CocktailLightDTO&gt;**](CocktailLightDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getCocktailsByIngredients

> List&lt;CocktailLightDTO&gt; getCocktailsByIngredients(ingredientIds, start, limit)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
List<Integer> ingredientIds = null; // List<Integer> | 
Integer start = null; // Integer | 
Integer limit = null; // Integer | 
try {
    List<CocktailLightDTO> result = apiInstance.getCocktailsByIngredients(ingredientIds, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getCocktailsByIngredients");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ingredientIds** | [**List&lt;Integer&gt;**](Integer.md)|  | [default to null]
 **start** | **Integer**|  | [optional] [default to null]
 **limit** | **Integer**|  | [optional] [default to null]

### Return type

[**List&lt;CocktailLightDTO&gt;**](CocktailLightDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getCocktailsByName

> PagedCocktailLightResponse getCocktailsByName(name, start, limit)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
String name = null; // String | 
Integer start = null; // Integer | 
Integer limit = null; // Integer | 
try {
    PagedCocktailLightResponse result = apiInstance.getCocktailsByName(name, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#getCocktailsByName");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [default to null]
 **start** | **Integer**|  | [optional] [default to null]
 **limit** | **Integer**|  | [optional] [default to null]

### Return type

[**PagedCocktailLightResponse**](PagedCocktailLightResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## saveCocktail

> saveCocktail(saveCocktailRequestDTO)



### Example

```java
// Import classes:
//import org.openapitools.client.api.CocktailApi;

CocktailApi apiInstance = new CocktailApi();
SaveCocktailRequestDTO saveCocktailRequestDTO = new SaveCocktailRequestDTO(); // SaveCocktailRequestDTO | 
try {
    apiInstance.saveCocktail(saveCocktailRequestDTO);
} catch (ApiException e) {
    System.err.println("Exception when calling CocktailApi#saveCocktail");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **saveCocktailRequestDTO** | [**SaveCocktailRequestDTO**](SaveCocktailRequestDTO.md)|  | [optional]

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

