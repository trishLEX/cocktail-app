package org.openapitools.client.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class SaveCocktailRequestDTO  {
  
  @SerializedName("name")
  private String name = null;
  @SerializedName("image")
  private byte[] image = null;
  @SerializedName("preview")
  private byte[] preview = null;
  @SerializedName("ingredients")
  private List<SaveIngredientRequestDTO> ingredients = null;
  @SerializedName("tools")
  private List<Integer> tools = null;
  @SerializedName("instructions")
  private List<String> instructions = null;
  @SerializedName("description")
  private String description = null;
  @SerializedName("tags")
  private List<String> tags = null;

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
  public byte[] getPreview() {
    return preview;
  }
  public void setPreview(byte[] preview) {
    this.preview = preview;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<SaveIngredientRequestDTO> getIngredients() {
    return ingredients;
  }
  public void setIngredients(List<SaveIngredientRequestDTO> ingredients) {
    this.ingredients = ingredients;
  }

  /**
   **/
  @ApiModelProperty(value = "")
  public List<Integer> getTools() {
    return tools;
  }
  public void setTools(List<Integer> tools) {
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
    SaveCocktailRequestDTO saveCocktailRequestDTO = (SaveCocktailRequestDTO) o;
    return (this.name == null ? saveCocktailRequestDTO.name == null : this.name.equals(saveCocktailRequestDTO.name)) &&
        (this.image == null ? saveCocktailRequestDTO.image == null : this.image.equals(saveCocktailRequestDTO.image)) &&
        (this.preview == null ? saveCocktailRequestDTO.preview == null : this.preview.equals(saveCocktailRequestDTO.preview)) &&
        (this.ingredients == null ? saveCocktailRequestDTO.ingredients == null : this.ingredients.equals(saveCocktailRequestDTO.ingredients)) &&
        (this.tools == null ? saveCocktailRequestDTO.tools == null : this.tools.equals(saveCocktailRequestDTO.tools)) &&
        (this.instructions == null ? saveCocktailRequestDTO.instructions == null : this.instructions.equals(saveCocktailRequestDTO.instructions)) &&
        (this.description == null ? saveCocktailRequestDTO.description == null : this.description.equals(saveCocktailRequestDTO.description)) &&
        (this.tags == null ? saveCocktailRequestDTO.tags == null : this.tags.equals(saveCocktailRequestDTO.tags));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.name == null ? 0: this.name.hashCode());
    result = 31 * result + (this.image == null ? 0: this.image.hashCode());
    result = 31 * result + (this.preview == null ? 0: this.preview.hashCode());
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
    sb.append("class SaveCocktailRequestDTO {\n");
    
    sb.append("  name: ").append(name).append("\n");
    sb.append("  image: ").append(image).append("\n");
    sb.append("  preview: ").append(preview).append("\n");
    sb.append("  ingredients: ").append(ingredients).append("\n");
    sb.append("  tools: ").append(tools).append("\n");
    sb.append("  instructions: ").append(instructions).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  tags: ").append(tags).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
