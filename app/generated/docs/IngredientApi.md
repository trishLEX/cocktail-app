# IngredientApi

All URIs are relative to *http://51.250.75.34:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**getIngredient**](IngredientApi.md#getIngredient) | **GET** /ingredients/{id} | 
[**getIngredientNames**](IngredientApi.md#getIngredientNames) | **GET** /ingredients/names/{name} | 
[**getIngredients**](IngredientApi.md#getIngredients) | **GET** /ingredients | 
[**getIngredientsByIds**](IngredientApi.md#getIngredientsByIds) | **GET** /ingredients-by-id | 



## getIngredient

> IngredientDTO getIngredient(id)



### Example

```java
// Import classes:
//import org.openapitools.client.api.IngredientApi;

IngredientApi apiInstance = new IngredientApi();
Integer id = null; // Integer | 
try {
    IngredientDTO result = apiInstance.getIngredient(id);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngredientApi#getIngredient");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **id** | **Integer**|  | [default to null]

### Return type

[**IngredientDTO**](IngredientDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getIngredientNames

> List&lt;IngredientNameDTO&gt; getIngredientNames(name)



### Example

```java
// Import classes:
//import org.openapitools.client.api.IngredientApi;

IngredientApi apiInstance = new IngredientApi();
String name = null; // String | 
try {
    List<IngredientNameDTO> result = apiInstance.getIngredientNames(name);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngredientApi#getIngredientNames");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **name** | **String**|  | [default to null]

### Return type

[**List&lt;IngredientNameDTO&gt;**](IngredientNameDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getIngredients

> List&lt;IngredientLightDTO&gt; getIngredients(name, start, limit)



### Example

```java
// Import classes:
//import org.openapitools.client.api.IngredientApi;

IngredientApi apiInstance = new IngredientApi();
String name = null; // String | 
Integer start = null; // Integer | 
Integer limit = null; // Integer | 
try {
    List<IngredientLightDTO> result = apiInstance.getIngredients(name, start, limit);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngredientApi#getIngredients");
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

[**List&lt;IngredientLightDTO&gt;**](IngredientLightDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json


## getIngredientsByIds

> List&lt;IngredientLightDTO&gt; getIngredientsByIds(ids)



### Example

```java
// Import classes:
//import org.openapitools.client.api.IngredientApi;

IngredientApi apiInstance = new IngredientApi();
List<Integer> ids = null; // List<Integer> | 
try {
    List<IngredientLightDTO> result = apiInstance.getIngredientsByIds(ids);
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling IngredientApi#getIngredientsByIds");
    e.printStackTrace();
}
```

### Parameters


Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ids** | [**List&lt;Integer&gt;**](Integer.md)|  | [default to null]

### Return type

[**List&lt;IngredientLightDTO&gt;**](IngredientLightDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

