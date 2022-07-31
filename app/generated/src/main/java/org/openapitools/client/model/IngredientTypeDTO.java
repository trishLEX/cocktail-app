package org.openapitools.client.model;


import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class IngredientTypeDTO  {
  
  @SerializedName("name")
  private String name = null;
  public enum TypeEnum {
     GIN,  VODKA,  TEQUILA,  RUM,  WHISKEY,  BOURBON,  ABSINTHE,  COGNAC,  PORT_WINE,  SHERRY,  APERITIF,  LIQUOR,  VERMOUTH,  BITTER,  WINE,  BEER,  SYRUP,  NON_ALCOHOLIC,  HONEY,  DESSERT,  ADDITIVE,  GREENS,  MISC,  JAM,  JUICE,  SPICE,  DRIED_FRUIT,  DECORATION,  FRUIT,  VEGETABLE,  TEA,  NUT,  BERRY,  ICE, 
  };
  @SerializedName("type")
  private TypeEnum type = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public TypeEnum getType() {
    return type;
  }
  public void setType(TypeEnum type) {
    this.type = type;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IngredientTypeDTO ingredientTypeDTO = (IngredientTypeDTO) o;
    return (this.name == null ? ingredientTypeDTO.name == null : this.name.equals(ingredientTypeDTO.name)) &&
        (this.type == null ? ingredientTypeDTO.type == null : this.type.equals(ingredientTypeDTO.type));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.type == null ? 0: this.type.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IngredientTypeDTO {\n");
    
    sb.append("  name: ").append(name).append("\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
