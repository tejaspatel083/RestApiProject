
package com.example.restapiproject.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Support {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("text")
    @Expose
    public String text;

}
