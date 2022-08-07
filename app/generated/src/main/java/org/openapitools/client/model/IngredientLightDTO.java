package org.openapitools.client.model;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class IngredientLightDTO  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("preview")
  private byte[] preview = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("type")
  private IngredientTypeDTO type = null;

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
  public byte[] getPreview() {
    return preview;
  }
  public void setPreview(byte[] preview) {
    this.preview = preview;
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
  public IngredientTypeDTO getType() {
    return type;
  }
  public void setType(IngredientTypeDTO type) {
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
    IngredientLightDTO ingredientLightDTO = (IngredientLightDTO) o;
    return (this.id == null ? ingredientLightDTO.id == null : this.id.equals(ingredientLightDTO.id)) &&
        (this.preview == null ? ingredientLightDTO.preview == null : this.preview.equals(ingredientLightDTO.preview)) &&
        (this.name == null ? ingredientLightDTO.name == null : this.name.equals(ingredientLightDTO.name)) &&
        (this.type == null ? ingredientLightDTO.type == null : this.type.equals(ingredientLightDTO.type));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.preview == null ? 0: this.preview.hashCode());
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.type == null ? 0: this.type.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IngredientLightDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  preview: ").append(preview).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
