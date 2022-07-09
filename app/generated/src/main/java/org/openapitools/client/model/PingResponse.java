package org.openapitools.client.model;


import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description = "")
public class PingResponse  {
  
  @SerializedName("response")
  private String response = null;

  /**
   **/
  @ApiModelProperty(required = true, value = "")
  public String getResponse() {
    return response;
  }
  public void setResponse(String response) {
    this.response = response;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PingResponse pingResponse = (PingResponse) o;
    return (this.response == null ? pingResponse.response == null : this.response.equals(pingResponse.response));
  }

  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + (this.response == null ? 0: this.response.hashCode());
    return result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PingResponse {\n");
    
    sb.append("  response: ").append(response).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}
