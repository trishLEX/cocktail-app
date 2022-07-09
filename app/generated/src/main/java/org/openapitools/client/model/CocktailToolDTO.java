package org.openapitools.client.model;


import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class CocktailToolDTO  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("preview")
  private byte[] preview = null;

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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CocktailToolDTO cocktailToolDTO = (CocktailToolDTO) o;
    return (this.id == null ? cocktailToolDTO.id == null : this.id.equals(cocktailToolDTO.id)) &&
        (this.name == null ? cocktailToolDTO.name == null : this.name.equals(cocktailToolDTO.name)) &&
        (this.preview == null ? cocktailToolDTO.preview == null : this.preview.equals(cocktailToolDTO.preview));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.preview == null ? 0: this.preview.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CocktailToolDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  preview: ").append(preview).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
