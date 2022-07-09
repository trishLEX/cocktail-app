package org.openapitools.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class IngredientDTO  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("image")
  private byte[] image = null;
  @SerializedName("description")
  private String description = null;
  @SerializedName("tags")
  private List<String> tags = null;

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
  public byte[] getImage() {
    return image;
  }
  public void setImage(byte[] image) {
    this.image = image;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<String> getTags() {
    return tags;
  }
  public void setTags(List<String> tags) {
    this.tags = tags;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IngredientDTO ingredientDTO = (IngredientDTO) o;
    return (this.id == null ? ingredientDTO.id == null : this.id.equals(ingredientDTO.id)) &&
        (this.name == null ? ingredientDTO.name == null : this.name.equals(ingredientDTO.name)) &&
        (this.image == null ? ingredientDTO.image == null : this.image.equals(ingredientDTO.image)) &&
        (this.description == null ? ingredientDTO.description == null : this.description.equals(ingredientDTO.description)) &&
        (this.tags == null ? ingredientDTO.tags == null : this.tags.equals(ingredientDTO.tags));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.image == null ? 0: this.image.hashCode());
    result = 31 * result + (this.description == null ? 0: this.description.hashCode());
    result = 31 * result + (this.tags == null ? 0: this.tags.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class IngredientDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  image: ").append(image).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  tags: ").append(tags).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
