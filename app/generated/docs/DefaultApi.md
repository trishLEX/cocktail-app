# DefaultApi

All URIs are relative to *http://51.250.75.34:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**ping**](DefaultApi.md#ping) | **GET** /ping | 



## ping

> PingResponse ping()



### Example

```java
// Import classes:
//import org.openapitools.client.api.DefaultApi;

DefaultApi apiInstance = new DefaultApi();
try {
    PingResponse result = apiInstance.ping();
    System.out.println(result);
} catch (ApiException e) {
    System.err.println("Exception when calling DefaultApi#ping");
    e.printStackTrace();
}
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**PingResponse**](PingResponse.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: application/json

