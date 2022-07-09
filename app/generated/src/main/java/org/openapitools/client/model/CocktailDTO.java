package org.openapitools.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class CocktailDTO  {
  
  @SerializedName("id")
  private Integer id = null;
  @SerializedName("name")
  private String name = null;
  @SerializedName("image")
  private byte[] image = null;
  @SerializedName("ingredients")
  private List<CocktailIngredientDTO> ingredients = null;
  @SerializedName("tools")
  private List<CocktailToolDTO> tools = null;
  @SerializedName("instructions")
  private List<String> instructions = null;
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
  public List<CocktailIngredientDTO> getIngredients() {
    return ingredients;
  }
  public void setIngredients(List<CocktailIngredientDTO> ingredients) {
    this.ingredients = ingredients;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<CocktailToolDTO> getTools() {
    return tools;
  }
  public void setTools(List<CocktailToolDTO> tools) {
    this.tools = tools;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<String> getInstructions() {
    return instructions;
  }
  public void setInstructions(List<String> instructions) {
    this.instructions = instructions;
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
    CocktailDTO cocktailDTO = (CocktailDTO) o;
    return (this.id == null ? cocktailDTO.id == null : this.id.equals(cocktailDTO.id)) &&
        (this.name == null ? cocktailDTO.name == null : this.name.equals(cocktailDTO.name)) &&
        (this.image == null ? cocktailDTO.image == null : this.image.equals(cocktailDTO.image)) &&
        (this.ingredients == null ? cocktailDTO.ingredients == null : this.ingredients.equals(cocktailDTO.ingredients)) &&
        (this.tools == null ? cocktailDTO.tools == null : this.tools.equals(cocktailDTO.tools)) &&
        (this.instructions == null ? cocktailDTO.instructions == null : this.instructions.equals(cocktailDTO.instructions)) &&
        (this.description == null ? cocktailDTO.description == null : this.description.equals(cocktailDTO.description)) &&
        (this.tags == null ? cocktailDTO.tags == null : this.tags.equals(cocktailDTO.tags));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.id == null ? 0: this.id.hashCode());
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.image == null ? 0: this.image.hashCode());
    result = 31 * result + (this.ingredients == null ? 0: this.ingredients.hashCode());
    result = 31 * result + (this.tools == null ? 0: this.tools.hashCode());
    result = 31 * result + (this.instructions == null ? 0: this.instructions.hashCode());
    result = 31 * result + (this.description == null ? 0: this.description.hashCode());
    result = 31 * result + (this.tags == null ? 0: this.tags.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CocktailDTO {\n");
    
    sb.append("  id: ").append(id).append("\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  image: ").append(image).append("\n");
    sb.append("  ingredients: ").append(ingredients).append("\n");
    sb.append("  tools: ").append(tools).append("\n");
    sb.append("  instructions: ").append(instructions).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  tags: ").append(tags).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
