package org.openapitools.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class CocktailLightDTO  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("preview")
  private byte[] preview = null;
  @SerializedName("missingIngredientsCount")
  private Integer missingIngredientsCount = null;
  @SerializedName("ingredients")
  private List<CocktailIngredientDTO> ingredients = null;

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

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
  public byte[] getPreview() {
    return preview;
  }
  public void setPreview(byte[] preview) {
    this.preview = preview;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public Integer getMissingIngredientsCount() {
    return missingIngredientsCount;
  }
  public void setMissingIngredientsCount(Integer missingIngredientsCount) {
    this.missingIngredientsCount = missingIngredientsCount;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<CocktailIngredientDTO> getIngredients() {
    return ingredients;
  }
  public void setIngredients(List<CocktailIngredientDTO> ingredients) {
    this.ingredients = ingredients;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CocktailLightDTO cocktailLightDTO = (CocktailLightDTO) o;
    return (this.id == null ? cocktailLightDTO.id == null : this.id.equals(cocktailLightDTO.id)) &&
        (this.name == null ? cocktailLightDTO.name == null : this.name.equals(cocktailLightDTO.name)) &&
        (this.preview == null ? cocktailLightDTO.preview == null : this.preview.equals(cocktailLightDTO.preview)) &&
        (this.missingIngredientsCount == null ? cocktailLightDTO.missingIngredientsCount == null : this.missingIngredientsCount.equals(cocktailLightDTO.missingIngredientsCount)) &&
        (this.ingredients == null ? cocktailLightDTO.ingredients == null : this.ingredients.equals(cocktailLightDTO.ingredients));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.preview == null ? 0: this.preview.hashCode());
    result = 31 * result + (this.missingIngredientsCount == null ? 0: this.missingIngredientsCount.hashCode());
    result = 31 * result + (this.ingredients == null ? 0: this.ingredients.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CocktailLightDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  preview: ").append(preview).append("\n");
    sb.append("  missingIngredientsCount: ").append(missingIngredientsCount).append("\n");
    sb.append("  ingredients: ").append(ingredients).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
