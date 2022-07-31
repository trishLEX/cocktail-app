package org.openapitools.client;

import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import org.openapitools.client.model.CocktailDTO;
import org.openapitools.client.model.CocktailIngredientDTO;
import org.openapitools.client.model.CocktailLightDTO;
import org.openapitools.client.model.CocktailNameDTO;
import org.openapitools.client.model.CocktailToolDTO;
import org.openapitools.client.model.IngredientDTO;
import org.openapitools.client.model.IngredientLightDTO;
import org.openapitools.client.model.IngredientNameDTO;
import org.openapitools.client.model.IngredientTypeDTO;
import org.openapitools.client.model.PagedCocktailLightResponse;
import org.openapitools.client.model.PingResponse;
import org.openapitools.client.model.SearchIngredientDTO;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtil {
  public static GsonBuilder gsonBuilder;

  static {
    gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create();
    gsonBuilder.serializeNulls();
    gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
  }

  public static Gson getGson() {
    return gsonBuilder.create();
  }

  public static String serialize(Object obj){
    return getGson().toJson(obj);
  }

  public static <T> T deserializeToList(String jsonString, Class cls){
    return getGson().fromJson(jsonString, getListTypeForDeserialization(cls));
  }

  public static <T> T deserializeToObject(String jsonString, Class cls){
    return getGson().fromJson(jsonString, getTypeForDeserialization(cls));
  }

  public static Type getListTypeForDeserialization(Class cls) {
    String className = cls.getSimpleName();
    
    if ("CocktailDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<CocktailDTO>>(){}.getType();
    }
    
    if ("CocktailIngredientDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<CocktailIngredientDTO>>(){}.getType();
    }
    
    if ("CocktailLightDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<CocktailLightDTO>>(){}.getType();
    }
    
    if ("CocktailNameDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<CocktailNameDTO>>(){}.getType();
    }
    
    if ("CocktailToolDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<CocktailToolDTO>>(){}.getType();
    }
    
    if ("IngredientDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<IngredientDTO>>(){}.getType();
    }
    
    if ("IngredientLightDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<IngredientLightDTO>>(){}.getType();
    }
    
    if ("IngredientNameDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<IngredientNameDTO>>(){}.getType();
    }
    
    if ("IngredientTypeDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<IngredientTypeDTO>>(){}.getType();
    }
    
    if ("PagedCocktailLightResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<PagedCocktailLightResponse>>(){}.getType();
    }
    
    if ("PingResponse".equalsIgnoreCase(className)) {
      return new TypeToken<List<PingResponse>>(){}.getType();
    }
    
    if ("SearchIngredientDTO".equalsIgnoreCase(className)) {
      return new TypeToken<List<SearchIngredientDTO>>(){}.getType();
    }
    
    return new TypeToken<List<Object>>(){}.getType();
  }

  public static Type getTypeForDeserialization(Class cls) {
    String className = cls.getSimpleName();
    
    if ("CocktailDTO".equalsIgnoreCase(className)) {
      return new TypeToken<CocktailDTO>(){}.getType();
    }
    
    if ("CocktailIngredientDTO".equalsIgnoreCase(className)) {
      return new TypeToken<CocktailIngredientDTO>(){}.getType();
    }
    
    if ("CocktailLightDTO".equalsIgnoreCase(className)) {
      return new TypeToken<CocktailLightDTO>(){}.getType();
    }
    
    if ("CocktailNameDTO".equalsIgnoreCase(className)) {
      return new TypeToken<CocktailNameDTO>(){}.getType();
    }
    
    if ("CocktailToolDTO".equalsIgnoreCase(className)) {
      return new TypeToken<CocktailToolDTO>(){}.getType();
    }
    
    if ("IngredientDTO".equalsIgnoreCase(className)) {
      return new TypeToken<IngredientDTO>(){}.getType();
    }
    
    if ("IngredientLightDTO".equalsIgnoreCase(className)) {
      return new TypeToken<IngredientLightDTO>(){}.getType();
    }
    
    if ("IngredientNameDTO".equalsIgnoreCase(className)) {
      return new TypeToken<IngredientNameDTO>(){}.getType();
    }
    
    if ("IngredientTypeDTO".equalsIgnoreCase(className)) {
      return new TypeToken<IngredientTypeDTO>(){}.getType();
    }
    
    if ("PagedCocktailLightResponse".equalsIgnoreCase(className)) {
      return new TypeToken<PagedCocktailLightResponse>(){}.getType();
    }
    
    if ("PingResponse".equalsIgnoreCase(className)) {
      return new TypeToken<PingResponse>(){}.getType();
    }
    
    if ("SearchIngredientDTO".equalsIgnoreCase(className)) {
      return new TypeToken<SearchIngredientDTO>(){}.getType();
    }
    
    return new TypeToken<Object>(){}.getType();
  }

  private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      return Base64.decode(json.getAsString(), Base64.NO_WRAP);
    }

    public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(Base64.encodeToString(src, Base64.NO_WRAP));
    }
  }

};
